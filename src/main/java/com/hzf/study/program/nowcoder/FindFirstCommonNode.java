package com.hzf.study.program.nowcoder;

/**
 * @author zhuofan.han
 * @date 2022/1/29
 */
public class FindFirstCommonNode {

    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode l1 = pHead2, l2;
        while (l1 != null) {
            l2 = pHead1;
            while (l2 != null) {
                if (l1 == l2) return l1;
                l2 = l2.next;
            }
            l1 = l1.next;
        }
        return null;
    }

    public ListNode FindFirstCommonNode2(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while (p1 != p2) {
            p1 = p1 == null ? pHead2 : p1.next;
            p2 = p2 == null ? pHead1 : p2.next;
        }
        return p1;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
}
