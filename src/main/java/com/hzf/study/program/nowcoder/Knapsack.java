package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/22
 */
public class Knapsack {

    /**
     * 已知一个背包最多能容纳体积之和为v的物品
     * 现有 n 个物品，第 i 个物品的体积为 vi , 重量为 wi
     * 求当前背包最多能装多大重量的物品?
     * 计算01背包问题的结果
     * @param V int整型 背包的体积
     * @param n int整型 物品的个数
     * @param vw int整型二维数组 第一维度为n,第二维度为2的二维数组,vw[i][0],vw[i][1]分别描述i+1个物品的vi,wi
     * @return int整型
     */
    public int knapsack (int V, int n, int[][] vw) {
        // write code here
        if (V == 0 || n == 0 || vw == null) {
            return 0;
        }
        int[][] dp = new int[n + 1][V + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= V; j++) {
                int v = vw[i - 1][0];
                int w = vw[i - 1][1];
                if (j < v) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - v] + w);
                }
            }
        }
        return dp[n][V];
    }
}
