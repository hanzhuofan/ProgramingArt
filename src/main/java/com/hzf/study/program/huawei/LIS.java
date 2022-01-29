package com.hzf.study.program.huawei;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class LIS {
    public static void main(String[] args) {
        LIS lis = new LIS();
        System.out.println(Arrays.toString(lis.LIS(new int[] {2, 1, 5, 3, 6, 4, 8, 9, 7})));
        System.out.println(Arrays.toString(lis.LIS(new int[] {1, 2, 8, 6, 4})));
    }

    /**
     * retrun the longest increasing subsequence
     *
     * @param arr
     *            int整型一维数组 the array
     * @return int整型一维数组
     */
    public int[] LIS(int[] arr) {
        // write code here
        int len = 1;
        // end用来记录长度所对应的最大值,最长就是 n，加速匹配dp
        int[] end = new int[arr.length + 1];
        end[len] = arr[0];
        // 记录i位置的最大递增长度
        int[] dp = new int[arr.length];
        dp[0] = len;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > end[len]) {
                end[++len] = arr[i];
                dp[i] = len;
            } else {
                // 如果不是 那就从 1到 之前递增间找一个递增的
                int left = 1, right = len, pos = 0;
                while (left <= right) {
                    int mid = (left + right) >> 1;
                    if (end[mid] < arr[i]) {
                        pos = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                end[pos + 1] = arr[i];
                dp[i] = pos + 1;
            }
        }
        int[] ans = new int[len];
        for (int i = arr.length - 1; len > 0; i--) {
            if (dp[i] == len) {
                ans[--len] = arr[i];
            }
        }
        return ans;
    }
}
