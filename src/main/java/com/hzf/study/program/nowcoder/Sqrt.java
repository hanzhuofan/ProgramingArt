package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/25
 */
public class Sqrt {

    /**
     * 实现函数 int sqrt(int x).
     * 计算并返回 x 的平方根（向下取整）
     * @param x int整型
     * @return int整型
     */
    public static int sqrt (int x) {
        // write code here
        return (int)Math.sqrt(x);
    }
    public static int sqrt2(int x) {
        // write code here
        if (x < 1) return 0;
        if (x < 4) return 1;
        int ans = x / 2;
        while (ans > x / ans) {
            ans = (ans + x / ans) / 2;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(sqrt(2));
        System.out.println(sqrt(2143195649));
        System.out.println(sqrt2(2));
        System.out.println(sqrt2(2143195649));
    }
}
