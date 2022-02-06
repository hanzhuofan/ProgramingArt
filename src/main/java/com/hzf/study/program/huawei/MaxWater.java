package com.hzf.study.program.huawei;

public class MaxWater {

    /**
     * max water
     *
     * @param arr int整型一维数组 the array
     * @return long长整型
     */
    public long maxWater(int[] arr) {
        // write code here
        int len = arr.length;
        if (len < 3) return 0;
        long res = 0;
        int left = 0, right = len - 1;
        int lm = arr[0], rm = arr[right];
        while (left < right) {
            if (arr[left] <= arr[right]) {
                if (arr[left] >= lm) lm = arr[left];
                else res += lm - arr[left];
                left++;
            } else {
                if (arr[right] >= rm) rm = arr[right];
                else res += rm - arr[right];
                right--;
            }
        }
        return res;
    }
}
