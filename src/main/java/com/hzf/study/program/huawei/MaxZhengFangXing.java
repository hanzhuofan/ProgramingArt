package com.hzf.study.program.huawei;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/7
 */
public class MaxZhengFangXing {
    public static void main(String[] args) {
        int[][] data = {{1, 0, 1, 0, 0}, {1, 0, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 0, 0, 1, 0}};
        for (int[] datum : data) {
            System.out.println(Arrays.toString(datum));
        }
        System.out.println(solve(data));
        System.out.println(solve(new int[0][0]));
    }

    public static int solve(int[][] matrix) {
        // write code here
        int ans = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return ans;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1) {
                    int leftUp = i == 0 || j == 0 ? 0 : dp[i - 1][j - 1];
                    int up = i == 0 ? 0 : dp[i - 1][j];
                    int left = j == 0 ? 0 : dp[i][j - 1];
                    dp[i][j] = Math.min(Math.min(left, up), leftUp) + 1;
                    if (dp[i][j] > ans) {
                        ans = dp[i][j];
                    }
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return ans * ans;
    }
}
