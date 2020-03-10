package com.github.design.visitor;

/**
 * 销售人员具体类,规定具体的kpi需要乘以1.5
 */
public class SailStaff extends AbstractStaff {

  public SailStaff(String name, float kpi) {
    super(name, kpi);
    this.kpi *= 1.5f;
  }

  /**
   * @param visitor 访问者对象
   */
  @Override public void accept(IVisitor visitor) {
    visitor.visit(this);
  }
}
