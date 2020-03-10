package com.github.design.visitor;

/**
 * 抽象访问者
 */
public interface IVisitor {

  /**
   * 访问方法
   *
   * @param staff 抽象元素类型
   */
  public void visit(AbstractStaff staff);
}
