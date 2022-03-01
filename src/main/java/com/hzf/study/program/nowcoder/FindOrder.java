package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhuofan.han
 * @date 2022/3/1
 */
public class FindOrder {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findOrder(new int[][]{{1, 0}, {2, 1}}, 3)));
        System.out.println(Arrays.toString(findOrder(new int[][]{{1, 0}, {2, 1}}, 4)));
    }
    /**
     * 为了毕业你需要选择 n 门课程，这 n 门课程中存在一定的依赖关系，例如想要完成 B 课程，必须先完成 A 课程，请你找出一个可以完成全部课程的顺序，如果无论如何选择都无法完成全部课程则返回空数组。
     *
     *
     * @param prerequisites int整型二维数组
     * @param n int整型
     * @return int整型一维数组
     */
    static boolean invalid = true;
    static int index = 0;
    public static int[] findOrder (int[][] prerequisites, int n) {
        // write code here
        HashMap<Integer, TreeNode> map = new HashMap<>();
        for (int[] prerequisite : prerequisites) {
            int next = prerequisite[0];
            int val = prerequisite[1];
            TreeNode node = map.computeIfAbsent(val, TreeNode::new);
            node.nexts.add(map.computeIfAbsent(next, TreeNode::new));
        }

        int[] ans = new int[n];
        int[] visit = new int[n];
        index = n - 1;
        for (Integer val : map.keySet()) {
            if (!invalid) break;
            if (visit[val] == 0) {
                dfs(val, visit, ans, map);
            }
        }
        return invalid ? ans : new int[0];
    }

    private static void dfs(Integer val, int[] visit, int[] ans, HashMap<Integer, TreeNode> map) {
        visit[val] = 1;
        for (TreeNode next : map.get(val).nexts) {
            if (visit[next.val] == 0) {
                dfs(next.val, visit, ans, map);
            } else if (visit[next.val] == 1) {
                invalid = false;
                return;
            }
        }
        visit[val] = 2;
        ans[index--] = val;
    }

    static class TreeNode {
        int val;
        List<TreeNode> nexts = new ArrayList<>();

        public TreeNode(int val) {
            this.val = val;
        }
    }
}
