#include <jv_http_client.h>

void jv_http_client_test() {
	jv_socket_t fd;
	jv_listen_t _listen;
	_listen.host = inet_addr("127.0.0.1");
	_listen.port = 80;
	fd = jv_socket_client(&_listen);
	if (jv_http_browser(fd) == JV_ERROR) {
		printf("http browser error:%s\n", strerror(errno));
	}
	close(fd);
}
