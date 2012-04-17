#include <jv_base64.h>
#include <assert.h>

void jv_base64_test(void) {
	jv_str_t src1 = jv_string("Hello");
	jv_str_t src2 = jv_string("World");
	jv_str_t src3;
	/* jv_str_t src3=jv_string("V29ybGQ="); */

	u_char dst1[32], dst2[32],dst3[32];
	u_char *tmp1, *tmp2,*tmp3;

	tmp1=jv_base64_encode(dst1, &src1);
	assert(dst1==tmp1);
	printf("encode: %s,%s,%u,%u\n", dst1,tmp1,(jv_uint_t)dst1,(jv_uint_t)tmp1);

	tmp2=jv_base64_encode(dst2, &src2);
	assert(dst2==tmp2);
	printf("encode: %s,%s,%u,%u\n", dst2,tmp2,(jv_uint_t)dst2,(jv_uint_t)tmp2);

	/* src3=jv_string(dst1); */
	jv_str_set(&src3,dst1);
	tmp3 = jv_base64_decode(dst3,&src3);
	assert(tmp3=dst3);
	printf("decode: %s,%s\n", dst3, tmp3);

}
