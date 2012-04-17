#include <jv_log.h>

static jv_log_t _log;
static jv_file_t _file;

static jv_str_t err_levels[] = { jv_string("stderr"), jv_string("emerg"),
		jv_string("alert"), jv_string("crit"), jv_string("error"),
		jv_string("warn"), jv_string("notice"), jv_string("info"),
		jv_string("debug") };

static jv_int_t jv_log_add(jv_uint_t level, const char *fmt, va_list args);

static jv_int_t jv_log_add(jv_uint_t level, const char *fmt, va_list args) {
	time_t rawtime;
	time_t t = time(0);
	char local_time[20];
	struct tm *timeinfo;
	FILE *fd;
	time(&rawtime);
	timeinfo = localtime(&rawtime);

	strftime(local_time, sizeof(local_time), "%X", localtime(&t));

	fd = _log.file->fd;
	if (_log.level == 0) {
		fd = stderr;
	}
	fprintf(fd, "[%-6s %s] ", (char *) (err_levels[level].data), local_time);
	vfprintf(fd, fmt, args);
	fprintf(fd, "%s", "\n");

	return JV_OK;
}

jv_log_t *jv_log_create(const jv_str_t name, jv_uint_t level) {
	jv_str_t tmp;
	FILE *fd;
	if (name.data == NULL) {
		time_t rawtime;
		time_t t = time(0);
		u_char local_time[20];
		struct tm *timeinfo;
		time(&rawtime);
		timeinfo = localtime(&rawtime);

		strftime((char *) local_time, sizeof(local_time), "%Y-%m-%d.log",
				localtime(&t));
		jv_str_set(&tmp, local_time);
	} else {
		jv_str_set(&tmp, name.data);
	}

	fd = fopen((char *) tmp.data, "a+");
	if (fd == NULL) {
		printf("file '%s' open faild.", tmp.data);
		return NULL;
	}
	_file.fd = fd;
	_file.name = tmp;
	_log.file = &_file;
	_log.level = level;
	return &_log;
}

jv_int_t jv_log_stderr(const char *fmt, ...) {
	va_list args;
	if (_log.level > 0)
		return JV_OK;va_start(args, fmt);
	jv_log_add(0, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_emerg(const char *fmt, ...) {
	va_list args;
	if (_log.level > 1)
		return JV_OK;va_start(args, fmt);
	jv_log_add(1, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_alert(const char *fmt, ...) {
	va_list args;
	if (_log.level > 2)
		return JV_OK;va_start(args, fmt);
	jv_log_add(2, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_crit(const char *fmt, ...) {
	va_list args;
	if (_log.level > 3)
		return JV_OK;va_start(args, fmt);
	jv_log_add(3, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_error(const char *fmt, ...) {
	va_list args;
	if (_log.level > 4)
		return JV_OK;va_start(args, fmt);
	jv_log_add(4, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_warn(const char *fmt, ...) {
	va_list args;
	if (_log.level > 5)
		return JV_OK;va_start(args, fmt);
	jv_log_add(5, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_notice(const char *fmt, ...) {
	va_list args;
	if (_log.level > 6)
		return JV_OK;va_start(args, fmt);
	jv_log_add(6, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_info(const char *fmt, ...) {
	va_list args;
	if (_log.level > 7)
		return JV_OK;va_start(args, fmt);
	jv_log_add(7, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_debug(const char *fmt, ...) {
	va_list args;
	if (_log.level > 8)
		return JV_OK;va_start(args, fmt);
	jv_log_add(8, fmt, args);
	va_end(args);
	return JV_OK;
}

jv_int_t jv_log_destroy(void) {
	FILE *fd = _log.file->fd;
	fflush(fd);
	fclose(fd);
	return JV_OK;
}
