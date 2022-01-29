package com.hzf.study.program.huawei;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class FindKth {
    public static void main(String[] args) {
        FindKth findKth = new FindKth();
        System.out.println(findKth.findKth(new int[] {1,3,5,2,2}, 5, 3));
        System.out.println(findKth.findKth(new int[] {10,10,9,9,8,7,5,6,4,3,4,2}, 12, 3));
    }

    public int findKth(int[] a, int n, int K) {
        // write code here
        qsort(a, 0, n - 1, K);
        return a[K - 1];
    }

    private void qsort(int[] a, int begin, int end, int K) {
        if (begin >= end) {
            return;
        }
        int left = begin, right = end, base = a[begin];
        while (left < right) {
            while (base > a[right] && left < right) {
                right--;
            }
            if (left >= right) break;
            a[left] = a[right];
            left++;
            while (base < a[left] && left < right) {
                left++;
            }
            if (left >= right) break;
            a[right] = a[left];
            right--;
        }

        a[left] = base;
        if (left == K - 1) return;
        if (left > K - 1) qsort(a, begin, left - 1, K);
        if (left < K - 1) qsort(a, left + 1, end, K);
    }
}
