#include <stdio.h>
#include <stdlib.h>

#include <jv_core.h>
#include <jv_string.h>
#include <jv_log.h>

int main(void) {
	jv_str_t file = jv_null_string;

	(void) jv_log_create(file, JV_LOG_STDERR);

	jv_log_stderr("ffffffffffffffffffffff%s", "ddd");
	jv_log_debug("dddddddddddddddd%s", "ss");

	printf("insert num:\n");
	puts("dddd");
	return 0;
}

