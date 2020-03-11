package com.github.design.bridge;

/**
 * 榛果拿铁
 */
public class HazelnutLatte extends Coffee {
  public HazelnutLatte(Sugar sugar) {
    super(sugar);
  }

  @Override public void coffeeType() {
    System.out.println("榛果拿铁 加糖量：" + mSugar.sugarQuantity());
  }
}
