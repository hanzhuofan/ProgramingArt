package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/13
 */
public class FindUnsortedSubarray {
    public static void main(String[] args) {
        System.out.println(findUnsortedSubarray(new int[] {2, 6, 4, 8, 10, 9, 15}));
        System.out.println(findUnsortedSubarray(new int[] {1, 2, 3, 5, 4}));
        System.out.println(findUnsortedSubarray(new int[] {1,24,14249,62,346,9234,6923,469,32896,23,6234}));
    }

    /**
     * 给定一个整数数组，你需要找出一个连续子数组，将这个子数组升序排列后整个数组都将是升序数组。 请你找出满足题设的最短的子数组。
     *
     *
     * @param nums
     *            int整型一维数组
     * @return int整型
     */
    public static int findUnsortedSubarray(int[] nums) {
        // write code here
        int min = 0, minVal = 10000, max = 0, maxVal = 0;
        // 左开始找第一个逆序索引
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                min = i;
                max = i + 1;
                maxVal = nums[i];
                break;
            }
        }
        // 右开始找第一个逆序索引
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] < maxVal) {
                max = i;
                break;
            }
            if (nums[i] < nums[i - 1]) {
                max = i;
                break;
            }
        }
        // 寻找子数组中的最小值
        for (int i = min; i <= max; i++) {
            minVal = Math.min(nums[i], minVal);
        }
        // 左边逆序之前第一个小于最小值的索引
        for (int i = min; i >=0; i--) {
            if (nums[i] <= minVal) {
                min = i + 1;
                break;
            }
        }
        return max == 0 ? 0 : max - min + 1;
    }
}
