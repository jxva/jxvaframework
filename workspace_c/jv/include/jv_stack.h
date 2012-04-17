#ifndef _JV_STACK_H_INCLUDED_
#define _JV_STACK_H_INCLUDED_

#include <jv_core.h>
#include <jv_pool.h>
#include <jv_string.h>
#include <jv_log.h>

typedef struct jv_stack_s jv_stack_t;
typedef struct jv_stack_node_s jv_stack_node_t;

struct jv_stack_node_s {
	void *data;
	jv_stack_node_t *prev;
	jv_stack_node_t *next;
};

struct jv_stack_s {
	jv_uint_t length;
	jv_stack_node_t *bottom;
	jv_stack_node_t *top;
	jv_pool_t *pool;
};

jv_stack_t *jv_stack_create(jv_pool_t *pool);

jv_int_t jv_stack_clear(jv_stack_t *stack);

jv_int_t jv_stack_empty(jv_stack_t *stack);

jv_uint_t jv_stack_size(jv_stack_t *stack);

jv_stack_node_t *jv_stack_get(jv_stack_t *stack, jv_stack_node_t *node);

jv_int_t jv_stack_push(jv_stack_t *stack, jv_stack_node_t *node);

jv_stack_node_t *jv_stack_pop(jv_stack_t *stack, jv_stack_node_t *node);

jv_int_t jv_stack_destroy(jv_stack_t *stack);

#define jv_stack_each(stack,node,i) \
for((node) = (stack)->top,(i) = 0; (node)!=NULL; (i)++,(node) = (node)->prev)

#endif /* _JV_STACK_H_INCLUDED_ */
