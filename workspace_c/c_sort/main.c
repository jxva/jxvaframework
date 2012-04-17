#include <stdio.h>
#include <stdlib.h>
#include <jv_data.h>

void straight_insert_sort(int a[], int len) {
	int i, j, key;
	for (i = 1; i < len; i++) {
		j = i - 1;
		key = a[j + 1];
		while (j >= 0 && a[j] > key) {
			a[j + 1] = a[j];
			j--;
			if (j < 0) {
				printf("A\n");
			}
		}
		a[j + 1] = key;
	}
}

void binary_insert_sort(int a[], int len) {
	int key, i, j, low, high, mid;

	for (i = 1; i < len; i++) {
		if (a[i] < a[i - 1]) {
			low = 0;
			high = i - 1;
			key = a[i];
			while (low <= high) {
				mid = (low + high) / 2;

				if (key < a[mid])
					high = mid - 1;
				else
					low = mid + 1;
			}
			for (j = i; j > high + 1; j--)
				a[j] = a[j - 1];

			a[high + 1] = key;
		}
	}
}

int main(int argc, const char *argv[]) {
	int i;
	binary_insert_sort(tiny, 10);
	for (i = 0; i < 10; i++) {
		printf("%d ", tiny[i]);
	}
	return 0;
}
