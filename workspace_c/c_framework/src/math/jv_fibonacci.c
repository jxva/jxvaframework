#include <jv_fibonacci.h>

static jv_int_t serie[64] = { 0, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		-1, -1, -1, -1, -1, -1, -1, -1 };

static jv_uint_t cache(jv_uint_t n) {
	if (serie[n] == -1) {
		serie[n] = jv_fibonacci(n);
	}
	return serie[n];
}

jv_uint_t jv_fibonacci(jv_uint_t n) {
	return n < 3 ? (jv_uint_t) serie[n] : cache(n - 2) + cache(n - 1);
}
