#ifndef _JV_LIST_H_INCLUDED_
#define _JV_LIST_H_INCLUDED_

#include <jv_core.h>
#include <jv_pool.h>

typedef struct jv_node_s jv_node_t;
typedef struct jv_list_s jv_list_t;

struct jv_node_s {
	void *data;
	jv_node_t *prev;
	jv_node_t *next;
};

struct jv_list_s {
	jv_uint_t length;
	jv_node_t *head;
	jv_pool_t *pool;
};

jv_list_t *jv_list_create(jv_pool_t *pool);

jv_flag_t jv_list_add(jv_list_t *list, jv_node_t *node);
jv_flag_t jv_list_add_tail(jv_list_t *list, jv_node_t *node);

#define jv_list_each(list,node,i) \
for(node=(list)->head->next,i=0;node!=(list)->head;i++,node=node->next)

#define jv_list_reverse(list,node,i) \
for(node=(list)->head->prev,i=0;node!=(list)->head;i++,node=node->prev)

jv_flag_t jv_list_length(jv_list_t *list);

jv_node_t *jv_list_get(jv_list_t *list, jv_uint_t index);
jv_flag_t jv_list_remove(jv_list_t *list, jv_node_t *node);
jv_flag_t jv_list_delete(jv_list_t *list, jv_uint_t index);

jv_flag_t jv_list_is_empty(jv_list_t *list);

jv_flag_t jv_list_reset(jv_list_t *list);
jv_flag_t jv_list_destroy(jv_list_t *list);

#endif
