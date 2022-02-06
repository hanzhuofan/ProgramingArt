package com.hzf.study.program.huawei;

import java.util.HashMap;
import java.util.Map;

public class MoreThanHalfNum {

    public int MoreThanHalfNum_Solution(int [] array) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 0;
        int maxVal = 0;
        for (int j : array) {
            Integer num = map.getOrDefault(j, 0) + 1;
            map.put(j, num);
            if (num > max) {
                max = num;
                maxVal = j;
            }
        }
        return maxVal;
    }
}
