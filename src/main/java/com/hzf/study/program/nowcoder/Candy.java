package com.hzf.study.program.nowcoder;

import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/12
 */
public class Candy {
    public static void main(String[] args) {
        System.out.println(candy(new int[]{1,1,3,3,4,5,1,3}));
    }
    /**
     * pick candy
     * 每个孩子不管得分多少，起码分到一个糖果。
     * 任意两个相邻的孩子之间，得分较多的孩子必须拿多一些糖果。(若相同则无此限制)
     * 给定一个数组 arrarr 代表得分数组，请返回最少需要多少糖果。
     * @param arr int整型一维数组 the array
     * @return int整型
     */
    public static int candy (int[] arr) {
        // write code here
        if (arr.length == 1) {
            return 1;
        }
        int[] ans = new int[arr.length];
        Arrays.fill(ans, 1);
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                ans[i + 1] = ans[i] + 1;
            }
        }
        for (int i = arr.length - 1; i > 0; i--) {
            if (arr[i] < arr[i - 1] && ans[i - 1] < ans[i] + 1) {
                ans[i - 1] = ans[i] + 1;
            }
        }
        return Arrays.stream(ans).sum();
    }
}
