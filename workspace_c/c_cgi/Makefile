include ../config.$(OS)

CC=gcc

CFLAGS=-Wall -g -pedantic -pipe -W  -Wpointer-arith -Wno-unused-parameter -Wunused-function -Wunused-variable -Wunused-value -Werror

INCLUDES=-I.

LIBS=

LINKS=

all:	
	$(CC) $(CFLAGS) $(INCLUDES) -c main.c -o main.o
	
	$(CC) $(LIBS) -o main main.o  $(LINKS)

	@echo 
	@echo success compiled.
	@echo

clean:
	rm -rf main *.o *~
