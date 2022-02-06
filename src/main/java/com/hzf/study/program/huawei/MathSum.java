package com.hzf.study.program.huawei;

public class MathSum {

    public static void main(String[] args) {

    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 返回表达式的值
     * @param s string字符串 待计算的表达式
     * @return int整型
     */
    public int solve (String s) {
        // write code here
        return jisuan(s, 0, s.length() - 1);
    }

    private int jisuan(String s, int i, int j) {
        for (; i <= j; i++) {
            switch (s.charAt(i)) {
                case '(': s.indexOf(')', i);
            }
        }
        return 0;
    }
}
