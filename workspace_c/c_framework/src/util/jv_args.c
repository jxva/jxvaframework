#include <jv_args.h>
#include <jv_string.h>
#include <jv_daemon.h>

static void usage(void) {
	char s[] = "Usage: C_Temp [-?vVt] [-help] [-stop] [-s signal] " JV_LINEFEED
	"Options:" JV_LINEFEED
	"  -?,-help      : this help" JV_LINEFEED
	"  -v            : show version and exit" JV_LINEFEED
	"  -V            : show version and configure options then exit"
	JV_LINEFEED
	"  -t            : test configuration and exit" JV_LINEFEED
	"  -stop         : send signal to a master process: stop" JV_LINEFEED
	"  -s signal     : send signal to a master process: "
	"stop, quit, reopen, reload" JV_LINEFEED;
	puts(s);
}

jv_int_t jv_command_line(int argc, char * const *argv) {
	if (argc == 1) {
		if (jv_daemon() == 0) {
			char buf[6];
			int fd = open("c:/jv.pid", (O_WRONLY | O_CREAT | O_TRUNC), 00777);
			if (fd == JV_ERROR) {
				perror("open failed: ");
				close(fd);
				return 0;
			}
			snprintf(buf, 5, "%d", getpid());
			if (write(fd, buf, jv_strlen(buf)) == JV_ERROR) {
				perror("write failed: ");
				close(fd);
				return 0;
			}
			while (1) {
				sleep(5);
			}
		} else {
			printf("error.\n");
		}
		printf("Server Starting...\n");
	} else {
		int i = 1; /* "i=1" means will ignore the first argument: "filename.exe" */
		while (i < argc) {
			char *arg = argv[i++];
			if (*arg != '-') {
				printf(
						"The argument '%s' is error, Arguments must be the '-' of beginning.\n",
						arg);
				continue;
			} else {
				arg++; /* ignore first '-' symbol */

				if (jv_strcmp(arg, "stop") == 0) {
					char buf[6];
					jv_uint_t pid;
					int fd = open("c:/jv.pid", (O_RDONLY), 00400);
					if (fd == JV_ERROR) {
						printf(
								"Open 'c:/jv.pid' failed. Server had been stoped.");
						close(fd);
						return JV_ERROR;
					}
					if (read(fd, buf, 5) == JV_ERROR) {
						perror("read failed.");
						close(fd);
						return JV_ERROR;
					}
					buf[5] = '\0';
					pid = atoi(buf);
					if (kill(pid, SIGKILL) != JV_ERROR) {
						if (remove("c:/jv.pid") == JV_OK) {
							/* ignore */
						}
					}
					continue;
				}

				if (*arg == '?' || *arg == 'h' || jv_strcmp(arg, "help") == 0) {
					usage();
					continue;
				}

				if (*arg == 'v' || *arg == 'V') {
					puts("Jxva Framework Version 0.0.1");
					continue;
				}

				printf("The argument '%s' can't be supported.\n", --arg);
			}
		}
	}
	return JV_OK;
}
