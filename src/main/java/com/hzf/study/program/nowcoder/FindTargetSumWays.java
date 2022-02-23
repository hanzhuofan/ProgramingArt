package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/23
 */
public class FindTargetSumWays {

    /**
     * 给定一个整数数组nums和一个整数target，请你返回该数组能构成多少种不同的表达式等于target。
     * 规则如下：
     * 1.将数组里每个整数前面可以添加"+"或者"-"符号，组成一个表达式，例如[1,2]，可以变成”+1+2","+1-2","-1+2","-1-2"，这四种
     * 2.只能添加"+"与"-"符号，不能添加其他的符号
     * 3.如果构不成等于target的表达式，请返回0
     * 4.保证返回的结果个数在整数范围内
     *
     *
     * @param nums int整型一维数组
     * @param target int整型
     * @return int整型
     */
    public int findTargetSumWays (int[] nums, int target) {
        // write code here
        findTargetSum(nums, nums.length - 1, target);
        return ans;
    }

    int ans = 0;
    private void findTargetSum(int[] nums, int i, int target) {
        if (i == nums.length - 1) {
            if (target == 0) {
                ans++;
            }
            return;
        }

        findTargetSum(nums, i - 1, target - nums[i]);
        findTargetSum(nums, i - 1, target + nums[i]);
    }

    public int findTargetSumWays2(int[] nums, int target) {
        //边界情况判断
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        // 记录累加和
        int sum = 0;
        // 遍历nums数组
        for (int num : nums) {
            sum += num;
        }
        // 计算背包容量
        int V = (sum + target) / 2;
        // 如果为奇数，说明nums数组中找不打和为(sum+target)/2的若干数字
        if ((sum + target) % 2 == 1) {
            return 0;
        }

        // dp[i][j]表示i个元素时，有多少种不同的组合，其累加和为j
        int[][] dp = new int[n + 1][V + 1];
        // 初始化
        dp[0][0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= V; j++) {
                // 默认不选当前数字
                dp[i + 1][j] = dp[i][j];
                // 如果选择当前数字，则需要加上j-nums[i]时的组合数
                if (j >= nums[i]) {
                    dp[i + 1][j] += dp[i][j - nums[i]];
                }
            }
        }
        return dp[n][V];
    }

    public int findTargetSumWays3(int[] nums, int target) {
        //边界情况判断
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        // 记录累加和
        int sum = 0;
        // 遍历nums数组
        for (int num : nums) {
            sum += num;
        }
        // 计算背包容量
        int V = (sum + target) / 2;
        // 如果为奇数，说明nums数组中找不打和为(sum+target)/2的若干数字
        if ((sum + target) % 2 == 1) {
            return 0;
        }

        // dp[j]表示有多少种不同的组合，其累加和为j
        int[] dp = new int[V + 1];
        // 初始化
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            // 每个数字只选一次，所以需要倒序遍历，避免重复
            for (int j = V; j >= nums[i]; j--) {
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[V];
    }
}
