#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>

#include <unistd.h>
#include <pthread.h>

#include "ev.h"
#include "event.h"

#define MAXLEN 1023
#define PORT 8003
#define ADDR_IP "127.0.0.1"

int socket_init();
void accept_callback(struct ev_loop *loop, ev_io *w, int revents);
void recv_callback(struct ev_loop *loop, ev_io *w, int revents);
void write_callback(struct ev_loop *loop, ev_io *w, int revents);

int main(int argc, char** argv) {
	int listen;
	ev_io ev_io_watcher;
	listen = socket_init();
	struct ev_loop *loop = ev_loop_new(EVBACKEND_EPOLL);
	ev_io_init(&ev_io_watcher, accept_callback, listen, EV_READ);
	ev_io_start(loop, &ev_io_watcher);
	ev_loop(loop, 0);
	ev_loop_destroy(loop);
	return 0;

}

int socket_init() {
	struct sockaddr_in my_addr;
	int listener;
	if ((listener = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("socket");
		exit(1);
	} else {
		printf("SOCKET CREATE SUCCESS!\n");
	}
	//setnonblocking(listener);
	int so_reuseaddr = 1;
	setsockopt(listener, SOL_SOCKET, SO_REUSEADDR, &so_reuseaddr,
			sizeof(so_reuseaddr));
	bzero(&my_addr, sizeof(my_addr));
	my_addr.sin_family = PF_INET;
	my_addr.sin_port = htons(PORT);
	my_addr.sin_addr.s_addr = INADDR_ANY;

	if (bind(listener, (struct sockaddr *) &my_addr, sizeof(struct sockaddr))
			== -1) {
		perror("bind error!\n");
		exit(1);
	} else {
		printf("IP BIND SUCCESS,IP:%s\n", ADDR_IP);
	}

	if (listen(listener, 1024) == -1) {
		perror("listen error!\n");
		exit(1);
	} else {
		printf("LISTEN SUCCESS,PORT:%d\n", PORT);
	}
	return listener;
}

void accept_callback(struct ev_loop *loop, ev_io *w, int revents) {
	int newfd;
	struct sockaddr_in sin;
	socklen_t addrlen = sizeof(struct sockaddr);
	ev_io* accept_watcher = malloc(sizeof(ev_io));
	while ((newfd = accept(w->fd, (struct sockaddr *) &sin, &addrlen)) < 0) {
		if (errno == EAGAIN || errno == EWOULDBLOCK) {
			//these are transient, so don't log anything.
			continue;
		} else {
			printf("accept error.[%s]\n", strerror(errno));
			break;
		}
	}ev_io_init(accept_watcher, recv_callback, newfd, EV_READ);
	ev_io_start(loop, accept_watcher);
	printf("accept callback : fd :%d\n", accept_watcher->fd);

}

void recv_callback(struct ev_loop *loop, ev_io *w, int revents) {
	char buffer[1024] = { 0 };
	int ret = 0;
	//ev_io write_event;
	loop: ret = recv(w->fd, buffer, MAXLEN, 0);
	if (ret > 0) {
		printf("recv message :%s  \n", buffer);

	} else if (ret == 0) {
		printf("remote socket closed!socket fd: %d\n", w->fd);
		close(w->fd);
		ev_io_stop(loop, w);
		free(w);
		return;
	} else {
		if (errno == EAGAIN || errno == EWOULDBLOCK) {
			goto loop;
		} else {
			printf("ret :%d ,close socket fd : %d\n", ret, w->fd);
			close(w->fd);
			ev_io_stop(loop, w);
			free(w);
			return;
		}
	}
	int fd = w->fd;
	ev_io_stop(loop, w);
	ev_io_init(w, write_callback, fd, EV_WRITE);
	ev_io_start(loop, w);
	printf("socket fd : %d, turn read 2 write loop! ", fd);

}

void write_callback(struct ev_loop *loop, ev_io *w, int revents) {
	char buffer[1024] = { 0 };
	//ev_io read_event;
	snprintf(buffer, 1023, "this is a libev server!\n");
	write(w->fd, buffer, strlen(buffer));
	int fd = w->fd;
	ev_io_stop(loop, w);
	ev_io_init(w, recv_callback, fd, EV_READ);
	ev_io_start(loop, w);
}
