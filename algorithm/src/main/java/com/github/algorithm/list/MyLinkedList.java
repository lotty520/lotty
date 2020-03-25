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
  private MyNode tail;

  /** Initialize your data structure here. */
  public MyLinkedList() {

    head = null;
    tail = head;
  }

  /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
  public int get(int index) {
    if (index < 0) {
      return 0;
    } else {
      int cur = 0;
      MyNode curNode = head;
      while (cur < index) {
        if (curNode.next == null) {
          return 0;
        }
        curNode = curNode.next;
        cur++;
      }
      return curNode.value;
    }
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
    tail.next = last;
    tail = last;
  }

  /**
   * Add a node of value val before the index-th node in the linked list. If index equals to the
   * length of linked list, the node will be appended to the end of linked list. If index is greater
   * than the length, the node will not be inserted.
   */
  public void addAtIndex(int index, int val) {
    if (index < 0) {
      return;
    }

    int cur = 0;
    MyNode curNode = head;
    MyNode pre = null;
    while (cur < index) {
      if (curNode == null) {
        return;
      } else {
        pre = curNode;
        curNode = curNode.next;
        cur++;
      }
    }
    if (pre == null) {
      addAtHead(val);
    } else if (curNode.next == null) {
      addAtTail(val);
    } else {
      MyNode myNode = new MyNode(val, curNode);
      pre.next = myNode;
    }
  }

  /** Delete the index-th node in the linked list, if the index is valid. */
  public void deleteAtIndex(int index) {
    int cur = 0;
    MyNode curNode = head;
    while (cur < index-1) {
      if (curNode == null) {
        return;
      }
      curNode = curNode.next;
      cur++;
    }
    if (curNode.next != null) {
      MyNode t = curNode.next;
      curNode.next = t.next;
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
