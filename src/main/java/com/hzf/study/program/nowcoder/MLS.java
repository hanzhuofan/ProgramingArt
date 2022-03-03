package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/3/3
 */
public class MLS {

    /**
     * 给定无序数组arr，返回其中最长的连续序列的长度(要求值连续，位置可以不连续,例如 3,4,5,6为连续的自然数）
     *
     * @param arr int整型一维数组 the array
     * @return int整型
     */
    static int ans = 1;
    public static int MLS (int[] arr) {
        // write code here
        if (arr.length == 0) {
            return 0;
        }
        sort(arr, 0, arr.length - 1, new int[arr.length]);
        return ans;
    }

    private static void sort(int[] arr, int left, int right, int[] tmp) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2;
        sort(arr, left, mid, tmp);
        sort(arr, mid + 1, right, tmp);

        for (int i = left; i <= right; i++) {
            tmp[i] = arr[i];
        }

        int leftB = left, rightB = mid + 1, min = -1, max = 1;
        for (int i = left; i <= right; i++) {
            if (leftB > mid) {
                arr[i] = tmp[rightB++];
            } else if (rightB > right) {
                arr[i] = tmp[leftB++];
            } else if (tmp[leftB] > tmp[rightB]) {
                arr[i] = tmp[rightB++];
            } else {
                arr[i] = tmp[leftB++];
            }
            if (arr[i] - min > 1) {
                max = 1;
            } else if (arr[i] - min == 1) {
                max++;
            }
            min = arr[i];
            ans = Math.max(ans, max);
        }
    }

    public static void main(String[] args) {
        System.out.println(MLS(new int[]{100,4,200,1,3,22}));
    }
}
