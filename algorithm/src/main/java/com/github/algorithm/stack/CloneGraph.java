package com.github.algorithm.stack;

/**
 * 简单无向图的深拷贝
 */
public class CloneGraph {

  public Node cloneGraph(Node node) {
    Node first = new Node(node.val);
    first.neighbors.addAll(node.neighbors);
    for (int i = 0; i < node.neighbors.size(); i++) {
      cloneGraph(node.neighbors.get(i));
    }
    return first;
  }
}


