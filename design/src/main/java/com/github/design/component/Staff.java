package com.github.design.component;

/**
 * 叶子节点：普通员工
 */
public class Staff implements IStaff {
  private String name;

  private String position;

  public Staff(String name, String position) {
    this.name = name;
    this.position = position;
  }

  /**
   * 叶子节点空实现
   */
  @Override public IStaff getChild(int index) {
    return null;
  }

  /**
   * 叶子节点空实现
   */
  @Override public void addChild(IStaff child) {

  }

  /**
   * 叶子节点空实现
   */
  @Override public void removeChild(IStaff child) {

  }

  @Override public void information() {
    System.out.println("员工：姓名 " + name + ", 职位 " + position);
  }
}
