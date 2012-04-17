#include <jv_http_server.h>

jv_mime_type_t mimes[] = { { jv_string("htm"),
		jv_string("text/html;charset=utf-8") }, { jv_string("html"),
		jv_string("text/html;charset=utf-8") }, { jv_string("shtml"),
		jv_string("text/html;charset=utf-8") }, { jv_string("jpg"),
		jv_string("image/jpeg") },
		{ jv_string("jpeg"), jv_string("image/jpeg") }, { jv_string("gif"),
				jv_string("image/gif") }, { jv_string("png"),
				jv_string("image/png") }, { jv_string("css"),
				jv_string("text/css") }, { jv_string("js"),
				jv_string("text/javascript") }, { jv_string("swf"),
				jv_string("application/x-shockwave-flash") }, {
				jv_string("ico"), jv_string("image/x-icon") }, {
				jv_string("htc"), jv_string("text/x-component") }, {
				jv_null_string, jv_null_string } };

const u_char *get_mime_type(const char *ext) {
	jv_mime_type_t *tmp = mimes;
	while ((tmp->ext).len != 0) {
		if (jv_strncmp(tmp->ext.data,ext,tmp->ext.len) == 0) {
			return tmp->content.data;
		}
		tmp++;
	}
	return DEFAULT_MIME_TYPE;
}

int jv_http_request(jv_accept_t fd, char head[]) {
	int n;
	if ((n = recv(fd, head, BUFSIZ, 0)) > 0) {
		/* printf("SSSSSSSS:%s\n",head); */
	}
	if (n < 0) {
		printf("recv error:%s\n", strerror(errno));
		return n;
	}
	head[n] = '\0';
	return n;
}

int jv_http_response(jv_accept_t fd, char head[]) {
	char wwwdoc[64] = WWWROOT;
	FILE *fpfile, *fpsock;
	char method[5], filename[32], protocol[10];
	sscanf(head, "%s %s %s\r\n", method, filename, protocol);
	if (filename[strlen(filename) - 1] == '/') {
		strncat(filename, INDEX, strlen(INDEX));
	}

	/* strncat(wwwdoc,WWWROOT,jv_strlen(WWWROOT)); */
	strncat(wwwdoc, filename, strlen(filename));

	/* printf("wwwdoc:%s,wl:%d,fl:%d\n",wwwdoc,jv_strlen(wwwdoc),jv_strlen(filename)); */

	fpsock = fdopen(fd, "w");
	if ((fpfile = fopen(wwwdoc, "r")) == NULL) {
		fprintf(
				fpsock,
				"HTTP/1.1 404 File Not Found\r\nServer: test/1.0\r\nDate: May,23 06:20:12 2012 GTC\r\nContent-type: text/html\r\nConnection: close\r\n\r\n\r\n<h1>404 Error.</h1>");
	} else {
		int c;
		char *ext = strrchr(filename, '.') + 1;
		const u_char *mime_type = get_mime_type(ext);
		fprintf(
				fpsock,
				"HTTP/1.1 200 OK\r\nServer: jxva test/1.0\r\nDate: May,23 06:20:12 2012 GTC\r\n"
						"Content-type: %s\r\nConnection: close\r\n\r\n",
				mime_type);
		while ((c = getc(fpfile)) != EOF) {
			putc(c, fpsock);
		}
		fclose(fpfile);
	}
	fclose(fpsock);
	return JV_OK;
}
