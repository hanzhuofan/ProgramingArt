package com.hzf.study.program;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author zhuofan.han
 * @date 2022/2/9
 */
public class BFS {

    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        if (pRoot == null) return ans;
        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        deque.push(pRoot);
        int num = 1;
        while (!deque.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int tmp = 0;
            for (int i = 0; i < num; i++) {
                TreeNode node = deque.pollLast();
                list.add(node.val);
                if (node.left != null) {
                    deque.push(node.left);
                    tmp++;
                }
                if (node.right != null) {
                    deque.push(node.right);
                    tmp++;
                }
            }
            num = tmp;
            ans.add(list);
        }
        for (int i = 0; i < ans.size(); i++) {
            if (i % 2 != 0) {
                Collections.reverse(ans.get(i));
            }
        }
        return ans;
    }

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }
    }
}
