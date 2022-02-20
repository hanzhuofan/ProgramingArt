package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/20
 */
public class EditDistance {
    public static void main(String[] args) {
        System.out.println(editDistance("nowcoder", "new"));
        System.out.println(editDistance("now", "nowcoder"));
        System.out.println(editDistance("intention", "execution"));
        System.out.println(minEditCost("abc","adc",5,3,2));
        System.out.println(minEditCost("abc","adc",5,3,100));
    }

    /**
     * 给定两个字符串 str1 和 str2 ，请你算出将 str1 转为 str2 的最少操作数。
     * 你可以对字符串进行3种操作：
     * 1.插入一个字符
     * 2.删除一个字符
     * 3.修改一个字符。
     *
     * @param str1 string字符串
     * @param str2 string字符串
     * @return int整型
     */
    public static int editDistance (String str1, String str2) {
        // write code here
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 0; i < str1.length(); i++) {
            dp[i + 1][0] = dp[i][0] + 1;
        }
        for (int i = 0; i < str2.length(); i++) {
            dp[0][i + 1] = dp[0][i] + 1;
        }
        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) != str2.charAt(j)) {
                    int leftUp = dp[i][j] + 1;
                    int left = dp[i][j + 1] + 1;
                    int up = dp[i + 1][j] + 1;
                    dp[i + 1][j + 1] = Math.min(Math.min(leftUp, left), up);
                } else {
                    dp[i + 1][j + 1] = dp[i][j];
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }

    /**
     * 给定两个字符串str1和str2，再给定三个整数ic，dc和rc，分别代表插入、删除和替换一个字符的代价，请输出将str1编辑成str2的最小代价。
     *
     * @param str1 string字符串
     * @param str2 string字符串
     * @return int整型
     */
    public static int minEditCost (String str1, String str2, int ic, int dc, int rc) {
        // write code here
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 0; i < str1.length(); i++) {
            dp[i + 1][0] = dp[i][0] + dc;
        }
        for (int i = 0; i < str2.length(); i++) {
            dp[0][i + 1] = dp[0][i] + ic;
        }
        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) != str2.charAt(j)) {
                    int leftUp = dp[i][j] + rc;
                    int left = dp[i][j + 1] + dc;
                    int up = dp[i + 1][j] + ic;
                    dp[i + 1][j + 1] = Math.min(Math.min(leftUp, left), up);
                } else {
                    dp[i + 1][j + 1] = dp[i][j];
                }
            }
        }
        return dp[str1.length()][str2.length()];
    }
}
