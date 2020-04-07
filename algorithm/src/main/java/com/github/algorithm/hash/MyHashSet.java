package com.github.algorithm.hash;

public class MyHashSet {

  private Node head;

  /** Initialize your data structure here. */
  public MyHashSet() {
    head = new Node(null);
  }

  public void add(int key) {
    Node t = head;
    while (t != null) {
      if (t.value == key) {
        return;
      }
      t = t.next;
    }
    Node node = new Node(key);
    node.next = head;
    head = node;
  }

  public void remove(int key) {

    if (head == null || head.next == null) {
      return;
    }
    if (head.value == key) {
      head = head.next;
      return;
    }

    Node t = head.next;
    Node pre = head;
    while (t != null) {
      if (t.value == key) {
        pre.next = t.next;
        return;
      }
      pre = t;
      t = t.next;
    }
  }

  /** Returns true if this set contains the specified element */
  public boolean contains(int key) {
    Node t = head;
    while (t != null) {
      if (t.value == key) {
        return true;
      }
      t = t.next;
    }
    return false;
  }

  class Node {
    Integer value;
    Node next;

    public Node(Integer value) {
      this.value = value;
      this.next = null;
    }
  }
}
