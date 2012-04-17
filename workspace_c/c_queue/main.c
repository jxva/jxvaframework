#include <jv_queue.h>

#define jv_strlen(s)                strlen((const char *) s)
#define jv_memcpy(dst, src, n)      (void *) memcpy(dst, src, n)

u_char *jv_strdup(jv_pool_t *pool, const u_char *s) {
	size_t n = jv_strlen(s) + 1;
	u_char *dst = jv_pool_alloc(pool, sizeof(u_char) * n);
	if (dst == NULL) {
		return NULL;
	}jv_memcpy(dst, s, n);
	return dst;
}

// 用雅虎的成员列表作为一个简单的例子
typedef struct yahoo_s {
	jv_queue_t queue;
} yahoo_t;

typedef struct yahoo_guy_s {
	jv_uint_t id;
	u_char* name;
	jv_queue_t queue;
} yahoo_guy_t;

// 排序使用的比较函数, 按照id的大小排序，id大放到到前面
jv_int_t yahoo_no_cmp(const jv_queue_t* p, const jv_queue_t* n) {
	yahoo_guy_t *pre, *next;
	pre = (yahoo_guy_t*) jv_queue_data(p, yahoo_guy_t, queue);
	next = (yahoo_guy_t*) jv_queue_data(n, yahoo_guy_t, queue);
	return ((pre->id > next->id) ? 1 : 0);
}

int main() {
	jv_pool_t* pool;
	yahoo_guy_t* guy;
	jv_queue_t* q;
	yahoo_t* yahoo;
	pool = jv_pool_create(1024); //初始化内存池
	int i;
	// 构建队列
	const jv_str_t names[] = { jv_string("rainx"), jv_string("xiaozhe"),
			jv_string("zhoujian") };
	const int ids[] = { 4611, 8322, 6111 };

	yahoo = jv_pool_alloc(pool, sizeof(yahoo_t));
	jv_queue_init(&yahoo->queue);
	//初始化queue

	for (i = 0; i < 3; i++) {
		guy = (yahoo_guy_t*) jv_pool_alloc(pool, sizeof(yahoo_guy_t));
		guy->id = ids[i];
		//guy->name = (char*) jv_palloc(pool, (size_t) (strlen(names[i]) + 1) );
		guy->name = (u_char*) jv_strdup(pool, names[i].data);

		jv_queue_init(&guy->queue);
		// 从头部进入队列
		jv_queue_insert_head(&yahoo->queue, &guy->queue);
	}

	// 从尾部遍历输出
	for (q = jv_queue_last(&yahoo->queue);
			q != jv_queue_sentinel(&yahoo->queue); q = jv_queue_prev(q)) {

		guy = jv_queue_data(q, yahoo_guy_t, queue);
		printf("No. %d guy in yahoo is %s \n", guy->id, guy->name);
	}

	// 找到位于队列中间(若队列有奇数个元素就是正中间，若有偶数个元素则为后半部分的首个元素)的元素输出
	{
		jv_queue_t *m = jv_queue_middle(&yahoo->queue);
		guy = jv_queue_data(m, yahoo_guy_t, queue);
		printf("Middle man: ID: %d, Name: %s\n", guy->id, guy->name);
	}

	// 排序从头部输出
	jv_queue_sort(&yahoo->queue, yahoo_no_cmp);
	printf("sorting....\n");
	for (q = jv_queue_prev(&yahoo->queue);
			q != jv_queue_sentinel(&yahoo->queue); q = jv_queue_last(q)) {

		guy = jv_queue_data(q, yahoo_guy_t, queue);
		printf("No. %d guy in yahoo is %s \n", guy->id, guy->name);
	}

	// 找到位于队列中间(若队列有奇数个元素就是正中间，若有偶数个元素则为后半部分的首个元素)的元素输出
	{
		jv_queue_t *m = jv_queue_middle(&yahoo->queue);
		guy = jv_queue_data(m, yahoo_guy_t, queue);
		printf("Middle man: ID: %d, Name: %s\n", guy->id, guy->name);
	}

	jv_pool_destroy(pool);
	return 0;
}
