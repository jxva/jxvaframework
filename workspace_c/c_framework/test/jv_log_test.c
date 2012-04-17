#include <jv_log.h>

void jv_log_test(void) {
	jv_int_t i;
	jv_str_t file = jv_null_string;
	/* jv_str_t file=jv_string("C:/1.log"); */
	(void) jv_log_create(file, JV_LOG_ALERT);

	for (i = 0; i < 10; i++) {
		jv_log_stderr("this a only printf %s", "test");
		jv_log_info("this a only info %s", "test");
		jv_log_debug("this a only debug %s", "test");
	}
	jv_log_destroy();
}
