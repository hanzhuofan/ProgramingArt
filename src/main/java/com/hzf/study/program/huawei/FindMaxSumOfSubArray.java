package com.hzf.study.program.huawei;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class FindMaxSumOfSubArray {
    public static void main(String[] args) {
        FindMaxSumOfSubArray sum = new FindMaxSumOfSubArray();
        System.out.println(sum.FindGreatestSumOfSubArray(new int[] {1,-2,3,10,-4,7,2,-5}));
        System.out.println(sum.FindGreatestSumOfSubArray(new int[] {2}));
        System.out.println(sum.FindGreatestSumOfSubArray(new int[] {-10}));
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
}
