package com.github.algorithm.stack;

import java.util.Stack;

/**
 * 每日温度问题
 *
 * 根据每日 气温 列表，请重新生成一个列表，对应位置的输出是需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。
 *
 * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
 *
 * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
 *
 * 题解：
 * 利用栈来实现，需要记录栈内的每个元素的索引
 *
 * 如果栈顶元素比当前元素小，则弹出栈顶，记录索引差，当前元素入栈
 *
 *
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
