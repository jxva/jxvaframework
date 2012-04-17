#include <jv_time.h>

void jv_time_test(void) {
	time_t rawtime;
	time_t t = time(0);
	char local_time[64];
	struct tm *timeinfo;
	time(&rawtime);
	timeinfo = localtime(&rawtime);
	printf("%d\n", (int) rawtime);
	printf("The current date/time is: %s", asctime(timeinfo));

	strftime(local_time, sizeof(local_time), "%Y-%m-%d %X", localtime(&t));
	printf("%d\n", (int) time);
	printf("The current date/time is: %s", local_time);

	/*
	 outprint:
	 1331798173
	 The current date/time is: Thu Mar 15 15:56:13 2012
	 4220552
	 The current date/time is: 2012-03-15 15:56:13
	 Process returned 0 (0x0)   execution time : 0.031 s
	 Press any key to continue.
	 */
}
