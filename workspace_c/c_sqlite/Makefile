include ../config.$(OS)

CFLAGS=-Wall -pedantic -g 

INCLUDES=-I.

LIBS=

LINKS=

all:
	$(CC) $(CFLAGS) $(INCLUDES) -c sqlite3.c -o sqlite3.o
	$(CC) $(CFLAGS) $(INCLUDES) -c main.c -o main.o
	
	$(CC) -o main sqlite3.o main.o $(LIBS) $(LINKS) 

	@echo 
	@echo success compiled.
	@echo

clean:
	rm -rf main *.o *~
