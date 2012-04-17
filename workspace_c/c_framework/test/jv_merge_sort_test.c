#include <assert.h>
#include <jv_merge_sort.h>

void jv_merge_sort_test(void) {
	int arr[] = { 4, 9, 1, 3, 0, 7, 2, 6, 8, 5 }, i;
	jv_merge_sort(arr, 0, 9);
	for (i = 0; i < 10; i++) {
		printf("arr[%d]=%d\n", i, arr[i]);
	}
}
