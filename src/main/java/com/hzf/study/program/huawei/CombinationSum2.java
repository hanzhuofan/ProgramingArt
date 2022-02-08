package com.hzf.study.program.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author zhuofan.han
 * @date 2022/2/8
 */
public class CombinationSum2 {
    public static void main(String[] args) {
        CombinationSum2 sum2 = new CombinationSum2();
        System.out.println(sum2.combinationSum2(new int[] {100, 10, 20, 70, 60, 10, 50}, 80));
        System.out.println(sum2.combinationSum2(new int[] {37, 23, 10, 36, 26, 12, 29, 44, 14, 26, 12, 12, 34, 13, 35, 40, 28, 36, 6, 32, 9, 4, 39, 28, 18,36, 13, 32, 43, 22, 11, 12, 35, 42, 22, 2, 1, 46, 17, 27, 28, 2, 36, 39, 42, 1, 42, 42, 41, 6}, 95));
        System.out.println(sum2.combinationSum2(
            new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 20));
    }

    public ArrayList<ArrayList<Integer>> combinationSum2(int[] num, int target) {
        Arrays.sort(num);
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        combinationSum2(num, target, ans, new ArrayList<>(), 0);
        return ans;
    }

    private void combinationSum2(int[] num, int target, ArrayList<ArrayList<Integer>> ans, ArrayList<Integer> vals,
        int start) {
        if (target == 0) {
            ans.add(new ArrayList<>(vals));
            return;
        }
        if (start > num.length - 1) {
            return;
        }
        for (int i = start; i < num.length; i++) {
            if (i > start && num[i] == num[i - 1]) {
                continue;
            }
            if (target - num[i] < 0) {
                return;
            }
            vals.add(num[i]);
            combinationSum2(num, target - num[i], ans, vals, i + 1);
            vals.remove(vals.size() - 1);
        }
    }
}
