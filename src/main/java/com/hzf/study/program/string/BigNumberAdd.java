package com.hzf.study.program.string;

/**
 * @author zhuofan.han
 * @date 2022/2/9
 */
public class BigNumberAdd {
    public static void main(String[] args) {
        BigNumberAdd add = new BigNumberAdd();
        System.out.println(add.solve("114514","100"));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 计算两个数之和
     * @param s string字符串 表示第一个整数
     * @param t string字符串 表示第二个整数
     * @return string字符串
     */
    public String solve (String s, String t) {
        // write code here
        if (s == null || "".equals(s) || "0".equals(s)) return t;
        if (t == null || "".equals(t) || "0".equals(t)) return s;
        StringBuilder sb = new StringBuilder();
        int i = s.length() - 1, j = t.length() - 1, yu = 0;
        while (i >= 0 || j >= 0) {
            int snum = i >= 0 ? s.charAt(i) - '0' : 0;
            int tnum = j >= 0 ? t.charAt(j) - '0' : 0;
            int sum = snum + tnum + yu;
            sb.append(sum % 10);
            yu = sum / 10;
            i--;
            j--;
        }
        if (yu == 1) sb.append(1);
        return sb.reverse().toString();
    }
}
