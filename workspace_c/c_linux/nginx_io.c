#include <sys/socket.h>
#include <sys/epoll.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

#define LISTENQ 20
#define SERV_PORT 5555

//监听套接字数组
int listen_list[10];

//输入输出缓冲区
struct _buf {
	int fd;
	int len;
	char ptr[1024];
};

//将套接字设置为非阻塞方式
void setnonblocking(int sock) {
	int opts;
	opts = fcntl(sock, F_GETFL);
	if (opts < 0) {
		perror("fcntl(sock,GETFL)");
		exit(1);
	}

	opts = opts | O_NONBLOCK;

	if (fcntl(sock, F_SETFL, opts) < 0) {
		perror("fcntl(sock,SETFL,opts)");

		exit(1);
	}
}

int listen_init() {
	int i = 0;

	int reuse = 1;

	const char *listen_addr = "10.204.75.234";

	struct sockaddr_in serveraddr;

	int ret = 0;

	//设置serveraddr
	inet_aton(listen_addr, &(serveraddr.sin_addr));

	serveraddr.sin_port = htons(SERV_PORT);

	//开启十个监听套接字监听同一个端口
	for (; i < 1; ++i) {
		//创建监听套接字
		listen_list[i] = socket(AF_INET, SOCK_STREAM, 0);

		//设置端口复用
		if (setsockopt(listen_list[i], SOL_SOCKET, SO_REUSEADDR,
				(const void *) &reuse, sizeof(int)) == -1) {
			printf("setsockopt SO_REUSEADDR failed!\n");

			return 1;
		} else {
			printf("setsockopt SO_REUSEADDR successfully!\n");
		}

		setnonblocking(listen_list[i]);

		//将监听套接字同sockaddr绑定
		ret = bind(listen_list[i], (struct sockaddr *) &serveraddr,
				sizeof(struct sockaddr_in));

		if (ret != 0) {
			perror("bind");

			return 1;
		} else {
			printf("bind successfully...\n");
		}

		//开始监听套接字
		listen(listen_list[i], LISTENQ);
	}
}

void proc() {
	int epfd;
	struct epoll_event ev, events[40];
	int i;
	int nfds;
	int conn_fd;
	struct sockaddr_in clientaddr;
	int clilen;

	epfd = epoll_create(256);

	//添加监听套接字
	for (i = 0; i < 1; ++i) {
		//设置与要处理的事件相关的文件描述符
		ev.data.fd = listen_list[i];

		//设置要处理的事件类型，当描述符可读时触发，触发方式为ET模式
		ev.events = EPOLLIN | EPOLLET;

		//添加事件
		epoll_ctl(epfd, EPOLL_CTL_ADD, listen_list[i], &ev);
	}

	for (;;) {

		//等待事件发生
		nfds = epoll_wait(epfd, events, 20, -1);

		printf("[%d]wake from epoll_wait\n", getpid());

		for (i = 0; i < nfds; ++i) {
			int k;

			//判断是不是监听套接字
			if (events[i].data.fd == listen_list[0]) {
				const char* str;

				//得到连接套接字
				conn_fd = accept(events[i].data.fd,
						(struct sockaddr *) &clientaddr, &clilen);

				if (conn_fd < 0) {
					//因为所有的进程都监听在同一个端口上，所以当该端口有连接时
					//所有的进程都将被唤醒，但只有一个进程会成为幸运儿能到
					//连接套接字，别的进程的accept会返回-1，并将errno设置成EAGAIN
					if (errno == EAGAIN)
						continue;
				}

				setnonblocking(conn_fd);

				str = inet_ntoa(clientaddr.sin_addr);

				printf("connect from %s, by process %d\n", str, getpid());

				//设置用于读操作的文件描述符
				ev.data.fd = conn_fd;

				//设置事件标志
				ev.events = EPOLLIN | EPOLLET;

				//注册ev，每次有新的客户端的连接到服务器，都需要为其生成一个事件
				epoll_ctl(epfd, EPOLL_CTL_ADD, conn_fd, &ev);
			}
			//读事件
			else if (events[i].events & EPOLLIN) {
				struct _buf* buf = malloc(sizeof(struct _buf));

				int fd;

				int n;

				memset(buf, 0, sizeof(struct _buf));

				fd = events[i].data.fd;

				//读取数据
				if ((n = read(fd, buf->ptr, 1024)) < 0) {

					if (errno == ECONNRESET)
						close(fd);
					else
						printf("readline error\n");

				} else if (n == 0) {

					close(fd);

					printf("Client close connect!\n");

					if (buf != NULL)
						free(buf);

				} else {

					printf("read from client: %s\n", buf->ptr);

					buf->fd = fd;

					buf->len = n;

					//设置需要传递出去的数据
					ev.data.ptr = buf;

					//设置用于注测的写操作事件
					ev.events = EPOLLOUT | EPOLLET;

					//修改sockfd上要处理的事件为EPOLLOUT，这将导致数据被发回客户端（在上面的for循环中）
					epoll_ctl(epfd, EPOLL_CTL_MOD, fd, &ev);
				}
			}
			//写事件
			else if (events[i].events & EPOLLOUT) {

				int sockfd;

				struct _buf* buf;

				int n;

				buf = (struct _buf*) events[i].data.ptr;

				n = write(buf->fd, buf->ptr, buf->len);

				if (n == -1) {
					perror("write");
					continue;
				}

				//设置用于读操作的文件描述符
				ev.data.fd = buf->fd;

				//设置用于注册的读操作事件
				ev.events = EPOLLIN | EPOLLET;

				epoll_ctl(epfd, EPOLL_CTL_MOD, buf->fd, &ev);

				if (events[i].data.ptr != NULL) {
					free(events[i].data.ptr);
					events[i].data.ptr = NULL;
				}
			} else {
				printf("event error!\n");
				break;
			}
		}
	}

	exit(0);
}

int main() {
	int pid;
	int pid_num = 5;
	int i;

	int status;

	//初始化监听套接字
	listen_init();

	//创建子进程
	for (i = 0; i < pid_num; ++i) {
		pid = fork();

		switch (pid) {
		case -1:
			printf("fork error!\n");
			return 1;
		case 0:
			proc();
		default:
			printf("process %d forked\n", pid);
			break;
		}
	}

	//父进程并不监听该套接字
	close(listen_list[0]);

	//等待子进程运行结束
	waitpid(-1, &status, 0);

	return 0;
}
