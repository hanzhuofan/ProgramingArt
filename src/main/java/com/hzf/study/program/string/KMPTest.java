package com.hzf.study.program.string;

/**
 * @author zhuofan.han
 * @date 2022/1/23
 */
public class KMPTest {
    public static void main(String[] args) {
        KMPTest test = new KMPTest();
        System.out.println(test.kmp("abab", "abacabab"));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 计算模板串S在文本串T中出现了多少次
     *
     * @param S
     *            string字符串 模板串
     * @param T
     *            string字符串 文本串
     * @return int整型
     */
    public int kmp(String S, String T) {
        // write code here
        int[] next = getNext(S);
        char[] str = T.toCharArray();
        char[] t = S.toCharArray();
        int si = 0;
        int ti = 0;

        int ans = 0;
        while (si < str.length) {
            if (ti == -1 || str[si] == t[ti]) {
                si++;
                ti++;
            } else {
                ti = next[ti];
            }
            if (ti == t.length) {
                ans++;
                ti = next[ti];
            }
        }
        return ans;
    }

    public int[] getNext(String S) {
        char[] str = S.toCharArray();
        int[] next = new int[str.length + 1];
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
