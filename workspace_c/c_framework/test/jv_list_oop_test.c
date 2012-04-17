#include <assert.h>
#include <jv_oop_list.h>

void jv_list_oop_test(void) {
	node_t node;
	list_t *list = list_create();

	list->add("Apple");
	list->add("Borland");
	list->add("Cisco");
	list->add("Dell");
	list->add("Electrolux");
	list->add("FireFox");
	list->add("Google");

	list->print();

	printf("list size = %d\n", list->size());
	printf("node2 [%p] = <%s>\n", (void *) list->get(2),
			(char *) list->get(2)->data);

	node.data = "Electrolux";
	node.next = NULL;
	list->drop(&node);

	printf("node2 [%p] = <%s>\n", (void *) list->get(2),
			(char *) list->get(2)->data);
	node.data = "Cisco";
	node.next = NULL;
	list->drop(&node);

	list->print();
	printf("list size = %d\n", list->size());
	printf("node2 [%p] = <%s>\n", (void *) list->get(2),
			(char *) list->get(2)->data);

	list->destroy();
}
