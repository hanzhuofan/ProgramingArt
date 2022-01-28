package com.hzf.study.program.huawei;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class TwoSum {
    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        System.out.println(Arrays.toString(twoSum.twoSum(new int[] {3, 2, 4}, 6)));
        System.out.println(Arrays.toString(twoSum.twoSum(new int[] {20, 70, 110, 150}, 90)));
    }

    public int[] twoSum(int[] numbers, int target) {
        // write code here
        int[] ans = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            int val = target - numbers[i];
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[j] == val) {
                    ans[0] = i + 1;
                    ans[1] = j + 1;
                    return ans;
                }
            }
        }
        return ans;
    }
}
