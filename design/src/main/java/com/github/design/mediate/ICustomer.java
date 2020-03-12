package com.github.design.mediate;

/**
 * 抽象模块角色，持有一个中介者的引用
 */
public abstract class ICustomer {

  protected AbstractMediator mediator;

  public ICustomer(AbstractMediator mediator) {
    this.mediator = mediator;
  }

  public abstract String getName();
}
