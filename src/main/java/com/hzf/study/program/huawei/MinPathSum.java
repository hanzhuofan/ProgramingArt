package com.hzf.study.program.huawei;

import java.util.Arrays;

public class MinPathSum {

    public static void main(String[] args) {
        int[][] arr = {{3, 3, 1, 6, 7, 6, 0, 7, 8, 0}, {5, 0, 5, 0, 5, 3, 9, 8, 0, 4}, {2, 8, 8, 9, 0, 6, 8, 7, 6, 7}, {6, 1, 0, 9, 0, 9, 6, 0, 6, 6}, {2, 3, 6, 9, 5, 3, 2, 4, 3, 4}, {1, 5, 2, 1, 4, 5, 8, 4, 2, 6}, {3, 0, 0, 5, 0, 0, 4, 6, 2, 2}, {4, 6, 6, 2, 5, 1, 7, 9, 8, 0}, {3, 1, 7, 7, 2, 4, 2, 0, 8, 6}, {9, 4, 7, 9, 9, 7, 1, 4, 5, 5}};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(Arrays.toString(arr[i]));
        }
        MinPathSum sum = new MinPathSum();
        System.out.println(sum.minPathSum(arr));
    }
    /**
     *
     * @param matrix int整型二维数组 the matrix
     * @return int整型
     */
    int n,m;

    public int minPathSum2(int[][] matrix) {
        // write code here
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        dp[0][0] = matrix[0][0];
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + matrix[0][i];
        }
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + matrix[i][0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
            }
        }
        return dp[n - 1][m - 1];
    }

    public int minPathSum(int[][] matrix) {
        // write code here
        n = matrix.length;
        m = matrix[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int min;
                if (i == 0 && j  == 0) {
                    min = 0;
                } else if (i == 0) {
                    min = matrix[i][j - 1];
                } else if (j == 0) {
                    min = matrix[i - 1][j];
                } else {
                    min = Math.min(matrix[i - 1][j], matrix[i][j - 1]);
                }
                matrix[i][j] = min + matrix[i][j];
            }
        }
        return matrix[n - 1][m - 1];
    }

    // 暴力解法
    private int minPathSum(int[][] matrix, int i, int j, int ans) {
        if (i == n || j == m) {
            return Integer.MAX_VALUE;
        }
        ans += matrix[i][j];
        if (i == n - 1 && j == m - 1) {
            return ans;
        } else {
            int next = minPathSum(matrix, i + 1, j, ans);
            int left = minPathSum(matrix, i, j + 1, ans);
            if (next < left) {
                return next;
            } else {
                return left;
            }
        }
    }
}
