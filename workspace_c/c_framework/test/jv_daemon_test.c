#include <assert.h>
#include <jv_daemon.h>
#include <jv_string.h>

jv_int_t jv_daemon_test(void) {
	if (jv_daemon() == 0) {
		char s[] = "Hello,中国\n";
		int fd = open("c:/test_daemon.txt", (O_RDWR | O_CREAT), 00777);
		while (1) {
			if (fd == JV_ERROR) {
				perror("open failed: ");
				close(fd);
				return JV_ERROR;
			}
			if (write(fd, s, jv_strlen(s)) == JV_ERROR) {
				perror("write failed: ");
				close(fd);
				return JV_ERROR;
			}
			sleep(2);
		}
	} else {
		printf("error.\n");
	}
	return JV_OK;
}
