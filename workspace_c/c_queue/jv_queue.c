#include <jv_queue.h>

/*
 * find the middle queue element if the queue has odd number of elements
 * or the first element of the queue's second part otherwise
 */

jv_queue_t *jv_queue_middle(jv_queue_t *queue) {
	jv_queue_t *middle, *next;

	middle = jv_queue_head(queue);

	if (middle == jv_queue_last(queue)) {
		return middle;
	}

	next = jv_queue_head(queue);

	for (;;) {
		middle = jv_queue_next(middle);

		next = jv_queue_next(next);

		if (next == jv_queue_last(queue)) {
			return middle;
		}

		next = jv_queue_next(next);

		if (next == jv_queue_last(queue)) {
			return middle;
		}
	}
}

/* the stable insertion sort */

void jv_queue_sort(jv_queue_t *queue,
		jv_int_t(*cmp)(const jv_queue_t *, const jv_queue_t *)) {
	jv_queue_t *q, *prev, *next;

	q = jv_queue_head(queue);

	if (q == jv_queue_last(queue)) {
		return;
	}

	for (q = jv_queue_next(q); q != jv_queue_sentinel(queue); q = next) {

		prev = jv_queue_prev(q);
		next = jv_queue_next(q);

		jv_queue_remove(q);

		do {
			if (cmp(prev, q) <= 0) {
				break;
			}

			prev = jv_queue_prev(prev);

		} while (prev != jv_queue_sentinel(queue));

		jv_queue_insert_after(prev, q)
		;
	}
}
