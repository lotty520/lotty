package com.github.design.bridge;

/**
 * 咖啡抽象类
 * 持有一个加糖量的引用
 */
public abstract class Coffee {

  protected Sugar mSugar;

  public Coffee(Sugar sugar) {
    this.mSugar = sugar;
  }

  /**
   * 业务方法
   */
  public abstract void coffeeType();
}
