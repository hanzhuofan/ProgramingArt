package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/23
 */
public class LongestCommonSubarry {

    /**
     * 给定两个整数数组，求两个数组的最长的公共子数组的长度。子数组是连续的，比如[1,3,5,7,9]的子数组有[1,3]，[3,5,7]等等，但是[1,3,7]不是子数组
     *
     *
     * @param A int整型一维数组
     * @param B int整型一维数组
     * @return int整型
     */
    public int longestCommonSubarry (int[] A, int[] B) {
        // write code here
        int[][] dp = new int[A.length + 1][B.length + 1];
        int ans = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                if (A[i] == B[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                    ans = Math.max(ans, dp[i + 1][j + 1]);
                }
            }
        }
        return ans;
    }
}
