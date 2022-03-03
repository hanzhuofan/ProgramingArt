package com.hzf.study.program.nowcoder;

import java.util.ArrayList;

/**
 * @author zhuofan.han
 * @date 2022/3/3
 */
public class FindPath {

    ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int expectNumber) {
        if (root == null) {
            return ans;
        }
        find(root, expectNumber, new ArrayList<>());
        return ans;
    }

    private void find(TreeNode root, int expectNumber, ArrayList<Integer> integers) {
        integers.add(root.val);
        if (root.left == null && root.right == null) {
            if (expectNumber - root.val == 0) {
                ans.add(integers);
            }
            return;
        }
        if (root.left != null) {
            find(root.left, expectNumber - root.val, new ArrayList<>(integers));
        }
        if (root.right != null) {
            find(root.right, expectNumber - root.val, new ArrayList<>(integers));
        }
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
