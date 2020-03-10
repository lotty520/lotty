package com.github.design.duty;

/**
 * 处理者抽象类
 */
public abstract class IHandler {

  public IHandler next;

  /**
   * 处理请求抽象方法
   *
   * @param duty 请求
   * @return 是否处理
   */
  abstract boolean handle(IDuty duty);
}
