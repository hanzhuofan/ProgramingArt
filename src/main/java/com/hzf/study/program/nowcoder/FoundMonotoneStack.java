package com.hzf.study.program.nowcoder;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author zhuofan.han
 * @date 2022/2/10
 */
public class FoundMonotoneStack {
    public static void main(String[] args) {
        FoundMonotoneStack stack = new FoundMonotoneStack();
        System.out.println(Arrays.deepToString(stack.foundMonotoneStack(new int[]{3, 4, 1, 5, 6, 2, 7})));
    }

    /**
     * 单调栈
     * 给定一个长度为 n 的可能含有重复值的数组 arr ，找到每一个 i 位置左边和右边离 i 位置最近且值比 arri 小的位置。
     *
     * @param nums int一维数组
     * @return int二维数组
     */
    public int[][] foundMonotoneStack (int[] nums) {
        // write code here
        int[][] ans = new int[nums.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                ans[i][0] = -1;
            } else {
                ans[i][0] = stack.peek();
            }
            stack.push(i);
        }
        stack.clear();
        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                ans[i][1] = -1;
            } else {
                ans[i][1] = stack.peek();
            }
            stack.push(i);
        }
        return ans;
    }
}
