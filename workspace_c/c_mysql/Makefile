include ../config.txt
CC=gcc

CFLAGS=$CFLAGS 

INCLUDES=-I.  -I./include -I./test -IC:/mysql/include

LIBS=-LC:/mysql/lib

LINK=-lmysql

OBJS=objs

all:
	test -d ${OBJS} || mkdir ${OBJS}
	
	${CC} ${CFLAGS} ${INCLUDES}  -c src/base/jv_hash.c -o ${OBJS}/jv_hash.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/base/jv_list.c -o ${OBJS}/jv_list.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/base/jv_oop_list.c -o ${OBJS}/jv_oop_list.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/base/jv_queue.c -o ${OBJS}/jv_queue.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/base/jv_stack.c -o ${OBJS}/jv_stack.o
	
	${CC} ${CFLAGS} ${INCLUDES}  -c src/db/jv_db.c -o ${OBJS}/jv_db.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/db/jv_mysql.c -o ${OBJS}/jv_mysql.o
	
	${CC} ${CFLAGS} ${INCLUDES}  -c src/math/jv_fibonacci.c -o ${OBJS}/jv_fibonacci.o
	
	${CC} ${CFLAGS} ${INCLUDES}  -c src/socket/jv_http_client.c -o ${OBJS}/jv_http_client.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/socket/jv_http_server.c -o ${OBJS}/jv_http_server.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/socket/jv_socket.c -o ${OBJS}/jv_socket.o
	
	${CC} ${CFLAGS} ${INCLUDES}  -c src/sort/jv_merge_sort.c -o ${OBJS}/jv_merge_sort.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/sort/jv_quick_sort.c -o ${OBJS}/jv_quick_sort.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/sort/jv_sort.c -o ${OBJS}/jv_sort.o
	
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_base64.c -o ${OBJS}/jv_base64.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_args.c -o ${OBJS}/jv_args.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_daemon.c -o ${OBJS}/jv_daemon.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_file.c -o ${OBJS}/jv_file.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_log.c -o ${OBJS}/jv_log.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_pool.c -o ${OBJS}/jv_pool.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_random.c -o ${OBJS}/jv_random.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_string.c -o ${OBJS}/jv_string.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_template.c -o ${OBJS}/jv_template.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_thread.c -o ${OBJS}/jv_thread.o
	${CC} ${CFLAGS} ${INCLUDES}  -c src/util/jv_time.c -o ${OBJS}/jv_time.o
	
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_base64_test.c -o ${OBJS}/jv_base64_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_daemon_test.c -o ${OBJS}/jv_daemon_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_hash_test.c -o ${OBJS}/jv_hash_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_http_client_test.c -o ${OBJS}/jv_http_client_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_http_server_test.c -o ${OBJS}/jv_http_server_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_list_oop_test.c -o ${OBJS}/jv_list_oop_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_list_test.c -o ${OBJS}/jv_list_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_log_test.c -o ${OBJS}/jv_log_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_math_test.c -o ${OBJS}/jv_math_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_merge_sort_test.c -o ${OBJS}/jv_merge_sort_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_pool_test.c -o ${OBJS}/jv_pool_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_queue_test.c -o ${OBJS}/jv_queue_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_quick_sort_test.c -o ${OBJS}/jv_quick_sort_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_sort_test.c -o ${OBJS}/jv_sort_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_stack_test.c -o ${OBJS}/jv_stack_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_string_test.c -o ${OBJS}/jv_string_test.o
	${CC} ${CFLAGS} ${INCLUDES}  -c test/jv_thread_test.c -o ${OBJS}/jv_thread_test.o
	
	${CC} ${CFLAGS} ${INCLUDES}  -c main.c -o ${OBJS}/main.o
	
	${CC} ${LIBS} -o main ${OBJS}/main.o  \
        ${OBJS}/jv_http_client_test.o ${OBJS}/jv_template.o ${OBJS}/jv_fibonacci.o \
        ${OBJS}/jv_quick_sort_test.o ${OBJS}/jv_string.o ${OBJS}/jv_list_test.o \
        ${OBJS}/jv_stack.o ${OBJS}/jv_base64.o ${OBJS}/jv_random.o ${OBJS}/jv_math_test.o \
        ${OBJS}/jv_queue.o ${OBJS}/jv_hash.o ${OBJS}/jv_sort.o ${OBJS}/jv_pool.o \
        ${OBJS}/jv_stack_test.o ${OBJS}/jv_oop_list.o ${OBJS}/jv_list_oop_test.o \
        ${OBJS}/jv_hash_test.o ${OBJS}/jv_quick_sort.o ${OBJS}/jv_database_pool.o \
        ${OBJS}/jv_log.o ${OBJS}/jv_pool_test.o ${OBJS}/jv_list.o ${OBJS}/jv_merge_sort.o \
        ${OBJS}/jv_file.o ${OBJS}/jv_base64_test.o ${OBJS}/jv_socket.o ${OBJS}/jv_daemon.o \
        ${OBJS}/jv_log_test.o ${OBJS}/jv_http_server_test.o ${OBJS}/jv_time.o ${OBJS}/jv_http_server.o \
        ${OBJS}/jv_args.o ${OBJS}/jv_mysql.o ${OBJS}/jv_sort_test.o ${OBJS}/jv_merge_sort_test.o \
        ${OBJS}/jv_thread_test.o ${OBJS}/jv_daemon_test.o ${OBJS}/jv_thread.o \
        ${OBJS}/jv_http_client.o ${OBJS}/jv_string_test.o   ${LINK}

	@echo 
	@echo success compiled.
	@echo

clean:
	rm -rf main ${OBJS} *~
