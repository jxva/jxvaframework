#include <stdio.h>
#include <stdlib.h>

#include <errno.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <sys/stat.h>

void cgi(int sock) {
	pid_t pid;
	int fd1[2], fd2[2];
	int i, size;
	char ret_buf[1024 + 1];
	int totalSent;
	int bytesSent;

	char head[BUFSIZ + 1];
	char buf[2048] =
			"HTTP/1.1 200 OK\r\nCache-Control:no-cache\r\nServer:test/1.0\r\nDate:Thu, 22 Oct 2009 04:13:02 GMT\r\nPragma:no-cache\r\nContent-type:text/html;charset=utf-8\r\nConnection:close\r\n\r\n";

	recv(sock, head, BUFSIZ, 0);

	/*  创建父子进程通信用的管道 */
	if (pipe(fd1) < 0 || pipe(fd2) < 0) {
		fprintf(stderr, "creat pipe error!\n");
		exit(1);
	}

	if ((pid = fork()) < 0) {
		fprintf(stderr, "fork error!\n");
		exit(1);
	} else if (pid > 0) {/* 父进程中 */
		close(fd1[0]); /* 关闭管道的读端以只写 */
		close(fd2[1]); /* 关闭管道的写端以只读 */

		/* 等待子进程结束 */
		printf("waiting for cgi's respond..........\n");

		waitpid(pid, NULL, 0);

		/* 从管道fd2[0]读取cgi 程序的返回结果 */
		i = read(fd2[0], ret_buf, 1024);
		if (i == 0) {
			fprintf(stderr, " read 0!\n");
			close(sock);
			return;
		}
		ret_buf[i] = '\0';

		/* printf("FFFFFF:%s\n",ret_buf); */

		totalSent = 0;

		strcat(buf, ret_buf);

		/* 将取得的返回结果发送给浏览器 */
		size = strlen(buf);

		do {
			bytesSent = send(sock, buf + totalSent, strlen(buf + totalSent), 0);
			if (bytesSent == -1)
				break;
			totalSent += bytesSent;
		} while (totalSent < size);
		/* printf("ddddddddddddddddddddd\n"); */
		close(sock);
	} else {/* 子进程中执行CGI程序 */
		char *envp[] = { "CONTENT_TYPE=application/x-www-form-urlencoded",
				"REQUEST_METHOD=GET", "CONTENT_LENGTH=",
				"QUERY_STRING=username", NULL };

		close(fd1[1]); /* 关闭管道的写端以只读 */
		close(fd2[0]); /* 关闭管道的读端以只写 */

		if (fd1[0] != STDIN_FILENO) {/* 重定向子进程的标准输入到fd1[0] */
			if (dup2(fd1[0], STDIN_FILENO) != STDIN_FILENO) {
				fprintf(stderr, "dup2 error\n");
				return;
			}
			close(fd1[0]);
		}

		if (fd2[1] != STDOUT_FILENO) {/* 重定向子进程的标准输出到fd2[1] */
			if (dup2(fd2[1], STDOUT_FILENO) != STDOUT_FILENO) {
				fprintf(stderr, "dup2 error\n");
				return;
			}
			close(fd2[1]);
		}

		/* 取得当前的工作目录 */
		/* getcwd(buf, sizeof(buf));
		 //strcat(buf, pathname);
		 */

		if (execle("c:/index.exe", "index.exe", NULL, envp) < 0) {
			printf("cgi failed.\n");
		}

	}
}

int main(int argc, const char *argv[]) {
	int listenfd;
	struct sockaddr_in addr;
	if ((listenfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("socket error.\n");
		return 1;
	}
	bzero(&addr, sizeof(addr));
	addr.sin_family = AF_INET;
	addr.sin_port = htons(80);
	addr.sin_addr.s_addr = INADDR_ANY;

	if (bind(listenfd, (struct sockaddr *) &addr, sizeof(addr)) == -1) {
		perror("bind error.\n");
		return 1;
	}

	if (listen(listenfd, 511) == -1) {
		perror("listen error.\n");
		return 1;
	}

	while (1) {
		int connfd;
		if ((connfd = accept(listenfd, NULL, NULL)) == -1) {
			printf("accept is error");
			continue;
		}
		cgi(connfd);
	}
	return 0;
}
