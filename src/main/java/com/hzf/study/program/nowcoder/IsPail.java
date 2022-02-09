package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/2/7
 */
public class IsPail {
    public static void main(String[] args) {
        IsPail isPail = new IsPail();
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(2);
        ListNode listNode4 = new ListNode(2);
        ListNode listNode5 = new ListNode(1);
        listNode.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        System.out.println(isPail.isPail(listNode));
    }

    /**
     * @param head ListNode类 the head
     * @return bool布尔型
     */
    public boolean isPail(ListNode head) {
        // write code here
        if (head == null || head.next == null) {
            return true;
        }
        // 找中心位置
        ListNode top = head, end = head;
        while (end != null && end.next != null) {
            top = top.next;
            end = end.next.next;
        }

        // 从中心位置反转链表
        ListNode first = null;
        while (top != null) {
            ListNode tmp = top.next;
            top.next = first;
            first = top;
            top = tmp;
        }

        // 比较是否是回文
        while (head != null && first != null) {
            if (head.val != first.val) {
                return false;
            }
            head = head.next;
            first = first.next;
        }
        return true;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
