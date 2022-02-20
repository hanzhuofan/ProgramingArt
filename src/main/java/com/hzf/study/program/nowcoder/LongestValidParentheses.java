package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/20
 */
public class LongestValidParentheses {
    public static void main(String[] args) {
        System.out.println(longestValidParentheses("()(())"));
    }
    /**
     * 最长有效括号
     *
     * @param s string字符串
     * @return int整型
     */
    public static int longestValidParentheses (String s) {
        // write code here
        int[] dp = new int[s.length()];
        int ans = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i - 2 < 0 ? 0 : dp[i - 2]) + 2;
                } else if (i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '('){
                    dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] - 2 >= 0 ? dp[i - dp[i - 1] - 2] : 0);
                }
                ans = Math.max(dp[i], ans);
            }
        }
        return ans;
    }
}
