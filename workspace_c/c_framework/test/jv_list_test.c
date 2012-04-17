#include <assert.h>
#include <jv_list.h>

void dump(jv_list_t *list) {
	jv_list_print(list);
	printf("list size = %d\n", list->length);
	if (list->length >= 3) {
		jv_node_t node;
		jv_node_t *tmp = jv_list_get(list, &node, 3);
		printf("get node [%p] = <%s>\n", (void *) &node, (char *) node.data);
		printf("get node [%p] = <%s>\n", (void *) tmp, (char *) tmp->data);

	}
}

#define jv_node(s)  {(s),NULL,NULL}

void jv_list_test(void) {
	jv_node_t n[16];
	int i = 0;
	jv_list_t *list;

	char *s[] = { "IBM", "Apple", "Borland", "Cisco", "Dell", "Electrolux",
			"FireFox", "Google", "Access", "MySQL", "SQL Server", "Oracle",
			"Jsp", "Asp", "PHP", "Java" };

	printf("sizeof(jv_node_t) = %d, sizeof(jv_list_t) = %d \n",
			sizeof(jv_node_t), sizeof(jv_list_t));

	list = jv_list_create();

	for (; i < 16; i++) {
		n[i].data = s[i];
		n[i].prev = n[i].next = NULL;
		/* if(i<6)jv_list_clear(list); */
		if (i < 10) {
			jv_list_add_tail(list, &n[i]);
		} else {
			jv_list_add(list, &n[i]);
		}
	}

	dump(list);

	/*

	 dump(list);
	 jv_list_reverse(list);
	 jv_list_reverse(list);


	 jv_list_clear(list);

	 jv_list_delete(list,6);

	 jv_list_remove(list,&n[4]);
	 jv_list_delete(list,4);

	 jv_list_remove(list,&n[3]);
	 jv_list_delete(list,10);

	 jv_list_remove(list,&n[10]);


	 dump(list);
	 jv_list_remove(list,&n[5]);

	 jv_list_delete(list,3);

	 dump(list);
	 jv_list_reverse(list);

	 printf("%d\n",jv_list_is_empty(list));

	 printf("%X,%X,%X,%X,%X\n",list,list->self,list->self->head,list->self->head->prev,list->self->head->next);

	 jv_list_clear(list);

	 printf("%X,%X,%X,%X,%X\n",list,list->self,list->self->head,list->self->head->prev,list->self->head->next);

	 printf("%d\n",jv_list_is_empty(list));

	 dump(list);

	 dump(list);
	 jv_list_reverse(list);
	 */
	jv_list_destroy(list);

	/*printf("%X,%X,%X,%X,%X\n",list,list->self,list->self->head,list->self->head->prev,list->self->head->next);*/
}
