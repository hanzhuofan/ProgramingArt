package com.hzf.study.program.nowcoder;

import lombok.EqualsAndHashCode;

import java.util.HashMap;

/**
 * @author zhuofan.han
 * @date 2022/2/16
 */
public class Rob {

    /**
     * 你是一个经验丰富的小偷，准备偷沿街的一排房间，每个房间都存有一定的现金，为了防止被发现，你不能偷相邻的两家，即，如果偷了第一家，就不能再偷第二家；如果偷了第二家，那么就不能偷第一家和第三家。
     * 给定一个整数数组nums，数组中的元素表示每个房间存有的现金数额，请你计算在不被发现的前提下最多的偷窃金额。
     *
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public static int rob (int[] nums) {
        // write code here
        int[] dp = new int[nums.length];
        int ans = 0, max = 0;
        for (int i = 0; i < nums.length; i++) {
            dp[i] = nums[i] + max;
            max = Math.max(i > 0 ? dp[i - 1] : 0, i > 1 ? dp[i - 2] : (i > 0 ? dp[i - 1] : 0));
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    /**
     * 你是一个经验丰富的小偷，准备偷沿湖的一排房间，每个房间都存有一定的现金，为了防止被发现，你不能偷相邻的两家，即，如果偷了第一家，就不能再偷第二家，如果偷了第二家，那么就不能偷第一家和第三家。沿湖的房间组成一个闭合的圆形，即第一个房间和最后一个房间视为相邻。
     * 给定一个长度为n的整数数组nums，数组中的元素表示每个房间存有的现金数额，请你计算在不被发现的前提下最多的偷窃金额。
     *
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public static int rob2(int[] nums) {
        // write code here
        if (nums.length == 1) {
            return 0;
        }
        int[] dp0 = new int[nums.length];
        int[] dp1 = new int[nums.length];
        int ans = 0, max0 = 0, max1 = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            dp0[i] = nums[i] + max0;
            max0 = Math.max(i == 0 ? 0 : dp0[i - 1], i > 1 ? dp0[i - 2] : (i == 0 ? 0 : dp0[i - 1]));

            int j = i + 1;
            dp1[j] = nums[j] + max1;
            max1 = Math.max(dp1[j - 1], j > 1 ? dp1[j - 2] : dp1[j - 1]);

            ans = Math.max(ans, Math.max(dp0[i], dp1[j]));
        }
        return ans;
    }

    /**
     * 你是一个经验丰富的小偷，经过上次在街边和湖边得手后你准备挑战一次自己，你发现了一个结构如二叉树的小区，小区内每个房间都存有一定现金，你观察到除了小区入口的房间以外每个房间都有且仅有一个父房间和至多两个子房间。
     * 问，给定一个二叉树结构的小区，如之前两次行动一样，你无法在不触动警报的情况下同时偷窃两个相邻的房间，在不触动警报的情况下最多的偷窃金额。
     *
     * @param root TreeNode类
     * @return int整型
     */
    public static int rob3(TreeNode root) {
        // write code here
        HashMap<TreeNode, Integer> dp0 = new HashMap<>();
        HashMap<TreeNode, Integer> dp1 = new HashMap<>();
        dp0.put(null, 0);
        dp1.put(null, 0);
        rob(root, dp0, dp1);
        return Math.max(dp0.get(root), dp1.get(root));
    }

    private static void rob(TreeNode root, HashMap<TreeNode, Integer> dp0, HashMap<TreeNode, Integer> dp1) {
        if (root == null) {
            return ;
        }
        rob(root.left, dp0, dp1);
        rob(root.right, dp0, dp1);
        dp0.put(root, root.val + dp1.get(root.left) + dp1.get(root.right));
        dp1.put(root, Math.max(dp0.get(root.left), dp1.get(root.left)) + Math.max(dp0.get(root.right), dp1.get(root.right)));
    }

    public static void main(String[] args) {
        System.out.println(rob(new int[]{1,2,3,4}));
        System.out.println(rob(new int[]{1,3,6}));
        System.out.println(rob(new int[]{2,10,5}));
        System.out.println(rob2(new int[]{1,2,3,4}));
        System.out.println(rob2(new int[]{1,3,6}));
        System.out.println(rob2(new int[]{19,43,94,4,34,33,91,75,38,79}));
        System.out.println(rob2(new int[]{69,27,25,44,1,16,76,98,22,52}));
    }

    @EqualsAndHashCode
    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
