package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class MaxLongNumber {
    public static void main(String[] args) {
        MaxLongNumber number = new MaxLongNumber();
        System.out.println(number.maxLength(new int[] {2, 3, 4, 5}));
        System.out.println(number.maxLength(new int[] {2, 2, 3, 4, 3}));
        System.out.println(number.maxLength(new int[] {9}));
    }

    /**
     * @param arr int整型一维数组 the array
     * @return int整型
     */
    public int maxLength(int[] arr) {
        // write code here
        int result = 0, start = 0;
        int[] hash = new int[100000];
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            if (start < hash[num]) {
                start = hash[num];
            }
            if (result < i - start + 1) {
                result = i - start + 1;
            }
            hash[num] = i + 1;
        }
        return result;
    }
}
