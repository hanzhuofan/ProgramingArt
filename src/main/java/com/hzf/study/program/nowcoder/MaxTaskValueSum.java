package com.hzf.study.program.nowcoder;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author zhuofan.han
 * @date 2022/3/4
 */
public class MaxTaskValueSum {

    public static void main(String[] args) {
        System.out.println(maxTaskValueSum(new int[][]{{1, 6}, {1, 7}, {3, 2}, {3, 1}, {2, 4}, {2, 5}, {6, 1}}));
        System.out.println(maxTaskValueSum(new int[][]{{1, 6}, {1, 7}, {4, 1}, {4, 2}, {4, 1}, {6, 1}}));
    }

    public static int maxTaskValueSum (int[][] arr) {
        Arrays.sort(arr, (a, b) -> {
            int c = a[0] - b[0];
            if (c == 0) {
                return b[1] - a[1];
            }
            return c;
        });
        int ans = 0;
        int t = 0;
        for (int i = 0; i < arr.length; i++) {
            int time = arr[i][0];
            int value = arr[i][1];
            if (time - t >= 1) {
                ans += value;
                t++;
            }
        }
        return ans;
    }
}
