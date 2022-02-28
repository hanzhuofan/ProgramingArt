package com.hzf.study.program.nowcoder;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/25
 */
public class MySort {
    /**
     * 给定一个长度为 n 的数组，请你编写一个函数，返回该数组按升序排序后的结果。
     * 将给定数组排序
     * @param arr int整型一维数组 待排序的数组
     * @return int整型一维数组
     */
    public static int[] MySort (int[] arr) {
        // write code here
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        return arr;
    }

    public static int[] MySort2(int[] arr) {
        // write code here
        qsort(arr, 0, arr.length - 1);
        return arr;
    }

    private static void qsort(int[] arr, int begin, int end) {
        if (begin >= end) {
            return;
        }
        int base = arr[begin], left = begin, right = end;
        while (left < right) {
            while (base < arr[right] && left < right) {
                right--;
            }
            if (left < right) {
                arr[left++] = arr[right];
            }
            while (arr[left] < base && left < right) {
                left++;
            }
            if (left < right) {
                arr[right--] = arr[left];
            }
        }
        arr[left] = base;
        qsort(arr, begin, left - 1);
        qsort(arr, left + 1, end);
    }

    public static int[] MySort3(int[] arr) {
        // write code here
        sort(arr, new int[arr.length], 0, arr.length - 1);
        return arr;
    }

    private static void sort(int[] arr, int[] tmp, int begin, int end) {
        if (begin >= end) return;
        int mid = begin + (end - begin) / 2;
        sort(arr, tmp, begin, mid);
        sort(arr, tmp, mid + 1, end);

        System.arraycopy(arr, begin, tmp, begin, end + 1 - begin);
        int left = begin, right = mid + 1;
        for (int i = begin; i <= end; i++) {
            if (left > mid) {
                arr[i] = tmp[right++];
            } else if (right > end) {
                arr[i] = tmp[left++];
            } else if (tmp[left] > tmp[right]) {
                arr[i] = tmp[right++];
            } else {
                arr[i] = tmp[left++];
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(MySort(new int[] {5,2,3,1,4})));
        System.out.println(Arrays.toString(MySort2(new int[] {5,2,3,1,4})));
        System.out.println(Arrays.toString(MySort(new int[] {5,1,6,2,5})));
        System.out.println(Arrays.toString(MySort2(new int[] {5,1,6,2,5})));
        System.out.println(Arrays.toString(MySort3(new int[] {5,2,3,1,4})));
        System.out.println(Arrays.toString(MySort3(new int[] {5,1,6,2,5})));
    }
}
