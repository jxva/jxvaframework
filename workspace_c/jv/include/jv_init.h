#ifndef _JV_INIT_H_INCLUDED_
#define _JV_INIT_H_INCLUDED_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <errno.h>
#include <time.h>
#include <limits.h>
#include <setjmp.h>
#include <signal.h>
#include <stdarg.h>
#include <stddef.h>
#include <math.h>

#if defined(_WIN32) || defined(_WIN64)
#include <windows.h>
#include <winsock.h>
#else
#include <fcntl.h>
#include <sys/socket.h>
#include <sys/fcntl.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <unistd.h>
#include <pthread.h>
#endif

#define JV_LINUX "i686-pc-linux-gnu"
#define JV_CYGWIN "i686-pc-cygwin"
#define JV_WIN32   "i686-pc-windows32"
#define JV_WIN64   "i686-pc-windows64"

#endif /* _JV_INIT_H_INCLUDED_ */
