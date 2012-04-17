#ifndef _JV_LIST_OOP_H_INCLUDED_
#define _JV_LIST_OOP_H_INCLUDED_

#include <jv_core.h>

typedef struct node_s node_t;
typedef struct list_s list_t;

struct node_s {
	void *data;
	node_t *next;
};

struct list_s {
	list_t *self;
	node_t *head;
	int length;
	void (*add)(void *node_t);
	void (*drop)(void *node_t);
	void (*destroy)();
	int (*size)();
	node_t *(*get)(int index);
	void (*print)();
};

list_t *list_create();

#endif
