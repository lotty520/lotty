package com.github.design.mediate;

/**
 * 抽象中介者
 */
public abstract class AbstractMediator {

  /**
   * 接收消息的方法
   */
  public abstract void coordinate(ICustomer customer);
}
