package com.github.algorithm.list;

/**
 * Your MyLinkedList object will be instantiated and called as such:
 * MyLinkedList obj = new MyLinkedList();
 * int param_1 = obj.get(index);
 * obj.addAtHead(val);
 * obj.addAtTail(val);
 * obj.addAtIndex(index,val);
 * obj.deleteAtIndex(index);
 */
public class MyLinkedList {

  private MyNode head;

  /** Initialize your data structure here. */
  public MyLinkedList() {
  }

  public static void main(String[] args) {
    MyLinkedList mll = new MyLinkedList();
    mll.addAtHead(1);
    mll.print();

    mll.addAtTail(3);
    mll.print();

    mll.addAtIndex(1, 2);
    mll.print();

    mll.get(1);
    System.out.println("第1个: " + mll.get(1));

    mll.deleteAtIndex(0);
    System.out.println("删除第0个");

    mll.print();
    mll.get(0);
    System.out.println("第0个: " + mll.get(0));
  }

  private void print() {
    MyNode cursor = head;
    while (cursor != null) {
      System.out.print(cursor.value + " ");
      cursor = cursor.next;
    }
    System.out.println("");
  }

  /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
  public int get(int index) {
    MyNode cur = head;
    for (int i = 0; i < index; i++) {
      if (cur != null) {
        cur = cur.next;
      } else {
        return -1;
      }
    }
    if (cur != null) {
      return cur.value;
    }
    return -1;
  }

  /**
   * Add a node of value val before the first element of the linked list. After the insertion, the
   * new node will be the first node of the linked list.
   */
  public void addAtHead(int val) {
    MyNode node = new MyNode(val, head);
    head = node;
  }

  /** Append a node of value val to the last element of the linked list. */
  public void addAtTail(int val) {
    MyNode last = new MyNode(val, null);
    if (head == null) {
      head = last;
    }
    MyNode cur = head;
    while (cur.next != null) {
      cur = cur.next;
    }
    cur.next = last;
  }

  /**
   * Add a node of value val before the index-th node in the linked list. If index equals to the
   * length of linked list, the node will be appended to the end of linked list. If index is greater
   * than the length, the node will not be inserted.
   */
  public void addAtIndex(int index, int val) {
    if (index == 0) {
      addAtHead(val);
      return;
    }
    MyNode cur = head;
    for (int i = 1; i < index; i++) {
      if (cur != null) {
        cur = cur.next;
      } else {
        return;
      }
    }
    if (cur != null) {
      MyNode in = new MyNode(val, cur.next);
      cur.next = in;
    }
  }

  /** Delete the index-th node in the linked list, if the index is valid. */
  public void deleteAtIndex(int index) {
    if (index == 0) {
      if (head != null) {
        head = head.next;
      }
    }
    MyNode cur = head;
    for (int i = 1; i < index; i++) {
      if (cur != null) {
        cur = cur.next;
      } else {
        return;
      }
    }
    if (cur != null && cur.next != null) {
      MyNode del = cur.next;
      cur.next = del.next;
    }
  }

  static class MyNode {

    int value;
    MyNode next;

    public MyNode(int value, MyNode next) {
      this.value = value;
      this.next = next;
    }
  }
}
