#include <stdio.h>
#include <stdlib.h>

int input, result, tmp;

int main() {
	input = 5;
	tmp = 9;
	__asm__ (
			"movl %1,%0" : "=r" (result) : "r" (input)
	);

	__asm__ __volatile__ (
			"movl %1,%0" : "=r" (input) : "r" (tmp)
	);
	printf("input:%d,result:%d,tmp:%d\n", input, result, tmp);
	return 0;
}
