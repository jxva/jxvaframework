#ifndef _JV_QUEUE_H_INCLUDED_
#define _JV_QUEUE_H_INCLUDED_

#include <jv_core.h>
#include <jv_pool.h>

typedef struct {
	size_t len;
	u_char *data;
} jv_str_t;

#define jv_string(str)              { sizeof(str) - 1, (u_char *) str }

typedef struct jv_queue_s jv_queue_t;

struct jv_queue_s {
	jv_queue_t *prev;
	jv_queue_t *next;
};

#define jv_queue_init(q)                                                     \
    (q)->prev = q;                                                            \
    (q)->next = q

#define jv_queue_empty(h)                                                    \
    (h == (h)->prev)

#define jv_queue_insert_head(h, x)                                           \
    (x)->next = (h)->next;                                                    \
    (x)->next->prev = x;                                                      \
    (x)->prev = h;                                                            \
    (h)->next = x

#define jv_queue_insert_after   jv_queue_insert_head

#define jv_queue_insert_tail(h, x)                                           \
    (x)->prev = (h)->prev;                                                    \
    (x)->prev->next = x;                                                      \
    (x)->next = h;                                                            \
    (h)->prev = x

#define jv_queue_head(h)                                                     \
    (h)->next

#define jv_queue_last(h)                                                     \
    (h)->prev

#define jv_queue_sentinel(h)                                                 \
    (h)

#define jv_queue_next(q)                                                     \
    (q)->next

#define jv_queue_prev(q)                                                     \
    (q)->prev

#define jv_queue_remove(x)                                                   \
    (x)->next->prev = (x)->prev;                                              \
    (x)->prev->next = (x)->next

#define jv_queue_split(h, q, n)                                              \
    (n)->prev = (h)->prev;                                                    \
    (n)->prev->next = n;                                                      \
    (n)->next = q;                                                            \
    (h)->prev = (q)->prev;                                                    \
    (h)->prev->next = h;                                                      \
    (q)->prev = n;

#define jv_queue_add(h, n)                                                   \
    (h)->prev->next = (n)->next;                                              \
    (n)->next->prev = (h)->prev;                                              \
    (h)->prev = (n)->prev;                                                    \
    (h)->prev->next = h;

#define jv_queue_data(q, type, link)                                         \
    (type *) ((u_char *) q - offsetof(type, link))

jv_queue_t *jv_queue_middle(jv_queue_t *queue);

void jv_queue_sort(jv_queue_t *queue,
		jv_int_t(*cmp)(const jv_queue_t *, const jv_queue_t *));

#endif /* _JV_QUEUE_H_INCLUDED_ */
