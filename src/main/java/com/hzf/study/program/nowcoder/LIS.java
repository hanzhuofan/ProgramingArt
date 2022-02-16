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
        System.out.println(lis.LIS3(new int[] {2, 1, 5, 3, 6, 4, 8, 9, 7}));
        System.out.println(lis.LIS3(new int[] {1, 2, 8, 6, 4}));
        System.out.println(lis.LIS3(new int[] {1,2,3,4,3,5}));
        System.out.println(lis.LIS4(new int[] {2, 1, 5, 3, 6, 4, 8, 9, 7}));
        System.out.println(lis.LIS4(new int[] {1, 2, 8, 6, 4}));
        System.out.println(lis.LIS4(new int[] {1,2,3,4,3,5}));
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

    /**
     * 给定一个长度为 n 的数组 arr，求它的最长严格上升子序列的长度。
     * 所谓子序列，指一个数组删掉一些数（也可以不删）之后，形成的新数组。例如 [1,5,3,7,3] 数组，其子序列有：[1,3,3]、[7] 等。但 [1,6]、[1,3,5] 则不是它的子序列。
     * 数据范围： 0≤n≤1000
     * 要求：时间复杂度 O(n^2)， 空间复杂度 O(n)
     * 给定数组的最长严格上升子序列的长度。
     *
     * @param arr int整型一维数组 给定的数组
     * @return int整型
     */
    public int LIS3(int[] arr) {
        // write code here
        if (arr.length == 0) {
            return 0;
        }
        int[] dp = new int[arr.length];
        int ans = 1;
        dp[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            dp[i] = 1;
            for (int j = i; j >= 0; j--) {
                if (arr[i] > arr[j]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                }
            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    /**
     * 给定一个长度为 n 的数组 a，求它的最长严格上升子序列的长度。
     * 所谓子序列，指一个数组删掉一些数（也可以不删）之后，形成的新数组。例如 [1,5,3,7,3] 数组，其子序列有：[1,3,3]、[7] 等。但 [1,6]、[1,3,5] 则不是它的子序列。
     * 数据范围： 0≤n≤1000
     * 要求：时间复杂度 O(nlogn)， 空间复杂度 O(n)
     * 给定数组的最长严格上升子序列的长度。
     *
     * @param a int整型一维数组 给定的数组
     * @return int整型
     */
    public int LIS4(int[] a) {
        // write code here
        if (a.length == 0) {
            return 0;
        }
        int[] sdp = new int[a.length + 1];
        int[] dp = new int[a.length];
        int ans = 1;
        dp[0] = 1;
        sdp[1] = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > sdp[ans]) {
                dp[i] = ans + 1;
            } else {
                // 可以改为二分查找法
                dp[i] = 1;
                for (int j = ans; j >= 0; j--) {
                    if (a[i] > sdp[j]) {
                        dp[i] = j + 1;
                        break;
                    }
                }
            }
            sdp[dp[i]] = a[i];
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }
}
