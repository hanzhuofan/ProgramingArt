package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class LIS {
    public static void main(String[] args) {
        LIS lis = new LIS();
        System.out.println(Arrays.toString(lis.LIS(new int[] {2, 1, 5, 3, 6, 4, 8, 9, 7})));
        System.out.println(Arrays.toString(lis.LIS(new int[] {1, 2, 8, 6, 4})));
        System.out.println(Arrays.toString(lis.LIS2(new int[] {2, 1, 5, 3, 6, 4, 8, 9, 7})));
        System.out.println(Arrays.toString(lis.LIS2(new int[] {1, 2, 8, 6, 4})));
    }

    /**
     * retrun the longest increasing subsequence
     *
     * @param arr
     *            int整型一维数组 the array
     * @return int整型一维数组
     */
    public int[] LIS(int[] arr) {
        // write code here
        int len = 1;
        // 用来记录长度所对应的最近的值，加速匹配dp
        int[] sdp = new int[arr.length + 1];
        sdp[len] = arr[0];
        // 记录i位置的最大递增长度
        int[] dp = new int[arr.length];
        dp[0] = len;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > sdp[len]) {
                sdp[++len] = arr[i];
                dp[i] = len;
            } else {
                // 如果不是 那就从 1到 之前递增间找一个递增的
                int pos = getFirstMinPos(arr[i], len, sdp);
                sdp[pos + 1] = arr[i];
                dp[i] = pos + 1;
            }
        }
        int[] ans = new int[len];
        for (int i = arr.length - 1; len > 0; i--) {
            if (dp[i] == len) {
                ans[--len] = arr[i];
            }
        }
        return ans;
    }

    private int getFirstMinPos(int target, int len, int[] sdp) {
        int left = 1, right = len, pos = 0;
        while (left <= right) {
            int mid = (left + right) >> 1;
            if (sdp[mid] < target) {
                pos = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return pos;
    }

    public int[] LIS2 (int[] arr) {
        // write code here
        int[] dp = new int[arr.length];
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int i1 = arr[i];
            dp[0] = 1;
            int currVal = 0;
            int max = 0;
            ArrayList<Integer> curr = new ArrayList<>();
            curr.add(i1);
            for (int j = i + 1; j < arr.length; j++) {
                int j1 = arr[j];
                if (i1 < j1) {
                    int m = j;
                    while (arr[--m] > j1) {
                    }
                    dp[j] = dp[m] + 1;
                    if (dp[j] > max) {
                        max = dp[j];
                        curr.add(j1);
                        currVal = j1;
                    } else if (dp[j] == max && j1 < currVal) {
                        curr.remove(curr.size() - 1);
                        curr.add(j1);
                    }
                }
            }
            if (ans.size() < curr.size() || (ans.size() == curr.size() && ans.get(0) > curr.get(0))) {
                ans = curr;
            }
        }
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }
}
