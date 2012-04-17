#include <jv_string.h>

/*
 u_char p[]="  fd Sa h = Fa elLo  ";
 u_char *last=p+8;
 u_char c='a';
 u_char *tmp=jv_strlchr(p,last,c);
 if(tmp!=NULL){
 printf("\n%s\n",tmp);
 }
 从字符串p的第1个字符开始到last个字符结束搜索字符c
 如果搜索成功将返回匹配c的p指针
 否则，到last个字符未搜索到c将返回NULL
 */
u_char *jv_strlchr(u_char *p, const u_char *last, const u_char c) {
	while (p < last) {
		if (*p == '\0')
			return NULL;
		if (*p == c)
			return p;
		p++;
	}
	return NULL;
}

/*
 const u_char s[]="  fd Sa h = Fa elLo  ";
 u_char dst[30];
 u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);

 tmp=jv_strlow(dst,s);
 if(tmp!=NULL){
 printf("tmpX:%X,dstX:%X\n",(unsigned int)tmp,(unsigned int)dst);
 printf("tmp:%s,d:%d\n",tmp,jv_strlen(tmp));
 printf("dst:%s,d:%d\n",dst,jv_strlen(dst));
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);
 }
 */
u_char *jv_strlow(u_char *dst, const u_char *src) {
	u_char *tmp = dst;
	do {
		(*dst++) = jv_tolower(*src);
	} while (*src++);
	return tmp;
}

/*
 const u_char s[]="  fd Sa h = Fa elLo  ";
 u_char dst[30];
 u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);

 tmp=jv_strncpy(dst,s,jv_strlen(s));
 if(tmp!=NULL){
 printf("tmpX:%X,dstX:%X\n",(unsigned int)tmp,(unsigned int)dst);
 printf("tmp:%s,d:%d\n",tmp,jv_strlen(tmp));
 printf("dst:%s,d:%d\n",dst,jv_strlen(dst));
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);
 }
 仅复制src的前n个字符到dst，函数会自动添加'\0'
 */
u_char *jv_strncpy(u_char *dst, const u_char *src, size_t n) {
	u_char *tmp = dst;
	while (n--) {
		/*
		 method one
		 *dst = *src;
		 if (*dst == '\0') {
		 return tmp;
		 }
		 dst++;
		 src++;
		 */
		/*use "if (*dst++ = *src++)" complier will warning: suggest parentheses around assignment used as truth value */
		if ((*dst++ = *src++) == '\0') {
			return tmp;
		}
	}
	*dst = '\0';
	return tmp;
}

/*
 const u_char s[]="  fd Sa h = Fa elLo  ";
 u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);

 tmp=jv_strdup(s);
 if(tmp!=NULL){
 printf("tmp:%s,d:%d,tmpX:%X\n",tmp,jv_strlen(tmp),(unsigned int)tmp);
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);
 free(tmp);
 }
 将字符串s拷贝一份做为函数的返回值
 必须记得释放返回值:free(return_value);
 */
u_char *jv_strdup(jv_pool_t *pool, const u_char *s) {
	size_t n = jv_strlen(s) + 1;
	u_char *dst = jv_pool_alloc(pool, sizeof(u_char) * n);
	if (dst == NULL) {
		return NULL;
	}jv_memcpy(dst, s, n);
	return dst;
}

/*
 * We use jv_strcasecmp()/jv_strncasecmp() for 7-bit ASCII strings only,
 * and implement our own jv_strcasecmp()/jv_strncasecmp()
 * to avoid libc locale overhead.  Besides, we use the jv_uint_t's
 * instead of the u_char's, because they are slightly faster.
 */

/*
 const u_char s1[]="  fd Sa h = Fa elLo  ";
 const u_char s2[]="  Fd sa H = fa eLlO  ";
 jv_int_t i;
 i=jv_strcasecmp(s1,s2);
 printf("i:%d\n",i);
 忽略大小写比较s1与s2
 这里函数内采用int比char会更快
 */
jv_int_t jv_strcasecmp(const u_char *s1, const u_char *s2) {
	jv_uint_t c1, c2;
	for (;;) {
		c1 = (jv_uint_t) *s1++;
		c2 = (jv_uint_t) *s2++;

		c1 = (c1 >= 'A' && c1 <= 'Z') ? (c1 | 0x20) : c1;
		c2 = (c2 >= 'A' && c2 <= 'Z') ? (c2 | 0x20) : c2;
		if (c1 == c2) {
			if (c1) {
				continue;
			}
			return 0;
		}
		return c1 - c2;
	}
}

/*
 const u_char s1[]="  fd Sa h = Fa elLo  ";
 const u_char s2[]="  Fd sa H = fa eLlO  ";
 jv_int_t i;
 i=jv_strncasecmp(s1,s2,10);
 printf("i:%d\n",i);
 仅比较前n个字符，同时忽略大小写
 这里函数内采用int比char会更快
 */
jv_int_t jv_strncasecmp(const u_char *s1, const u_char *s2, size_t n) {
	jv_uint_t c1, c2;
	while (n) {
		c1 = (jv_uint_t) *s1++;
		c2 = (jv_uint_t) *s2++;
		c1 = (c1 >= 'A' && c1 <= 'Z') ? (c1 | 0x20) : c1;
		c2 = (c2 >= 'A' && c2 <= 'Z') ? (c2 | 0x20) : c2;
		if (c1 == c2) {
			if (c1) {
				n--;
				continue;
			}
			return 0;
		}

		return c1 - c2;
	}
	return 0;
}

/*
 const u_char s1[]="  fd Sa h = Fa elLo  ";
 const u_char s2[]=" = ";
 const u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s1,jv_strlen(s1),(unsigned int)s1);

 tmp=jv_strnstr(s1,s2,15);
 if(tmp!=NULL){
 printf("tmp:%s,d:%d,tmpX:%X\n",tmp,jv_strlen(tmp),(unsigned int)tmp);
 printf("sss:%s,d:%d,sX:%X\n",s1,jv_strlen(s1),(unsigned int)s1);
 }
 仅在s1的前n字符中搜索s2字符串
 如果搜索成功将返回匹配s2的s1指针，否则返回NULL

 if s2 is empty string,will return NULL
 attention: when s2 is empty, jv_strnstr and jv_strstr's return value is different.
 */
const u_char *jv_strnstr(const u_char *s1, const u_char *s2, size_t n) {
	u_char c1, c2 = *s2++;
	size_t len = jv_strlen(s2);
	do {
		do {
			if (n-- == 0) {
				return NULL;
			}
			c1 = *s1++;
			if (c1 == 0) {
				return NULL;
			}
		} while (c1 != c2);
		if (len > n) {
			return NULL;
		}
	} while (jv_strncmp(s1, s2, len) != 0);
	return --s1;
}

/*
 * jv_strstrn() and jv_strcasestrn() are intended to search for static
 * substring with known length in null-terminated string. The argument n
 * must be length of the second substring length.
 */

/*
 const u_char s1[]="  fd Sa h = Fa elLo  ";
 const u_char s2[]=" = ";
 const u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s1,jv_strlen(s1),(unsigned int)s1);

 tmp=jv_strstrn(s1,s2,jv_strlen(s2));
 if(tmp!=NULL){
 printf("tmp:%s,d:%d,tmpX:%X\n",tmp,jv_strlen(tmp),(unsigned int)tmp);
 printf("sss:%s,d:%d,sX:%X\n",s1,jv_strlen(s1),(unsigned int)s1);
 }
 在s1中仅搜索s2前n个字符
 如果搜索成功将返回匹配s2前n个字符的s1指针
 否则返回NULL
 注意：n<=jv_strlen(s2)
 */
const u_char *jv_strstrn(const u_char *s1, const u_char *s2, size_t n) {
	u_char c1 = *s1, c2 = *s2;
	do {
		while (c1 != c2) {
			if ((c1 = *++s1) == 0) {
				return NULL;
			}
		}
	} while (jv_strncmp(s1, s2, n) != 0);
	return s1;
}

/*
 const u_char s1[]="  fd Sa h = Fa elLo  ";
 const u_char s2[]=" fA ";
 const u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s1,jv_strlen(s1),(unsigned int)s1);

 tmp=jv_strcasestrn(s1,s2,jv_strlen(s2));
 if(tmp!=NULL){
 printf("tmp:%s,d:%d,tmpX:%X\n",tmp,jv_strlen(tmp),(unsigned int)tmp);
 printf("sss:%s,d:%d,sX:%X\n",s1,jv_strlen(s1),(unsigned int)s1);
 }
 在s1中忽略大小仅搜索s2前n个字符
 如果搜索成功将返回匹配s2前n个字符的s1指针
 否则返回NULL
 注意：n<=jv_strlen(s2)
 */
const u_char *jv_strcasestrn(const u_char *s1, const u_char *s2, size_t n) {
	jv_uint_t c1 = (jv_uint_t) *s1, c2 = (jv_uint_t) *s2;
	c1 = (c1 >= 'A' && c1 <= 'Z') ? (c1 | 0x20) : c1;
	c2 = (c2 >= 'A' && c2 <= 'Z') ? (c2 | 0x20) : c2;
	do {
		while (c1 != c2) {
			c1 = (jv_uint_t) *++s1;
			c1 = (c1 >= 'A' && c1 <= 'Z') ? (c1 | 0x20) : c1;
			if (c1 == 0) {
				return NULL;
			}
		}
	} while (jv_strncasecmp(s1, s2, n) != 0);
	return s1;
}

/*
 * jv_strlcasestrn() is intended to search for static substring
 * with known length in string until the argument last. The argument n
 * must be length of the second substring length.
 */

/*
 const u_char s1[]="  fd Sa h = Fa elLo  ";
 const u_char s2[]=" fA ";
 const u_char *last=s1+15;
 const u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s1,jv_strlen(s1),(unsigned int)s1);

 tmp=jv_strlcasestrn(s1,last,s2,jv_strlen(s2));
 if(tmp!=NULL){
 printf("tmp:%s,d:%d,tmpX:%X\n",tmp,jv_strlen(tmp),(unsigned int)tmp);
 printf("sss:%s,d:%d,sX:%X\n",s1,jv_strlen(s1),(unsigned int)s1);
 }
 从字符串s1的前last个字符中搜索s2前n个字符
 如果搜索成功将返回匹配s2的前n个字符的s1指针
 否则，到last个字符未搜索到s2的前n个字符将返回NULL
 注意：n<=jv_strlen(s2)
 */
const u_char *jv_strlcasestrn(const u_char *s1, const u_char *last,
		const u_char *s2, size_t n) {
	jv_uint_t c1, c2 = (jv_uint_t) *s2++;
	c2 = (c2 >= 'A' && c2 <= 'Z') ? (c2 | 0x20) : c2;
	last -= n - 1;
	do {
		do {
			if (s1 >= last) {
				return NULL;
			}
			c1 = (jv_uint_t) *s1++;
			c1 = (c1 >= 'A' && c1 <= 'Z') ? (c1 | 0x20) : c1;
		} while (c1 != c2);
	} while (jv_strncasecmp(s1, s2, n - 1) != 0);
	return --s1;

	/*
	 the way is effective too.
	 jv_uint_t  c1, c2;
	 c1 = (jv_uint_t) *s1;
	 c1 = (c1 >= 'A' && c1 <= 'Z') ? (c1 | 0x20) : c1;
	 c2 = (jv_uint_t) *s2;
	 c2 = (c2 >= 'A' && c2 <= 'Z') ? (c2 | 0x20) : c2;
	 do {
	 while(c1!= c2){
	 if(s1>=last)return NULL;
	 c1 = (jv_uint_t)*++s1;
	 c1 = (c1 >= 'A' && c1 <= 'Z') ? (c1 | 0x20) : c1;
	 if(c1==0){
	 return NULL;
	 }
	 }
	 printf("s1:%s,s2:%s\n",s1,s2);
	 } while (jv_strncasecmp(s1, s2, n) != 0);
	 return s1;
	 */
}

/*
 parse a fixed point number, e.g., jv_atofp("10.5", 4, 2) returns 1050
 (1) n表示截取s的前n位
 (2) .点号将替换为0
 (3) point取点号后point-1位
 解说不是太规则，可以参见单元测试
 */
jv_int_t jv_atofp(const u_char *s, size_t n, size_t point) {
	jv_int_t value;
	jv_uint_t dot;
	if (n == 0) {
		return JV_ERROR;
	}
	dot = 0;
	for (value = 0; n--; s++) {
		if (point == 0) {
			return JV_ERROR;
		}
		if (*s == '.') {
			if (dot) {
				return JV_ERROR;
			}
			dot = 1;
			continue;
		}
		if (*s < '0' || *s > '9') {
			return JV_ERROR;
		}
		value = value * 10 + (*s - '0');
		point -= dot;
	}
	while (point--) {
		value = value * 10;
	}
	return value < 0 ? JV_ERROR : value;
}
/*
 将s的前n个hex字符串转换为十进制整型
 jv_hextoi((u_char *)"-5A")  :    -90;
 jv_hextoi((u_char *)"40")   :    64;
 */
jv_int_t jv_hextoi(const u_char *s, size_t n) {
	u_char c, ch;
	jv_int_t value, sign;
	if (s[0] == '-') {
		sign = -1;
		n--;
		s++;
	} else {
		sign = 1;
	}
	for (value = 0; n--; s++) {
		ch = *s;
		if (ch >= '0' && ch <= '9') {
			value = value * 16 + (ch - '0');
			continue;
		}
		c = (u_char) (ch | 0x20);
		if (c >= 'a' && c <= 'f') {
			value = value * 16 + (c - 'a' + 10);
			continue;
		}
		return 0;
	}
	return sign * value;
}

/*
 将src的前n个字符转换为hex码
 const u_char s[]="abcdefg";
 u_char dst[30];
 u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);

 tmp=jv_hex_dump(dst,s,4);
 if(tmp!=NULL){
 printf("tmpX:%X,dstX:%X\n",(unsigned int)tmp,(unsigned int)dst);
 printf("tmp:%s,d:%d\n",tmp,jv_strlen(tmp));
 printf("dst:%s,d:%d\n",dst,jv_strlen(dst));
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);
 }
 */
u_char *jv_hex_dump(u_char *dst, const u_char *src, size_t n) {
	static const u_char hex[] = "0123456789abcdef";
	u_char *tmp = dst;
	while (n--) {
		*dst++ = hex[*src >> 4];
		*dst++ = hex[*src++ & 0xf];
	}
	*dst = '\0';
	return tmp;
}

/*
 将src的前n个字符中的html字符进行转义
 const u_char s[]="<font color=\"red\"><b>Hello,World</b></font>";
 u_char dst[120];
 u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);

 tmp=jv_escape_html(dst,s,jv_strlen(s));
 if(tmp!=NULL){
 printf("tmpX:%X,dstX:%X\n",(unsigned int)tmp,(unsigned int)dst);
 printf("tmp:%s,d:%d\n",tmp,jv_strlen(tmp));
 printf("dst:%s,d:%d\n",dst,jv_strlen(dst));
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);
 }
 */
u_char *jv_escape_html(u_char *dst, const u_char *src, size_t n) {
	u_char ch, *tmp = dst;
	while (n--) {
		ch = *src++;
		switch (ch) {
		case '\0':
			*dst = ch;
			return tmp;
		case '<':
			*dst++ = '&';
			*dst++ = 'l';
			*dst++ = 't';
			*dst++ = ';';
			break;
		case '>':
			*dst++ = '&';
			*dst++ = 'g';
			*dst++ = 't';
			*dst++ = ';';
			break;
		case '&':
			*dst++ = '&';
			*dst++ = 'a';
			*dst++ = 'm';
			*dst++ = 'p';
			*dst++ = ';';
			break;
		case '"':
			*dst++ = '&';
			*dst++ = 'q';
			*dst++ = 'u';
			*dst++ = 'o';
			*dst++ = 't';
			*dst++ = ';';
			break;
		default:
			*dst++ = ch;
			break;
		}
	}
	*dst = '\0';
	return tmp;
}

/*
 const u_char s[]="  Hello,  World   ";
 u_char dst[120];
 u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);

 tmp=jv_ltrim(dst,s);
 if(tmp!=NULL){
 printf("tmpX:%X,dstX:%X\n",(unsigned int)tmp,(unsigned int)dst);
 printf("tmp:%s,d:%d\n",tmp,jv_strlen(tmp));
 printf("dst:%s,d:%d\n",dst,jv_strlen(dst));
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);
 }
 */
u_char *jv_ltrim(u_char *dst, const u_char *src) {
	u_char *tmp = dst;
	while (*src == ' ' || *src == '\t') {
		src++;
	}
	while (*src != '\0') {
		*dst++ = *src++;
	}
	*dst = '\0';
	return tmp;
}

u_char *jv_rtrim(u_char *dst, const u_char *src) {
	jv_int_t i, len = jv_strlen(src) - 1;
	while (src[len] == ' ' || src[len] == '\t') {
		len--;
	}
	for (i = 0; i <= len; i++) {
		*dst++ = src[i];
	}
	*dst = '\0';
	return dst - i;
}

/*
 const u_char s[]="  Hello,  World   ";
 u_char dst[120];
 u_char *tmp;
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);

 tmp=jv_trim(dst,s);
 if(tmp!=NULL){
 printf("tmpX:%X,dstX:%X\n",(unsigned int)tmp,(unsigned int)dst);
 printf("tmp:%s,d:%d\n",tmp,jv_strlen(tmp));
 printf("dst:%s,d:%d\n",dst,jv_strlen(dst));
 printf("sss:%s,d:%d,sX:%X\n",s,jv_strlen(s),(unsigned int)s);
 }
 */
u_char *jv_trim(u_char *dst, const u_char *src) {
	u_char *tmp = dst;
	jv_int_t i = 0, len = jv_strlen(src);
	while (src[i] == ' ' || src[i] == '\t') {
		i++;
	}

	do {
		len--;
	} while (src[len] == ' ' || src[len] == '\t');

	while (i <= len) {
		*dst++ = src[i++];
	}
	*dst = '\0';
	return tmp;
}

u_char *jv_reverse(u_char *dst, const u_char *src) {
	size_t n, len;
	n = len = jv_strlen(src);
	while (len) {
		*dst++ = src[--len];
	}
	*dst = '\0';
	return dst - n;
}

/*
 char *str = "10,20,30";
 char *arr[3];
 const char *del = ",";
 int i = 0;
 split(arr, str, del);

 while(i<3)
 printf("%s\n", *(arr+i++));
 */
void split(char **arr, char *str, const char *del) {
	char *s = strtok(str, del);
	while (s != NULL) {
		*arr++ = s;
		s = strtok(NULL, del);
	}
}
