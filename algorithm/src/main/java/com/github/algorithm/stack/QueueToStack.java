package com.github.algorithm.stack;

import java.util.Stack;

public class QueueToStack {

  private Stack<Integer> stack = new Stack<>();

  /** Initialize your data structure here. */
  public QueueToStack() {

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
