package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/3/3
 */
public class FindMedianinTwoSortedAray {

    /**
     * 给定两个递增数组arr1和arr2，已知两个数组的长度都为N，求两个数组中所有数的上中位数。
     * 上中位数：假设递增序列长度为n，为第n/2个数
     *
     * @param arr1 int整型一维数组 the array1
     * @param arr2 int整型一维数组 the array2
     * @return int整型
     */
    public static int findMedianinTwoSortedAray (int[] arr1, int[] arr2) {
        // write code here
        int m = 0, n = 0;
        for (int i = 0; i < arr1.length - 1; i++) {
            if (arr1[m] < arr2[n]) {
                m++;
            } else {
                n++;
            }
        }
        return Math.min(arr1[m], arr2[n]);
    }

    public static void main(String[] args) {
        System.out.println(findMedianinTwoSortedAray(new int[]{1, 2, 3, 4}, new int[]{3, 4, 5, 6}));
    }
}
