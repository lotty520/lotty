package com.github.design.obsever;

/**
 * 抽象订阅者
 */
public interface IObsever {

  /**
   * 订阅者抽象方法，接收主题状态改变
   */
  void change(int type);
}
