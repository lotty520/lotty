package com.github.algorithm.list;

public class CycleList {

  /**
   * 快慢指针：
   * 如果有环，最后两个指针相交的地方：
   * 1、如果不为null 则有环
   * 2、如果为null 则无环
   */
  public boolean hasCycle(ListNode head) {
    ListNode slow = head;
    ListNode quick = head;
    if (head == null) {
      return false;
    } else if (head.next == null) {
      return false;
    }
    quick = quick.next;

    int i = 0;
    while (quick != slow) {
      System.out.println("循环---> " + i);
      if (slow != null) {
        slow = slow.next;
      }
      if (quick != null) {
        quick = quick.next;
        if (quick != null) {
          quick = quick.next;
        }
      }
    }
    if (quick != null) {
      return true;
    }
    return false;
  }

  public ListNode detectCycle(ListNode head) {
    if (head != null && head.next != null){

    }
    return null;
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
