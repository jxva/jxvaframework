#include <jv_pool.h>

static void *jv_pool_alloc_block(jv_pool_t *pool, size_t size);

jv_pool_t *jv_pool_create(jv_uint_t size) {
	jv_pool_t *pool;
	jv_block_t *block;
	jv_lump_t *lump;
	u_char *cp;

	size = jv_align(size, JV_ALIGNMENT);

	if (size > JV_BLOCK_MAX_SIZE) {
		size = JV_BLOCK_MAX_SIZE;
	}

	if (size < JV_BLOCK_MIN_SIZE) {
		size = JV_BLOCK_MIN_SIZE;
	}

	cp = malloc(size);
	if (cp == NULL) {
		printf("malloc error in creating pool.\n");
		return NULL;
	}
	printf("**************** malloc cp address:%u\n", (jv_uint_t) cp);

	block = (jv_block_t *) cp;
	pool = (jv_pool_t *) (cp + sizeof(jv_block_t));
	lump = (jv_lump_t *) (cp + sizeof(jv_block_t) + sizeof(jv_pool_t));
	lump->size = size - sizeof(jv_lump_t) - sizeof(jv_block_t)
			- sizeof(jv_pool_t);
	lump->used = 0;

	pool->max = size;
	block->next = NULL;
	pool->first = pool->last = block;
	pool->pos = pool->lump = lump->next = lump->prev = lump;
	return pool;
}

void *jv_pool_alloc(jv_pool_t *pool, jv_uint_t size) {
	jv_lump_t *p;
	jv_uint_t i;
	size = jv_align(size, JV_ALIGNMENT);

	if (size > pool->max - sizeof(jv_block_t) - sizeof(jv_lump_t)) { /* 必须保证一个Block空闲的内存能存放jv_block_t与jv_lump_t类型的长度 */
		printf("申请的空间过大\n");
		return NULL;
	}

	for (p = pool->pos, i = 0; i == 0 || p != pool->pos; i++, p = p->next) {
		if (p->used == 0 && p->size >= size) { /* first fit */
			if (p->size <= size + sizeof(jv_lump_t)) { /* best fit  */
				p->used = 1;
				return (void *) (p + 1);
			} else {/* 将所找的Lump分成两个，即在Lump后面新增一个Lump，分配给所申请者 */
				jv_lump_t *lump;
				lump = (jv_lump_t *) ((u_char *) p + p->size - size);
				lump->size = size;
				lump->used = 1;
				lump->next = p->next;
				p->next = lump;

				/* prev add */
				lump->next->prev = lump;
				lump->prev = p;

				p->size -= size + sizeof(jv_lump_t);
				/*
				 if using blow statement is error,must be use p->size -= size + sizeof(jv_lumpt_t);
				 p->size -= size - sizeof(jv_lump_t);
				 */
				return (void *) (lump + 1);
			}
		}
	}

	return jv_pool_alloc_block(pool, size);
}

void *jv_pool_calloc(jv_pool_t *pool, size_t size) {
	void *p;
	p = jv_pool_alloc(pool, size);
	if (p) {
		(void) jv_memzero(p, size);
	}
	return p;
}

void *jv_pool_alloc_block(jv_pool_t *pool, size_t size) {
	jv_block_t *block;
	jv_lump_t *tail = pool->lump->prev;

	u_char *cp = malloc(pool->max);
	if (cp == NULL) {
		printf("malloc error in allocing block.\n");
		return NULL;
	}
	printf("****************  需要申请更多空间malloc cp address:%u\n", (jv_uint_t) cp);
	block = (jv_block_t *) cp;

	block->next = pool->last->next;
	pool->last->next = block;
	pool->last = block;

	if (size > pool->max - sizeof(jv_block_t) - 2 * sizeof(jv_lump_t)) { /* 新申请的block，仅生成一个lump节点,并将block的所有空闲内存分配给新节点 */
		jv_lump_t *lump;
		lump = (jv_lump_t *) (cp + sizeof(jv_block_t));
		lump->size = pool->max - sizeof(jv_block_t) - sizeof(jv_lump_t);
		lump->used = 1;

		lump->next = pool->lump;
		tail->next = lump;

		/* prev add */
		lump->next->prev = lump;
		lump->prev = tail;

		pool->pos = lump;
		return (void *) (lump + 1);
	} else { /* 新申请的block，生成两个lump节点，并将后一个lump节点的size个空闲空间分配给申请者，前一个lump节点的空闲空间保留 */
		jv_lump_t *lump, *alloc;
		lump = (jv_lump_t *) (cp + sizeof(jv_block_t));
		lump->size = pool->max - sizeof(jv_block_t) - sizeof(jv_lump_t) - size
				- sizeof(jv_lump_t);
		lump->used = 0;

		alloc =
				(jv_lump_t *) ((u_char *) lump + sizeof(jv_lump_t) + lump->size);
		alloc->size = size;
		alloc->used = 1;

		alloc->next = pool->lump;
		lump->next = alloc;
		tail->next = lump;

		/* prev add */
		alloc->next->prev = alloc;
		alloc->prev = lump;
		lump->prev = tail;

		pool->pos = lump;
		return (void *) (alloc + 1);
	}
}

jv_int_t jv_pool_destroy(jv_pool_t *pool) {
	jv_block_t *block, *tmp = NULL;
	jv_uint_t i = 0;
	for (block = pool->first; block != NULL; block = tmp) {
		tmp = block->next;
		printf("destroy block:%u\n", (jv_uint_t) block);
		free(block);
		i++;
	}
	printf("jv_pool_destroy block count:%d\n", i);
	return i;
}

jv_int_t jv_pool_free(jv_pool_t *pool, void *ptr) {
	jv_lump_t *prior, *idle;
	idle = (jv_lump_t *) ((u_char *) ptr - sizeof(jv_lump_t));

	if (idle->used != 1) { /* 待释放的指针不在内存池中 */
		return JV_ERROR;
	}
	idle->used = 0;
	pool->pos = idle;

	if ((u_char *) idle + sizeof(jv_lump_t) + idle->size
			== (u_char *) idle->next) { /* 与下一个节点相临 */
		if (idle->next->used == 0) { /* 下一个节点空闲，则合并 */
			idle->size = idle->size + sizeof(jv_lump_t) + idle->next->size;
			idle->next = idle->next->next;
			idle->next->prev = idle;
			pool->pos = idle;
		}
	}

	prior = idle->prev;

	if ((u_char *) prior + sizeof(jv_lump_t) + prior->size == (u_char *) idle) { /* 与上一个节点相临 */
		if (prior->used == 0) { /* 上一个节点空闲，则合并 */
			prior->size = prior->size + sizeof(jv_lump_t) + idle->size;
			prior->next = prior->next->next;
			prior->next->prev = prior;
			pool->pos = prior;
		}
	}

	return JV_OK;
}
