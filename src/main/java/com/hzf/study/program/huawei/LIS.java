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
    }

    /**
     * retrun the longest increasing subsequence
     *
     * @param arr int整型一维数组 the array
     * @return int整型一维数组
     */
    public int[] LIS(int[] arr) {
        // write code here
        int[] dp = new int[arr.length];
        Arrays.fill(dp, 1);
        int max = 0;
        for (int i = 1; i < arr.length; i++) {
            int j = i - 1;
            while (j >= 0 && arr[j] > arr[i]) {
                j--;
            }
            if (j > 0) {
                dp[i] = dp[j] + 1;
                if (dp[i] > max) {
                    max = dp[i];
                }
            }
        }
        int[] ans = new int[max];
        for (int i = arr.length - 1; i >= 0;i--) {
            int index = dp[i] - 1;
            if (ans[index] > arr[i]) {
                ans[index] = arr[i];
            }
        }
        return ans;
    }
}
