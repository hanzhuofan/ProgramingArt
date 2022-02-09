package com.hzf.study.program.nowcoder;

public class MaxWater {
    public static void main(String[] args) {
        MaxWater water = new MaxWater();
        System.out.println(water.maxWater(new int[]{3, 1, 2, 5, 2, 4}));
    }

    /**
     * max water
     *
     * @param arr int整型一维数组 the array
     * @return long长整型
     */
    public long maxWater(int[] arr) {
        // write code here
        if (arr.length < 3) {
            return 0;
        }
        long res = 0;
        int left = 0, right = arr.length - 1;
        int lm = arr[left], rm = arr[right];
        while (left < right) {
            if (arr[left] <= arr[right]) {
                if (arr[left] >= lm) {
                    lm = arr[left];
                } else {
                    res += lm - arr[left];
                }
                left++;
            } else {
                if (arr[right] >= rm) {
                    rm = arr[right];
                } else {
                    res += rm - arr[right];
                }
                right--;
            }
        }
        return res;
    }
}
