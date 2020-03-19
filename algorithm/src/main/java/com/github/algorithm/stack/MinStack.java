package com.github.algorithm.stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 设计一个支持 push，pop，top 操作，并能在常数时间内检索到最小元素的栈。
 *
 * push(x) -- 将元素 x 推入栈中。
 * pop() -- 删除栈顶的元素。
 * top() -- 获取栈顶元素。
 * getMin() -- 检索栈中的最小元素。
 *
 * 题解：利用动态数组可以实现
 *
 * 方式一：缓存最小值
 *
 * 方式二：最小值永远处于栈顶
 */
public class MinStack {

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