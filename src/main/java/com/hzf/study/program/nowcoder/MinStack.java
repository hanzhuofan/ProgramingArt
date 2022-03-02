package com.hzf.study.program.nowcoder;

import java.util.Stack;

/**
 * @author zhuofan.han
 * @date 2022/3/2
 */
public class MinStack {

    Stack<Integer> normal = new Stack<>();
    Stack<Integer> minval = new Stack<>();
    public void push(int node) {
        normal.push(node);
        if (minval.isEmpty()) {
            minval.push(node);
        } else {
            if (minval.peek() >= node) {
                minval.push(node);
            } else {
                minval.push(minval.peek());
            }
        }
    }

    public void pop() {
        normal.pop();
        minval.pop();
    }

    public int top() {
        return normal.peek();
    }

    public int min() {
        return minval.peek();
    }
}
