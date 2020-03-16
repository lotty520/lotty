package com.github.algorithm.stack;

import java.util.Arrays;

public class MainT {

  public static void main(String[] args) {
    //MinStack ms = new MinStack();
    //ms.push(2147483646);
    //ms.push(2147483646);
    //ms.push(2147483647);
    //ms.top();
    //ms.pop();
    //ms.getMin();
    //ms.pop();
    //ms.getMin();
    //ms.pop();
    //ms.push(2147483647);
    //ms.getMin();
    //ms.push(-2147483648);
    //ms.top();
    //ms.getMin();
    //ms.pop();
    //ms.getMin();
    //
    //ValidBracket vb = new ValidBracket();
    //boolean valid = vb.isValid("([)");
    //System.out.println(valid);
    Temperatures tem = new Temperatures();
    int[] data = new int[] { 73, 74, 75, 71, 69, 72, 76, 73 };
    int[] ints = tem.dailyTemperatures(data);

    System.out.println(Arrays.toString(ints));
  }
}
