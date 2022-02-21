package com.hzf.study.program.nowcoder;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/20
 */
public class CanJump {

    public static void main(String[] args) {
        System.out.println(canJump(new int[]{2, 1, 3, 3, 0, 0, 100}));
        System.out.println(canJump(new int[]{2, 1, 3, 2, 0, 0, 100}));
        System.out.println(canJump(new int[]{0}));
        System.out.println(maxJumpGrade(new int[]{2,4,2,1,0,100}));
        System.out.println(maxJumpGrade(new int[]{2,4,0,2,0,100}));
        System.out.println(maxJumpGrade(new int[]{2,3,2,1,0,100}));
        System.out.println(maxJumpGrade2(new int[]{2,4,2,1,0,100}));
        System.out.println(maxJumpGrade2(new int[]{2,4,0,2,0,100}));
        System.out.println(maxJumpGrade2(new int[]{2,3,2,1,0,100}));
    }

    /**
     * 给定一个非负整数数组nums，假定最开始处于下标为0的位置，数组里面的每个元素代表下一跳能够跳跃的最大长度。如果能够跳到数组最后一个位置，则返回true，否则返回false。
     *
     * @param nums int整型一维数组
     * @return bool布尔型
     */
    public static boolean canJump (int[] nums) {
        // write code here
        int[] dp = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i == nums.length - 1) {
                return true;
            }
            dp[i] = Math.max(i == 0 ? 0 : dp[i - 1] - 1, nums[i]);
            if (dp[i] == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给定一个非负整数数组nums，假定最开始处于下标为0的位置，数组里面的每个元素代表下一跳能够跳跃的最大长度，如果可以跳到数组最后一个位置，请你求出跳跃路径中所能获得的最多的积分。
     * 1.如果能够跳到数组最后一个位置，才能计算所获得的积分，否则积分值为-1
     * 2.如果无法跳跃(即数组长度为0)，也请返回-1
     * 3.数据保证返回的结果不会超过整形范围，即不会超过2^{31}-1
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public static int maxJumpGrade (int[] nums) {
        // write code here
        int[] dp = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i == nums.length - 1) {
                return sumNums(nums);
            }
            int left = i == 0 ? 0 : dp[i - 1] - 1;
            dp[i] = Math.max(left, nums[i]);
            if (dp[i] == 0) {
                return -1;
            }
        }
        return -1;
    }

    private static int sumNums(int[] nums) {
        int ans = 0;
        int i0 = 0;
        boolean has0 = false;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (!has0 && nums[i] == 0) {
                i0 = 0;
                has0 = true;
            }
            if (has0) {
                if (nums[i] <= i0) {
                    i0++;
                } else {
                    has0 = false;
                    ans += nums[i];
                }
            } else {
                ans += nums[i];
            }
        }
        return ans;
    }

    public static int maxJumpGrade2(int[] nums) {
        // write code here
        if (nums.length == 0) {
            return -1;
        }
        int ans = nums[nums.length - 1];
        int i0 = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= i0) {
                i0 = i;
                ans += nums[i];
            }
        }
        return i0 != 0 ? -1 : ans;
    }

    /**
     * 给定一个非负整数数组nums，假定最开始处于下标为0的位置，数组里面的每个元素代表下一跳能够跳跃的最大长度。请你判断最少跳几次能跳到数组最后一个位置。
     * 1.如果跳不到数组最后一个位置或者无法跳跃(即数组长度为0)，请返回-1
     * 2.数据保证返回的结果不会超过整形范围，即不会超过2^{31}
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public int minJumpStep(int[] nums) {
        // write code here
        if (nums.length == 0) {
            return -1;
        }

        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (i > maxPosition) {
                return -1;
            }
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }

        return maxPosition < nums.length - 1 ? -1 : steps;
    }
}
