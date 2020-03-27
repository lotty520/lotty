package com.github.algorithm.list;

public class SingleListAlgorithm {

  /**
   * 反转一个单链表。
   *
   * 示例:
   *
   * 输入: 1->2->3->4->5->NULL
   * 输出: 5->4->3->2->1->NULL
   * 进阶:
   * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
   */
  public ListNode reverseList(ListNode head) {
    ListNode next = head;
    ListNode temp = head;
    while (head != null && head.next != null) {
      temp = head.next;
      head.next = temp.next;
      temp.next = next;
      next = temp;
    }
    return temp;
  }

  /**
   * 删除链表中等于给定值 val 的所有节点。
   *
   * 示例:
   *
   * 输入: 1->2->6->3->4->5->6, val = 6
   * 输出: 1->2->3->4->5
   */
  public ListNode removeElements(ListNode head, int val) {
    if (head == null) {
      return null;
    } else if (head.next == null) {
      if (head.val == val) {
        return null;
      }
    }
    while (head.val == val){
      head = head.next;
    }
    ListNode ans = head;
    while (head != null && head.next != null) {
      if (head.next.val == val) {
        head.next = head.next.next;
      } else {
        head = head.next;
      }
    }
    return ans;
  }

  class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
      next = null;
    }
  }
}
