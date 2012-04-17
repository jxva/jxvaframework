#include <jv_oop_list.h>

static list_t *list = NULL;

static void add(void *node);
static void drop(void *node);
static void destroy();
static inline int size();
static node_t *get(int index);
static void print();

static node_t *get(int index) {
	int i;
	node_t *node = list->self->head;
	if (index > list->self->length) {
		return NULL;
	} else {
		for (i = 0; i < index; i++) {
			node = node->next;
		}
		return node;
	}
}

static void print() {
	node_t *t = list->self->head;
	int i;
	for (i = 0; i < list->self->length; i++) {
		printf("[%p] = <%s>\n", (void *) t->next, (char *) t->next->data);
		t = t->next;
	}
}

static void add(void *data) {
	node_t *current = malloc(sizeof(node_t));
	current->data = data;
	current->next = list->self->head->next;
	list->self->head->next = current;
	(list->self->length)++;
}

static void drop(void *node) {
	node_t *t = list->self->head;
	node_t *d = NULL;
	int i;
	for (i = 0; i < (list->self->length); i++) {
		d = list->self->head->next;
		if (d->data == ((node_t*) node)->data) {
			list->self->head->next = d->next;
			free(d);
			(list->self->length)--;
			break;
		} else {
			list->self->head = list->self->head->next;
		}
	}
	list->self->head = t;
}

static void destroy() {
	node_t *node = NULL;
	int i;
	for (i = 0; i < list->self->length; i++) {
		node = list->self->head;
		list->self->head = list->self->head->next;
		free(node);
	}
	list->self->length = 0;
	list = NULL;
}
static inline int size() {
	return list->self->length;
}

list_t *list_create() {
	node_t *node = malloc(sizeof(node_t));
	list = malloc(sizeof(list_t));
	list->head = node;
	list->add = add;
	list->drop = drop;
	list->destroy = destroy;
	list->length = 0;
	list->size = size;
	list->get = get;
	list->print = print;
	list->self = list;/* 用self指针将_list本身保存起来 */
	return list;
}
