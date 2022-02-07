package com.hzf.study.program.huawei;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/7
 */
public class UniquePaths {
    public static void main(String[] args) {
        UniquePaths paths = new UniquePaths();
        System.out.println(paths.uniquePaths(2, 1));
        System.out.println(paths.uniquePaths(2, 2));
        System.out.println(paths.uniquePaths(2, 3));
    }
    /**
     *
     * @param m int整型
     * @param n int整型
     * @return int整型
     */
    public int uniquePaths (int m, int n) {
        // write code here
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                int up = i > 0 ? dp[i - 1][j] : 0, left = j > 0 ? dp[i][j - 1] : 0;
                dp[i][j] = up + left;
            }
        }
        return dp[m - 1][n - 1];
    }
}
