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

void jv_select_thread_test(void) {
	int listenfd;
	struct sockaddr_in addr;
	int maxfd, maxi, i, sockfd, nready, client[FD_SETSIZE];
	fd_set rset, wset, eset, allset;

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

	maxfd = listenfd; /* initialize */
	maxi = -1; /* index into client[] array */

	for (i = 0; i < FD_SETSIZE; i++) {
		client[i] = -1; /* -1 indicates available entry */
	}FD_ZERO(&allset);
	FD_SET(listenfd, &allset);

	for (;;) {
		rset = allset; /* structure assignment */
		wset = allset;
		eset = allset;
		nready = select(maxfd + 1, &rset, &wset, &eset, NULL);
		if (nready < 0) {
			perror("select error.");
			exit(0);
		}
		if (FD_ISSET(listenfd , &rset)) { /* new client connection */
			int connfd;
			if ((connfd = accept(listenfd, NULL, NULL)) == JV_ERROR) {
				printf("accept error:%s\n", strerror(errno));
				continue;
			} else {
				for (i = 0; i < FD_SETSIZE; i++) {
					if (client[i] < 0) {
						client[i] = connfd; /* save descriptor */
						break;
					}
				}
				if (i == FD_SETSIZE)
					perror("too many clients");

				FD_SET(connfd, &allset);
				/* add new descriptor to set */
				if (connfd > maxfd)
					maxfd = connfd; /* for select */
				if (i > maxi)
					maxi = i; /* max index in client[] array */

				if (--nready <= 0)
					continue; /* no more readable descriptors */
			}
		}

		for (i = 0; i <= maxi; i++) { /* check all clients for data */
			if ((sockfd = client[i]) < 0)
				continue;

			if (FD_ISSET(sockfd, &rset)) {
				jv_thread_queue(thread, test, (void *) sockfd);
				/* close(sockfd); */
				FD_CLR(sockfd, &allset);
				client[i] = -1;
			}
			if (FD_ISSET(sockfd, &eset)) {
				perror("eset");
				jv_pool_destroy(pool);
				jv_thread_wait(thread);
				jv_thread_destroy(thread);
				exit(0);
			}
			if (--nready <= 0)
				break; /* no more readable descriptors */
		}
	}

}
