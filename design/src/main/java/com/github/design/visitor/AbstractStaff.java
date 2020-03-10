package com.github.design.visitor;

/**
 * 稳定的数据结构抽象类
 */
public abstract class AbstractStaff {

  protected String name;

  protected float kpi;

  public AbstractStaff(String name, float kpi) {
    this.name = name;
    this.kpi = kpi;
  }

  /**
   * 接收访问者的方法
   * @param visitor 访问者对象
   */
  public abstract void accept(IVisitor visitor);
}
