#ifndef _JV_STRING_H_INCLUDED_
#define _JV_STRING_H_INCLUDED_

#include <jv_core.h>
#include <jv_pool.h>

typedef struct {
	size_t len;
	u_char *data;
} jv_str_t;

#define jv_string(str)              { sizeof(str) - 1, (u_char *) str }
#define jv_null_string              { 0, NULL }
#define jv_str_set(str, text)                                               \
    (str)->len = sizeof(text) - 1; (str)->data = (u_char *) text

#define jv_str_null(str)            (str)->len = 0; (str)->data = NULL

#define jv_tolower(c)               (u_char) ((c >= 'A' && c <= 'Z') ? (c | 0x20) : c)
#define jv_toupper(c)               (u_char) ((c >= 'a' && c <= 'z') ? (c & ~0x20) : c)

#define jv_strcmp(s1, s2)           strcmp((const char *) s1, (const char *) s2)
#define jv_strncmp(s1, s2, n)       strncmp((const char *) s1, (const char *) s2, n)

/*
 if s2 is empty string,will return s1
 attention: when s2 is empty, jv_strnstr and jv_strstr's return value is different.
 */
#define jv_strstr(s1, s2)           strstr((const char *) s1, (const char *) s2)
#define jv_strlen(s)                strlen((const char *) s)
#define jv_strchr(s1, c)            strchr((const char *) s1, (int) c)

#define jv_memzero(buf, n)          (void *) memset(buf, 0, n)
#define jv_memset(buf, c, n)        (void *) memset(buf, c, n)
#define jv_memcmp(s1, s2, n)        (void *) memcmp(s1,s2, n)
#define jv_memcpy(dst, src, n)      (void *) memcpy(dst, src, n)
/*
 由src所指内存区域复制n个字节到dst所指内存区域
 s="Hello,World";
 assert(jv_strcmp(jv_memmove(s,s+6,jv_strlen(s)+1-6),"World")==0);
 assert(jv_strcmp(jv_memmove(s,s+3,jv_strlen(s)+1-3),"ld")==0);

 u_char s[]="Hello,World";
 u_char *tmp=jv_memove(s,s+6,jv_strlen(s)+1-6);
 printf("%s,%d,%X\n",tmp,jv_strlen(tmp),(jv_uint_t)tmp);
 printf("%s,%d,%X\n",s,jv_strlen(s),(jv_uint_t)s);
 */
#define jv_memmove(dst, src, n)     (void *) memmove(dst, src, n)

/*
 const u_char s[]="<font color=\"red\"><b>Hello,World</b></font>";
 u_char dst[120];
 u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);

 tmp=jv_copy(dst,s,jv_strlen(s));
 if(tmp!=NULL){
 printf("tmpX:%X,dstX:%X\n",(unsigned int)tmp,(unsigned int)dst);
 //返回地址因为增加了n，所以输出时，必须减少n
 jv_copy与jv_memcpy主要区别在返回值的起始地址与返回类型
 printf("tmp:%s,d:%d\n",tmp-jv_strlen(s),jv_strlen(tmp));
 printf("dst:%s,d:%d\n",dst,jv_strlen(dst));
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);
 }
 */
#define jv_copy(dst, src, n)     (((u_char *) memcpy(dst, src, n)) + (n))

/*
 u_char x2[12];
 jv_uint_t len;
 u_char *tmp;
 jv_strncpy(x2,(u_char *)"Hello,China",12);

 len=jv_strlen(x2);
 tmp=jv_move(x2,x2+6,len+1-6)-len-1+6;
 assert(jv_strcmp(tmp,"China")==0);
 assert(tmp==x2); //address is equals

 len=jv_strlen(x2);
 tmp=jv_move(x2,x2+3,len+1-3)-len-1+3;
 assert(jv_strcmp(tmp,"na")==0);
 assert(tmp==x2); //address is equals

 --------------

 u_char s[]="Hello,World";
 jv_int_t len=jv_strlen(s);
 u_char *tmp=jv_move(s,s+6,len+1-6)-len-1+6;
 printf("%s,%d,%X\n",tmp,jv_strlen(tmp),(jv_uint_t)tmp);
 printf("%s,%d,%X\n",s,jv_strlen(s),(jv_uint_t)s);

 //返回地址因为增加了n，所以输出时，必须减少n
 jv_move与jv_memmove主要区别在返回值的起始地址与返回类型
 */
#define jv_move(dst, src, n)     (((u_char *) memmove(dst, src, n)) + (n))

u_char *jv_strlchr(u_char *p, const u_char *last, const u_char c);

u_char *jv_strlow(u_char *dst, const u_char *src);

u_char *jv_strncpy(u_char *dst, const u_char *src, size_t n);

u_char *jv_strdup(jv_pool_t *pool, const u_char *s);

jv_int_t jv_strcasecmp(const u_char *s1, const u_char *s2);

jv_int_t jv_strncasecmp(const u_char *s1, const u_char *s2, size_t n);

const u_char *jv_strnstr(const u_char *s1, const u_char *s2, size_t n);

const u_char *jv_strstrn(const u_char *s1, const u_char *s2, size_t n);

const u_char *jv_strcasestrn(const u_char *s1, const u_char *s2, size_t n);

const u_char * jv_strlcasestrn(const u_char *s1, const u_char *last,
		const u_char *s2, size_t n);

jv_int_t jv_atofp(const u_char *s, size_t n, size_t point);

jv_int_t jv_hextoi(const u_char *s, size_t n);

u_char *jv_hex_dump(u_char *dst, const u_char *src, size_t n);

u_char *jv_escape_html(u_char *dst, const u_char *src, size_t n);

u_char *jv_ltrim(u_char *dst, const u_char *src);

u_char *jv_rtrim(u_char *dst, const u_char *src);

u_char *jv_trim(u_char *dst, const u_char *src);

u_char *jv_split(u_char *dst, const u_char *src, size_t n);

u_char *jv_reverse(u_char *dst, const u_char *src);

#endif /* _JV_STRING_H_INCLUDED_ */
