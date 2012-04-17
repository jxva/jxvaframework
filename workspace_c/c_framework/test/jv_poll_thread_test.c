#include <jv_thread.h>
#include <jv_pool.h>

static jv_int_t hits;
static jv_thread_t *thread;
static jv_pool_t *pool;

static void each(jv_pool_t *pool) {
	jv_lump_t *lump;
	jv_block_t *block;
	jv_int_t i;
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
			lump->next) {
		printf("lump address:%u, lump size:%u, lump->used:%u,i:%u \n",
				(jv_uint_t) lump, lump->size, lump->used, i);
		if (i == 10) {
			break;
		}
	}

	printf(
			"\n- - - - - - - - - - - -each block - - - - - - - - - - - - - - - - - - - - \n\n");
	for (block = pool->first; block != NULL; block = block->next) {
		printf("AAblock address:%u\n", (jv_uint_t) block);
	}
}

static void *test(void *arg) {
	int connfd = (int) arg, n = 0;
	char head[512];
	static const char buf[] =
			"HTTP/1.1 200 OK\r\nServer: test/1.0\r\nDate: May,23 06:20:12 2012 GTC\r\nContent-type: text/html; charset=utf-8\r\nConnection: close\r\n\r\n"
					"<html><head><meta charset=\"utf-8\"><title>Test</title></head><body><h1>Hello,中国！</h1></body></html>";
	if ((n = recv(connfd, head, 512, 0)) > 0) {
		/* printf("SSSSSSSS:%s\n",head); */
	}
	if (n == 0) {
		printf("recv data is zero:%s\n", strerror(errno));
		return NULL;
	}
	if (n < 0) {
		printf("recv error:%s\n", strerror(errno));
		return NULL;
	}
	/*printf("head:%d\n",strlen(head));*/
	head[n] = '\0';

	if ((n = send(connfd, buf, sizeof(buf) - 1, 0)) > 0) {

	}
	if (n == 0) {
		printf("send data is zero:%s\n", strerror(errno));
	}
	if (n < 0) {
		printf("write is error:%s\n", strerror(errno));
	}
	close(connfd);
	if (hits++ % 2000 == 0) {
		printf("web id:%d,tid:%X,n:%d,hits:%d\n", connfd,
				(unsigned int) pthread_self(), n, hits);
		each(pool);
	}
	return NULL;
}

void jv_poll_thread_test(void) {
	int listenfd;
	struct sockaddr_in addr;

	if ((listenfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("socket error.\n");
		return;
	}

	bzero(&addr, sizeof(addr));
	addr.sin_family = AF_INET;
	addr.sin_port = htons(80);
	addr.sin_addr.s_addr = INADDR_ANY;

	if (bind(listenfd, (struct sockaddr *) &addr, sizeof(addr)) == -1) {
		perror("bind error.\n");
		return;
	}

	if (listen(listenfd, 511) == -1) {
		perror("listen error.\n");
		return;
	}
	/* printf("qaaaa\n"); */
	pool = jv_pool_create(1024);
	thread = jv_thread_create(pool, 50, 200, 0, NULL);

	/* printf("aaaaaaaaaaaa\n");
	 printf("thread size:%d,idle:%d\n",thread->thread_nthreads,thread->thread_idle);
	 */

	while (1) {
		int connfd;
		/* printf("A.thread size:%d,idle:%d\n",thread->thread_nthreads,thread->thread_idle); */
		if ((connfd = accept(listenfd, NULL, NULL)) == -1) {
			printf("accept is error");
			continue;
		}

		/* printf("AAAA:%d\n",connfd); */
		jv_thread_queue(thread, test, (void *) connfd);
		/* test((void *)connfd); */

		/* printf("B.thread size:%d,idle:%d\n",thread->thread_nthreads,thread->thread_idle); */
	}
	/* for(i = 0; i < 10; i++) {
	 //    jv_thread_queue(thread, test,(void *)i);
	 //}
	 */

	printf("thread size:%d,idle:%d\n", thread->count, thread->idle);
	printf("fdsafa\n");
	jv_pool_destroy(pool);
	jv_thread_wait(thread);
	jv_thread_destroy(thread);
}
