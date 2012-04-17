#ifndef _JV_LOG_H_INCLUDED_
#define _JV_LOG_H_INCLUDED_

#include <jv_core.h>
#include <jv_string.h>
#include <jv_pool.h>
#include <jv_file.h>

#define JV_LOG_STDERR            0
#define JV_LOG_EMERG             1
#define JV_LOG_ALERT             2
#define JV_LOG_CRIT              3
#define JV_LOG_ERR               4
#define JV_LOG_WARN              5
#define JV_LOG_NOTICE            6
#define JV_LOG_INFO              7
#define JV_LOG_DEBUG             8

struct jv_log_s {
	jv_uint_t level;
	jv_file_t *file;
	u_char *buffer;
	u_char *pos;
	u_char *last;
};

jv_log_t *jv_log_create(const jv_str_t log_file, jv_uint_t level);

jv_int_t jv_log_stderr(const char *fmt, ...);

jv_int_t jv_log_emerg(const char *fmt, ...);

jv_int_t jv_log_alert(const char *fmt, ...);

jv_int_t jv_log_crit(const char *fmt, ...);

jv_int_t jv_log_error(const char *fmt, ...);

jv_int_t jv_log_warn(const char *fmt, ...);

jv_int_t jv_log_notice(const char *fmt, ...);

jv_int_t jv_log_info(const char *fmt, ...);

jv_int_t jv_log_debug(const char *fmt, ...);

jv_int_t jv_log_destroy(void);

#endif /* _JV_LOG_H_INCLUDED_ */
