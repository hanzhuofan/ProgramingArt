package com.hzf.study.program.nowcoder;

import java.util.ArrayList;

/**
 * @author zhuofan.han
 * @date 2022/3/4
 */
public class MakeConnected {

    public static void main(String[] args) {
        System.out.println(makeConnected(4, new int[][]{{0,1},{0,2},{1,2}}));
        System.out.println(makeConnected(6, new int[][]{{0,1},{0,2},{0,3},{1,2},{1,3}}));
        System.out.println(makeConnected(6, new int[][]{{0,1},{0,2},{0,3},{1,2}}));
        System.out.println(makeConnected(5, new int[][]{{0,1},{0,2},{3,4},{2,3}}));
        System.out.println(makeConnected(5, new int[][]{{0,1},{0,2},{3,1},{2,3}}));
        System.out.println(makeConnected2(4, new int[][]{{0,1},{0,2},{1,2}}));
        System.out.println(makeConnected2(6, new int[][]{{0,1},{0,2},{0,3},{1,2},{1,3}}));
        System.out.println(makeConnected2(6, new int[][]{{0,1},{0,2},{0,3},{1,2}}));
        System.out.println(makeConnected2(5, new int[][]{{0,1},{0,2},{3,4},{2,3}}));
        System.out.println(makeConnected2(5, new int[][]{{0,1},{0,2},{3,1},{2,3}}));
    }

    /**
     * 连通网络的操作次数
     *
     * @param n
     * @param connections
     * @return
     */
    public static int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) {
            return -1;
        }
        ArrayList<Integer>[] dp = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new ArrayList<>();
        }
        for (int[] i : connections) {
            dp[i[0]].add(i[1]);
            dp[i[1]].add(i[0]);
        }
        int num = 0;
        boolean[] used = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs(i, dp, used);
                num++;
            }
        }
        return num - 1;
    }

    private static void dfs(int i, ArrayList<Integer>[] dp, boolean[] used) {
        used[i] = true;
        for (Integer j : dp[i]) {
            if (!used[j]) {
                dfs(j, dp, used);
            }
        }
    }


    static int[] uset;
    static int[] rank;
    static int ans = 0;
    public static int makeConnected2(int n, int[][] connections) {
        if (connections.length < n - 1) {
            return -1;
        }

        uset = new int[n];
        rank = new int[n];
        ans = n;
        for (int i = 0; i < n; i++) {
            uset[i] = i;
        }

        for (int[] connection : connections) {
            unionSet(connection[0], connection[1]);
        }
        return ans - 1;
    }

    static int find(int x){
        if(x != uset[x]) {
            uset[x] = find(uset[x]);
        }
        return uset[x];
    }

    static void unionSet(int x, int y){
        if((x = find(x)) == (y = find(y))) {
            return;
        }

        if(rank[x] > rank[y]) {
            uset[y] = x;
        } else{
            uset[x] = y;
            if(rank[x] == rank[y]) {
                rank[y]++;
            }
        }
        ans--;
    }
}
