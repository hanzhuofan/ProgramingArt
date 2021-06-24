package com.hzf.study.program.sort;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2021/6/24
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        sort(arr, 0, arr.length - 1);
        Arrays.stream(arr).forEach(System.out::println);
    }

    private static void sort(int[] arr, int begin, int end) {
        if (end - begin < 2) {
            if (arr[begin] > arr[end]) {
                swap(arr, begin, end);
            }
            return;
        }
        int mid = (begin + end) / 2;
        sort(arr, begin, mid);
        sort(arr, mid + 1, end);
        merge(arr, begin, mid + 1, end);
    }

    private static void merge(int[] arr, int begin, int mid, int end) {
        int[] copy = new int[end - begin + 1];
        System.arraycopy(arr, begin, copy, 0, copy.length);
        int m = 0;
        mid -= begin;
        int n = mid;
        for (int i = 0; i < copy.length; i++) {
            if (m >= mid) {
                arr[begin++] = copy[n++];
            } else if (n >= copy.length) {
                arr[begin++] = copy[m++];
            } else if (copy[m] < copy[n]) {
                arr[begin++] = copy[m++];
            } else {
                arr[begin++] = copy[n++];
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
