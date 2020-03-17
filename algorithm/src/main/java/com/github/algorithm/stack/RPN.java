package com.github.algorithm.stack;

import java.util.Stack;

/**
 * 逆波兰表达式求值
 */
public class RPN {

  private static boolean isOp(String ch) {
    return "+".equals(ch) || "-".equals(ch) || "*".equals(ch) || "/".equals(ch);
  }

  private int charge(int before, int last, String op) {
    if ("+".equals(op)) {
      return before + last;
    } else if ("-".equals(op)) {
      return last - before;
    } else if ("*".equals(op)) {
      return before * last;
    } else {
      return last / before;
    }
  }

  public int evalRPN(String[] tokens) {
    Stack<Integer> stack = new Stack<>();
    for (String s : tokens) {
      if (!isOp(s)) {
        stack.push(Integer.valueOf(s));
      } else {
        int charge = charge(stack.pop(), stack.pop(), s);
        stack.push(charge);
      }
    }
    return stack.pop();
  }
}
