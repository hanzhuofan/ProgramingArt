package com.hzf.study.program.huawei;

import java.util.Stack;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class CheckKuoHao {
    public static void main(String[] args) {
        CheckKuoHao check = new CheckKuoHao();
        System.out.println(check.isValid("["));
        System.out.println(check.isValid("[]"));
        System.out.println(check.isValid("([)]"));
        System.out.println(check.isValid("(]"));
    }
    /**
     *
     * @param s string字符串
     * @return bool布尔型
     */
    public boolean isValid (String s) {
        // write code here
        char[] str = s.toCharArray();
        char[] stack = new char[str.length];
        int index = 0, si = 0;
        while (index < str.length) {
            if (si == 0 && (str[index] == '}' || str[index] == ']' || str[index] == ')')) {
                return false;
            }
            if (str[index] == '}' && stack[si - 1] != '{') return false;
            if (str[index] == ']' && stack[si - 1] != '[') return false;
            if (str[index] == ')' && stack[si - 1] != '(') return false;
            if (str[index] == '}' || str[index] == ']' || str[index] == ')') {
                si--;
            } else {
                stack[si++] = str[index];
            }
            index++;
        }
        return si == 0;
    }

    public boolean isValid2(String s) {
        // write code here
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '[')
                stack.push(']');
            else if (c == '{')
                stack.push('}');
            else if (stack.empty() || stack.pop() != c)
                return false;
        }
        return stack.empty();
    }
}
