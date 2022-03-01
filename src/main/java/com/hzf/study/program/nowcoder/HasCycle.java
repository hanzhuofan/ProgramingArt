package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author zhuofan.han
 * @date 2022/3/1
 */
public class HasCycle {

    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            set.add(head);
            head = head.next;
            if (set.contains(head)) {
                return true;
            }
        }
        return false;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
