package com.hzf.study.program.nowcoder;

import java.util.HashMap;

/**
 * @author zhuofan.han
 * @date 2022/3/3
 */
public class DeleteDuplicates {

    /**
     * 给出一个升序排序的链表，删除链表中的所有重复出现的元素，只保留原链表中只出现一次的元素。
     * 例如：
     * 给出的链表为1→2→3→3→4→4→5, 返回1→2→5.
     * 给出的链表为1→1→1→2→3, 返回2→3.
     *
     * @param head ListNode类
     * @return ListNode类
     */
    public static ListNode deleteDuplicates (ListNode head) {
        // write code here
        if (head == null || head.next == null) {
            return head;
        }
        ListNode ans = new ListNode(-1);
        ListNode tmp = ans;
        int last = -1;
        while (head != null) {
            if (last != head.val && (head.next == null || head.val != head.next.val)) {
                tmp.next = head;
                tmp = tmp.next;
            }
            last = head.val;
            head = head.next;
            tmp.next = null;
        }
        return ans.next;
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(5);
        node.next = node1;
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = node6;
        System.out.println(deleteDuplicates(node));
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
