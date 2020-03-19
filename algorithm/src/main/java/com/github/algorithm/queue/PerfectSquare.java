package com.github.algorithm.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 完全平方数问题
 * 解题思路：是一个广度优先搜索的题目
 * 针对一个正整数： N 我们可以获取一个n，其中n*n是最靠近N而且比N小的完全平方数。
 *
 * 那么我们可以得出一个结论： 1、2、3 ... n 可以认为是N的邻居节点，
 * 1、2、3 ... n 是 n 的邻居节点
 * 1、2、3 ... n-1 是 n-1 的邻居节点
 *
 * 以此类推，我们就可以构造一颗树形结构
 *
 * 然后通过广度优先来搜索计算每条链路的和
 * @author lotty
 */
public class PerfectSquare {

  public static void main(String[] args) {
    PerfectSquare perfectSquare = new PerfectSquare();
    int charge = 4;
    System.out.println("---->" + perfectSquare.numSquares(charge));
  }

  public int numSquares(int target) {
    int sqrt = (int) Math.floor(Math.sqrt(target));
    Node root = new Node(0, 0, sqrt);
    Queue<Node> queue = new LinkedList();
    int step = 0;
    System.out.println("添加 ---> [" + root.square + " , " + root.value + " , " + root.total + "]");
    queue.add(root);

    while (!queue.isEmpty()) {
      step = step + 1;
      System.out.println("step +1  ---->" + step);
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Node top = queue.peek();
        if (top.total == target) {
          System.out.println(
              "栈顶命中 ---> [" + top.square + " , " + top.value + " , " + top.total + "]");
          return step;
        }
        for (int j = top.square; j > 0; j--) {
          Node n = new Node(j * j);
          n.square = j;
          n.total = top.total + n.value;
          if (n.total < target) {
            System.out.println("添加 ---> [" + n.square + " , " + n.value + " , " + n.total + "]");
            queue.add(n);
          } else if (n.total == target) {
            System.out.println(
                "命中即将添加的节点 ---> [" + n.square + " , " + n.value + " , " + n.total + "]");
            return step;
          }
        }
        System.out.println("移除 ---> ["
            + queue.peek().square
            + " , "
            + queue.peek().value
            + " , "
            + queue.peek().total
            + "]");
        queue.poll();
      }
    }

    return 0;
  }

  static class Node {

    int value;

    int total;

    int square;

    public Node(int value, int total, int square) {
      this.value = value;
      this.total = total;
      this.square = square;
    }

    public Node(int value, int total) {
      this.value = value;
      this.total = total;
    }

    public Node(int value) {
      this.value = value;
    }
  }
}


































