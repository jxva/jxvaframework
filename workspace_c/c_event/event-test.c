#include <event.h>

void fifo_read(int fd, short event, void *arg) {
	char buf[255];
	int len;
	struct event *ev = arg;

	/* Reschedule this event */
	event_add(ev, NULL);

	fprintf(stdout, "fifo_read called with fd: %d, event: %d, arg: %p\n", fd,
			event, arg);

	len = read(fd, buf, sizeof(buf) - 1);
	if (len == -1) {
		perror("read");
		return;
	} else if (len == 0) {
		fprintf(stdout, "Connection closed\n");
		return;
	}

	buf[len] = '\0';
	fprintf(stdout, "Read: %s\n", buf);
}

void event_test1(void) {
	struct stat st;
	char *fifo = "event.fifo";
	int socket;
	struct event evfifo;

	if (lstat(fifo, &st) == 0) {
		if ((st.st_mode & S_IFMT) == S_IFREG) {
			errno = EEXIST;
			perror("lstat,file has exist.\n");
			exit(1);
		}
	}

	unlink(fifo);
	if (mkfifo(fifo, 0600) == -1) {
		perror("mkfifo\n");
		exit(1);
	}

	/* Linux pipes are broken, we need O_RDWR instead of O_RDONLY */
#ifdef __linux
	socket = open(fifo, O_RDWR | O_NONBLOCK, 0);
#else
	socket = open(fifo, O_RDONLY | O_NONBLOCK, 0);
#endif

	if (socket == -1) {
		perror("open");
		exit(1);
	}

	fprintf(stdout, "Write data to %s\n", fifo);

	/* Initalize the event library */
	event_init();

	/* Initalize one event */

	event_set(&evfifo, socket, EV_READ, fifo_read, &evfifo);

	/* Add it to the active events, without a timeout */
	event_add(&evfifo, NULL);

	event_dispatch();

}

int lasttime;

void timeout_cb(int fd, short event, void *arg) {
	struct timeval tv;
	struct event *timeout = arg;
	int newtime = time(NULL);

	printf("%s: called at %d: %d\n", __FUNCTION__, newtime, newtime - lasttime);
	lasttime = newtime;

	timerclear(&tv);
	tv.tv_sec = 2;
	event_add(timeout, &tv);
}

void event_test2(void) {
	struct event timeout;
	struct timeval tv;

	/* Initalize the event library */
	event_init();

	/* Initalize one event */
	timeout_set(&timeout, timeout_cb, &timeout);

	timerclear(&tv);
	tv.tv_sec = 2;
	event_add(&timeout, &tv);

	lasttime = time(NULL);

	event_dispatch();
	return NULL;
}

