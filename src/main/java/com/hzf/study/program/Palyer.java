package com.hzf.study.program;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author hanzhuofan
 * @date 2020/3/8 20:
 */
public class Palyer {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isValid("()}"));
        Food food = new Bread();
        Bread bread = new Bread();
        System.out.println(food.name);
        System.out.println(bread.name);
        System.out.println(food.make());
    }
}

class Food {

    public String name = "Food";

    private String food_name;

    public Food() {
    }

    public Food(String food_name) {
        this.food_name = food_name;
    }

    public String make() {
        return name;
    }

}

class Bread extends Food {

    public String name = "Bread";

    private Food basic_food;

    public Bread(Food basic_food) {
        this.basic_food = basic_food;
    }

    public Bread() {

    }

    @Override
    public String make() {
        return name + "+面包";
    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

class Solution {
    private final Map<Character, Character> map;

    public Solution() {
        this.map = new HashMap<>();
        this.map.put('(', ')');
        this.map.put('[', ']');
        this.map.put('{', '}');
        this.map.put('#', '$');
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!map.containsKey(c)) {
                char tmp = stack.isEmpty() ? '#' : stack.pop();
                if (c != map.get(tmp)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val > l2.val) {
            return mergeTwoLists(l1.next, l2);
        } else {
            return mergeTwoLists(l1, l2.next);
        }
    }
}
