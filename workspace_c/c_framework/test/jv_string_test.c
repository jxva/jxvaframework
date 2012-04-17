#include <assert.h>
#include <jv_string.h>

void jv_string_test(void) {
	/* const u_char s1[]=" <b> Welcome to the C Language World. </b> "; */
	u_char x1[12], x2[12], x3[12], x4[12], x5[32], x6[128];
	jv_uint_t len;
	u_char *tmp;
	char *temp;
	u_char buf[32];
	jv_pool_t *pool;

	/*--- jv_tolower ---*/
	assert(jv_tolower('A')=='a');
	assert(jv_tolower('a')=='a');
	assert(jv_tolower('0')=='0');
	assert(jv_tolower(' ')==' ');

	/*--- jv_toupper ---*/
	assert(jv_toupper('A')=='A');
	assert(jv_toupper('a')=='A');
	assert(jv_toupper('0')=='0');
	assert(jv_toupper(' ')==' ');

	/*--- jv_strcmp ---*/
	assert(jv_strcmp("Hello","Hello")==0);
	assert(jv_strcmp("Hell","Hello")==-1);
	assert(jv_strcmp("Hello","Hell")==1);
	assert(jv_strcmp("hello","Hel")==1);
	assert(jv_strcmp("Hello","ok")==-1);
	assert(jv_strcmp("Hello","")==1);

	/*--- jv_strncmp ---*/
	assert(jv_strncmp("Hello","Hello",5)==0);
	assert(jv_strncmp("Hel","Hel",100)==0);
	/* n is very large */
	assert(jv_strncmp("Hel","Hell",5)==-1);
	assert(jv_strncmp("Hello","Hell",4)==0);
	assert(jv_strncmp("hello","Hel",3)==1);
	assert(jv_strncmp("Hello","ok",2)==-1);
	assert(jv_strncmp("Hello","H",1)==0);
	assert(jv_strncmp("Hello","",1)==1);
	assert(jv_strncmp("Hello","",0)==0);
	/* if n is zero, return NULL */
	assert((void *)jv_strncmp("Hello","",0)==NULL);
	/* if n is zero, return NULL */
	assert(jv_strncmp("Hello","He",0)==0);
	/* if n is zero, return NULL */
	assert((void *)jv_strncmp("Hello","He",0)==NULL);
	/* if n is zero, return NULL */

	/*--- jv_strstr ---*/
	assert(jv_strcmp(jv_strstr("Hello","el"),"ello")==0);
	assert(jv_strstr("Hello","ab")==NULL);
	/* not found, will return NULL */
	assert(jv_strstr("Hello"," ")==NULL);
	assert(jv_strcmp(jv_strstr("Hello",""),"Hello")==0);
	/* if s2 is empty string,will return s1 */
	temp = "China,World";
	assert(jv_strstr(temp,"")==temp);
	/*  address is equals, if s2 is empty string,will return s1 */

	/*--- jv_strlen ---*/
	assert(jv_strlen("Hello")==5);
	assert(jv_strlen(" ")==1);
	assert(jv_strlen("")==0);

	/*--- jv_strchr ---*/
	assert(jv_strcmp(jv_strchr("Hello",'e'),"ello")==0);
	assert(jv_strchr("Hello",'a')==NULL);
	/* not found, will return NULL */
	assert(jv_strchr("Hello",' ')==NULL);

	/*--- jv_memzero ---*/
	tmp = jv_memzero(buf,32);
	assert(tmp==buf);
	/* address is equals */
	assert(tmp[0]==0 && tmp[15]==0 && tmp[31]==0);
	assert(buf[0]==0);
	assert(buf[15]==0);
	assert(buf[31]==0);

	/*--- jv_memset ---*/
	tmp = jv_memset(buf, 'A', 32);
	assert(tmp==buf);
	/* address is equals */
	assert(tmp[0]=='A' && tmp[13]=='A' && tmp[31]=='A');
	assert(buf[0]=='A');
	assert(buf[13]=='A');
	assert(buf[31]=='A');

	/*--- jv_memcmp ---*/
	assert(jv_memcmp("Hello","Hello",6)==0);
	assert((jv_int_t)jv_memcmp("Hello","Hell",6)==111);
	assert((jv_int_t)jv_memcmp("Hell","Hello",6)==-111);
	assert(jv_memcmp("","",1)==0);
	assert(jv_memcmp(" "," ",2)==0);
	assert(jv_memcmp(" "," ",2)==NULL);
	assert(jv_memcmp(" "," ",0)==0);
	assert(jv_memcmp(" "," ",0)==NULL);

	/*--- jv_memcpy ---*/
	tmp = jv_memcpy(buf, "Hello", 6);
	assert(tmp==buf);
	/* address is equals */
	assert(tmp[0]==buf[0] && tmp[3]==buf[3] && tmp[0]=='H' && tmp[4]== 'o');
	assert(jv_strcmp(tmp,"Hello")==0);
	assert(jv_strcmp(buf,"Hello")==0);

	/*--- jv_memmove ---*/
	jv_strncpy(x1, (u_char *) "Hello,World", 12);
	assert(jv_strcmp(jv_memmove(x1,x1+6,jv_strlen(x1)+1-6),"World")==0);
	assert(jv_strcmp(jv_memmove(x1,x1+3,jv_strlen(x1)+1-3),"ld")==0);

	/*--- jv_strcopy ---*/
	jv_memzero(buf, 32);
	assert(jv_strcmp(jv_copy(buf,"Hello",6)-6,"Hello")==0);
	assert(jv_strcmp(buf,"Hello")==0);

	/*--- jv_move ---*/
	jv_strncpy(x2, (u_char *) "Hello,China", 12);
	len = jv_strlen(x2);
	tmp = jv_move(x2,x2+6,len+1-6) - len - 1 + 6;
	assert(jv_strcmp(tmp,"China")==0);
	assert(tmp==x2);
	/* address is equals */
	len = jv_strlen(x2);
	tmp = jv_move(x2,x2+3,len+1-3) - len - 1 + 3;
	assert(jv_strcmp(tmp,"na")==0);
	assert(tmp==x2);
	/* address is equals */

	/*--- jv_strlchr ---*/
	jv_strncpy(x3, (u_char *) "Hello,China", 12);
	assert(jv_strcmp(jv_strlchr(x3,x3+8,'C'),"China")==0);
	assert(jv_strlchr(x3,x3+5,'C')==NULL);
	assert(jv_strlchr(x3,x3+8,'c')==NULL);
	/* not found, will return NULL */
	assert(jv_strlchr(x3,x3+11,'X')==NULL);
	tmp = (u_char *) "";
	assert(jv_strlchr(tmp,tmp+2,'X')==NULL);
	assert(jv_strlchr(x3,x3+8,'C')==x3+6);
	/* address is equals */

	/*--- jv_strlow ---*/
	tmp = jv_strlow(x4, (u_char *) "Hello,World");
	assert(tmp==x4);
	/* address is equals */
	assert(jv_strcmp(x4,"hello,world")==0);
	assert(jv_strcmp(tmp,"hello,world")==0);
	assert(jv_strcmp(jv_strlow(x4,(u_char *)" ")," ")==0);
	assert(jv_strcmp(jv_strlow(x4,(u_char *)""),"")==0);

	/*--- jv_strncpy ---*/
	tmp = jv_strncpy(x4, (u_char *) "Hello,World", 12);
	assert(tmp==x4);
	/* address is equals */
	assert(jv_strcmp(x4,"Hello,World")==0);
	assert(jv_strcmp(tmp,"Hello,World")==0);
	assert(jv_strcmp(jv_strncpy(x4,(u_char *)" ",2)," ")==0);
	assert(jv_strcmp(jv_strncpy(x4,(u_char *)"",1),"")==0);
	assert(jv_strcmp(jv_strncpy(x4,(u_char *)"Hello,World",5),"Hello")==0);
	assert(
			jv_strcmp(jv_strncpy(x4,(u_char *)"Hello,World",100000),"Hello,World")==0);
	/* n is very large */

	/*--- jv_strdup ---*/
	pool = jv_pool_create(1024);
	tmp = jv_strdup(pool, (u_char *) "Hello,World");
	assert(jv_strcmp(tmp,"Hello,World")==0);
	jv_pool_free(pool, tmp);
	jv_pool_destroy(pool);

	/*--- jv_strcasecmp ---*/
	assert(jv_strcasecmp((u_char *)"Hello", (u_char *)"hElLo")==0);
	assert(jv_strcasecmp((u_char *)"Hel", (u_char *)"HEL")==0);
	assert(jv_strcasecmp((u_char *)"Hel", (u_char *)"Hell")==-'l');
	assert(jv_strcasecmp((u_char *)"Hello", (u_char *)"Hell")=='o');
	assert(jv_strcasecmp((u_char *)"hello", (u_char *)"Hel")=='l');
	assert(jv_strcasecmp((u_char *)"Hello", (u_char *)"ok")=='h'-'o');
	assert(jv_strcasecmp((u_char *)"Hello", (u_char *)"")=='h');

	/*--- jv_strncasecmp ---*/
	assert(jv_strncasecmp((u_char *)"Hello", (u_char *)"hElLo",5)==0);
	assert(jv_strncasecmp((u_char *)"Hel", (u_char *)"HEL",100000)==0);
	/* n is very large */
	assert(jv_strncasecmp((u_char *)"Hel", (u_char *)"Hell",5)==-'l');
	assert(jv_strncasecmp((u_char *)"Hello", (u_char *)"Hell",4)==0);
	assert(jv_strncasecmp((u_char *)"hello", (u_char *)"Hel",3)==0);
	assert(jv_strncasecmp((u_char *)"hello", (u_char *)"Hel",4)=='l');
	assert(jv_strncasecmp((u_char *)"Hello", (u_char *)"ok",2)=='h'-'o');
	assert(jv_strncasecmp((u_char *)"Hello", (u_char *)"",1)=='h');

	/*--- jv_strnstr ---*/
	assert(jv_strcmp(jv_strnstr((u_char *)"Hello",(u_char *)"H",1),"Hello")==0);
	assert(jv_strcmp(jv_strnstr((u_char *)"Hello",(u_char *)"el",3),"ello")==0);
	assert(
			jv_strcmp(jv_strnstr((u_char *)"Hello",(u_char *)"el",100000),"ello")==0);
	/* n is very large */
	assert(jv_strnstr((u_char *)"Hello",(u_char *)"ab",2)==NULL);
	/* not found, will return NULL */
	assert(jv_strnstr((u_char *)"Hello",(u_char *)" ",1)==NULL);
	assert(jv_strnstr((u_char *)"Hello",(u_char *)"",1)==NULL);
	/* if s2 is empty string,will return NULL, attention: when s2 is empty, jv_strnstr and jv_strstr's return value is different. */
	assert(jv_strnstr((u_char *)"Hello",(u_char *)"",0)==NULL);

	/*--- jv_strstrn ---*/
	assert(jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"H",1),"Hello")==0);
	/*attention: len must be jv_strlen(s2) */
	assert(
			jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"ell",3),"ello")==0);
	assert(jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"llo",3),"llo")==0);
	assert(
			jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"ell",3),"ello")==0);
	assert(jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"el",2),"ello")==0);
	assert(jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"o",1),"o")==0);
	assert(jv_strstrn((u_char *)"Hello",(u_char *)"ab",2)==NULL);
	/* not found, will return NULL */
	assert(jv_strstrn((u_char *)"Hello",(u_char *)" ",1)==NULL);
	assert(jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"l",1),"llo")==0);
	assert(jv_strstrn((u_char *)"Hello",(u_char *)"",1)==NULL);
	/* if s2 is empty string,will return NULL*/
	assert(
			jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"elABC",2),"ello")==0);
	assert(
			jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"ellABC",3),"ello")==0);
	assert(
			jv_strcmp(jv_strstrn((u_char *)"Hello",(u_char *)"elloABC",4),"ello")==0);

	/*--- jv_strcasestrn ---*/
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"H",1),"Hello")==0);
	/*attention: len must be jv_strlen(s2) */
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"ell",3),"ello")==0);
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"llo",3),"llo")==0);
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"ell",3),"ello")==0);
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"EL",2),"ello")==0);
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"eL",2),"ello")==0);
	assert(jv_strcasestrn((u_char *)"Hello",(u_char *)"ab",2)==NULL);
	/* not found, will return NULL */
	assert(jv_strcasestrn((u_char *)"Hello",(u_char *)" ",1)==NULL);
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"l",1),"llo")==0);
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"L",1),"llo")==0);
	assert(jv_strcasestrn((u_char *)"Hello",(u_char *)"",1)==NULL);
	/* if s2 is empty string,will return NULL*/
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"ElABC",2),"ello")==0);
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"eLlABC",3),"ello")==0);
	assert(
			jv_strcmp(jv_strcasestrn((u_char *)"Hello",(u_char *)"eLlOABC",4),"ello")==0);

	/*--- jv_strlcasestrn ---*/
	tmp = (u_char *) "Hello";
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+5,(u_char *)"H",1),"Hello")==0);
	/*attention: len must be jv_strlen(s2) */
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+5,(u_char *)"ell",3),"ello")==0);
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+5,(u_char *)"llo",3),"llo")==0);
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+4,(u_char *)"ell",3),"ello")==0);
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+3,(u_char *)"EL",2),"ello")==0);
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+3,(u_char *)"eL",2),"ello")==0);
	assert(jv_strlcasestrn(tmp,tmp+5,(u_char *)"ab",2)==NULL);
	/* not found, will return NULL */
	assert(jv_strlcasestrn(tmp,tmp+5,(u_char *)" ",1)==NULL);
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+3,(u_char *)"l",1),"llo")==0);
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+4,(u_char *)"L",1),"llo")==0);
	assert(jv_strlcasestrn(tmp,tmp+5,(u_char *)"",1)==NULL);
	/* if s2 is empty string,will return NULL*/
	assert(jv_strcmp(jv_strlcasestrn(tmp,tmp+3,(u_char *)"ElABC",2),"ello")==0);
	assert(
			jv_strcmp(jv_strlcasestrn(tmp,tmp+4,(u_char *)"eLlABC",3),"ello")==0);
	assert(
			jv_strcmp(jv_strlcasestrn(tmp,tmp+5,(u_char *)"eLlOABC",4),"ello")==0);

	/*--- jv_atofp ---*/
	/* printf("%d\n",jv_atofp("510.05", 1, 1)); */
	assert(jv_atofp((u_char *)"510.05", 6, 2)==51005);
	/* */
	assert(jv_atofp((u_char *)"510.05", 5, 2)==51000);
	assert(jv_atofp((u_char *)"510.05", 4, 2)==51000);
	assert(jv_atofp((u_char *)"510.05", 3, 2)==51000);
	assert(jv_atofp((u_char *)"510.05", 2, 2)==5100);
	assert(jv_atofp((u_char *)"510.05", 1, 2)==500);
	assert(jv_atofp((u_char *)"510.05", 1, 1)==50);
	assert(jv_atofp((u_char *)"10.5", 4, 2)==1050);
	assert(jv_atofp((u_char *)"10.05", 5, 2)==1005);
	assert(jv_atofp((u_char *)"0.0501", 5, 3)==50);
	assert(jv_atofp((u_char *)"0.0501", 6, 4)==501);
	assert(jv_atofp((u_char *)"0.0501", 5, 5)==5000);
	assert(jv_atofp((u_char *)"1.0501", 5, 4)==10500);

	/*--- jv_hextoi ---*/
	assert(jv_hextoi((u_char *)"-5A",3)==-90);
	assert(jv_hextoi((u_char *)"40",2)==64);

	/*--- jv_hextoi ---*/
	tmp = jv_hex_dump(x5, (u_char *) "abcdef", 6);
	assert(tmp==x5);
	assert(jv_strcmp(tmp,"616263646566")==0);
	assert(jv_strcmp(x5,"616263646566")==0);

	/*--- jv_escape_html ---*/
	tmp = jv_escape_html(x6,
			(u_char *) "<font color=\"red\"><b>Hello,World</b></font>", 43);
	assert(tmp==x6);
	assert(
			jv_strcmp(tmp,"&lt;font color=&quot;red&quot;&gt;&lt;b&gt;Hello,World&lt;/b&gt;&lt;/font&gt;")==0);
	assert(
			jv_strcmp(x6,"&lt;font color=&quot;red&quot;&gt;&lt;b&gt;Hello,World&lt;/b&gt;&lt;/font&gt;")==0);

	tmp = jv_escape_html(x6,
			(u_char *) "<font color=\"red\"><b>Hello,World</b></font>", 5);
	assert(tmp==x6);
	assert(jv_strcmp(tmp,"&lt;font")==0);
	assert(jv_strcmp(x6,"&lt;font")==0);

	tmp = jv_escape_html(x6,
			(u_char *) "<font color=\"red\"><b>Hello,World</b></font>", 1000); /* n is very large */
	assert(tmp==x6);
	assert(
			jv_strcmp(tmp,"&lt;font color=&quot;red&quot;&gt;&lt;b&gt;Hello,World&lt;/b&gt;&lt;/font&gt;")==0);
	assert(
			jv_strcmp(x6,"&lt;font color=&quot;red&quot;&gt;&lt;b&gt;Hello,World&lt;/b&gt;&lt;/font&gt;")==0);

	/*--- jv_ltrim ---*/
	tmp = jv_ltrim(x5, (u_char *) "  Hello   ");
	assert(tmp==x5);
	assert(jv_strcmp(tmp,"Hello   ")==0);
	assert(jv_strcmp(x5,"Hello   ")==0);

	/*--- jv_rtrim ---*/
	tmp = jv_rtrim(x5, (u_char *) "  Hello   ");
	assert(tmp==x5);
	assert(jv_strcmp(tmp,"  Hello")==0);
	assert(jv_strcmp(x5,"  Hello")==0);

	/*--- jv_trim ---*/
	tmp = jv_trim(x5, (u_char *) "  Hello   ");
	assert(tmp==x5);
	assert(jv_strcmp(tmp,"Hello")==0);
	assert(jv_strcmp(x5,"Hello")==0);

	/*--- jv_reverse ---*/
	tmp = jv_reverse(x5, (u_char *) "Hello");
	assert(tmp==x5);
	assert(jv_strcmp(tmp,"olleH")==0);
	assert(jv_strcmp(x5,"olleH")==0);
}
