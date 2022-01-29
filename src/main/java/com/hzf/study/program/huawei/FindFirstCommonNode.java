package com.hzf.study.program.huawei;

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

    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
}
