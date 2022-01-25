// 给你一个字符串 s，找到 s 中最长的回文子串。
//
//
//
// 示例 1：
//
//
// 输入：s = "babad"
// 输出："bab"
// 解释："aba" 同样是符合题意的答案。
//
//
// 示例 2：
//
//
// 输入：s = "cbbd"
// 输出："bb"
//
//
// 示例 3：
//
//
// 输入：s = "a"
// 输出："a"
//
//
// 示例 4：
//
//
// 输入：s = "ac"
// 输出："a"
//
//
//
//
// 提示：
//
//
// 1 <= s.length <= 1000
// s 仅由数字和英文字母（大写和/或小写）组成
//
// Related Topics 字符串 动态规划 👍 4612 👎 0

// leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestPalindrome(String s) {
        if (s.length() == 1) {
            return s;
        }

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
// leetcode submit region end(Prohibit modification and deletion)
