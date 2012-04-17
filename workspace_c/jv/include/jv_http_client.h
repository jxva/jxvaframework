#ifndef _JV_HTTP_CLIENT_H_INCLUDED_
#define _JV_HTTP_CLIENT_H_INCLUDED_

#include <jv_socket.h>

#define REQUEST "GET /test.html HTTP/1.0\r\n\r\n"
#define	MAXLINE	1024

int jv_http_browser(jv_socket_t fd);

#endif /* _JV_HTTP_CLIENT_H_INCLUDED_ */
