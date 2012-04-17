#ifndef _JV_CORE_H_INCLUDED_
#define _JV_CORE_H_INCLUDED_

#include <jv_init.h>

#define JV_OK       0
#define JV_ERROR    -1

typedef struct jv_log_s jv_log_t;
typedef struct jv_pool_s jv_pool_t;

typedef struct jv_file_s jv_file_t;
typedef struct jv_open_file_s jv_open_file_t;

typedef int jv_int_t;
typedef unsigned int jv_uint_t;
typedef int jv_flag_t;

/*
 #include <jv_string.h>
 #include <jv_daemon.h>
 #include <jv_atomic.h>
 #include <jv_file.h>
 #include <jv_hash.h>
 #include <jv_thread_pool.h>
 #include <jv_sort.h>
 #include <jv_quick_sort.h>
 #include <jv_pool.h>
 #include <jv_merge_sort.h>
 #include <jv_log.h>
 #include <jv_list_oop.h>
 #include <jv_list.h>
 #include <jv_http.h>


 #include <jv_pool.h>
 #include <jv_string.h>
 #include <jv_log.h>
 */
#define LF     (u_char) 10
#define CR     (u_char) 13
#define CRLF   "\x0d\x0a"

#define jv_abs(a)       (((a) >= 0) ? (a) : - (a))
#define jv_max(a, b)    ((a < b) ? (a) : (b))
#define jv_min(a, b)    ((a > b) ? (a) : (b))

#endif /* _JV_CORE_H_INCLUDED_ */
