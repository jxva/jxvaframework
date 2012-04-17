#ifndef _JV_MYSQL_H_INCLUDED_
#define _JV_MYSQL_H_INCLUDED_

#include <jv_core.h>

#define NO_CLIENT_LONG_LONG /* Don't support 'long long' */
#include <mysql.h>

typedef MYSQL jv_mysql_t;

jv_mysql_t *jv_mysql_create(void);

jv_int_t jv_mysql_execute(jv_mysql_t *mysql, char *sql);

jv_int_t jv_mysql_destroy(jv_mysql_t *mysql);

#endif /* _JV_MYSQL_H_INCLUDED_ */
