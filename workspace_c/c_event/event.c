#include <event.h>

/* Prototypes */
void event_add_post(struct event *);

//libevent定义了四种类型的队列（tail queue）
TAILQ_HEAD (timeout_list, event) timequeue; // 超时队列  其中timequeue这个定时器队列是按照结构体timeval从小到大的顺序进行排列的，后面增加事件时会提到。
TAILQ_HEAD (event_wlist, event) writequeue; // 写等待队列
TAILQ_HEAD (event_rlist, event) readqueue; // 读等待队列
TAILQ_HEAD (event_ilist, event) addqueue; // 添加队列

int event_inloop = 0; //锁。用于判断程序此时是否在轮询之中
int event_fds; /* Highest fd in fd set */
int event_fdsz; //size。用于计算event_fds总共要用多大空间
fd_set *event_readset; //对应于select的readset
fd_set *event_writeset; //对应于select的writeset

void event_init(void) {
	TAILQ_INIT(&timequeue);
	TAILQ_INIT(&writequeue);
	TAILQ_INIT(&readqueue);
	TAILQ_INIT(&addqueue);
}

/*
 * Called with the highest fd that we know about.  If it is 0, completely
 * recalculate everything.
 */

int events_recalc(int max) {
	fd_set *readset, *writeset;
	struct event *ev;
	int fdsz;

	event_fds = max;

	if (!event_fds) {
		TAILQ_FOREACH(ev, &writequeue, ev_write_next)
			if (ev->ev_fd > event_fds) {
				event_fds = ev->ev_fd;
			}

		TAILQ_FOREACH(ev, &readqueue, ev_read_next)
			if (ev->ev_fd > event_fds) {
				event_fds = ev->ev_fd;
			}
	}

	fdsz = howmany(event_fds + 1, NFDBITS) * sizeof(fd_mask);
	if (fdsz > event_fdsz) {
		if ((readset = realloc(event_readset, fdsz)) == NULL) {
			perror("malloc\n");
			return (-1);
		}

		if ((writeset = realloc(event_writeset, fdsz)) == NULL) {
			perror("malloc\n");
			free(readset);
			return (-1);
		}

		memset(readset + event_fdsz, 0, fdsz - event_fdsz);
		memset(writeset + event_fdsz, 0, fdsz - event_fdsz);

		event_readset = readset;
		event_writeset = writeset;
		event_fdsz = fdsz;
	}

	return (0);
}

int event_dispatch(void) {
	struct timeval tv;
	struct event *ev, *old;
	int res, maxfd;

	fprintf(stdout, "event dispatch\n");

	/* Calculate the initial events that we are waiting for */
	if (events_recalc(0) == -1) {
		return (-1);
	}

	while (1) {
		memset(event_readset, 0, event_fdsz);
		memset(event_writeset, 0, event_fdsz);

		TAILQ_FOREACH(ev, &writequeue, ev_write_next)
			FD_SET(ev->ev_fd, event_writeset);

		TAILQ_FOREACH(ev, &readqueue, ev_read_next)
			FD_SET(ev->ev_fd, event_readset);

		timeout_next(&tv);

		if ((res = select(event_fds + 1, event_readset, event_writeset, NULL,
				&tv)) == -1) {
			if (errno != EINTR) {
				perror("select\n");
				return (-1);
			}
			continue;
		}

		fprintf(stdout, "select reports %d\n", res);

		maxfd = 0;
		event_inloop = 1;
		for (ev = TAILQ_FIRST(&readqueue); ev;) {
			old = TAILQ_NEXT(ev, ev_read_next);
			if (FD_ISSET(ev->ev_fd, event_readset)) {
				event_del(ev);
				(*ev->ev_callback)(ev->ev_fd, EV_READ, ev->ev_arg);
			} else if (ev->ev_fd > maxfd) {
				maxfd = ev->ev_fd;
			}

			ev = old;
		}

		for (ev = TAILQ_FIRST(&writequeue); ev;) {
			old = TAILQ_NEXT(ev, ev_read_next);
			if (FD_ISSET(ev->ev_fd, event_writeset)) {
				event_del(ev);
				(*ev->ev_callback)(ev->ev_fd, EV_WRITE, ev->ev_arg);
			} else if (ev->ev_fd > maxfd) {
				maxfd = ev->ev_fd;
			}

			ev = old;
		}
		event_inloop = 0;

		for (ev = TAILQ_FIRST(&addqueue); ev; ev = TAILQ_FIRST(&addqueue)) {
			TAILQ_REMOVE(&addqueue, ev, ev_add_next);
			ev->ev_flags &= ~EVLIST_ADD;

			event_add_post(ev);

			if (ev->ev_fd > maxfd) {
				maxfd = ev->ev_fd;
			}
		}

		if (events_recalc(maxfd) == -1) {
			return (-1);
		}

		timeout_process();
	}

	return (0);
}

/*
 *ev         事件指针
 fd          IO文件描述符
 events      事件类型
 callback    事件回调函数
 arg         事件回调函数参数
 */
void event_set(struct event *ev, int fd, short events,
		void(*callback)(int, short, void *), void *arg) {
	ev->ev_callback = callback;
	ev->ev_arg = arg;
	ev->ev_fd = fd;
	ev->ev_events = events;
	ev->ev_flags = EVLIST_INIT;
}

/*
 * Checks if a specific event is pending or scheduled.
 */

int event_pending(struct event *ev, short event, struct timeval *tv) {
	int flags = ev->ev_flags;

	/*
	 * We might not have been able to add it to the actual queue yet,
	 * check if we will enqueue later.
	 */
	if (ev->ev_flags & EVLIST_ADD) {
		flags |= (ev->ev_events & (EV_READ | EV_WRITE));
	}

	event &= (EV_TIMEOUT | EV_READ | EV_WRITE);

	/* See if there is a timeout that we should report */
	if (tv != NULL && (flags & event & EV_TIMEOUT)) {
		*tv = ev->ev_timeout;
	}

	return (flags & event);
}

void event_add(struct event *ev, struct timeval *tv) {
	fprintf(stdout, "event_add: event: %p, %s%s%scall %p\n", ev,
			ev->ev_events & EV_READ ? "EV_READ " : " ",
			ev->ev_events & EV_WRITE ? "EV_WRITE " : " ",
			tv ? "EV_TIMEOUT " : " ", ev->ev_callback);
	if (tv != NULL) {
		struct timeval now;
		struct event *tmp;

		gettimeofday(&now, NULL);
		timeradd(&now, tv, &ev->ev_timeout);

		fprintf(stdout, "event_add: timeout in %d seconds, call %p\n",
				tv->tv_sec, ev->ev_callback);
		if (ev->ev_flags & EVLIST_TIMEOUT) {
			TAILQ_REMOVE(&timequeue, ev, ev_timeout_next);
		}

		/* Insert in right temporal order */
		for (tmp = TAILQ_FIRST(&timequeue); tmp; tmp =
				TAILQ_NEXT(tmp, ev_timeout_next)) {
			if (timercmp(&ev->ev_timeout, &tmp->ev_timeout, <= )) {
				break;
			}
		}

		if (tmp) {
			TAILQ_INSERT_BEFORE(tmp, ev, ev_timeout_next);
		} else {
			TAILQ_INSERT_TAIL(&timequeue, ev, ev_timeout_next);
		}

		ev->ev_flags |= EVLIST_TIMEOUT;
	}

	if (event_inloop) {
		/* We are in the event loop right now, we have to postpone the change until later.
		 */
		if (ev->ev_flags & EVLIST_ADD) {
			return;
		}

		TAILQ_INSERT_TAIL(&addqueue, ev, ev_add_next);
		ev->ev_flags |= EVLIST_ADD;
	} else {
		event_add_post(ev);
	}
}

void event_add_post(struct event *ev) {
	fprintf(stdout, "event add post\n");
	if ((ev->ev_events & EV_READ) && !(ev->ev_flags & EVLIST_READ)) {
		TAILQ_INSERT_TAIL(&readqueue, ev, ev_read_next);

		ev->ev_flags |= EVLIST_READ;
	}

	if ((ev->ev_events & EV_WRITE) && !(ev->ev_flags & EVLIST_WRITE)) {
		TAILQ_INSERT_TAIL(&writequeue, ev, ev_write_next);

		ev->ev_flags |= EVLIST_WRITE;
	}
}

void event_del(struct event *ev) {
	fprintf(stdout, "event_del: %p, callback %p\n", ev, ev->ev_callback);

	if (ev->ev_flags & EVLIST_ADD) {
		TAILQ_REMOVE(&addqueue, ev, ev_add_next);

		ev->ev_flags &= ~EVLIST_ADD;
	}

	if (ev->ev_flags & EVLIST_TIMEOUT) {
		TAILQ_REMOVE(&timequeue, ev, ev_timeout_next);

		ev->ev_flags &= ~EVLIST_TIMEOUT;
	}

	if (ev->ev_flags & EVLIST_READ) {
		TAILQ_REMOVE(&readqueue, ev, ev_read_next);

		ev->ev_flags &= ~EVLIST_READ;
	}

	if (ev->ev_flags & EVLIST_WRITE) {
		TAILQ_REMOVE(&writequeue, ev, ev_write_next);

		ev->ev_flags &= ~EVLIST_WRITE;
	}
}

int timeout_next(struct timeval *tv) {
	struct timeval now;
	struct event *ev;

	if ((ev = TAILQ_FIRST(&timequeue)) == NULL) {
		timerclear(tv);
		tv->tv_sec = TIMEOUT_DEFAULT;
		return (0);
	}

	if (gettimeofday(&now, NULL) == -1) {
		return (-1);
	}

	if (timercmp(&ev->ev_timeout, &now, <= )) {
		timerclear(tv);
		return (0);
	}

	timersub(&ev->ev_timeout, &now, tv);

	fprintf(stdout, "timeout_next: in %d seconds\n", tv->tv_sec);
	return (0);
}

void timeout_process(void) {
	struct timeval now;
	struct event *ev;

	gettimeofday(&now, NULL);

	while ((ev = TAILQ_FIRST(&timequeue)) != NULL) {
		if (timercmp(&ev->ev_timeout, &now, > )) {
			break;
		}

		TAILQ_REMOVE(&timequeue, ev, ev_timeout_next);
		ev->ev_flags &= ~EVLIST_TIMEOUT;

		fprintf(stdout, "timeout_process: call %p\n", ev->ev_callback);
		(*ev->ev_callback)(ev->ev_fd, EV_TIMEOUT, ev->ev_arg);
	}
}
