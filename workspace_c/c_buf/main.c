#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>
#include <errno.h>

ssize_t ngx_unix_recv(int sock, u_char *buf, size_t size) {
	ssize_t n;
	int err;
	int read, ready, eof;

	do {
		n = recv(sock, buf, size, 0);

		fprintf(stdout, "recv: fd:%d %d of %d", sock, n, size);

		if (n == 0) {
			ready = 0;
			eof = 1;
			return n;

		} else if (n > 0) {

			// if ((size_t) n < size&& !(ngx_event_flags & NGX_USE_GREEDY_EVENT)){
			//     ready = 0;
			//  }

			return n;
		}

		err = errno;

		if (err == EAGAIN || err == EINTR) {
			fprintf(stdout, "recv() not ready");
			n = -2;

		} else {
			n = -1;
			fprintf(stdout, "recv() failed");
			break;
		}

	} while (err == EINTR);
	return n;
}

int main() {

	return 0;
}

