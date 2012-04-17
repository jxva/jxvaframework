#define NO_CLIENT_LONG_LONG
#include <jv_mysql.h>

void jv_mysql_test(void) {
	jv_int_t i;
	jv_mysql_t *mysql = jv_mysql_create();
	i = jv_mysql_execute(mysql,
			"insert into tbl_url (href,description) values ('hello','world')");
	printf("insert num:%d\n", i);
	jv_mysql_destroy(mysql);
}
