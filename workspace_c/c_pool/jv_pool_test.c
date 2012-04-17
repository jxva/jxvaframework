#include <jv_pool.h>
#define DEBUG
#include <assert.h>
#include <time.h>
#include <pthread.h>

void each(jv_pool_t *pool) {
	jv_lump_t *lump;
	jv_block_t *block;
	jv_uint_t i;
	printf(
			"\n- - - - - - - - - - - -each lump - - - - - - - - - - - - - - - - - - - - \n\n");
	/*
	 lump=pool->lump;
	 do{
	 printf("lump address:%u, lump size:%u, lump->used:%u \n",(jv_uint_t)lump,lump->size,lump->used);
	 lump = lump->next;
	 }while(lump!=pool->lump);
	 */

	for (lump = pool->lump, i = 0; i == 0 || lump != pool->lump; i++, lump =
			lump->prev) {
		printf("lump address:%u, lump size:%u, lump->used:%u \n",
				(jv_uint_t) lump, lump->size, lump->used);
	}

	printf("-------------------each block----------------------\n");
	for (block = pool->first; block != NULL; block = block->next) {
		printf("AAblock address:%u\n", (jv_uint_t) block);
	}
}

void *test_pool(void *arg) {
	jv_pool_t *pool;
	jv_uint_t i;
	pool = jv_pool_create(1024);
	srand(time(NULL));
	for (i = 0; i < 100; i++) {
		jv_uint_t j = rand() % 100;
		jv_lump_t *lump = jv_pool_alloc(pool, j);
		if (lump == NULL) {
			printf("free is error.\n");
			continue;
		}
		jv_pool_free(pool, lump);
	}
	each(pool);
	jv_pool_destroy(pool);
	return NULL;
}

#define MAX 100

void test1(void) {
	jv_uint_t i;

	pthread_t pid[MAX];
	for (i = 0; i < MAX; i++) {
		printf("CCCCCCCC\n");
		pthread_create(&pid[i], NULL, test_pool, NULL);
	}

	for (i = 0; i < MAX; i++) {
		printf("JJJJJJJJJ\n");
		pthread_join(pid[i], NULL);
	}

	printf("DDDDDDDDD\n");

}

void test2(void) {
	jv_pool_t *pool;
	void *a, *b, *c, *d, *e, *f, *g, *h;
	jv_lump_t *lump;
	char *s;
	/* printf("sizeof(jv_pool_t):%u\n",sizeof(jv_pool_t)); */
	pool = jv_pool_create(128);
	printf("pool:%u,pos:%u,block:%u,block->next:%u,lump:%u,lump->next:%u\n",
			(jv_uint_t) pool, (jv_uint_t) pool->pos, (jv_uint_t) pool->first,
			(jv_uint_t) pool->first->next, (jv_uint_t) pool->lump,
			(jv_uint_t) pool->lump->next);
	printf("\n");

	assert((u_char *)pool->first+sizeof(jv_block_t)==(u_char *)pool);
	assert(
			(u_char *)pool->first+sizeof(jv_block_t)+sizeof(jv_pool_t)==(u_char *)pool->lump);
	assert((u_char *)pool+sizeof(jv_pool_t)==(u_char *)(pool->lump));

	a = jv_pool_alloc(pool, 40);
	printf("pool:%u,pos:%u,block:%u,block->next:%u,lump:%u,lump->next:%u\n",
			(jv_uint_t) pool, (jv_uint_t) pool->pos, (jv_uint_t) pool->first,
			(jv_uint_t) pool->first->next, (jv_uint_t) pool->lump,
			(jv_uint_t) pool->lump->next);
	printf("a address:%u,pos:%u\n", (jv_uint_t) a, (jv_uint_t) pool->pos);
	assert(
			(u_char *)a==(u_char *)pool+sizeof(jv_pool_t)+sizeof(jv_lump_t)+32+sizeof(jv_lump_t));

	printf(
			"\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");
	b = jv_pool_alloc(pool, 56);
	printf("pool:%u,pos:%u,block:%u,block->next:%u,lump:%u,lump->next:%u\n",
			(jv_uint_t) pool, (jv_uint_t) pool->pos, (jv_uint_t) pool->first,
			(jv_uint_t) pool->first->next, (jv_uint_t) pool->lump,
			(jv_uint_t) pool->lump->next);
	printf("b address:%u,pos:%u\n", (jv_uint_t) b, (jv_uint_t) pool->pos);
	assert(
			(u_char *)b==(u_char *)pool->first->next+sizeof(jv_block_t)+sizeof(jv_lump_t)+52);

	//each(pool);

	printf(
			"\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");
	c = jv_pool_alloc(pool, 32); /* (32,36) */
	printf("pool:%u,pos:%u,block:%u,block->next:%u,lump:%u,lump->next:%u\n",
			(jv_uint_t) pool, (jv_uint_t) pool->pos, (jv_uint_t) pool->first,
			(jv_uint_t) pool->first->next, (jv_uint_t) pool->lump,
			(jv_uint_t) pool->lump->next);
	printf("c address:%u,pos:%u\n", (jv_uint_t) c, (jv_uint_t) pool->pos);
	assert(
			(u_char *)c==(u_char *)pool->first->next+sizeof(jv_block_t)+sizeof(jv_lump_t));

	// each(pool);

	printf(
			"\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");
	d = jv_pool_alloc(pool, 8);
	printf("pool:%u,pos:%u,block:%u,block->next:%u,lump:%u,lump->next:%u\n",
			(jv_uint_t) pool, (jv_uint_t) pool->pos, (jv_uint_t) pool->first,
			(jv_uint_t) pool->first->next, (jv_uint_t) pool->lump,
			(jv_uint_t) pool->lump->next);
	printf("d address:%u,pos:%u\n", (jv_uint_t) d, (jv_uint_t) pool->pos);
	assert((u_char *)d==(u_char *)pool+sizeof(jv_pool_t)+sizeof(jv_lump_t)+24);

	printf(
			"\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");
	e = jv_pool_alloc(pool, 4); /* (4,8) */
	printf("pool:%u,pos:%u,block:%u,block->next:%u,lump:%u,lump->next:%u\n",
			(jv_uint_t) pool, (jv_uint_t) pool->pos, (jv_uint_t) pool->first,
			(jv_uint_t) pool->first->next, (jv_uint_t) pool->lump,
			(jv_uint_t) pool->lump->next);
	printf("e address:%u,pos:%u\n", (jv_uint_t) e, (jv_uint_t) pool->pos);
	assert((u_char *)e==(u_char *)pool+sizeof(jv_pool_t)+sizeof(jv_lump_t));

	printf(
			"\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");
	f = jv_pool_alloc(pool, 24);
	printf(
			"pool:%u,pos:%u,block:%u,block->next->next:%u,lump:%u,lump->next:%u\n",
			(jv_uint_t) pool, (jv_uint_t) pool->pos, (jv_uint_t) pool->first,
			(jv_uint_t) pool->first->next->next, (jv_uint_t) pool->lump,
			(jv_uint_t) pool->lump->next);
	printf("f address:%u,pos:%u\n", (jv_uint_t) f, (jv_uint_t) pool->pos);
	assert(
			(u_char *)f==(u_char *)pool->first->next->next+sizeof(jv_block_t)+sizeof(jv_lump_t)+68+sizeof(jv_lump_t));

	printf(
			"\n- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \n\n");
	g = jv_pool_alloc(pool, 104);
	printf(
			"pool:%u,pos:%u,block:%u,block->next->next:%u,lump:%u,lump->next:%u\n",
			(jv_uint_t) pool, (jv_uint_t) pool->pos, (jv_uint_t) pool->first,
			(jv_uint_t) pool->first->next->next, (jv_uint_t) pool->lump,
			(jv_uint_t) pool->lump->next);
	printf("g address:%u,pos:%u\n", (jv_uint_t) g, (jv_uint_t) pool->pos);
	assert(
			(u_char *)g==(u_char *)pool->first->next->next->next+sizeof(jv_block_t)+sizeof(jv_lump_t));

	each(pool);

	jv_pool_free(pool, c);
	lump = ((jv_lump_t *) ((u_char *) pool->first->next + sizeof(jv_block_t)));
	assert(lump->used==0);
	assert(lump->size==36);

	jv_pool_free(pool, f);
	lump = ((jv_lump_t *) ((u_char *) pool->first->next->next
			+ sizeof(jv_block_t) + sizeof(jv_lump_t) + 68));
	assert(lump->used==0);
	assert(lump->size==24);

	jv_pool_free(pool, a);
	lump = ((jv_lump_t *) ((u_char *) pool->first + sizeof(jv_block_t)
			+ sizeof(jv_pool_t) + sizeof(jv_lump_t) + 32));
	assert(lump->used==0);
	assert(lump->size==40);

	jv_pool_free(pool, e);
	lump = ((jv_lump_t *) ((u_char *) pool->first + sizeof(jv_block_t)
			+ sizeof(jv_pool_t)));
	assert(lump->used==0);
	assert(lump->size==8);

	jv_pool_free(pool, d);
	lump = ((jv_lump_t *) ((u_char *) pool->first + sizeof(jv_block_t)
			+ sizeof(jv_pool_t)));
	assert(lump->used==0);
	assert(lump->size==88);

	jv_pool_free(pool, b);
	lump = ((jv_lump_t *) ((u_char *) pool->first->next + sizeof(jv_block_t)));
	assert(lump->used==0);
	assert(lump->size==108);

	jv_pool_free(pool, g);
	lump = ((jv_lump_t *) ((u_char *) pool->first->next->next
			+ sizeof(jv_block_t)));
	assert(lump->used==0);
	assert(lump->size==108);

	each(pool);
	h = jv_pool_alloc(pool, 36);
	each(pool);
	jv_pool_free(pool, h);
	each(pool);
	s = malloc(4);
	assert(jv_pool_free(pool,s)==JV_ERROR);
	free(s);
	jv_pool_destroy(pool);
}

void test3(void) {
	jv_pool_t *pool;
	jv_uint_t i;
	pool = jv_pool_create(4096);
	each(pool);

	srand(time(NULL));
	for (i = 0; i < 2000; i++) {
		jv_uint_t j = rand() % 100;
		jv_lump_t *lump = jv_pool_alloc(pool, j);
		printf("apply memory size:%u\n", j);
		if (lump == NULL) {
			printf("free is error.\n");
			continue;
		}
		jv_pool_free(pool, lump);
	}
	each(pool);

	jv_pool_destroy(pool);
}

void test4(void) {
	jv_pool_t *pool;
	char *s;
	pool = jv_pool_create(4096);
	s = malloc(4);
	assert(jv_pool_free(pool,s)==JV_ERROR);

	free(s);
	jv_pool_destroy(pool);
}

static void *test_pool5(void *arg) {
	jv_pool_t *pool = (jv_pool_t *) arg;
	jv_uint_t i;

	srand(time(NULL));
	for (i = 0; i < 2000; i++) {
		jv_uint_t j = rand() % 100;
		jv_lump_t *lump = jv_pool_alloc(pool, j);
		if (lump == NULL) {
			printf("free is error.\n");
			continue;
		}
		jv_pool_free(pool, lump);
	}
	each(pool);
	return NULL;
}

void test5(void) {
	jv_uint_t i;
	jv_pool_t *pool;
	pthread_t pid[MAX];
	pool = jv_pool_create(1024);

	for (i = 0; i < MAX; i++) {
		printf("CCCCCCCC\n");
		pthread_create(&pid[i], NULL, test_pool5, pool);
	}

	for (i = 0; i < MAX; i++) {
		printf("JJJJJJJJJ\n");
		pthread_join(pid[i], NULL);
	}
	each(pool);
	jv_pool_destroy(pool);
	printf("DDDDDDDDD\n");

}

