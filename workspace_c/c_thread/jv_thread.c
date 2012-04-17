#include <jv_thread.h>

/* the list of all created and not yet destroyed threads */
static jv_thread_t *jv_threads = NULL;

/* set of all signals */
static sigset_t fillset;

static pthread_mutex_t jv_thread_lock = PTHREAD_MUTEX_INITIALIZER;

static void *jv_worker_thread(void *arg);
static void jv_worker_cleanup(jv_thread_t *thread);
static jv_int_t jv_create_worker(jv_thread_t *thread);
static void jv_notify_waiters(jv_thread_t *thread);
static void *jv_job_thread_cleanup(jv_thread_t *thread);
static void jv_clone_attributes(pthread_attr_t *new_attr,
		pthread_attr_t *old_attr);

jv_thread_t *jv_thread_create(jv_pool_t *pool, jv_uint_t min, jv_uint_t max,
		jv_uint_t linger, pthread_attr_t *attr) {
	jv_thread_t *thread;
	(void) sigfillset(&fillset);
	if (min > max || max < 1) {
		errno = EINVAL;
		return NULL;
	}
	if ((thread = jv_pool_alloc(pool, sizeof(jv_thread_t))) == NULL) {
		errno = ENOMEM;
		return NULL;
	}
	(void) pthread_mutex_init(&thread->mutex, NULL);
	(void) pthread_cond_init(&thread->busycv, NULL);
	(void) pthread_cond_init(&thread->workcv, NULL);
	(void) pthread_cond_init(&thread->waitcv, NULL);
	thread->active = NULL;
	thread->head = NULL;
	thread->tail = NULL;
	thread->flag = 0;
	thread->linger = linger;
	thread->min = min;
	thread->max = max;
	thread->count = 0;
	thread->idle = 0;
	thread->pool = pool;

	/* We cannot just copy the attribute pointer.
	 * We need to initialize a new pthread_attr_t structure using
	 * the values from the caller-supplied attribute structure.
	 * If the attribute pointer is NULL, we need to initialize
	 * the new pthread_attr_t structure with default values. */
	jv_clone_attributes(&thread->attr, attr);
	/* insert into the global list of all thread threads */
	(void) pthread_mutex_lock(&jv_thread_lock);
	if (jv_threads == NULL) {
		thread->forw = thread;
		thread->back = thread;
		jv_threads = thread;
	} else {
		jv_threads->back->forw = thread;
		thread->forw = jv_threads;
		thread->back = jv_threads->back;
		jv_threads->back = thread;
	}
	(void) pthread_mutex_unlock(&jv_thread_lock);

	return thread;
}

jv_int_t jv_thread_queue(jv_thread_t *thread, void * (*func)(void *), void *arg) {
	jv_job_thread_t *job;
	if ((job = jv_pool_alloc(thread->pool, sizeof(jv_job_thread_t))) == NULL) {
		errno = ENOMEM;
		return JV_ERROR;
	}
	job->next = NULL;
	job->func = func;
	job->arg = arg;

	(void) pthread_mutex_lock(&thread->mutex);

	if (thread->head == NULL) {
		thread->head = job;
	} else {
		thread->tail->next = job;
	}
	thread->tail = job;

	if (thread->idle > 0) {
		(void) pthread_cond_signal(&thread->workcv);
	} else if (thread->count < thread->max && jv_create_worker(thread) == 0) {
		thread->count++;
	}
	(void) pthread_mutex_unlock(&thread->mutex);
	return (0);
}

void jv_thread_wait(jv_thread_t *thread) {
	(void) pthread_mutex_lock(&thread->mutex);
	pthread_cleanup_push ( (void (*) (void*)) pthread_mutex_unlock, &thread->mutex);
		while (thread->head != NULL || thread->active != NULL) {
			thread->flag |= JV_THREAD_WAIT;
			(void) pthread_cond_wait(&thread->waitcv, &thread->mutex);
		}
		pthread_cleanup_pop(1);
		/* pthread_mutex_unlock(&thread->mutex); */
}

void jv_thread_destroy(jv_thread_t *thread) {
	jv_active_thread_t *activep;
	jv_job_thread_t *jv_thread_job;

	(void) pthread_mutex_lock(&thread->mutex);
	pthread_cleanup_push ( (void (*) (void*)) pthread_mutex_unlock, &thread->mutex);

		/* mark the thread as being destroyed; wakeup idle workers */
		thread->flag |= JV_THREAD_DESTROY;
		(void) pthread_cond_broadcast(&thread->workcv);

		/* cancel all active workers */
		for (activep = thread->active; activep != NULL; activep = activep->next)
			(void) pthread_cancel(activep->tid);

		/* wait for all active workers to finish */
		while (thread->active != NULL) {
			thread->flag |= JV_THREAD_WAIT;
			(void) pthread_cond_wait(&thread->waitcv, &thread->mutex);
		}

		/* the last worker to terminate will wake us up */
		while (thread->count != 0)
			(void) pthread_cond_wait(&thread->busycv, &thread->mutex);

		pthread_cleanup_pop(1);
		/* pthread_mutex_unlock(&thread->mutex); */

		/*
		 * Unlink the thread from the global list of all threads.
		 */
	(void) pthread_mutex_lock(&jv_thread_lock);
	if (jv_threads == thread)
		jv_threads = thread->forw;
	if (jv_threads == thread)
		jv_threads = NULL;
	else {
		thread->back->forw = thread->forw;
		thread->forw->back = thread->back;
	}
	(void) pthread_mutex_unlock(&jv_thread_lock);

	/* There should be no pending jv_thread_jobs, but just in case... */
	for (jv_thread_job = thread->head; jv_thread_job != NULL; jv_thread_job =
			thread->head) {
		thread->head = jv_thread_job->next;
		jv_pool_free(thread->pool, jv_thread_job);
	}
	(void) pthread_attr_destroy(&thread->attr);
	jv_pool_free(thread->pool, thread);
}

static jv_int_t jv_create_worker(jv_thread_t *thread) {
	sigset_t oset;
	jv_int_t error;
	pthread_t pid;
	(void) pthread_sigmask(SIG_SETMASK, &fillset, &oset);
	error = pthread_create(&pid, &thread->attr, jv_worker_thread, thread);
	(void) pthread_sigmask(SIG_SETMASK, &oset, NULL);
	return error;
}

/*
 * Worker thread is terminating.  Possible reasons:
 * - excess idle thread is terminating because there is no work.
 * - thread was cancelled (thread is being destroyed).
 * - the job function called pthread_exit().
 * In the last case, create another worker thread
 * if necessary to keep the thread populated.
 */
static void jv_worker_cleanup(jv_thread_t *thread) {
	--thread->count;
	if (thread->flag & JV_THREAD_DESTROY) {
		if (thread->count == 0)
			(void) pthread_cond_broadcast(&thread->busycv);
	} else if (thread->head != NULL && thread->count < thread->max
			&& jv_create_worker(thread) == 0) {
		thread->count++;
	}
	(void) pthread_mutex_unlock(&thread->mutex);
}

static void jv_notify_waiters(jv_thread_t *thread) {
	if (thread->head == NULL && thread->active == NULL) {
		thread->flag &= ~JV_THREAD_WAIT;
		(void) pthread_cond_broadcast(&thread->waitcv);
	}
}

/* Called by a worker thread on return from a jv_thread_job. */
static void *jv_job_thread_cleanup(jv_thread_t *thread) {
	pthread_t my_tid = pthread_self();
	jv_active_thread_t *activep;
	jv_active_thread_t **activepp;

	(void) pthread_mutex_lock(&thread->mutex);
	for (activepp = &thread->active; (activep = *activepp) != NULL; activepp =
			&activep->next) {
		if (activep->tid == my_tid) {
			*activepp = activep->next;
			break;
		}
	}
	if (thread->flag & JV_THREAD_WAIT)
		jv_notify_waiters(thread);
	return NULL;
}

static void *jv_worker_thread(void *arg) {
	jv_thread_t *thread = (jv_thread_t *) arg;
	jv_int_t timedout;
	jv_job_thread_t *job;
	void * (*func)(void *);
	jv_active_thread_t active;
	timestruc_t ts;

	/*
	 * This is the worker's main loop.  It will only be left
	 * if a timeout occurs or if the thread is being destroyed.
	 */
	(void) pthread_mutex_lock(&thread->mutex);
	pthread_cleanup_push ( (void (*) (void*)) jv_worker_cleanup, thread);
		active.tid = pthread_self();
		for (;;) {
			/*
			 * We don't know what this thread was doing during
			 * its last jv_thread_job, so we reset its signal mask and
			 * cancellation state back to the initial values.
			 */
			(void) pthread_sigmask(SIG_SETMASK, &fillset, NULL);
			(void) pthread_setcanceltype(PTHREAD_CANCEL_DEFERRED, NULL);
			(void) pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, NULL);

			timedout = 0;
			thread->idle++;
			if (thread->flag & JV_THREAD_WAIT)
				jv_notify_waiters(thread);
			while (thread->head == NULL && !(thread->flag & JV_THREAD_DESTROY)) {
				if (thread->count <= thread->min) {
					(void) pthread_cond_wait(&thread->workcv, &thread->mutex);
				} else {
					(void) clock_gettime(CLOCK_REALTIME, &ts);
					ts.tv_sec += thread->linger;
					if (thread->linger
							== 0 || pthread_cond_timedwait (&thread->workcv, &thread->mutex, &ts) == ETIMEDOUT) {
						timedout = 1;
						break;
					}
				}
			}
			thread->idle--;
			if (thread->flag & JV_THREAD_DESTROY)
				break;
			if ((job = thread->head) != NULL) {
				timedout = 0;
				func = job->func;
				arg = job->arg;
				thread->head = job->next;
				if (job == thread->tail)
					thread->tail = NULL;
				active.next = thread->active;
				thread->active = &active;
				(void) pthread_mutex_unlock(&thread->mutex);
				pthread_cleanup_push ( (void (*) (void*)) jv_job_thread_cleanup, thread);
					jv_pool_free(thread->pool, job);
					(void) func(arg); /* Call the specified jv_thread_job function. */

					/*
					 *  If the jv_thread_job function calls pthread_exit(), the thread
					 *  calls jv_job_thread_cleanup(thread) and jv_worker_cleanup(thread);
					 *  the integrity of the thread is thereby maintained.
					 */
					pthread_cleanup_pop(1);
					/* jv_job_thread_cleanup(thread) */
			}
			if (timedout && thread->count > thread->min) {
				/*
				 * We timed out and there is no work to be done and
				 * the number of workers exceeds the minimum.
				 * Exit now to reduce the size of the thread.
				 */
				break;
			}
		}
		pthread_cleanup_pop(1);
		/* jv_worker_cleanup(thread) */
	return (NULL);
}

static void jv_clone_attributes(pthread_attr_t *new_attr,
		pthread_attr_t *old_attr) {
	struct sched_param param;
	void *addr;
	size_t size;
	jv_int_t value;

	(void) pthread_attr_init(new_attr);

	if (old_attr != NULL) {
		(void) pthread_attr_getstack(old_attr, &addr, &size);
		/* don't allow a non-NULL thread stack address */
		(void) pthread_attr_setstack(new_attr, NULL, size);

		(void) pthread_attr_getscope(old_attr, &value);
		(void) pthread_attr_setscope(new_attr, value);

		(void) pthread_attr_getinheritsched(old_attr, &value);
		(void) pthread_attr_setinheritsched(new_attr, value);

		(void) pthread_attr_getschedpolicy(old_attr, &value);
		(void) pthread_attr_setschedpolicy(new_attr, value);

		(void) pthread_attr_getguardsize(old_attr, &size);
		(void) pthread_attr_setguardsize(new_attr, size);

		(void) pthread_attr_getschedparam(old_attr, &param);
		(void) pthread_attr_setschedparam(new_attr, &param);
	}
	/* make all thread threads be detached threads */
	(void) pthread_attr_setdetachstate(new_attr, PTHREAD_CREATE_DETACHED);
}
