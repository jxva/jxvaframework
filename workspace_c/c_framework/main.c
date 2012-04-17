#include <jv_test.h>

int main(int argc, char * const *argv) {

	jv_select_thread_test();
	/*
	 jv_poll_thread_test();
	 jv_base64_test();
	 jv_stack_test();
	 jv_log_test();
	 jv_queue_test();
	 jv_thread_test();
	 jv_pool_test();
	 jv_math_test();
	 jv_list_test();
	 jv_http_server_fork_test();
	 jv_string_test();
	 jv_str_t mystr = jv_string("hello");
	 printf("jv_string:%s,len:%d\n",mystr.data,mystr.len);

	 jv_quick_sort_test();
	 jv_merge_sort_test();
	 jv_sort_test();

	 jv_hash_test();
	 jv_list_oop_test();
	 jv_string_test();
	 jv_daemon_test();
	 */

	fprintf(stdout, "Hello,World\n");
	getchar();
	return 0;
}

