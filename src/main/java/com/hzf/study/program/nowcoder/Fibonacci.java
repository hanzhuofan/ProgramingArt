package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class Fibonacci {
    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.Fibonacci(4));
        System.out.println(fibonacci.Fibonacci(1));
        System.out.println(fibonacci.Fibonacci(2));
    }

    public int Fibonacci(int n) {
        if (n == 1 || n == 2) return 1;
        return Fibonacci(n - 1) + Fibonacci(n - 2);
    }
}
