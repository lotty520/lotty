package com.github.design.bridge;

/**
 * 香草拿铁
 */
public class VanillaLatte extends Coffee {

  public VanillaLatte(Sugar sugar) {
    super(sugar);
  }

  @Override public void coffeeType() {
    System.out.println("香草拿铁 加糖量：" + mSugar.sugarQuantity());
  }
}
