package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/11
 */
public class MaxProfit {
    /**
     * 买入一次股票和卖出一次股票,假设你有一个数组prices，长度为n，其中prices[i]是股票在第i天的价格，请根据这个价格数组，返回买卖股票能获得的最大收益
     * @param prices int整型一维数组
     * @return int整型
     */
    public int maxProfit (int[] prices) {
        // write code here
        int ans = 0;
        int min = (int)Math.pow(10, 5);
        for (int price : prices) {
            if (price < min) {
                min = price;
            } else {
                ans = Math.max(ans, price - min);
            }
        }
        return ans;
    }

    /**
     * 你可以多次买卖该只股票，但是再次购买前必须卖出之前的股票,假设你有一个数组prices，长度为n，其中prices[i]是股票在第i天的价格，请根据这个价格数组，返回买卖股票能获得的最大收益
     * @param prices int整型一维数组
     * @return int整型
     */
    public int maxProfit2(int[] prices) {
        // write code here
        int ans = 0;
        int min = prices[0];
        for (int price : prices) {
            if (price > min) {
                ans += price - min;
            }
            min = price;
        }
        return ans;
    }

    /**
     * 假设你有一个数组prices，长度为n，其中prices[i]是某只股票在第i天的价格，请根据这个价格数组，返回买卖股票能获得的最大收益
     * 1. 你最多可以对该股票有两笔交易操作，一笔交易代表着一次买入与一次卖出，但是再次购买前必须卖出之前的股票
     * 2. 如果不能获取收益，请返回0
     * 3. 假设买入卖出均无手续费
     *
     * @param prices int整型一维数组 股票每一天的价格
     * @return int整型
     */
    public int maxProfit3(int[] prices) {
        // write code here
        int n = prices.length;

        int buy1 = -prices[0], sell1 = 0;
        int buy2 = -prices[0], sell2 = 0;

        for (int i = 1; i < n; i++) {
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }
        return sell2;
    }

    /**
     * 假设你有一个数组prices，长度为n，其中prices[i]是某只股票在第i天的价格，请根据这个价格数组，返回买卖股票能获得的最大收益
     * 1. 你最多可以对该股票有kk笔交易操作，一笔交易代表着一次买入与一次卖出，但是再次购买前必须卖出之前的股票
     * 2. 如果不能获取收益，请返回0
     * 3. 假设买入卖出均无手续费
     *
     * @param prices int整型一维数组 股票每一天的价格
     * @return int整型
     */
    public static int maxProfit4(int[] prices, int k) {
        // write code here
        int[][] dp = new int[k + 1][2];
        for (int i = 0; i < prices.length; i++) {
            for (int j = k; j >= 1; j--) {
                if (i == 0) {
                    // sell
                    dp[j][0] = 0;
                    // buy
                    dp[j][1] = -prices[0];
                } else {
                    dp[j][0] = Math.max(dp[j][0], dp[j][1] + prices[i]);
                    dp[j][1] = Math.max(dp[j][1], dp[j - 1][0] - prices[i]);
                }
            }
        }
        return dp[k][0];
    }

    public static void main(String[] args) {
        System.out.println(maxProfit4(new int[]{8,9,3,5,1,3}, 3));
    }
}
