package com.github.algorithm.stack;

import java.util.Stack;

/**
 * 使用栈实现队列的下列操作：
 *
 * push(x) -- 将一个元素放入队列的尾部。
 * pop() -- 从队列首部移除元素。
 * peek() -- 返回队列首部的元素。
 * empty() -- 返回队列是否为空。
 *
 * 题解：利用两个栈来实现pop和peek操作
 *
 * @author lotty
 */
public class StackToQueue {

  private Stack<Integer> stack = new Stack<>();

  /** Initialize your data structure here. */
  public StackToQueue() {

  }

  /** Push element x to the back of queue. */
  public void push(int x) {
    stack.push(x);
  }

  /** Removes the element from in front of queue and returns that element. */
  public int pop() {
    Stack<Integer> copy = new Stack();
    while (!stack.empty()) {
      copy.push(stack.pop());
    }
    int top = copy.pop();
    while (!copy.empty()) {
      stack.push(copy.pop());
    }
    return top;
  }

  /** Get the front element. */
  public int peek() {
    Stack<Integer> copy = new Stack();
    while (!stack.empty()) {
      copy.push(stack.pop());
    }
    int top = copy.peek();
    while (!copy.empty()) {
      stack.push(copy.pop());
    }
    return top;
  }

  /** Returns whether the queue is empty. */
  public boolean empty() {
    return stack.empty();
  }
}
