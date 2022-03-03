package com.hzf.study.program.nowcoder;

import java.util.*;

public class MathSum {

    public static void main(String[] args) {
        MathSum mathSum = new MathSum();
        System.out.println(mathSum.solve("(2*(3-4))*5"));
        System.out.println(multiply("733064366", "459309139"));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     * 返回表达式的值
     * @param s string字符串 待计算的表达式
     * @return int整型
     */
    public int solve (String s) {
        // write code here
        // 将所有的空格去掉
        s = s.replaceAll(" ", "");

        char[] cs = s.toCharArray();
        int n = s.length();

        // 存放所有的数字
        Deque<Integer> nums = new ArrayDeque<>();
        // 为了防止第一个数为负数，先往 nums 加个 0
        nums.addLast(0);
        // 存放所有「非数字以外」的操作
        Deque<Character> ops = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            char c = cs[i];
            if (c == '(') {
                ops.addLast(c);
            } else if (c == ')') {
                // 计算到最近一个左括号为止
                while (!ops.isEmpty()) {
                    if (ops.peekLast() != '(') {
                        calc(nums, ops);
                    } else {
                        ops.pollLast();
                        break;
                    }
                }
            } else {
                if (isNumber(c)) {
                    int u = 0;
                    int j = i;
                    // 将从 i 位置开始后面的连续数字整体取出，加入 nums
                    while (j < n && isNumber(cs[j])) {
                        u = u * 10 + (cs[j++] - '0');
                    }
                    nums.addLast(u);
                    i = j - 1;
                } else {
                    if (i > 0 && (cs[i - 1] == '(' || cs[i - 1] == '+' || cs[i - 1] == '-')) {
                        nums.addLast(0);
                    }
                    // 有一个新操作要入栈时，先把栈内可以算的都算了
                    // 只有满足「栈内运算符」比「当前运算符」优先级高/同等，才进行运算
                    while (!ops.isEmpty() && ops.peekLast() != '(') {
                        char prev = ops.peekLast();
                        if (map.get(prev) >= map.get(c)) {
                            calc(nums, ops);
                        } else {
                            break;
                        }
                    }
                    ops.addLast(c);
                }
            }
        }
        // 将剩余的计算完
        while (!ops.isEmpty() && ops.peekLast() != '(') {
            calc(nums, ops);
        }
        return nums.peekLast();
    }

    // 使用 map 维护一个运算符优先级
    // 这里的优先级划分按照「数学」进行划分即可
    Map<Character, Integer> map = new HashMap<Character, Integer>() {{
        put('-', 1);
        put('+', 1);
        put('*', 2);
        put('/', 2);
        put('%', 2);
        put('^', 3);
    }};

    // 计算逻辑：从 nums 中取出两个操作数，从 ops 中取出运算符，然后根据运算符进行计算即可
    void calc(Deque<Integer> nums, Deque<Character> ops) {
        if (nums.isEmpty() || nums.size() < 2)
            return;
        if (ops.isEmpty())
            return;
        int b = nums.pollLast(), a = nums.pollLast();
        char op = ops.pollLast();
        int ans = 0;
        if (op == '+')
            ans = a + b;
        else if (op == '-')
            ans = a - b;
        else if (op == '*')
            ans = a * b;
        else if (op == '/')
            ans = a / b;
        else if (op == '^')
            ans = (int)Math.pow(a, b);
        else if (op == '%')
            ans = a % b;
        nums.addLast(ans);
    }

    boolean isNumber(char c) {
        return Character.isDigit(c);
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param s string字符串
     * @return int整型
     */
    public int calculate (String s) {
        // write code here
        s = s.replaceAll(" ", "");

        ArrayDeque<Integer> nums = new ArrayDeque<>();
        ArrayDeque<Character> opts = new ArrayDeque<>();
        nums.addLast(0);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                opts.addLast('(');
            } else if (s.charAt(i) == ')') {
                while (!opts.isEmpty() && opts.peekLast() != '(') {
                    calcs(nums, opts);
                }
                opts.pollLast();
            } else if (Character.isDigit(s.charAt(i))) {
                int num = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i++) - '0');
                }
                nums.addLast(num);
                i--;
            } else {
                if (i > 0 && (s.charAt(i - 1) == '(' || s.charAt(i - 1) == '+' || s.charAt(i - 1) == '-')) {
                    nums.addLast(0);
                }
                while (!opts.isEmpty() && opts.peekLast() != '(') {
                    calcs(nums, opts);
                }
                opts.addLast(s.charAt(i));
            }
        }
        // 将剩余的计算完
        while (!opts.isEmpty() && opts.peekLast() != '(') {
            calcs(nums, opts);
        }
        return nums.peekLast();
    }


    void calcs(Deque<Integer> nums, Deque<Character> ops) {
        if (nums.isEmpty() || nums.size() < 2) {
            return;
        }
        if (ops.isEmpty()) {
            return;
        }
        int b = nums.pollLast(), a = nums.pollLast();
        char op = ops.pollLast();
        int ans = 0;
        if (op == '+') {
            ans = a + b;
        } else if (op == '-') {
            ans = a - b;
        }
        nums.addLast(ans);
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param s string字符串 第一个整数
     * @param t string字符串 第二个整数
     * @return string字符串
     */
    public static String multiply (String s, String t) {
        // write code here
        if ("0".equals(s) || "0".equals(t)) return "0";
        int[] dp = new int[s.length() + t.length()];
        for (int i = s.length() - 1; i >= 0; i--) {
            for (int j = t.length() - 1; j >= 0; j--) {
                dp[i + j + 1] += (s.charAt(i) - '0') * (t.charAt(j) - '0');
                dp[i + j] += dp[i + j + 1] / 10;
                dp[i + j + 1] = dp[i + j + 1] % 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dp.length; i++) {
            if (i == 0 && dp[i] == 0) {
                continue;
            }
            sb.append(dp[i]);
        }
        return sb.toString();
    }
}
