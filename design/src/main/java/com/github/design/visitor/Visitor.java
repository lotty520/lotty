package com.github.design.visitor;

/**
 * 具体访问者
 */
public class Visitor implements IVisitor {

  /**
   * 此处只提供了一个访问方法，可以根据不同的对象类型来进行不同的操作
   * 这样做会引入很多分支判断
   * 实际上可以创建多个访问方法或者新建多个访问者类来解决这个问题
   *
   * @param staff 抽象元素类型
   */
  @Override public void visit(AbstractStaff staff) {
    if (staff instanceof RdStaff) {
      System.out.println("研发人员:" + staff.name + "-" + staff.kpi);
    } else if (staff instanceof SailStaff) {
      System.out.println("销售人员:" + staff.name + "-" + staff.kpi);
    } else {
      System.out.println("其他人员:" + staff.name + "-" + staff.kpi);
    }
  }
}
