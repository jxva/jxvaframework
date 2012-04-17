#include <jv_stack.h>

int main(int argc, const char *argv[]) {
	jv_stack_node_t node, node1, node2;
	jv_stack_node_t *tmp1, *tmp2, *tmp3;

	const char *s[] = { "IBM", "Apple", "Borland", "Cisco", "Dell",
			"Electrolux", "FireFox", "Google", "Access", "MySQL", "SQL Server",
			"Oracle", "Jsp", "Asp", "PHP", "Java", NULL };
	const char **k;
	jv_int_t i;

	jv_str_t log_file = jv_string("C:/1.log");
	jv_log_t *log = jv_log_create(log_file, JV_LOG_STDERR);

	jv_pool_t *pool = jv_pool_create(4096);
	jv_stack_t *stack = jv_stack_create(pool);

	for (k = s; *k != NULL; k++) {
		node.data = (void *) *k;
		jv_log_info("push data:%s", *k);
		jv_stack_push(stack, &node);
	}

	tmp1 = jv_stack_get(stack, &node1);
	jv_log_info("node1 value:%s,%s", (char *) node1.data, (char *) tmp1->data);

	tmp2 = jv_stack_pop(stack, &node2);
	jv_log_info("node2 value:%s,%s", (char *) node2.data, (char *) tmp2->data);

	for (tmp3 = stack->top, i = 0; i < stack->length; i++, tmp3 = tmp3->prev) {
		jv_log_info("node data:%s", tmp3->data);

	}

	/*
	 for(tmp3 = stack->top; tmp3!=NULL ; tmp3 = tmp3->prev) {
	 printf("node data:%s\n", tmp3->data);
	 }
	 */

	jv_stack_clear(stack);

	jv_stack_each(stack,tmp3,i) {
		jv_log_info("node data:%s,%d", tmp3->data, i);
	}

	jv_stack_destroy(stack);
	jv_pool_destroy(pool);
	jv_log_destroy();
	return 0;
}
