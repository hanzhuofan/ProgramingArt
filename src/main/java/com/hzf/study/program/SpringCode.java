package com.hzf.study.program;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author hanzhuofan
 * @date 2020/11/14 18:26
 */
public class SpringCode {
    public static void main(String[] args) {
        SpringCode code = new SpringCode();
        System.out.println(code.GetLeastNumbers_Solution(new int[] {4, 5, 1, 6, 2, 7, 3, 8}, 4));
        System.out.println(code.GetLeastNumbers_Solution(new int[] {1}, 0));
        System.out.println(code.GetLeastNumbers_Solution(new int[] {0, 1, 2, 1, 2}, 3));
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        Arrays.sort(input);
        for (int i = 0; i < k; i++) {
            for (int j = i; j < input.length; j++) {
                if (input[i] > input[j]) {
                    int tmp = input[i];
                    input[i] = input[j];
                    input[j] = tmp;
                }
            }
            list.add(input[i]);
        }
        return list;
    }
}
