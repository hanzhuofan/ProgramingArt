package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/8
 */
public class NumberOf1 {

    public static void main(String[] args) {
        NumberOf1 number = new NumberOf1();
        System.out.println(number.NumberOf1(10));
        System.out.println(number.NumberOf1(-1));
        System.out.println(number.NumberOf1(-2147483648));
        System.out.println(number.NumberOf1(-2147483647));
        System.out.println(number.NumberOf1(2147483647));
    }

    public int NumberOf1(int n) {
        int res = 0;
        while (n != 0) {
            res += n & 1;
            n >>>= 1;
        }
        return res;
    }
}
