package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author zhuofan.han
 * @date 2021/6/7
 */
public class MinDelay {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String[] s = scanner.nextLine().split(" ");
            int n = Integer.parseInt(s[0]);
            int m = Integer.parseInt(s[1]);
            List<Node> list = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                String[] s1 = scanner.nextLine().split(" ");
                Node node = new Node();
                node.u = Integer.parseInt(s1[0]);
                node.v = Integer.parseInt(s1[1]);
                node.w = Integer.parseInt(s1[2]);
                list.add(node);
            }
            String[] s1 = scanner.nextLine().split(" ");
            int u = Integer.parseInt(s1[0]);
            int v = Integer.parseInt(s1[1]);
            System.out.println(find(u, v, list, 0));
        }
    }

    private static int find(int u, int v, List<Node> list, int w) {
        List<Node> us = list.stream().filter(item -> item.u == u && item.v != v).collect(Collectors.toList());
        List<Node> vs = list.stream().filter(item -> item.v == v).collect(Collectors.toList());
        if (us.isEmpty() && vs.isEmpty()) {
            return -1;
        }

        int min = -1;

        for (Node node : vs) {
            if (node.u == u && node.v == v) {
                if (min == -1 || node.w + w < min) {
                    min = node.w + w;
                }
            }
        }

        for (Node node : us) {
            int des = find(node.v, v, list, node.w + w);
            if (min == -1 || (des > 0 && des < min)) {
                min = des;
            }
        }

        return min;
    }

    static class Node {
        public int u;
        public int v;
        public int w;
    }
}
