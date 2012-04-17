#include <jv_sort.h>

/*
 void straight_insert_sort(int a[], int len) {
 int i, j, key;
 for(i = 1; i < len; i++) {
 j = i - 1;
 key = a[j + 1];
 while(j >= 0 && a[j] > key) {
 a[j + 1] = a[j];
 j--;
 if(j < 0) {
 printf("A\n");
 }
 }
 a[j + 1] = key;
 }
 }

 void binary_insert_sort(int a[], int len) {
 int key,i, j,low, high, mid;

 for(i = 1; i < len; i++) {
 if(a[i] < a[i-1]) {
 low = 0;
 high = i - 1;
 key = a[i];
 while(low <= high) {
 mid = (low + high) / 2;

 if(key < a[mid])
 high = mid - 1;
 else
 low = mid + 1;
 }
 for(j = i; j > high + 1; j--)
 a[j] = a[j - 1];

 a[high + 1] = key;
 }
 }
 }
 */

/* Time Complexity: T(n)=n^2 */
void jv_insert_sort(int arr[], int len) {
	int j, key, i; /*复杂度*/
	for (j = 1; j < len; j++) { /*n-1*/
		key = arr[j]; /*key要插入的元素               n-1*/
		i = j - 1; /*n-1*/
		/*asc*/
		while (i >= 0 && arr[i] > key) { /*n(n-1)/2*/
			/*desc*/
			/*while(i>=0&&arr[i]<key){  //n(n-1)/2*/
			arr[i + 1] = arr[i]; /*n(n-1)/2*/
			i--; /*n(n-1)/2*/
		}
		arr[i + 1] = key; /*插入key    //n-1*/
	}
}

void jv_bubble_sort(int arr[], int len) {
	int i, j, tmp, n = len - 1;
	for (i = 0; i < n; i++) {
		for (j = 0; j < n - i; j++) {
			/*asc*/
			if (arr[j] > arr[j + 1]) {
				/*desc*/
				/*if(arr[j]<arr[j+1]){*/
				tmp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = tmp;
			}
		}
	}
}

void jv_select_sort(int arr[], int len) {
	int i, j, k, tmp;
	for (i = 0; i < len - 1; i++) {
		k = i; /*给记号赋值*/
		for (j = i + 1; j < len; j++) {
			/*asc*/
			if (arr[k] > arr[j])
				k = j; /*k总是指向最小元素*/
			/*desc*/
			/*if(arr[k]<arr[j])k=j; k总是指向最大元素*/
		}
		if (i != k) { /*当k!=i是才交换，否则arr[i]即为最小/大*/
			tmp = arr[i];
			arr[i] = arr[k];
			arr[k] = tmp;
		}
	}
}

/*
 shell法是一个叫 shell 的美国人与1969年发明的。
 它首先把相距k(k>=1)的那几个元素排好序，再缩小k值（一般取其一半），再排序，直到k=1时完成排序
 */
void jv_shell_sort(int arr[], int len) {
	int i, j, k, x;
	k = len / 2; /*间距值*/
	while (k >= 1) {
		for (i = k; i < len; i++) {
			x = arr[i];
			j = i - k;
			/*asc*/
			while (j >= 0 && x < arr[j]) {
				/*desc
				 //while(j>=0&&x>arr[j]) {*/
				arr[j + k] = arr[j];
				j -= k;
			}
			arr[j + k] = x;
		}
		k /= 2; /*缩小间距值*/
	}
}

void jv_binary_sort(int arr[], int len) {
	/*
	 int temp;
	 int i,j;
	 int l,r,m;
	 for(i=1;i<len;i++){
	 temp=arr[i];
	 l=0;  r=i-1;
	 while(l<=r){
	 m=(l+r)/2;
	 if(temp<arr[m]) {r=m-1;}
	 else {l=m+1;}
	 }
	 for(j=i-1;j>=l;j--) {
	 arr[j+1]=arr[j];
	 }
	 arr[l]=temp;

	 }*/

	int middle = 0, i;
	for (i = 1; i < len; i++) {
		int low = 0;
		int high = i - 1;
		int key = arr[i];
		int k;
		while (low <= high) {
			middle = (low + high) / 2;
			if (key < arr[middle])
				high = middle - 1;
			else
				low = middle + 1;
		}

		for (k = i; k > middle; k--)
			arr[k] = arr[k - 1];
		arr[low] = key; /* or use another statement arr[high+1] = key ;*/
	}
}

void jv_count_sort(int arr[], int len) {
	int j, i, z = 0;
	int *c = malloc(sizeof(int) * len);
	memset(c, 0, sizeof(int) * len);
	/* another initialize array with zero
	 for (j = 0; j < len; j++){
	 c[j]=0;
	 }
	 */

	for (j = 0; j < len; j++) {
		c[arr[j]]++;
	}

	for (i = 0; i < len; i++) {
		/*printf("C[%d]=%d\n",c[i],i);*/
		while (c[i]-- > 0) {
			arr[z++] = i;
		}

	}
	free(c);
}
