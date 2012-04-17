#include <jv_socket.h>

jv_socket_t jv_socket_server(jv_listen_t *jlt) {
	jv_socket_t fd;
	struct sockaddr_in addr;
	if ((fd = socket(AF_INET, SOCK_STREAM, 0)) == JV_ERROR) {
		perror("socket error.\n");
		return JV_ERROR;
	}
	bzero(&addr, sizeof(addr));
	addr.sin_family = AF_INET;
	addr.sin_port = htons(jlt->port);
	addr.sin_addr.s_addr = jlt->host; /* INADDR_ANY */

	if (bind(fd, (struct sockaddr *) &addr, sizeof(addr)) == JV_ERROR) {
		perror("bind error.\n");
		return JV_ERROR;
	}

	if (listen(fd, 511) == JV_ERROR) {
		perror("listen error.\n");
		return JV_ERROR;
	}

	return fd;
}

jv_socket_t jv_socket_client(jv_listen_t *jlt) {
	jv_socket_t fd;
	struct sockaddr_in addr;
	if ((fd = socket(AF_INET, SOCK_STREAM, 0)) == JV_ERROR) {
		perror("socket error.\n");
		return JV_ERROR;
	}
	bzero(&addr, sizeof(addr));
	addr.sin_family = AF_INET;
	addr.sin_port = htons(jlt->port);
	addr.sin_addr.s_addr = jlt->host; /* INADDR_ANY */

	if (connect(fd, (struct sockaddr *) &addr, sizeof(addr)) == JV_ERROR) {
		printf("connect error:%s\n", strerror(errno));
		return JV_ERROR;
	}
	return fd;
}

jv_int_t jv_socket_read(jv_accept_t fd, char *buffer, jv_int_t length) {
	jv_int_t bytes_left;
	jv_int_t bytes_read;

	bytes_left = length;
	while (bytes_left > 0) {
		bytes_read = read(fd, buffer, BUFSIZ);
		if (bytes_read < 0) {
			if (errno == EINTR) {
				bytes_read = 0;
			} else {
				return JV_ERROR;
			}
		} else if (bytes_read == 0) {
			break;
		}
		bytes_left -= bytes_read;
		buffer += bytes_read;
	}
	return (length - bytes_left);
}

jv_int_t jv_socket_write(jv_accept_t fd, char *buffer, jv_int_t length) {
	jv_int_t bytes_left;
	jv_int_t written_bytes;
	char *ptr;

	ptr = buffer;
	bytes_left = length;
	while (bytes_left > 0) {
		/* 开始写*/
		written_bytes = write(fd, ptr, bytes_left);
		if (written_bytes <= 0) { /* 出错了*/
			if (errno == EINTR) /* 中断错误 我们继续写*/
				written_bytes = 0;
			else
				/* 其他错误 没有办法,只好撤退了*/
				return (-1);
		}
		bytes_left -= written_bytes;
		ptr += written_bytes; /* 从剩下的地方继续写  */
	}
	return (0);
}

ssize_t jv_recv(int sock, u_char *buf, size_t size) {
	ssize_t n;
	int err, ready, eof;
	do {
		n = recv(sock, buf, size, 0);
		/* fprintf(stdout,"recv: fd:%d %d of %d", sock, n, size); */
		if (n == 0) {
			ready = 0;
			eof = 1;
			return n;
		} else if (n > 0) {
			/* if ((size_t) n < size&& !(ngx_event_flags & NGX_USE_GREEDY_EVENT)){
			 ready = 0;
			 } */
			return n;
		}
		err = errno;
		if (err == EAGAIN || err == EINTR) {
			n = -2;
			fprintf(stdout, "recv() not ready");
		} else {
			n = -1;
			fprintf(stdout, "recv() failed");
			break;
		}
	} while (err == EINTR);
	return n;
}

ssize_t jv_send(int sock, u_char *buf, size_t size) {
	ssize_t n;
	int err;
	int ready, sent;
	for (;;) {
		n = send(sock, buf, size, 0);
		/* fprintf(stdout,"send: fd:%d %d of %d",sock, n, size); */
		if (n > 0) {
			if (n < (ssize_t) size) {
				ready = 0;
			}
			sent += n;
			return sent;
		}
		err = errno;
		if (n == 0) {
			fprintf(stdout, "send() returned zero");
			ready = 0;
			return n;
		}
		if (err == EAGAIN || err == EINTR) {
			ready = 0;
			fprintf(stdout, "send() not ready");

			if (err == EAGAIN) {
				return -2;
			}
		} else {
			fprintf(stdout, "send() failed");
			return -1;
		}
	}
}