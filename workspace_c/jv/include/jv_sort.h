#ifndef _JV_SORT_H_INCLUDED_
#define _JV_SORT_H_INCLUDED_

#include <jv_core.h>

void jv_insert_sort(int arr[], int len);

void jv_bubble_sort(int arr[], int len);

void jv_select_sort(int arr[], int len);

void jv_shell_sort(int arr[], int len);

void jv_binary_sort(int arr[], int len);

void jv_count_sort(int arr[], int len);

/*

 堆排序
 void jv_heap_sort(int arr[],int len);

 基数排序
 void jv_radix_sort(int arr[],int len);

 桶排序
 void jv_bucket_sort(int arr[],int len);
 */

#endif /* JV_SORT_H_INCLUDED_ */
