package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/16
 */
public class MaximalRectangle {

    /**
     * 给定一个仅包含 0 和 1 ，大小为 n*m 的二维二进制矩阵，找出仅包含 1 的最大矩形并返回面积。
     * 例如输入[[1,0,1,0,0],[1,1,1,1,0],[1,1,1,1,1],[1,0,0,1,0]]时，对应的输出为8
     *
     * @param matrix int整型二维数组
     * @return int整型
     */
    public static int maximalRectangle (int[][] matrix) {
        // write code here
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 1) {
                    dp[i][j] = (j > 0 ? dp[i][j - 1] : 0) + 1;
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (dp[i][j] != 0) {
                    int min = dp[i][j];
                    for (int k = 0; k <= i;) {
                        if (dp[i - k][j] == 0) {
                            break;
                        }
                        min = Math.min(min, dp[i - k][j]);
                        ans = Math.max(ans, min * ++k);
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(maximalRectangle(new int[][] {{1, 0, 1, 0, 0}, {1, 1, 1, 1, 0}, {1, 1, 1, 1, 1}, {1, 0, 0, 1, 0}}));
    }
}
