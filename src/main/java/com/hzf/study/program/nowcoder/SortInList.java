package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author zhuofan.han
 * @date 2022/3/2
 */
public class SortInList {

    /**
     * 给定一个节点数为n的无序单链表，对其按升序排序。
     * @param head ListNode类 the head node
     * @return ListNode类
     */
    public ListNode sortInList (ListNode head) {
        // write code here
        ArrayList<ListNode> listNodes = new ArrayList<>();
        while (head != null) {
            listNodes.add(head);
            head = head.next;
        }
        head = new ListNode(-1);
        final ListNode[] tmp = {head};
        listNodes.stream().sorted(Comparator.comparingInt(a -> a.val)).forEach(a -> {
            tmp[0].next = a;
            tmp[0] = tmp[0].next;
        });
        tmp[0].next = null;
        return head.next;
    }

    public ListNode sortInList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while(slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode left = head;
        ListNode right = slow.next;
        slow.next = null;
        left = sortInList(left);
        right = sortInList(right);
        return merge(left, right);
    }

    public ListNode merge(ListNode left, ListNode right) {
        ListNode p = new ListNode(-1);
        ListNode head = p;
        while(left != null && right != null) {
            if (left.val < right.val) {
                p.next = left;
                left = left.next;
            } else {
                p.next = right;
                right = right.next;
            }
            p = p.next;
        }
        if (left != null) {
            p.next = left;
        }
        if (right != null) {
            p.next = right;
        }
        return head.next;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
