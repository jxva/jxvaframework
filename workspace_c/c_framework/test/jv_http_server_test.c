#include <jv_http_server.h>
#include <poll.h>
#include <wait.h>

static jv_socket_t sockt_init(void) {
	jv_listen_t _listen;
	_listen.host = htonl(INADDR_ANY);
	_listen.port = 80;
	return jv_socket_server(&_listen);
}

static jv_int_t http_process(jv_accept_t connfd) {
	char request_head[BUFSIZ];
	if (jv_http_request(connfd, request_head) == JV_ERROR) {
		printf("http request error:%s\n", strerror(errno));
		return JV_ERROR;
	}
	if (jv_http_response(connfd, request_head) == JV_ERROR) {
		printf("http response error:%s\n", strerror(errno));
		return JV_ERROR;
	}
	return JV_OK;
}

static void *thread_function(void *arg) {
	jv_accept_t connfd = (int) arg;
	pthread_detach(pthread_self());
	http_process(connfd);
	pthread_exit(NULL);
	close(connfd); /* close(connfd) must be after pthread_exit(NULL) when using multithread. */
	return NULL;
}

void jv_http_server_test(void) {
	jv_socket_t fd = sockt_init();
	while (1) {
		jv_accept_t connfd;
		if ((connfd = accept(fd, NULL, NULL)) == JV_ERROR) {
			printf("accept error:%s\n", strerror(errno));
		} else {
			if (http_process(connfd) == JV_ERROR) {
				printf("http process error:%s\n", strerror(errno));
			}
			close(connfd);
		}
	}
	close(fd);
}

void jv_http_server_thread_test(void) {
	jv_socket_t fd = sockt_init();
	while (1) {
		jv_accept_t connfd;
		if ((connfd = accept(fd, NULL, NULL)) == JV_ERROR) {
			printf("accept error:%s\n", strerror(errno));
		} else {
			pthread_t tid;
			if (pthread_create(&tid, NULL, thread_function,
					(void *) connfd) != JV_OK) {
				printf("thread create failed:%s\n", strerror(errno));
				continue;
			}
		}
	}
	close(fd);
}

static void sig_chld(int signo) {
	pid_t pid;
	int stat;
	while ((pid = waitpid(-1, &stat, WNOHANG)) > 0) {
		/* printf("child %d terminated\n",pid); */
	}
	return;
}

void jv_http_server_fork_test(void) {
	jv_socket_t fd = sockt_init();
	signal(SIGCHLD, sig_chld);
	while (1) {
		struct sockaddr_in cliaddr;
		socklen_t clilen = sizeof(cliaddr);
		jv_accept_t connfd;
		pid_t pid;
		if ((connfd = accept(fd, (struct sockaddr *) &cliaddr, &clilen))
				== JV_ERROR) {
			perror("accept error.");
			continue;
		}/* else{
		 char buff[80];
		 printf("Connection from %s,port %d\n",inet_ntop(AF_INET,&cliaddr.sin_addr,buff,sizeof(buff)),ntohs(cliaddr.sin_port));
		 } */
		pid = fork();
		if (pid == (pid_t) -1) {
			perror("First fork()\n");
			exit(1);
		}
		if (pid == (pid_t) 0) {
			close(fd);
			/* printf("pid:%d\n",getppid()); */
			if (http_process(connfd) == JV_ERROR) {
				printf("http process error:%s\n", strerror(errno));
			}
			close(connfd);/* 非必须 */
			exit(0);/*child terminates*/
		}
		/*else{
		 do {
		 pid = waitpid ((pid_t)-1, NULL, WNOHANG);
		 if (pid == (pid_t)-1){
		 perror("Wait for child\n");
		 exit(1);
		 }
		 } while (pid != (pid_t)0);
		 }*/
		close(connfd); /*parent closes connected socket*/
	}
}

void jv_http_server_select_test(void) {
	jv_socket_t fd = sockt_init();
	int maxfd = fd; /* initialize */
	int maxi = -1; /* index into client[] array */
	int i, sockfd, nready, client[FD_SETSIZE];
	fd_set rset, wset, eset, allset;
	for (i = 0; i < FD_SETSIZE; i++) {
		client[i] = -1; /* -1 indicates available entry */
	}FD_ZERO(&allset);
	FD_SET(fd, &allset);

	for (;;) {
		rset = allset; /* structure assignment */
		wset = allset;
		eset = allset;
		nready = select(maxfd + 1, &rset, &wset, &eset, NULL);
		if (nready < 0) {
			perror("select error.");
			exit(0);
		}
		if (FD_ISSET(fd, &rset)) { /* new client connection */
			jv_accept_t connfd;
			if ((connfd = accept(fd, NULL, NULL)) == JV_ERROR) {
				printf("accept error:%s\n", strerror(errno));
				continue;
			} else {
				for (i = 0; i < FD_SETSIZE; i++) {
					if (client[i] < 0) {
						client[i] = connfd; /* save descriptor */
						break;
					}
				}
				if (i == FD_SETSIZE)
					perror("too many clients");

				FD_SET(connfd, &allset);
				/* add new descriptor to set */
				if (connfd > maxfd)
					maxfd = connfd; /* for select */
				if (i > maxi)
					maxi = i; /* max index in client[] array */

				if (--nready <= 0)
					continue; /* no more readable descriptors */
			}
		}

		for (i = 0; i <= maxi; i++) { /* check all clients for data */
			if ((sockfd = client[i]) < 0)
				continue;

			if (FD_ISSET(sockfd, &rset)) {
				if (http_process(sockfd) == JV_ERROR) {
					printf("http process error:%s\n", strerror(errno));
				}
				close(sockfd);
				FD_CLR(sockfd, &allset);
				client[i] = -1;
			}
			if (FD_ISSET(sockfd, &eset)) {
				perror("eset");
				exit(0);
			}
			if (--nready <= 0)
				break; /* no more readable descriptors */
		}
	}
}

void jv_http_server_poll_test(void) {
	jv_socket_t fd = sockt_init();
	int i, nready;
	int maxi = 0; /* max index into client[] array */
	struct pollfd client[OPEN_MAX];
	client[0].fd = fd;
	client[0].events = POLLRDNORM;
	for (i = 1; i < OPEN_MAX; i++) {
		client[i].fd = -1; /* -1 indicates available entry */
	}

	for (;;) {
		nready = poll(client, maxi + 1, -1);
		if (nready < 0) {
			perror("select error.");
			exit(0);
		}

		if (client[0].revents & POLLRDNORM) { /* new client connection */
			int connfd;
			if ((connfd = accept(fd, NULL, NULL)) == JV_ERROR) {
				perror("accept error.");
				continue;
			} else {
				for (i = 1; i < OPEN_MAX; i++) {
					if (client[i].fd < 0) {
						client[i].fd = connfd; /* save descriptor */
						break;
					}
				}
				if (i == OPEN_MAX) {
					perror("too many clients");
					exit(0);
				}

				client[i].events = POLLRDNORM;
				if (i > maxi)
					maxi = i; /* max index in client[] array */

				if (--nready <= 0)
					continue; /* no more readable descriptors */
			}
		}

		for (i = 1; i <= maxi; i++) { /* check all clients for data */
			int sockfd;
			if ((sockfd = client[i].fd) < 0)
				continue;
			if (client[i].revents & (POLLRDNORM | POLLERR)) {
				char request_head[BUFSIZ];
				jv_int_t ret;
				if ((ret = jv_http_request(sockfd, request_head)) < 0) {
					if (errno == ECONNRESET) {
						close(sockfd);
						client[i].fd = -1;
					} else {
						fprintf(stderr, "recv data error!\n");
						exit(0);
					}
				} else if (ret == 0) {
					printf("client[%d] closed connection\n", i);
					close(sockfd);
					client[i].fd = -1;
				} else {
					if (jv_http_response(sockfd, request_head) == JV_ERROR) {
						printf("http response error:%s\n", strerror(errno));
					}
					close(sockfd);
					client[i].fd = -1;
				}
				if (--nready <= 0)
					break; /* no more readable descriptors */
			}

		}
	}
}
