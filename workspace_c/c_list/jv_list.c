#include <jv_list.h>
#include <assert.h>

static inline void __jv_list_add(jv_node_t *node, jv_node_t *before,
		jv_node_t *after);

static inline void __jv_list_add(jv_node_t *node, jv_node_t *before,
		jv_node_t *after) {
	before->next = after->prev = node;
	node->prev = before;
	node->next = after;
}

jv_list_t *jv_list_create(jv_pool_t *pool) {
	jv_list_t *list = jv_pool_alloc(pool,
			sizeof(jv_list_t) + sizeof(jv_node_t));
	jv_node_t *node = (jv_node_t *) (list + 1);
	node->next = node->prev = node;
	node->data = NULL;
	list->head = node;
	list->length = 0;
	list->pool = pool;
	return list;
}

jv_flag_t jv_list_is_empty(jv_list_t *list) {
	return list->head->prev == list->head->next ? JV_OK : JV_ERROR; /* || list->length == 0 */
}

jv_flag_t jv_list_add(jv_list_t *list, jv_node_t *node) {
	jv_node_t *head;
	jv_node_t *p;

	head = list->head;
	p = jv_pool_alloc(list->pool, sizeof(jv_node_t));
	p->data = node->data;

	__jv_list_add(p, list->head, list->head->next);
	printf("[add] value=%d,list lenght=%d\n", (jv_uint_t) p->data,
			list->length);
	/*
	 p->next=head->next;
	 p->prev=head;
	 head->next->prev=p;
	 head->next=p;
	 */
	list->length++;
	return JV_OK;
}

jv_flag_t jv_list_add_tail(jv_list_t *list, jv_node_t *node) {
	jv_node_t *head;
	jv_node_t *p;

	head = list->head;
	p = jv_pool_alloc(list->pool, sizeof(jv_node_t));
	p->data = node->data;

	__jv_list_add(p, list->head->prev, list->head);
	printf("[tail] value=%d,list lenght=%d\n", (jv_uint_t) p->data,
			list->length);
	/*
	 p->prev = head;
	 p->next = head->next;
	 head->next->prev = p;
	 head->next = p;
	 */
	list->length++;
	return JV_OK;
}

jv_flag_t jv_list_reset(jv_list_t *list) {
	jv_node_t *tmp;
	for (tmp = list->head->prev; tmp != list->head; tmp = tmp->prev) {
		tmp->prev->next = tmp->next;
		tmp->next->prev = tmp->prev;
		jv_pool_free(list->pool, tmp);
	}

	assert(list->head == list->head->prev);
	assert(list->head == list->head->next);

	printf("[reset] list\n");
	list->length = 0;
	return JV_OK;
}

jv_flag_t jv_list_destroy(jv_list_t* list) {
	jv_node_t *node = NULL;
	jv_uint_t i, n;
	for (i = 0, n = list->length; i < n; i++) {
		node = list->head->next;
		list->head->next = list->head->next->next;
		jv_pool_free(list->pool, node);
	}
	jv_pool_free(list->pool, list);
	return JV_OK;
}

jv_flag_t jv_list_delete(jv_list_t *list, jv_uint_t index) {
	if (index > list->length) {
		return JV_ERROR;
	} else {
		jv_uint_t i, count = 0, mid = list->length / 2;
		jv_node_t *tmp = list->head;
		if (index >= mid) {
			for (i = 0; i < list->length - index; i++) {
				tmp = tmp->prev;
				count++;
			}
		} else {
			for (i = 0; i < index + 1; i++) {
				tmp = tmp->next;
				count++;
			}
		}
		printf("[delete] %d,value=%u,list lenght=%d,count=%d\n", index,
				(jv_uint_t) tmp->data, list->length, count);
		tmp->prev->next = tmp->next;
		tmp->next->prev = tmp->prev;
		jv_pool_free(list->pool, tmp);
		list->length--;

		return JV_OK;
	}
}

jv_flag_t jv_list_remove(jv_list_t* list, jv_node_t* node) {
	jv_uint_t count = 0;
	jv_node_t *tmp;
	for (tmp = list->head->prev; tmp != list->head; tmp = tmp->prev) {
		if (tmp->data == node->data) {
			tmp->prev->next = tmp->next;
			tmp->next->prev = tmp->prev;
			printf("[remove] value=%d,list lenght=%d,count=%d\n",
					(jv_uint_t) tmp->data, list->length, count);
			jv_pool_free(list->pool, tmp);
			list->length--;
			return JV_OK;
		}
		count++;
	}
	return JV_ERROR;
}

jv_node_t *jv_list_get(jv_list_t *list, jv_uint_t index) {
	if (index > list->length) {
		return NULL;
	} else {
		jv_uint_t i, count = 0, mid = list->length / 2;
		jv_node_t *tmp = list->head;
		if (index >= mid) {
			for (i = 0; i < list->length - index; i++) {
				tmp = tmp->prev;
				count++;
			}
		} else {
			for (i = 0; i < index + 1; i++) {
				tmp = tmp->next;
				count++;
			}
		}
		printf("[get] %d,value=%s,list lenght=%d,count=%d\n", index,
				(char *) tmp->data, list->length, count);
		return tmp;
	}
}
