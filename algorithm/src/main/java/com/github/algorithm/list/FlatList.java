package com.github.algorithm.list;

public class FlatList {

  public static void main(String[] args) {

  }

  public Node flatten(Node head) {

    if (head == null) {
      return head;
    }
    Node next = head.next;
    Node child = null;
    while (head.child != null) {
      child = flatten(head.child);
    }

    return head;
  }

  private Node link(Node head, Node node) {
    if (node == null) {
      return head;
    } else if (node.child == null) {
      head.next = node;
      return head;
    } else {
      return link(head, node.child);
    }
  }

  class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
  }
}
