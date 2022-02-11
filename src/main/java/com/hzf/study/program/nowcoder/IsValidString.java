package com.hzf.study.program.nowcoder;

import java.util.Stack;

/**
 * @author zhuofan.han
 * @date 2022/2/11
 */
public class IsValidString {

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param s string字符串
     * @return bool布尔型
     */
    public boolean isValidString (String s) {
        if (s.charAt(0) == ')' || s.charAt(s.length() - 1) == '(') {
            return false;
        }
        Stack<Integer> left = new Stack<>();
        Stack<Integer> star = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left.push(i);
            } else if (s.charAt(i) == '*') {
                star.push(i);
            } else {
                if (!left.isEmpty()) {
                    left.pop();
                } else if (!star.isEmpty()) {
                    star.pop();
                } else {
                    return false;
                }
            }
        }
        while (!left.isEmpty() && !star.isEmpty()) {
            if (left.pop() > star.pop()) {
                return false;
            }
        }
        return left.isEmpty();
    }

    public boolean isValidString2(String s) {
        if (s.charAt(0) == ')' || s.charAt(s.length() - 1) == '(') {
            return false;
        }
        int left = 0, right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '*') left++;
            if (s.charAt(i) == ')') right++;
            if (left < right) return false;
        }
        left = 0; right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ')' || s.charAt(i) == '*') right++;
            if (s.charAt(i) == '(') left++;
            if (left > right) return false;
        }
        return true;
    }
}
