#include <jv_merge_sort.h>

static void jv_merge(int *arr, int start, int mid, int end) {
	int temp;
	int i, j;

	int n1 = mid - start + 1;
	int n2 = end - mid;

	int * t1 = (int *) malloc((n1 + 1) * sizeof(int));
	int * t2 = (int *) malloc((n2 + 1) * sizeof(int));

	for (temp = 0; temp < n1; temp++) {
		t1[temp] = arr[start + temp];
	}
	for (temp = 0; temp < n2; temp++) {
		t2[temp] = arr[mid + temp + 1];
	}

	t1[n1] = 65535;
	t2[n2] = 65535;

	i = 0;
	j = 0;

	for (temp = start; temp <= end; temp++) {
		if (t1[i] < t2[j]) {
			arr[temp] = t1[i];
			i++;
		} else {
			arr[temp] = t2[j];
			j++;
		}
	}
	free(t2);
	free(t1);
}

void jv_merge_sort(int *arr, int start, int end) {
	if (start < end) {
		int mid = (start + end) / 2;
		jv_merge_sort(arr, start, mid);
		jv_merge_sort(arr, mid + 1, end);
		jv_merge(arr, start, mid, end);
	}
}
