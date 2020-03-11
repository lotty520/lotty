package com.github.design.bridge;

/**
 * 添加糖量
 * 实现部分接口
 */
public interface Sugar {

  float NO_SUGAR = 0f;
  float HALF_SUGAR = 0.5f;
  float HOLE_SUGAR = 1.0f;

  /**
   * 返回添加糖的分量
   */
  float sugarQuantity();
}
