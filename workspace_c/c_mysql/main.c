#include <jv_mysql.h>

int main(void) {
	jv_int_t i;
	jv_mysql_t *mysql;
	jv_resultset_t *rs;
	jv_str_t sql;

	if ((mysql = jv_mysql_create((u_char *) "gbk")) == NULL) {
		fprintf(stdout, "mysql create faild.\n");
		return -1;
	}jv_str_set(
			&sql,
			"insert into tbl_test (username,email) values ('h中国ello','china@world.com')");

	i = jv_mysql_execute(mysql, sql);
	fprintf(stdout, "insert num:%d\n", i);

	jv_str_set(&sql, "select * from tbl_test limit 0,3");
	rs = jv_mysql_query(mysql, sql);
	if (rs != NULL) {
		int j = 1;
		int rows = mysql_num_rows(rs);
		int cols = mysql_num_fields(rs);

		jv_row_t row;
		fprintf(stdout, "共 %d 行记录，每行 %d 个字段。\n", rows, cols);

		while (row = mysql_fetch_row(rs)) {
			int i = 0;
			printf("\n 第 %d 行：\n", j);
			for (i = 0; i < cols; i++) {
				fprintf(stdout, " %s", row[i]);
			}
			j++;
		}
		jv_mysql_free(rs);
	}

	jv_mysql_destroy(mysql);
	return 0;
}
