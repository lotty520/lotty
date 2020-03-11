package com.github.design.bridge;

/**
 * 全糖咖啡
 */
public class HoleSugar implements Sugar {

  @Override public float sugarQuantity() {
    return HOLE_SUGAR;
  }
}
