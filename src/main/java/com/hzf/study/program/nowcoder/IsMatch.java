package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/11
 */
public class IsMatch {
    public static void main(String[] args) {
        System.out.println(isMatch("adcbee", "***b?b"));
    }

    /**
     * 请实现支持'?'and'*'.的通配符模式匹配
     * '?' 可以匹配任何单个字符。
     * '*' 可以匹配任何字符序列（包括空序列）。
     * isMatch("aa","a") → false
     * isMatch("aa","aa") → true
     * isMatch("aaa","aa") → false
     * isMatch("aa", "*") → true
     * isMatch("aa", "a*") → true
     * isMatch("ab", "?*") → true
     * isMatch("aab", "d*a*b") → false
     *
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i <= p.length(); i++) {
            if (p.charAt(i - 1) == '*') {
                dp[0][i] = true;
            } else {
                break;
            }
        }
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (p.charAt(j - 1) == '*') {
                    //p[j]是*的状态转移
                    //理解：两个状态只要有一个是true就可以实现完全匹配
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
                    //p[j]是问号或者p[j]和s[i]相等的时候
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    public static boolean isMatch2(String s, String p) {
        int i = 0, j = 0;
        int is = -1, ip = -1;
        int m = s.length(), n = p.length();
        while (i < m) {
            if (j < n && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '?')) {
                //指针后移
                i++;
                j++;
            } else if (j < n && p.charAt(j) == '*') {
                is = i;
                ip = j++;//指针标记
            } else if (is >= 0) {
                i = ++is;
                j = ip + 1;
            } else {
                return false;
            }
        }
        //把多余的星号去掉
        while (j < n && p.charAt(j) == '*') {
            j++;
        }
        return j == n;
    }
}
