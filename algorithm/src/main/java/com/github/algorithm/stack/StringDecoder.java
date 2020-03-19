package com.github.algorithm.stack;

import java.util.Stack;

/**
 * 字符串解码
 *
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 *
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 *
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 *
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 *
 * 示例:
 *
 * s = "3[a]2[bc]", 返回 "aaabcbc".
 * s = "3[a2[c]]", 返回 "accaccacc".
 * s = "2[abc]3[cd]ef", 返回 "abcabccdcdcdef".
 *
 * @author lotty
 */
public class StringDecoder {

  private final static String DT = "3[a]2[bc]";

  public static void main(String[] args) {
    String s = new StringDecoder().decodeString(DT);
    System.out.println(s);
  }

  public String decodeString(String s) {
    Stack<String> stack = new Stack();
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if (ch == ']') {
        String temp = "";
        while (!stack.empty() && !"[".equals(stack.peek())) {
          temp = String.valueOf(stack.pop()).concat(temp);
        }
        // 移除左括号
        stack.pop();
        int p = 1;
        int nu = 0;
        int cur;
        while (!stack.empty() && (cur = toNum(stack.peek())) >= 0) {
          nu += p * cur;
          p *= 10;
          stack.pop();
        }
        for (int j = 0; j < nu; j++) {
          stack.push(temp);
        }
      } else {
        stack.push(String.valueOf(ch));
      }
    }

    String re = "";
    while (!stack.empty()) {
      re = stack.pop() + re;
    }
    return re;
  }

  private int toNum(String character) {
    try {
      return Integer.valueOf(character);
    } catch (Exception e) {
      return -1;
    }
  }
}
