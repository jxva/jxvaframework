#include <jv_mysql.h>

static jv_mysql_t jv_mysql;

jv_mysql_t *jv_mysql_create(u_char *charset) {

	if (mysql_init(&jv_mysql) == NULL) {
		fprintf(stdout, "Failed to init\n");
		return NULL;
	}

	mysql_options(&jv_mysql, MYSQL_SET_CHARSET_NAME, (char *) charset);

	if (mysql_real_connect(&jv_mysql, "127.0.0.1", "root", "pass1009", "jv_db",
			0, NULL, 0) == NULL) {
		fprintf(stdout, "Failed to connect to database:%d,%s\n",
				mysql_errno(&jv_mysql), mysql_error(&jv_mysql));
		return NULL;
	}

	return &jv_mysql;
}

jv_int_t jv_mysql_execute(jv_mysql_t* mysql, jv_str_t sql) {
	if (mysql_real_query(mysql, (char *) sql.data, sql.len) == 0) {
		fprintf(stdout, "execute sql: %s\n", sql.data);
		return mysql_affected_rows(mysql);
	} else {
		fprintf(stdout, "execute failed: %d,%s\n", mysql_errno(&jv_mysql),
				mysql_error(&jv_mysql));
		return JV_ERROR;
	}
}

jv_resultset_t *jv_mysql_query(jv_mysql_t *mysql, jv_str_t sql) {
	if (mysql_real_query(mysql, (char *) sql.data, sql.len) == 0) {
		fprintf(stdout, "query sql: %s\n", sql.data);
		return mysql_store_result(mysql);
	} else {
		fprintf(stdout, "query failed: %d,%s\n", mysql_errno(&jv_mysql),
				mysql_error(&jv_mysql));
		return NULL;
	}
}

jv_int_t jv_mysql_free(jv_resultset_t *resultset) {
	if (resultset != NULL) {
		mysql_free_result(resultset);
	}
	return JV_OK;
}

jv_int_t jv_mysql_destroy(jv_mysql_t* mysql) {
	if (mysql == NULL) {
		return JV_ERROR;
	} else {
		mysql_close(mysql);
		return JV_OK;
	}
}
