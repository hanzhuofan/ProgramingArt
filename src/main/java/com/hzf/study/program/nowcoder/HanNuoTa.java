package com.hzf.study.program.nowcoder;

import java.util.ArrayList;

/**
 * @author zhuofan.han
 * @date 2022/2/10
 */
public class HanNuoTa {
    public static void main(String[] args) {
        HanNuoTa ta = new HanNuoTa();
        System.out.println(ta.getSolution(1));
        System.out.println(ta.getSolution(2));
        System.out.println(ta.getSolution(3));
    }

    public ArrayList<String> getSolution(int n) {
        // write code here
        ArrayList<String> ans = new ArrayList<>();
        hanoi(n, ans, "left", "mid", "right");
        return ans;
    }

    private void hanoi(int n, ArrayList<String> ans, String left, String mid, String right) {
        if (n == 0) {
            return;
        }
        hanoi(n - 1, ans, left, right, mid);
        ans.add("move from " + left + " to " + right);
        hanoi(n - 1, ans, mid, left, right);
    }
}
