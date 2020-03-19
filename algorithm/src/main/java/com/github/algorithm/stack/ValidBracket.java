package com.github.algorithm.stack;

import java.util.Stack;

/**
 * 有效的括号问题
 *
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 *
 * 题解：
 * 利用栈很好实现，最后判断栈是否为空即可
 */
public class ValidBracket {


  public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    for (char ch : s.toCharArray()) {
      if (stack.empty()) {
        stack.push(ch);
      } else {
        if (matching(stack.peek(), ch)) {
          stack.pop();
        } else {
          stack.push(ch);
        }
      }
    }
    System.out.println(stack.toString());
    return stack.empty();
  }

  private boolean matching(Character ch1, Character ch2) {
    if ('(' == ch1) {
      return ')' == ch2;
    } else if ('[' == ch1) {
      return ']' == ch2;
    } else if ('{' == ch1) {
      return '}' == ch2;
    } else if (')' == ch1) {
      return '(' == ch2;
    } else if (']' == ch1) {
      return '[' == ch2;
    } else if ('}' == ch1) {
      return '{' == ch2;
    }
    return false;
  }
}
