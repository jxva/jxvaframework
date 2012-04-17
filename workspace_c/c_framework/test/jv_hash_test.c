#include <assert.h>
#include <jv_hash.h>

static jv_uint_t jv_elf_hash(const u_char *s);

static jv_uint_t jv_base_hash(const u_char *s);

static jv_uint_t jv_elf_hash(const u_char *s) {
	jv_uint_t hash = 0;
	jv_uint_t x = 0;
	while (*s) {
		hash = (hash << 4) + (*s++); /* hash值左移4位加上一个字符 */
		if ((x = hash & 0xF0000000L) != 0) { /*判断hash值的高4位是否不为0，因为不为0时需要下面特殊处理，否则上面一步的左移4位会把这高四位给移走，造成信息丢失*/
			hash ^= (x >> 24); /*把刚才的高4位跟hash的低5-8位异或*/
			hash &= ~x; /*把高4位清0*/
		}
	}
	return (hash & 0x7FFFFFFF); /*希望hash值是一个非负数*/
}

static jv_uint_t jv_base_hash(const u_char *s) {
	jv_uint_t val;
	for (val = 0; *s != '\0'; s++) {
		/*printf("%d ",*s);*/
		val = *s + 31 * val;
	}
	return (val & 0x7FFFFFFF);
}

void jv_hash_test(void) {
	char const *s[] = { "IBM", "ibm", "Apple", "Borland", "Baidu", "Cisco",
			"Dell", "Electrolux", "FireFox", "Google", NULL };
	char const **k;
	for (k = s; *k != NULL; k++) {
		printf("%s - %d - %d\n", *k, jv_base_hash((u_char *) *k),
				jv_elf_hash((u_char *) *k));
	}
}
