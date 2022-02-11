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
}
