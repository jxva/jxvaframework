#ifndef _JV_THREAD_H_INCLUDED_
#define _JV_THREAD_H_INCLUDED_

#include <jv_core.h>
#include <jv_pool.h>

/* flag */
#define    JV_THREAD_WAIT       0x01        /* waiting in jv_thread_wait() */
#define    JV_THREAD_DESTROY    0x02        /* pool is being destroyed */

/*
 * The jv_thread_t type is opaque to the client.
 * It is created by jv_thread_create() and must be passed
 * unmodified to the remainder of the interfaces.
 */
typedef struct jv_thread_s jv_thread_t;

/* FIFO queued jv_job_thread_t */
typedef struct jv_job_thread_s jv_job_thread_t;

/* List of active worker threads, linked through their stacks. */
typedef struct jv_active_thread_s jv_active_thread_t;

struct jv_job_thread_s {
	jv_job_thread_t *next; /* linked list of job threads */
	void *(*func)(void *); /* function to call */
	void *arg; /* its argument */
};

struct jv_active_thread_s {
	jv_active_thread_t *next; /* linked list of threads */
	pthread_t tid; /* active thread id */
};

/* The thread pool, opaque to the clients. */
struct jv_thread_s {
	jv_thread_t *forw; /* circular linked list */
	jv_thread_t *back; /* of all thread pools */
	pthread_mutex_t mutex; /* protects the pool data */
	pthread_cond_t busycv; /* synchronization in thread_queue */
	pthread_cond_t workcv; /* synchronization with workers */
	pthread_cond_t waitcv; /* synchronization in thread_wait() */
	jv_active_thread_t *active; /* list of threads performing work */
	jv_job_thread_t *head; /* head of FIFO jv_thread_job queue */
	jv_job_thread_t *tail; /* tail of FIFO jv_thread_job queue */
	pthread_attr_t attr; /* attributes of the workers */
	jv_int_t flag; /* see below */
	jv_uint_t linger; /* seconds before idle workers exit */
	jv_int_t min; /* minimum number of worker threads */
	jv_int_t max; /* maximum number of worker threads */
	jv_int_t count; /* current number of worker threads */
	jv_int_t idle; /* number of idle workers */
	jv_pool_t *pool;
};

/*
 * Create a thread pool.
 *    min:    the minimum number of threads kept in the pool,
 *            always available to perform work requests.
 *    max:    the maximum number of threads that can be
 *            in the pool, performing work requests.
 *    linger: the number of seconds excess idle worker threads
 *            (greater than min_threads) linger before exiting.
 *    attr:   attributes of all worker threads (can be NULL);
 *            can be destroyed after calling jv_thread_create().
 * On error, jv_thread_create() returns NULL with errno set to the error code.
 */
jv_thread_t *jv_thread_create(jv_pool_t *pool, jv_uint_t min, jv_uint_t max,
		jv_uint_t linger, pthread_attr_t *attr);

/*
 * Enqueue a work request to the thread pool job queue.
 * If there are idle worker threads, awaken one to perform the job.
 * Else if the maximum number of workers has not been reached,
 * create a new worker thread to perform the job.
 * Else just return after adding the job to the queue;
 * an existing worker thread will perform the job when
 * it finishes the job it is currently performing.
 *
 * The job is performed as if a new detached thread were created for it:
 *    pthread_create(&pid, attr, void *(*func)(void *), void *arg);
 *
 * On error, jv_thread_queue() returns -1 with errno set to the error code.
 */
jv_int_t jv_thread_queue(jv_thread_t *thread, void * (*func)(void *), void *arg);

/* Wait for all queued jobs to complete. */
void jv_thread_wait(jv_thread_t *thread);

/* Cancel all queued jobs and destroy the pool. */
void jv_thread_destroy(jv_thread_t *thread);

#endif /* _JV_THREAD_H_INCLUDED_ */
