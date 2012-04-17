include ../config.$(OS)

CFLAGS=-Wall -g -pedantic

INCLUDES=-I. -I../jv/include

LIBS=

LINKS=-lpthread

TARGET=c_event

src=$(wildcard *.c)
dir=$(notdir $(src))
C_OBJS=$(patsubst %.c, %.o,$(dir))
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
	rm -rf *.o *~