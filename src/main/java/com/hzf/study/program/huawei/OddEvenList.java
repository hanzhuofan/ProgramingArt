package com.hzf.study.program.huawei;

/**
 * @author zhuofan.han
 * @date 2022/2/7
 */
public class OddEvenList {
    public static void main(String[] args) {
        OddEvenList list = new OddEvenList();
        ListNode listNode = new ListNode(1);
        ListNode listNode1 = new ListNode(4);
        ListNode listNode2 = new ListNode(6);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(7);
        listNode.next = listNode1;
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        System.out.println(list.oddEvenList(listNode));
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     *
     * @param head
     *            ListNode类
     * @return ListNode类
     */
    public ListNode oddEvenList(ListNode head) {
        // write code here
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode qs = head;
        ListNode qsw = head;
        ListNode os = head.next;
        ListNode osw = head.next;
        head = head.next.next;
        int i = 1;
        while (head != null) {
            if (i % 2 != 0) {
                qsw.next = head;
                qsw = head;
                head = head.next;
                qsw.next = null;
            } else {
                osw.next = head;
                osw = head;
                head = head.next;
                osw.next = null;
            }
            i++;
        }
        qsw.next = os;
        return qs;
    }

    static class ListNode {
        int val;
        ListNode next = null;

        public ListNode(int val) {
            this.val = val;
        }
    }
}
