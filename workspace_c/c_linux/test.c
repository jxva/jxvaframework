#include <stdio.h>
#include <stdlib.h>

int main() {

	int a[] = { 3, 4, 1, 2, 3, 4, 5 };
	int b[] = { 6, 7, 8, 9, 10 };

	int *p;
	p = b;
	int i;
	for (i = 0; i < 10; i++) {
		printf("p[%d]=", i);
		printf("%d\n", *p);
		p++;
	}
	return 0;
}
