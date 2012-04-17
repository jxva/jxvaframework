#ifndef _JV_TEST_H_INCLUDED_
#define _JV_TEST_H_INCLUDED_

#include <assert.h>
#include <jv_core.h>

#include <jv_sort.h>
#include <jv_socket.h>
#include <jv_quick_sort.h>
#include <jv_pool.h>
#include <jv_atomic.h>
#include <jv_oop_list.h>
#include <jv_merge_sort.h>
#include <jv_log.h>
#include <jv_list.h>
#include <jv_string.h>
#include <jv_hash.h>
#include <jv_file.h>
#include <jv_daemon.h>
#include <jv_http_server.h>
#include <jv_http_client.h>
#include <jv_thread.h>
#include <jv_pool.h>
#include <jv_db.h>
#include <jv_math.h>

void jv_list_oop_test(void);
void jv_list_test(void);
void jv_string_test(void);
void jv_sort_test(void);
void jv_merge_sort_test(void);
void jv_quick_sort_test(void);
void jv_hash_test(void);
void jv_daemon_test(void);
void jv_http_server_test(void);
void jv_http_server_thread_test(void);
void jv_http_server_fork_test(void);
void jv_http_server_select_test(void);
void jv_http_server_poll_test(void);
void jv_http_client_test(void);
void jv_thread_test(void);
void jv_select_thread_test(void);
void jv_poll_thread_test(void);
void jv_pool_test(void);
void jv_math_test(void);
void jv_queue_test(void);
void jv_log_test(void);
void jv_stack_test(void);
void jv_base64_test(void);
void jv_mysql_test(void);

#endif /* _JV_TEST_H_INCLUDED_ */
