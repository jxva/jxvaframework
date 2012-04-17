#include <jv_quick_sort.h>

/* implement with queue */
void jv_quick_sort_by_queue(int arr[], int len) {
#define LEN 10  /* LEN = len */
	struct node {
		int low, high;
	} qu[LEN];

	int i, j, low, high, temp, front = -1, rear = -1;

	rear++;
	qu[rear].low = 0;
	qu[rear].high = len - 1;

	while (front != rear) {
		front = (front + 1) % len;
		low = qu[front].low;
		high = qu[front].high;
		i = low;
		j = high;
		if (low < high) {
			temp = arr[low];
			while (i != j) {
				while (i < j && arr[j] > temp)
					j--;
				if (i < j) {
					arr[i] = arr[j];
					i++;
				}
				while (i < j && arr[i] < temp)
					i++;
				if (i < j) {
					arr[j] = arr[i];
					j--;
				}
			}
			arr[i] = temp;

			rear = (rear + 1) % len;
			qu[rear].low = low;
			qu[rear].high = i - 1;

			rear = (rear + 1) % len;
			qu[rear].low = i + 1;
			qu[rear].high = high;
		}
	}
}
