package com.github.design.visitor;

/**
 * 研发人员具体类,规定具体的kpi需要乘以系数2
 */
public class RdStaff extends AbstractStaff {

  public RdStaff(String name, float kpi) {
    super(name, kpi);
    this.kpi *= 2;
  }

  /**
   * @param visitor 访问者对象
   */
  @Override public void accept(IVisitor visitor) {

    visitor.visit(this);
  }
}
