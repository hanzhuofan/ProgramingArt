package com.hzf.study.program.huawei;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class Solve {
    public static void main(String[] args) {
        Solve solve = new Solve();
        System.out.println(solve.solve("abcd"));
    }

    /**
     * 反转字符串
     * @param str string字符串
     * @return string字符串
     */
    public String solve (String str) {
        // write code here
        if (str == null || "".equals(str)) return "";
        char[] ans = new char[str.length()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = str.charAt(str.length() - 1 -i);
        }
        return new String(ans);
    }
}
