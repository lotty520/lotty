package com.github.algorithm.stack;

import java.util.Stack;

/**
 * 每日温度问题
 */
public class Temperatures {

  public int[] dailyTemperatures(int[] T) {
    int[] res = new int[T.length];

    Stack<Node> stack = new Stack<>();
    for (int i = 0; i < T.length; i++) {
      if (stack.isEmpty()) {
        stack.push(new Node(i, T[i]));
      } else {
        Node peek = stack.peek();
        if (peek.value >= T[i]) {
          stack.push(new Node(i, T[i]));
        } else {
          checkStack(stack, i, T[i], res);
        }
      }
    }
    while (!stack.empty()) {
      Node pop = stack.pop();
      res[pop.position] = 0;
    }

    return res;
  }

  public void checkStack(Stack<Node> stack, int position, int value, int[] res) {
    if (stack.empty()) {
      stack.push(new Node(position, value));
      return;
    }
    Node peek = stack.peek();
    if (peek.value < value) {
      res[peek.position] = position - peek.position;
      stack.pop();
      checkStack(stack, position, value, res);
    } else {
      stack.push(new Node(position, value));
    }
  }

  static class Node {
    int position;
    int value;

    public Node(int position, int value) {
      this.position = position;
      this.value = value;
    }
  }
}
