package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        LongestPalindrome longestPalindrome = new LongestPalindrome();
        System.out.println(longestPalindrome.getLongestPalindrome("ababc"));
        System.out.println(longestPalindrome.getLongestPalindrome("acbdcbbbdbdaaccbcacdacdccababcddabddaaaaaaabdbabcdddaacabacbacbbdabdacddbbadaacbbdcbccacacdabcabacacbbbdcccacdcdcdcbcbabdcdacdddbbabcaccddddddabdacaabccdcabcbcbabacaaaccaccaddabbdadcdacdcdbaadbcabdcdcaaacbcadccbbddbaddcaddcaadcbbcbbdcbdadcddabdddcdbddbbdabaaddcaadd"));
        System.out.println(longestPalindrome.longestPalindrome("acbdcbbbdbdaaccbcacdacdccababcddabddaaaaaaabdbabcdddaacabacbacbbdabdacddbbadaacbbdcbccacacdabcabacacbbbdcccacdcdcdcbcbabdcdacdddbbabcaccddddddabdacaabccdcabcbcbabacaaaccaccaddabbdadcdacdcdbaadbcabdcdcaaacbcadccbbddbaddcaddcaadcbbcbbdcbdadcddabdddcdbddbbdabaaddcaadd"));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param A string字符串
     * @return int整型
     */
    public int getLongestPalindrome (String A) {
        // write code here
        if (A == null || "".equals(A)) return 0;
        // 动态规划,时间O(n2)，空间O(n)
        int[][] num = new int[A.length() + 1][A.length() + 1];
        int max = 0;
        for (int i = 0; i < A.length(); i++) {
            for (int j = 0; j < A.length(); j++) {
                if (A.charAt(i) == A.charAt(j)) {
                    num[i + 1][j] = num[i][j + 1] + 1;
                    int n = num[i + 1][j];
                    if (n > max && (i - j ==  n + 1 || i - j == n - 1 || i - j == n)) {
                        max = n;
                    }
                } else {
                    num[i + 1][j] = 0;
                }
            }
        }
        return max;
    }

    public int getLongestPalindrome2(String A) {
        // write code here
        //边界条件判断
        if (A.length() < 2) {
            return A.length();
        }
        //start表示最长回文串开始的位置，
        //maxLen表示最长回文串的长度
        int maxLen = 1;
        boolean[][] dp = new boolean[A.length()][A.length()];
        for (int right = 1; right < A.length(); right++) {
            for (int left = 0; left <= right; left++) {
                //如果两种字符不相同，肯定不能构成回文子串
                if (A.charAt(left) != A.charAt(right)) {
                    continue;
                }

                //下面是s.charAt(left)和s.charAt(right)两个
                //字符相同情况下的判断
                //如果只有一个字符，肯定是回文子串
                if (right == left) {
                    dp[left][right] = true;
                } else if (right - left <= 2) {
                    //类似于"aa"和"aba"，也是回文子串
                    dp[left][right] = true;
                } else {
                    //类似于"a******a"，要判断他是否是回文子串，只需要
                    //判断"******"是否是回文子串即可
                    dp[left][right] = dp[left + 1][right - 1];
                }
                //如果字符串从left到right是回文子串，只需要保存最长的即可
                if (dp[left][right] && right - left + 1 > maxLen) {
                    maxLen = right - left + 1;
                }
            }
        }
        //最长的回文子串
        return maxLen;
    }

    public String longestPalindrome(String s) {
        if (s.length() == 1) {
            return s;
        }

        // 暴力破解,时间O(n2)，空间O(1)
        String huiwen = null;
        int size = s.length();
        int left, right;
        for (int i = 0; i < size; i++) {
            if (huiwen != null && huiwen.length() / 2 > size - i) {
                break;
            }
            int index = i;
            for (int j = 0; j <= index; j++) {
                left = index - j;
                right = index + j;
                if (left < 0 || right >= size || s.charAt(left) != s.charAt(right)) {
                    break;
                }
                if (huiwen == null || huiwen.length() < j * 2 + 1) {
                    huiwen = s.substring(left, right + 1);
                }
            }
            for (int j = 1; j <= index + 1; j++) {
                left = index - j + 1;
                right = index + j;
                if (left < 0 || right >= size || s.charAt(left) != s.charAt(right)) {
                    break;
                }
                if (huiwen == null || huiwen.length() < j * 2) {
                    huiwen = s.substring(left, right + 1);
                }
            }
        }
        return huiwen;
    }
}
