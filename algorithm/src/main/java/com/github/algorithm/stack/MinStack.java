package com.github.algorithm.stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 可以快速检索最小值的栈
 */
class MinStack {

  /** initialize your data structure here. */

  private int min = 0;

  private List<Integer> list = new ArrayList<>();

  public MinStack() {

  }

  public void push(int x) {
    if (list.size() == 0) {
      min = x;
    }
    if (min > x) {
      min = x;
    }
    list.add(x);
  }

  public void pop() {
    if (list.size() > 0) {
      Integer remove = list.remove(list.size() - 1);
      if (list.size() > 0) {
        if (remove <= min) {
          min = Collections.min(list);
        }
      } else {
        min = 0;
      }
    }
  }

  public int top() {
    if (list.size() > 0) {
      return list.get(list.size() - 1);
    }
    return 0;
  }

  public int getMin() {
    return min;
  }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */