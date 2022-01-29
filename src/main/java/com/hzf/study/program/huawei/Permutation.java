package com.hzf.study.program.huawei;

import java.util.ArrayList;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class Permutation {
    public static void main(String[] args) {
        Permutation permutation = new Permutation();
        System.out.println(permutation.Permutation("abc"));
    }

    public ArrayList<String> Permutation(String str) {
        ArrayList<String> ans = new ArrayList<>();
        recur(str, "", ans);
        return ans;
    }

    private void recur(String str, String s, ArrayList<String> ans) {
        if (0 == str.length()) {
            if (!ans.contains(s)) ans.add(s);
            return;
        }
        for (int i = 0; i < str.length(); i++) {
            recur(str.substring(0, i) + str.substring(i + 1), s + str.charAt(i), ans);
        }
    }
}
