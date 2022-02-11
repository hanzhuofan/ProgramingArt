package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/11
 */
public class MaxValue {
    public static void main(String[] args) {
        MaxValue value = new MaxValue();
        System.out.println(value.maxValue("12345", 3));
        System.out.println(value.maxValue2("12345", 3));
    }

    /**
     * 有一个只由字符'1'到'9'组成的长度为 n 的字符串 s ，现在可以截取其中一段长度为 k 的子串并且将该子串当作十进制的正整数，如对于子串"123"，其对应的十进制数字就是123 。
     * 如果想让这个正整数尽可能的大的话，问该正整数最大能是多少。
     * 函数传入一个长度为 n 的字符串 s 和一个正整数 k ，请你返回答案。
     *
     *
     * @param s string字符串
     * @param k int整型
     * @return int整型
     */
    public int maxValue (String s, int k) {
        // write code here
        if (s.length() == k) return Integer.parseInt(s);
        int max = 0;
        for (int i = 0; i <= s.length() - k; i++) {
            int num = Integer.parseInt(s.substring(i, i + k));
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public int maxValue2(String s, int k) {
        // write code here
        if (s.length() == k) return Integer.parseInt(s);
        String max = s.substring(0, k);
        int n = k;
        for (int i = 1; i <= s.length() - k; i++) {
            if (max.charAt(k - n) < s.charAt(i)) {
                max = s.substring(i - k + n, i + n);
                n = k;
                i = i - k + n;
            } else if (n == 1) {
                n = k;
                i = i - k + 1;
            } else {
                n--;
            }
        }
        return Integer.parseInt(max);
    }
}
