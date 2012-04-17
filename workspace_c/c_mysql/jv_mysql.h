#ifndef _JV_MYSQL_H_INCLUDED_
#define _JV_MYSQL_H_INCLUDED_

#include <jv_core.h>
#include <jv_string.h>

#define NO_CLIENT_LONG_LONG
#include <mysql.h>

typedef MYSQL jv_mysql_t;
typedef MYSQL_RES jv_resultset_t;
typedef MYSQL_ROW jv_row_t;

jv_mysql_t *jv_mysql_create(u_char *charset);

jv_int_t jv_mysql_execute(jv_mysql_t *mysql, jv_str_t sql);

jv_resultset_t *jv_mysql_query(jv_mysql_t *mysql, jv_str_t sql);

jv_int_t jv_mysql_free(jv_resultset_t *resultset);

jv_int_t jv_mysql_destroy(jv_mysql_t *mysql);

#endif /* _JV_MYSQL_H_INCLUDED_ */
