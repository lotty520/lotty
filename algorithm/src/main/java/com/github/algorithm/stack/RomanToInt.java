package com.github.algorithm.stack;

import java.util.HashMap;
import java.util.Stack;

/**
 * 罗马数字转阿拉伯整数
 *
 * 分析：对字符的遍历，首先想到的就是栈结构，依次入栈
 * 逻辑：
 * 第一：V、L、D、M 在栈为空时，可以直接累加计算
 * 第二：V、L、D、M 在栈不为空时，判断栈顶的字符是否可以与之组队
 * <li>可以：累加组队值，弹出栈顶</li>
 * <li>不可以：累加当前值，弹出栈顶</li>
 * 第三：I、X、C 需要分情况讨论
 * <li>I:直接入栈</li>
 * <li>X:栈顶为I时，累加组合值，弹出栈顶；否则，入栈</li>
 * <li>C:栈顶为X时，累加组合值，弹出栈顶；否则，入栈</li>
 *
 * <p>特征</p>
 * <li>V、L、D、M不会入栈</li>
 * <li>遍历结束后，栈中只会存在I、X、C这三种</li>
 */
public class RomanToInt {

  private static HashMap<Character, Integer> MAP = new HashMap(4);
  private static HashMap<Character, Integer> B_MAP = new HashMap(3);

  static {
    B_MAP.put('I', 1);
    B_MAP.put('X', 10);
    B_MAP.put('C', 100);

    MAP.put('V', 5);
    MAP.put('L', 50);
    MAP.put('D', 500);
    MAP.put('M', 1000);
  }

  public static void main(String[] args) {

    RomanToInt romanToInt = new RomanToInt();
    int sul = romanToInt.romanToInt("LVIII");
    System.out.println("---> " + sul);
  }

  public int romanToInt(String s) {
    int total = 0;
    Stack<Character> stack = new Stack();
    for (int i = 0; i < s.length(); i++) {
      char at = s.charAt(i);
      if (stack.empty()) {
        if (MAP.containsKey(at)) {
          total += MAP.get(at);
        } else {
          stack.push(at);
        }
      } else {
        if ('I' == at) {
          stack.push(at);
        } else if ('V' == at) {
          if (stack.peek() == 'I') {
            total += 4;
            stack.pop();
          } else {
            total += 5;
          }
        } else if ('X' == at) {
          if (stack.peek() == 'I') {
            total += 9;
            stack.pop();
          } else {
            stack.push(at);
          }
        } else if ('L' == at) {
          if (stack.peek() == 'X') {
            total += 40;
            stack.pop();
          } else {
            total += 50;
          }
        } else if ('C' == at) {
          if (stack.peek() == 'X') {
            total += 90;
            stack.pop();
          } else {
            stack.push(at);
          }
        } else if ('D' == at) {
          if (stack.peek() == 'C') {
            total += 400;
            stack.pop();
          } else {
            total += 500;
          }
        } else if ('M' == at) {
          if (stack.peek() == 'C') {
            total += 900;
            stack.pop();
          } else {
            total += 1000;
          }
        }
      }
    }
    while (!stack.empty()) {
      total += B_MAP.get(stack.pop());
    }
    return total;
  }
}
