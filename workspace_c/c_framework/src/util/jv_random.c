#include <jv_random.h>

void jv_random_test(void) {
	int tmp, i;
	srand(time(NULL));
	for (i = 0; i < 100; i++) {
		tmp = rand() % 50;
		printf("%d\n", tmp);
	}
}
