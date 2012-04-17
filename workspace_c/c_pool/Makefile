include ../config.$(OS)

CFLAGS=-Wall -g -pedantic -pipe -W  -Wpointer-arith -Wno-unused-parameter -Wunused-function -Wunused-variable -Wunused-value -Werror

INCLUDES=-I. -I../jv/include

LIBS=

LINKS=-lpthread

TARGET=c_pool

src=$(wildcard *.c)
dir=$(notdir $(src))
C_OBJS=$(patsubst %.c, %.o,$(dir))
#C_OBJS=$(dir:%.c=%.o)

compile:$(TARGET)
	test -d $(OBJS) || mkdir $(OBJS)
	
$(C_OBJS):%.o:%.c
	$(CC) $(CFLAGS) $(INCLUDES) -o $*.o -c $*.c
	
$(TARGET):$(C_OBJS)
#	mv *.o $(OBJS)
#	$(CC) $(CFLAGS) $(INCLUDES) -c jv_pool.c -o $(OBJS)/jv_pool.o
#	$(CC) $(CFLAGS) $(INCLUDES) -c jv_pool_test.c -o $(OBJS)/jv_pool_test.o
	
#	$(CC) $(CFLAGS) $(INCLUDES) -c main.c -o $(OBJS)/main.o
#	$(CC) -o $(TARGET) $(OBJS)/main.o $(addprefix $(OBJS)/,$^) $(LIBS) $(LINKS) 

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
	rm -rf *.o *~