package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/2/24
 */
public class ThreeSum {

    /**
     * 给出一个有n个元素的数组S，S中是否有元素a,b,c满足a+b+c=0？找出数组S中所有满足条件的三元组。
     *
     * @param num
     * @return
     */
    public static ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        if (num.length < 3) {
            return ans;
        }
        Arrays.sort(num);
        for (int i = 0; i <= num.length - 3; i++) {
            if (i > 0 && num[i] == num[i - 1]) {
                continue;
            }
            for (int j = i + 1; j <= num.length - 2; j++) {
                if (j > i + 1 && num[j] == num[j - 1]) {
                    continue;
                }
                for (int k = j + 1; k <= num.length - 1; k++) {
                    if (k > j + 1 && num[k] == num[k - 1]) {
                        continue;
                    }
                    if (num[i] + num[j] + num[k] == 0) {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(num[i]);
                        list.add(num[j]);
                        list.add(num[k]);
                        ans.add(list);
                    }
                }
            }
        }
        return ans;
    }

    public static ArrayList<ArrayList<Integer>> threeSum2(int[] num) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        if (num.length < 3) {
            return ans;
        }
        Arrays.sort(num);
        for (int i = 0; i < num.length && num[i] <= 0; i++) {
            if (i > 0 && num[i] == num[i - 1]) {
                continue;
            }
            int j = i + 1;
            int k = num.length - 1;
            while (j < k) {
                int sum = num[i] + num[j] + num[k];
                if (sum == 0) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(num[i]);
                    list.add(num[j]);
                    list.add(num[k]);
                    ans.add(list);
                }

                if (sum > 0) {
                    do {
                        k--;
                    } while (k >= 0 && num[k] == num[k + 1]);
                } else {
                    do {
                        j++;
                    } while (j < num.length && num[j] == num[j - 1]);
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(threeSum(new int[] {-2, 0, 1, 1, 2}));
        System.out.println(threeSum(new int[] {-10, 0, 10, 20, -10, -40}));
        System.out.println(threeSum2(new int[] {-2, 0, 1, 1, 2}));
        System.out.println(threeSum2(new int[] {-10, 0, 10, 20, -10, -40}));
        System.out.println(threeSum2(new int[] {0, 0, 0}));
    }
}
