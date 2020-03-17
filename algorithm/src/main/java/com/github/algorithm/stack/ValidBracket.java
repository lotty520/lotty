package com.github.algorithm.stack;

import java.util.Stack;

/**
 * 有效的括号问题
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
