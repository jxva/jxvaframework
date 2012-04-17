include ../config

CC=gcc

CFLAGS=-Wall -pedantic -g 

INCLUDES=-I.

LIBS=

LINKS=-lpthread

all:
	test -d $(OBJS) || mkdir $(OBJS)
	
	$(CC) $(CFLAGS) $(INCLUDES) -c jv_pool.c -o $(OBJS)/jv_pool.o
	$(CC) $(CFLAGS) $(INCLUDES) -c jv_thread.c -o $(OBJS)/jv_thread.o
	
	$(CC) $(CFLAGS) $(INCLUDES) -c main.c -o $(OBJS)/main.o
	
	$(CC) $(LIBS) -o main $(OBJS)/main.o  \
		$(OBJS)/jv_pool.o \
		$(OBJS)/jv_thread.o   $(LINKS)

	@echo 
	@echo success compiled.
	@echo

clean:
	rm -rf main $(OBJS) *~
