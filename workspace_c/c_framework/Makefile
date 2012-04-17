include ../config.$(OS)

CFLAGS=-Wall -g -pedantic -pipe -W  -Wpointer-arith -Wno-unused-parameter -Wunused-function -Wunused-variable -Wunused-value

INCLUDES=-I. -I../jv/include -I./test -I$(MYSQL_INCLUDE)

LIBS=-L$(MYSQL_LIB)

LINKS=-lpthread -lmysql

TARGET=c_framework

src=$(wildcard *.c ./src/base/*.c ./test/*.c  src/db/*.c ./src/math/*.c ./src/socket/*.c ./src/sort/*.c ./src/tree/*.c ./src/util/*.c)
C_OBJS=$(patsubst %.c, %.o,$(src))
#C_OBJS=$(dir:%.c=%.o)

compile:$(TARGET)
	
$(C_OBJS):%.o:%.c
	$(CC) $(CFLAGS) $(INCLUDES) -o $*.o -c $*.c
	
$(TARGET):$(C_OBJS)
	$(CC) -o $(TARGET) $^ $(LIBS) $(LINKS) 

	@echo 
	@echo success compiled.
	@echo
	
install: $(TARGET)
	cp $(TARGET) $(INSTALL_PATH)

uninstall:
	rm -f $(INSTALL_PATH)/$(TARGET)

rebuild: clean compile

clean:
	rm -rf *.o ./src/*/*.o ./test/*.o *~