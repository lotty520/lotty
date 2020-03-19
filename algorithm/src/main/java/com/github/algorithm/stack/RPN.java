package com.github.algorithm.stack;

import java.util.Stack;

/**
 * 根据逆波兰表示法，求表达式的值。
 *
 * 有效的运算符包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
 *
 * 说明：
 *
 * 整数除法只保留整数部分。
 * 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
 * 示例 1：
 *
 * 输入: ["2", "1", "+", "3", "*"]
 * 输出: 9
 * 解释: ((2 + 1) * 3) = 9
 *
 * 题解：
 * 利用栈来实现，数字直接入栈，遇到符号时，去除栈顶2个元素进行操作再入栈，最后pop栈内的最后一个元素(栈内只会有一个元素)
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
