#include <jv_stack.h>

jv_stack_t *jv_stack_create(jv_pool_t *pool) {
	jv_stack_t *stack;
	stack = jv_pool_alloc(pool, sizeof(jv_stack_t));
	stack->top = stack->bottom = NULL;
	stack->length = 0;
	stack->pool = pool;
	return stack;
}

jv_int_t jv_stack_clear(jv_stack_t *stack) {
	jv_stack_node_t *tmp;
	for (tmp = stack->top; tmp != NULL; tmp = stack->top) {
		stack->top = stack->top->prev;
		jv_log_debug("stack clear node: %s", tmp->data);
		jv_pool_free(stack->pool, tmp);
	}
	stack->top = stack->bottom = NULL;
	stack->length = 0;
	return JV_OK;
}

jv_int_t jv_stack_empty(jv_stack_t *stack) {
	return stack->length == 0;
}

jv_uint_t jv_stack_size(jv_stack_t *stack) {
	return stack->length;
}

jv_stack_node_t *jv_stack_get(jv_stack_t *stack, jv_stack_node_t *node) {
	if (stack->length == 0)
		return NULL;

	node->data = stack->top->data;
	node->prev = stack->top->prev;
	node->next = stack->top->next;

	/* jv_memcpy(node,stack->top,sizeof(*node)); */
	return node;
}

jv_int_t jv_stack_push(jv_stack_t *stack, jv_stack_node_t *node) {
	jv_stack_node_t *tmp = jv_pool_alloc(stack->pool, sizeof(jv_stack_node_t));
	tmp->data = node->data;
	tmp->next = NULL;
	if (stack->top == NULL) {
		tmp->prev = NULL;
		stack->bottom = tmp;
	} else {
		tmp->prev = stack->top;
		stack->top->next = tmp;
	}
	stack->top = tmp;
	stack->length++;
	return JV_OK;
}

jv_stack_node_t *jv_stack_pop(jv_stack_t *stack, jv_stack_node_t *node) {
	jv_stack_node_t *tmp;
	tmp = stack->top;
	if (tmp == NULL) {
		return NULL;
	} else {
		node->data = tmp->data;
		stack->length--;

		if (stack->length == 1) {
			stack->top = stack->bottom = NULL;
		} else {
			stack->top->prev->next = stack->top->next;
			stack->top = stack->top->prev;
		}
		jv_pool_free(stack->pool, tmp);
		return node;
	}
}

jv_int_t jv_stack_destroy(jv_stack_t *stack) {
	jv_stack_node_t *tmp;
	for (tmp = stack->top; tmp != NULL; tmp = stack->top) {
		stack->top = stack->top->prev;
		jv_log_debug("stack destroy node: %s", tmp->data);
		jv_pool_free(stack->pool, tmp);
	}
	jv_pool_free(stack->pool, stack);
	return JV_OK;
}
