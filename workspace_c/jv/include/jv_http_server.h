#ifndef _JV_HTTP_H_INCLUDED_
#define _JV_HTTP_H_INCLUDED_

#include <jv_socket.h>

#define WWWROOT    "stdin/wwwroot"
#define INDEX       "index.html"
#define DEFAULT_MIME_TYPE (u_char *)"application/octet-stream"

typedef struct {
	jv_str_t ext;
	jv_str_t content;
} jv_mime_type_t;

typedef struct {
	char *method;
	char *filename;
	char *protocol;
} jv_http_head_t;

extern jv_mime_type_t mimes[];

const u_char *get_mime_type(const char *ext);

int jv_http_request(jv_accept_t fd, char head[]);

int jv_http_response(jv_accept_t fd, char head[]);

#endif /* _JV_HTTP_H_INCLUDED_ */
