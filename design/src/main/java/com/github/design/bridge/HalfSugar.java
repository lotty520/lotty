package com.github.design.bridge;

/**
 * 半糖咖啡
 */
public class HalfSugar implements Sugar {

  @Override public float sugarQuantity() {
    return HALF_SUGAR;
  }
}
