package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/9
 */
public class KMP {
    public static void main(String[] args) {
        KMP test = new KMP();
        System.out.println(test.kmp("abab", "abacabab"));
        System.out.println(test.kmp("aba", "abacabab"));
        System.out.println(test.kmp("abab", "abababab"));
    }
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 计算模板串S在文本串T中出现了多少次
     * @param S string字符串 模板串
     * @param T string字符串 文本串
     * @return int整型
     */
    public int kmp (String S, String T) {
        // write code here
        int[] next = getNext(S);
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int si = 0, ti = 0, ans = 0;
        while (ti < t.length) {
            if (si == -1 || s[si] == t[ti]) {
                si++;
                ti++;
            } else {
                si = next[si];
            }
            if (si == s.length) {
                si = next[si];
                ans++;
            }
        }
        return ans;
    }

    private int[] getNext(String s) {
        int[] next = new int[s.length() + 1];
        char[] str = s.toCharArray();
        next[0] = -1;
        next[1] = 0;
        int cur = 2;
        int x = next[cur - 1];
        while (cur <= str.length) {
            if (x == -1 || str[cur - 1] == str[x]) {
                next[cur++] = ++x;
            } else {
                x = next[x];
            }
        }
        return next;
    }
}
