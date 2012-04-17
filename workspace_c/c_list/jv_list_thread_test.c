#include <jv_list.h>

int k;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

static void each(jv_list_t *list) {
	jv_block_t *block;
	jv_lump_t *lump;
	jv_uint_t i;

	fprintf(
			stdout,
			"----------------------------- Pool Mointor ------------------------------\n");

	jv_pool_each_block(list->pool, block, i)
	{
		fprintf(stdout, "pool block address:%u\n", (jv_uint_t) block);
	}

	jv_pool_each_lump(list->pool, lump, i)
	{
		fprintf(stdout, "lump address:%u, lump size:%u, lump->used:%u \n",
				(jv_uint_t) lump, lump->size, lump->used);
	}

}

void *test1(void *arg) {
	jv_uint_t i;
	jv_list_t *list = (jv_list_t *) arg;
	for (i = 0; i < 100; i++) {
		jv_node_t node;
		node.data = (void *) i;
		/* pthread_mutex_lock (&mutex); */
		jv_list_add(list, &node);
		/* pthread_mutex_unlock (&mutex); */
		k++;
	}
	return NULL;
}

void *test2(void *arg) {
	jv_uint_t i;
	jv_list_t *list = (jv_list_t *) arg;
	for (i = 0; i < 100; i++) {
		jv_node_t node;
		node.data = (void *) i;
		/* pthread_mutex_lock (&mutex); */
		jv_list_remove(list, &node);
		/* pthread_mutex_unlock (&mutex); */
	}
	return NULL;
}

#define SIZE 50

void jv_list_thread_test(void) {
	jv_list_t *list;
	jv_node_t *node;
	jv_uint_t i;
	pthread_t pid[SIZE];

	jv_pool_t *pool = jv_pool_create(4096);
	list = jv_list_create(pool);

	for (i = 0; i < SIZE; i = i + 2) {
		pthread_create(&pid[i], NULL, test1, list);
		pthread_create(&pid[i + 1], NULL, test2, list);
	}

	for (i = 0; i < SIZE; i++) {
		pthread_join(pid[i], NULL);
	}

	printf("----------------------------------\n");
	jv_list_reverse(list, node, i) {
		printf("get node [%p] = <%d>\n", (void *) node, (jv_uint_t) node->data);
	}
	/* each(list); */
	jv_list_destroy(list);

	each(list);

	printf("k value:%d\n", k);
	jv_pool_destroy(pool);
}
