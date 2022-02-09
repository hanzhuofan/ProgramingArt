package com.hzf.study.program;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author zhuofan.han
 * @date 2022/2/9
 */
public class DFS {

    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        dfs(pRoot, ans, 0);
        for (int i = 0; i < ans.size(); i++) {
            if (i % 2 != 0) {
                Collections.reverse(ans.get(i));
            }
        }
        return ans;
    }

    private void dfs(TreeNode pRoot, ArrayList<ArrayList<Integer>> ans, int i) {
        if (pRoot == null) return;
        if (ans.size() == i) ans.add(new ArrayList<>());
        ans.get(i).add(pRoot.val);
        dfs(pRoot.left, ans, i + 1);
        dfs(pRoot.right, ans, i + 1);
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
