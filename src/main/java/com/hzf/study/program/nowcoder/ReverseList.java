package com.hzf.study.program.nowcoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuofan.han
 * @date 2022/1/28
 */
public class ReverseList {
    public static void main(String[] args) {
        ListNode listNode3 = new ListNode(3);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode1 = new ListNode(1);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        ListNode listNode = new ReverseList().ReverseList(listNode1);
        System.out.println(listNode);

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        System.out.println(reverseBetween(node1, 2, 5));
    }

    public ListNode ReverseList(ListNode head) {
        ListNode ans = null;
        ListNode temp = head;
        while (head != null) {
            head = head.next;
            temp.next = ans;
            ans = temp;
            temp = head;
        }
        return ans;
    }

    /**
     * 将一个节点数为 size 链表 m 位置到 n 位置之间的区间反转，要求时间复杂度 O(n)，空间复杂度 O(1)。
     * 例如：
     * 给出的链表为1→2→3→4→5→NULL, m=2,n=4m=2,n=4,
     * 返回1→4→3→2→5→NULL.
     * @param head ListNode类
     * @param m int整型
     * @param n int整型
     * @return ListNode类
     */
    public static ListNode reverseBetween (ListNode head, int m, int n) {
        // write code here
        if (m == n) {
            return head;
        }

        ListNode left = head, right = head,left2 = head, right2 = null;
        for (int i = 1; i < m; i++) {
            left2 = left;
            left = left.next;
        }
        for (int i = 1; i < n; i++) {
            right = right.next;
            right2 = right.next;
        }
        right.next = null;

        ListNode tmp = left, tmp2 = left, cur = null;
        while (tmp != null) {
            tmp = tmp.next;
            tmp2.next = cur;
            cur = tmp2;
            tmp2 = tmp;
        }

        left2.next = right;
        left.next = right2;
        if (m == 1) {
            head = right;
        }
        return head;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
}
