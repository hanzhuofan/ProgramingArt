package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author zhuofan.han
 * @date 2022/2/28
 */
public class LevelOrder {

    /**
     * 给定一个二叉树，返回该二叉树层序遍历的结果，（从左到右，一层一层地遍历）
     *
     * 广度优先搜索(BFS)
     * @param root TreeNode类
     * @return int整型ArrayList<ArrayList<>>
     */
    public ArrayList<ArrayList<Integer>> levelOrder (TreeNode root) {
        // write code here
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        if (root == null) return ans;
        LinkedList<TreeNode> link = new LinkedList<>();
        link.offer(root);
        while (!link.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int size = link.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = link.pop();
                list.add(node.val);
                if (node.left != null) {
                    link.offer(node.left);
                }
                if (node.right != null) {
                    link.offer(node.right);
                }
            }
            ans.add(list);
        }
        return ans;
    }

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
    }
}
