package com.hzf.study.program.nowcoder;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class FindGreatestSumOfSubArray {
    public static void main(String[] args) {
        FindGreatestSumOfSubArray sum = new FindGreatestSumOfSubArray();
        System.out.println(sum.FindGreatestSumOfSubArray(new int[] {1,-2,3,10,-4,7,2,-5}));
        System.out.println(sum.FindGreatestSumOfSubArray(new int[] {2}));
        System.out.println(sum.FindGreatestSumOfSubArray(new int[] {-10}));
        System.out.println(Arrays.toString(sum.FindGreatestSumOfSubArray2(new int[]{1, -2, 3, 10, -4, 7, 2, -5})));
        System.out.println(Arrays.toString(sum.FindGreatestSumOfSubArray2(new int[]{1})));
        System.out.println(Arrays.toString(sum.FindGreatestSumOfSubArray2(new int[]{-2,-1})));
        System.out.println(Arrays.toString(sum.FindGreatestSumOfSubArray2(new int[]{1,2,-3,4,-1,1,-3,2})));
    }

    public int FindGreatestSumOfSubArray(int[] array) {
        int ans = array[0];
        int tmp = 0;
        for (int a : array) {
            tmp += a;
            if (ans < tmp) {
                ans = tmp;
            }
            if (tmp < 0) {
                tmp = 0;
            }
        }
        return ans;
    }

    /**
     * 输入一个长度为n的整型数组array，数组中的一个或连续多个整数组成一个子数组，找到一个具有最大和的连续子数组。
     * 1.子数组是连续的，比如[1,3,5,7,9]的子数组有[1,3]，[3,5,7]等等，但是[1,3,7]不是子数组
     * 2.如果存在多个最大和的连续子数组，那么返回其中长度最长的，该题数据保证这个最长的只存在一个
     * 3.该题定义的子数组的最小长度为1，不存在为空的子数组，即不存在[]是某个数组的子数组
     * 4.返回的数组不计入空间复杂度计算
     *
     * @param array int整型一维数组
     * @return int整型一维数组
     */
    public int[] FindGreatestSumOfSubArray2(int[] array) {
        // write code here
        int startIndex = 0, endIndex = 0, sum = 0;
        int startIndexMax = 0, endIndexMax = 0, sumMax = -100;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
            if (sum >= sumMax) {
                sumMax = sum;
                startIndexMax = startIndex;
                endIndexMax = endIndex + 1;
            }
            if (sum < 0) {
                sum = 0;
                startIndex = i + 1;
                endIndex = i;
            }
            endIndex++;
        }
        int[] ans = new int[endIndexMax - startIndexMax];
        System.arraycopy(array, startIndexMax, ans, 0, endIndexMax - startIndexMax);
        return ans;
    }
}
