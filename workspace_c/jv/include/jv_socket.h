#ifndef _JV_SOCKET_H_INCLUDED
#define _JV_SOCKET_H_INCLUDED

#include <jv_core.h>
#include <jv_string.h>

typedef int jv_socket_t;
typedef int jv_accept_t;

typedef struct {
	jv_uint_t host;
	jv_uint_t port;
} jv_listen_t;

jv_socket_t jv_socket_server(jv_listen_t *jlt);

jv_socket_t jv_socket_client(jv_listen_t *jlt);

#if 0
jv_accept_t jv_socket_accpet(jv_socket_t jst);

int jv_socket_recv(jv_socket_t jst);
#endif

ssize_t jv_recv(int sock, u_char *buf, size_t size);

ssize_t jv_send(int sock, u_char *buf, size_t size)

#endif /* _JV_SOCKET_H_INCLUDED_ */
