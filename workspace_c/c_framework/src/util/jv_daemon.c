#include <jv_daemon.h>

jv_int_t jv_daemon(void) {
	int fd;

	switch (fork()) {/* 1. create a sub process */
	case -1:
		printf("fork() failed\n");
		return JV_ERROR;
	case 0:
		break;
	default:
		exit(0); /* 1. exit parent process and make sub process to a orphan */
	}

	if (setsid() == -1) { /* 2. create a new session */
		printf("setsid() failed\n");
		return JV_ERROR;
	}
	/*
	 3. change sub process path to root path
	 chdir("/");
	 */
	umask(0); /* 4. reset file privilege mask code */

	/*
	 5. close file description: stdin,stdout,stderr
	 for(i = 0; i < 3; i++) {
	 close(i);
	 }
	 */

	/* 5. or  redirect outprint to /dev/null, equals close file descriptions */
	fd = open("/dev/null", O_RDWR);

	if (fd == -1) {
		printf("open(\"/dev/null\") failed\n");
		return JV_ERROR;
	}

	if (dup2(fd, STDIN_FILENO) == -1) {
		printf("dup2(STDIN) failed\n");
		return JV_ERROR;
	}

	if (dup2(fd, STDOUT_FILENO) == -1) {
		printf("dup2(STDOUT) failed\n");
		return JV_ERROR;
	}

#if 0
	if (dup2(fd, STDERR_FILENO) == -1) {
		printf("dup2(STDERR) failed\n");
		return JV_ERROR;
	}
#endif

	if (fd > STDERR_FILENO) {
		if (close(fd) == -1) {
			printf("close() failed\n");
			return JV_ERROR;
		}
	}

	return JV_OK;
}
