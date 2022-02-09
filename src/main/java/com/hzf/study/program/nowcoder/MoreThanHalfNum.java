package com.hzf.study.program.nowcoder;

import java.util.HashMap;

public class MoreThanHalfNum {
    public static void main(String[] args) {
        MoreThanHalfNum num = new MoreThanHalfNum();
        System.out.println(num.MoreThanHalfNum_Solution(new int[] {1, 2, 3, 2, 2, 2, 5, 4, 2}));
        System.out.println(num.MoreThanHalfNum_Solution2(new int[] {1, 2, 3, 2, 2, 2, 5, 4, 2}));
    }

    public int MoreThanHalfNum_Solution(int[] array) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 0;
        int maxVal = 0;
        for (int j : array) {
            int num = map.getOrDefault(j, 0) + 1;
            map.put(j, num);
            if (num > max) {
                max = num;
                maxVal = j;
            }
        }
        return maxVal;
    }

    public int MoreThanHalfNum_Solution2(int[] array) {
        int val = array[0];
        int num = 1;
        for (int i = 1; i < array.length; i++) {
            if (val == array[i]) {
                num++;
            } else {
                num--;
            }
            if (num == 0) {
                val = array[i + 1];
            }
        }
        return val;
    }
}
