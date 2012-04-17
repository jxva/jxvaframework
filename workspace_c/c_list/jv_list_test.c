#include <assert.h>
#include <jv_list.h>
#include <jv_string.h>

void dump(jv_list_t *list) {
	jv_node_t *node;
	jv_uint_t i;
	jv_list_each(list, node, i) {
		printf("get node [%p] = <%s>\n", (void *) node, (char *) node->data);
	}
	printf("list size = %d\n", list->length);
	if (list->length >= 3) {

		jv_node_t *tmp = jv_list_get(list, 3);
		printf("get node [%p] = <%s>\n", (void *) tmp, (char *) tmp->data);

	}
}

void jv_list_test(void) {
	jv_node_t n[16];
	jv_uint_t i;
	jv_list_t *list;
	jv_node_t *node;
	jv_pool_t *pool;
	char *s[] = { "IBM", "Apple", "Borland", "Cisco", "Dell", "Electrolux",
			"FireFox", "Google", "Access", "MySQL", "SQL Server", "Oracle",
			"Jsp", "Asp", "PHP", "Java" };

	printf("sizeof(jv_node_t) = %d, sizeof(jv_list_t) = %d \n",
			sizeof(jv_node_t), sizeof(jv_list_t));
	pool = jv_pool_create(4096);
	list = jv_list_create(pool);

	for (i = 0; i < 16; i++) {
		n[i].data = s[i];
		/* n[i].prev = n[i].next = NULL; */
		if (i < 10) {
			jv_list_add_tail(list, &n[i]);
		} else {
			jv_list_add(list, &n[i]);
		}
	}

	dump(list);

	jv_list_reset(list);

	for (i = 0; i < 16; i++) {
		n[i].data = s[i];
		/* n[i].prev = n[i].next = NULL; */
		if (i < 10) {
			jv_list_add_tail(list, &n[i]);
		} else {
			jv_list_add(list, &n[i]);
		}
	}

	printf("----------------------------------\n");
	jv_list_reverse(list, node, i) {
		printf("get node [%p] = <%s>\n", (void *) node, (char *) node->data);
	}
	printf("list size = %d\n", list->length);

	/* jv_list_clear(list); */

	jv_list_delete(list, 6);

	jv_list_remove(list, &n[4]);
	jv_list_delete(list, 4);

	jv_list_remove(list, &n[3]);
	jv_list_delete(list, 10);

	jv_list_remove(list, &n[10]);

	dump(list);
	printf("list size = %d\n", list->length);
	jv_list_remove(list, &n[5]);

	jv_list_delete(list, 3);

	dump(list);
	printf("----------------------------------\n");
	jv_list_reverse(list, node, i) {
		printf("get node [%p] = <%s>\n", (void *) node, (char *) node->data);
	}
	printf("list size = %d\n", list->length);

	printf("is empty %d\n", jv_list_is_empty(list));

	printf("%u,%u,%u,%u\n", (jv_uint_t) list, (jv_uint_t) list->head,
			(jv_uint_t) list->head->prev, (jv_uint_t) list->head->next);

	jv_list_reset(list);

	printf("%u,%u,%u,%u\n", (jv_uint_t) list, (jv_uint_t) list->head,
			(jv_uint_t) list->head->prev, (jv_uint_t) list->head->next);

	printf("is empty: %d\n", jv_list_is_empty(list));

	dump(list);

	printf("----------------------------------\n");
	jv_list_reverse(list, node, i) {
		printf("get node [%p] = <%s>\n", (void *) node, (char *) node->data);
	}

	jv_list_destroy(list);

	printf("%u,%u,%u,%u\n", (jv_uint_t) list, (jv_uint_t) list->head,
			(jv_uint_t) list->head->prev, (jv_uint_t) list->head->next);

	jv_pool_destroy(pool);
}
