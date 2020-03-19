package com.github.algorithm.queue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 优化的广度优先搜索
 * 最短单源路径算法
 *
 * @author lotty
 */
public class BreadthFirstSearch {

  public BreadthFirstSearch() {
  }

  /**
   * Return the length of the shortest path between root and target node.
   */
  public int search(Node root, Node target) {
    Queue<Node> queue = new LinkedList<>();
    Set<Node> used = new HashSet<>();
    int step = 0;
    queue.add(root);
    used.add(root);
    while (!queue.isEmpty()) {
      step = step + 1;
      int size = queue.size();
      for (int i = 0; i < size; ++i) {
        Node cur = queue.peek();
        if (cur == target) {
          return step;
        }
        for (Node next : cur.neighbors) {
          if (!used.contains(next)) {
            queue.add(next);
            used.add(next);
          }
        }
        queue.poll();
      }
    }
    return -1;
  }

  static class Node {
    int value;
    List<Node> neighbors;

    public Node(int value) {
      this.value = value;
    }

    public List<Node> neighbors() {
      return neighbors;
    }

    public void addNeighbor(Node node) {
      neighbors.add(node);
    }
  }
}
