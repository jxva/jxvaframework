#include <jv_http_client.h>

int jv_http_browser(jv_socket_t fd) {
	int n;
	char tmp[MAXLINE];
	if ((n = write(fd, REQUEST, strlen(REQUEST))) > 0) {

	}
	if (n < 0) {
		printf("write error:%s\n", strerror(errno));
		return JV_ERROR;
	}
	if ((n = read(fd, tmp, MAXLINE)) > 0) {

	}
	if (n < 0) {
		printf("read error:%s\n", strerror(errno));
		return JV_ERROR;
	}
	tmp[n] = 0;
	printf("ddd:%s\n", tmp);
	return JV_OK;
}
