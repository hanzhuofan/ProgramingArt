package com.hzf.study.program.sort;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2021/6/24
 */
public class QuicklySort {
    public static void main(String[] args) {
        int[] arr = new int[]{3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        sort(arr, 0, arr.length - 1);
        Arrays.stream(arr).forEach(System.out::println);
    }

    private static void sort(int[] arr, int begin, int end) {
        if (end <= begin) {
            return;
        }
        int base = arr[begin];
        int index = begin;
        for (int i = index + 1; i <= end; i++) {
            if (arr[i] < base) {
                if (i > index + 1) {
                    swap(arr, index + 1, i);
                }
                index++;
            }
        }

        swap(arr, index, begin);

        sort(arr, begin, index - 1);
        sort(arr, index + 1, end);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
