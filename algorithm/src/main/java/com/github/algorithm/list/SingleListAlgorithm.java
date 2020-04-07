package com.github.algorithm.list;

public class SingleListAlgorithm {

  public static void main(String[] args) {
    SingleListAlgorithm sa = new SingleListAlgorithm();

    ListNode head = new ListNode(1);
    ListNode second = new ListNode(2);
    ListNode third = new ListNode(2);
    ListNode forth = new ListNode(1);
    //ListNode fifth = new ListNode(1);
    head.next = second;
    second.next = third;
    third.next = forth;
    System.out.println("---> " + sa.isPalindrome(head));
  }

  /**
   * 反转一个单链表。
   *
   * 示例:
   *
   * 输入: 1->2->3->4->5->NULL
   * 输出: 5->4->3->2->1->NULL
   * 进阶:
   * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
   *
   * 题解：
   * 将遍历到的节点一道头部
   *
   * 时间复杂度：O(n)
   *
   * 空间复杂度：O(1)
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
   *
   * 题解：
   *
   * 一次遍历
   */
  public ListNode removeElements(ListNode head, int val) {
    if (head == null) {
      return null;
    } else if (head.next == null) {
      if (head.val == val) {
        return null;
      }
    }
    while (head.val == val) {
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

  /**
   * 请判断一个链表是否为回文链表。
   *
   * 示例 1:
   *
   * 输入: 1->2
   * 输出: false
   * 示例 2:
   *
   * 输入: 1->2->2->1
   * 输出: true
   * 进阶：
   * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
   *
   * 题解：
   * 1、利用快慢指针，找到链表中点 时间复杂度O(n/2)
   * 2、翻转后半段 时间复杂度O(n/2)
   * 3、比较这两个
   */
  public boolean isPalindrome(ListNode head) {
    if (head == null) {
      return false;
    } else if (head.next == null) {
      return true;
    }

    ListNode slow = head;
    ListNode quick = head;
    while (quick != null && quick.next != null) {
      quick = quick.next.next;
      slow = slow.next;
    }
    quick = head;
    while (slow != null) {
      if (quick.val != slow.val) {
        return false;
      }
      quick = quick.next;
      slow = slow.next;
    }
    return true;
  }

  public ListNode oddEvenList(ListNode head) {
    return null;
  }

  /**
   * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
   *
   * 示例 1:
   *
   * 输入: 1->2->3->4->5->NULL, k = 2
   * 输出: 4->5->1->2->3->NULL
   * 解释:
   * 向右旋转 1 步: 5->1->2->3->4->NULL
   * 向右旋转 2 步: 4->5->1->2->3->NULL
   * 示例 2:
   *
   * 输入: 0->1->2->NULL, k = 4
   * 输出: 2->0->1->NULL
   * 解释:
   * 向右旋转 1 步: 2->0->1->NULL
   * 向右旋转 2 步: 1->2->0->NULL
   * 向右旋转 3 步: 0->1->2->NULL
   * 向右旋转 4 步: 2->0->1->NULL
   *
   * 题解：
   * 1、将链表连接成环，记录链表的长度
   * 2、在指定长度出解环 < (len - k % len);
   */
  public ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null) {
      return head;
    }
    // 第一步，拼接成环，并且计算出链表的长度
    int len = 1;
    ListNode cursor = head;
    while (cursor.next != null) {
      cursor = cursor.next;
      len++;
    }
    cursor.next = head;

    // 寻找断开的位置

    int del = len - k % len;

    int j = 1;
    ListNode cursor2 = head;
    while (cursor2!=null && j < del){
      cursor2 = cursor2.next;
      j++;
    }
    head = cursor2.next;
    cursor2.next = null;
    return head;
  }

  static class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
      val = x;
      next = null;
    }
  }
}
