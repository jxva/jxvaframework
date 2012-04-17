#ifndef _EVENT_H_
#define _EVENT_H_

#include <fcntl.h>

#include <sys/stat.h>
#include <sys/types.h>
#include <sys/time.h>

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>

//#include <sys/queue.h>
#include  <queue.h>

#define        howmany(x, y)   (((x)+((y)-1))/(y))

#define EVLIST_TIMEOUT	0x01
#define EVLIST_READ	    0x02
#define EVLIST_WRITE	0x04
#define EVLIST_ADD	    0x08
#define EVLIST_INIT	    0x80

#define EV_TIMEOUT	0x01
/** Wait for a socket or FD to become readable */
#define EV_READ		0x02
/** Wait for a socket or FD to become writeable */
#define EV_WRITE	0x04
/** Wait for a POSIX signal to be raised*/
#define EV_SIGNAL	0x08
/**
 * Persistent event: won't get removed automatically when activated.
 *
 * When a persistent event with a timeout becomes activated, its timeout
 * is reset to 0.
 */
#define EV_PERSIST	0x10
/** Select edge-triggered behavior, if supported by the backend. */
#define EV_ET       0x20

struct event {
	// 事件所处队列的链接指针
	TAILQ_ENTRY (event) ev_read_next;
	TAILQ_ENTRY (event) ev_write_next;
	TAILQ_ENTRY (event) ev_timeout_next;
	TAILQ_ENTRY (event) ev_add_next;

	int ev_fd; // 与事件关联的文件描述符
	short ev_events; // 事件类型

	struct timeval ev_timeout; // 事件超时时间

	void (*ev_callback)(int, short, void *arg); // 时间达到时的回调函数
	void *ev_arg; // 回调函数的参数

	int ev_flags; // 事件所在的队列标志

};

#define TIMEOUT_DEFAULT	5

void event_init(void);
int event_dispatch(void);

int timeout_next(struct timeval *);
void timeout_process(void);

#define timeout_add(ev, tv)		    event_add(ev, tv)
#define timeout_set(ev, cb, arg)	event_set(ev, -1, 0, cb, arg)
#define timeout_del(ev)			    event_del(ev)
#define timeout_pending(ev, tv)		event_pending(ev, EV_TIMEOUT, tv)
#define timeout_initalized(ev)		((ev)->ev_flags & EVLIST_INIT)

void event_set(struct event *, int, short, void(*)(int, short, void *), void *);
void event_add(struct event *, struct timeval *);
void event_del(struct event *);

int event_pending(struct event *, short, struct timeval *);

#define event_initalized(ev)		((ev)->ev_flags & EVLIST_INIT)

#endif /* _EVENT_H_ */
