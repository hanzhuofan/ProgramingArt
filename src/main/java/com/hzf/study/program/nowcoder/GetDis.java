package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/13
 */
public class GetDis {

    public static void main(String[] args) {
        System.out.println(getDis(new int[]{5, 1}, 2));
        System.out.println(getDis(new int[]{5, 6}, 2));
    }

    /**
     * 有一个长为 n 的数组 A ，求满足 0 ≤ a ≤ b < n 的 A[b] - A[a] 的最大值。
     *
     * 给定数组 A 及它的大小 n ，请返回最大差值。
     *
     *
     * @param A int整型一维数组
     * @param n int整型
     * @return int整型
     */
    public static int getDis (int[] A, int n) {
        // write code here
        int ans = 0, min = A[0];
        for (int j : A) {
            ans = Math.max(j - min, ans);
            min = Math.min(j, min);
        }
        return ans;
    }
}
