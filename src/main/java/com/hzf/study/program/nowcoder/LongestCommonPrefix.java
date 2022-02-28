package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/28
 */
public class LongestCommonPrefix {

    /**
     * 给你一个大小为 n 的字符串数组 strs ，其中包含n个字符串 , 编写一个函数来查找字符串数组中的最长公共前缀，返回这个公共前缀。
     *
     * @param strs string字符串一维数组
     * @return string字符串
     */
    public String longestCommonPrefix (String[] strs) {
        // write code here
        if (strs.length == 0) return "";
        String ans = strs[0];
        for (String str : strs) {
            StringBuilder sb = new StringBuilder();
            int len = Math.min(ans.length(), str.length());
            for (int i = 0; i < len; i++) {
                if (ans.charAt(i) == str.charAt(i)) {
                    sb.append(ans.charAt(i));
                } else {
                    break;
                }
            }
            ans = sb.toString();
        }
        return ans;
    }
}
