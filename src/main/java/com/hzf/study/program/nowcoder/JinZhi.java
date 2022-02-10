package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/10
 */
public class JinZhi {
    public static void main(String[] args) {
        JinZhi jinZhi = new JinZhi();
        System.out.println(jinZhi.solve(10, 2));
        System.out.println(jinZhi.solve(10,16));
        System.out.println(jinZhi.solve(-10,16));
    }

    /**
     * 进制转换
     * @param M int整型 给定整数
     * @param N int整型 转换到的进制
     * @return string字符串
     */
    public String solve (int M, int N) {
        // write code here
        if(M == 0) {
            return "0";
        }
        String val = "0123456789ABCDEF";
        StringBuilder sb = new StringBuilder();
        boolean fuShu = false;
        if (M < 0) {
            M = -M;
            fuShu = true;
        }
        while (M != 0) {
            sb.append(val.charAt(M % N));
            M /= N;
        }
        if (fuShu) {
            sb.append("-");
        }
        return sb.reverse().toString();
    }
}
