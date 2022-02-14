package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/14
 */
public class MaxSubArrayLengthTwo {
    public static void main(String[] args) {
        System.out.println(maxSubArrayLengthTwo(new int[] {7, 2, 3, 1, 5, 6}));
        System.out.println(maxSubArrayLengthTwo(new int[] {1,2,3,4}));
        System.out.println(maxSubArrayLengthTwo(new int[] {1,2,2,3}));
    }

    /**
     * 给定一个长度为n的正整数数组nums，可以任意改变数组的其中一个元素，改变的元素范围也在[1,100000]之内，然后返回nums的最长"严格上升"子数组的长度。
     * 1.子数组是连续的，比如[1,3,5,7,9]的子数组有[1,3]，[3,5,7]等等，但是[1,3,7]不是子数组
     * 2.严格上升指在数组上任意位置都满足 nums[i] < nums[i+1]，比如[1,2,2,3]，其中[1,2,2]不是严格上升的子数组，[1,2]是的
     *
     * @param nums int整型一维数组
     * @return int整型
     */
    public static int maxSubArrayLengthTwo (int[] nums) {
        // write code here
        if (nums.length <= 2) {
            return nums.length;
        }
        int fMax = 1, gMax = 1, hMax = 1;
        int f = 1, g = 1, h = 1;
        int vh = 1;

        for (int i = 1; i < nums.length; i++) {
            int f_cur = (nums[i] > nums[i-1]) ? f + 1 : 1;
            int g_curA = (nums[i] > nums[i-1]) ? g + 1 : 1;
            int g_curB = (nums[i] > vh) ? h + 1 : 1;
            int g_cur = Math.max(g_curA, g_curB);
            int h_cur = f + 1;

            f = f_cur;
            g = g_cur;
            h = h_cur;
            vh = nums[i-1] + 1;

            fMax = Math.max(fMax, f);
            gMax = Math.max(gMax, g);
            hMax = Math.max(hMax, h);
        }

        return Math.max(gMax, hMax);
    }
}
