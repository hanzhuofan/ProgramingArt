package com.hzf.study.program.sort;

import java.util.ArrayList;

/**
 * @author hanzhuofan
 * @date 2020/11/14 18:26
 */
public class QuicklySort2 {
    public static void main(String[] args) {
        QuicklySort2 code = new QuicklySort2();
        System.out.println(code.GetLeastNumbers_Solution(new int[] {4, 5, 1, 6, 2, 7, 3, 8}, 4));
        System.out.println(code.GetLeastNumbers_Solution(new int[] {1}, 0));
        System.out.println(code.GetLeastNumbers_Solution(new int[] {0, 1, 2, 1, 2}, 3));
        System.out.println(code.findKth(new int[] {1, 3, 5, 2, 2}, 5, 3));
        System.out.println(code.findKth(new int[] {10,10,9,9,8,7,5,6,4,3,4,2}, 12, 3));
    }

    public int findKth(int[] a, int n, int K) {
        // write code here
        qsort(a, 0, n - 1, K);
        return a[K -1];
    }

    private void qsort(int[] a, int left, int right, int k) {
        if (left >= right) return;
        int base = a[left], start = left, end = right;
        while (start < end) {
            while (base > a[end] && start < end) {
                end--;
            }
            if (start < end) {
                a[start] = a[end];
                start++;
            }
            while (base < a[start] && start < end) {
                start++;
            }
            if (start < end) {
                a[end] = a[start];
                end--;
            }
        }
        a[start] = base;
        if (start == k - 1) return;
        if (start > k - 1) qsort(a, left, start - 1, k);
        if (start < k - 1) qsort(a, start + 1, right, k);
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        sort(input, 0, input.length - 1, k);
        for (int i = 0; i < k; i++) {
            list.add(input[i]);
        }
        return list;
    }

    private void sort(int[] input, int left, int right, int k) {
        if (left >= right) {
            return;
        }
        int base = input[left], start = left, end = right;
        while (start < end) {
            while (base < input[end] && start < end) {
                end--;
            }
            if (start < end) {
                input[start] = input[end];
                start++;
            }
            while (base > input[start] && start < end) {
                start++;
            }
            if (start < end) {
                input[end] = input[start];
                end--;
            }
        }
        input[start] = base;
        sort(input, left, start - 1, k);
        if (start != k -1) {
            sort(input, start + 1, right, k);
        }
    }
}
