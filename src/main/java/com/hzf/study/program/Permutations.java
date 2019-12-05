package com.hzf.study.program;

import java.util.Scanner;

/**
 * 任意从键盘输入10个随机数字，数字范围为[-512,512]，然后从这10个数中随机抽取三个数，将这三个数x，y，z带入公式：x²+xy-y²+z中，
 * 要求必须保证输出的结果为所有组合结果的最小值。
 * 输入10个数为：1 1 1 1 1 1 1 1 1 3 ，那么将所有排列组合带入公式 x²+xy-y²+z 中，其最小值为 -4
 */
public class Permutations {
    private static int minVar;

    public static void main(String[] args) {
        int[] arr = getArray();
        minVar = arr[0] * arr[0] + arr[0] * arr[1] - arr[1] * arr[1] + arr[2];
        permutations(0, 3, arr);
        System.out.println(minVar);
    }

    private static void permutations(int m, int n, int[] arr) {
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                for (int k = 0; k < arr.length; k++) {
                    if (i == j || i == k || j == k) {
                        continue;
                    }
                    System.out.println(arr[i] + " " + arr[j] + " " + arr[k]);
                    index++;
                    int tmp = arr[i] * arr[i] + arr[i] * arr[j] - arr[j] * arr[j] + arr[k];
                    if (tmp < minVar) {
                        minVar = tmp;
                    }
                }
            }
        }
        System.out.println(index);
    }

    private static int[] getArray() {
        int[] arr = new int[10];
        int i = 0;
        Scanner s = new Scanner(System.in);
        while (i < 10) {
            arr[i] = s.nextInt();
            i++;
        }
        return arr;
    }
}
