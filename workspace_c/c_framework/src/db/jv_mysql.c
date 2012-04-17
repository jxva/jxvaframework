#include <jv_mysql.h>

static jv_mysql_t jv_mysql;

jv_mysql_t *jv_mysql_create() {

	if (mysql_init(&jv_mysql) == NULL) {
		printf("Failed to init\n");
		return NULL;
	}

	if (mysql_real_connect(&jv_mysql, "10.204.75.234", "ztemt", "ztemt",
			"task4", 0, NULL, 0) == NULL) {
		printf("Failed to connect to database:%d,%s\n", mysql_errno(&jv_mysql),
				mysql_error(&jv_mysql));
		return NULL;
	}

	return &jv_mysql;
}

jv_int_t jv_mysql_execute(jv_mysql_t* mysql, char *sql) {
	jv_int_t i;
	i = mysql_query(mysql, sql);
	if (i == 0) {
		fprintf(stdout, "execute sql: %s\n", sql);
		return mysql_affected_rows(mysql);
	}
	return JV_ERROR;
}

jv_int_t jv_mysql_destroy(jv_mysql_t* mysql) {
	if (mysql == NULL) {
		return JV_ERROR;
	} else {
		mysql_close(mysql);
		return JV_OK;
	}
}
