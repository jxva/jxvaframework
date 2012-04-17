include ../config.$(OS)

CC=gcc

CFLAGS=-Wall -g -pedantic -pipe -W  -Wpointer-arith -Wno-unused-parameter -Wunused-function -Wunused-variable -Wunused-value -Werror

INCLUDES=-I.

LIBS=

LINKS=

all:	
	$(CC) $(CFLAGS) $(INCLUDES) -c index.c -o index.o
	$(CC) $(CFLAGS) $(INCLUDES) -c test.c -o test.o
	
	$(CC) $(LIBS) -o index.cgi index.o  $(LINKS)
	$(CC) $(LIBS) -o test.cgi test.o  $(LINKS)

	@echo 
	@echo success compiled.
	@echo

clean:
	rm -rf *.cgi *.o *~
