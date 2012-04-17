include ../config.$(OS)

CFLAGS=-Wall -pedantic -g 

INCLUDES=-I. -IC:/MinGW/include

LIBS=-LC:/MinGW/lib -L.

LINKS=-lopengl32 -lglu32 -lgdi32  -mwindows -lglut32

all:
	$(CC) $(CFLAGS) $(INCLUDES) -c main.c -o main.o
	$(CC) $(CFLAGS) $(INCLUDES) -c main2.c -o main2.o
	
	$(CC) -o main main.o  $(LIBS) $(LINKS)
	$(CC) -o main2 main2.o  $(LIBS) $(LINKS)  

	@echo 
	@echo success compiled.
	@echo

clean:
	rm -rf main main2 *.o *~
