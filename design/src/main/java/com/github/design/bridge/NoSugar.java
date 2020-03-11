package com.github.design.bridge;

/**
 * 无糖咖啡
 */
public class NoSugar implements Sugar {


  @Override public float sugarQuantity() {
    return NO_SUGAR;
  }
}
