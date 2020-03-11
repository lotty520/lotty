package com.github.design.component;

import java.util.ArrayList;
import java.util.List;

/**
 * 枝干节点：部门
 */
public class Department implements IStaff {

  /**
   * 部门名称
   */
  private String name;

  /**
   * 部门内部员工
   */
  private List<IStaff> mems = new ArrayList<>();

  public Department(String name) {
    this.name = name;
  }

  @Override public IStaff getChild(int index) {
    if (index < mems.size()) {
      return mems.get(index);
    }
    return null;
  }

  @Override public void addChild(IStaff child) {
    mems.add(child);
  }

  @Override public void removeChild(IStaff child) {
    mems.remove(child);
  }

  @Override public void information() {
    System.out.println("***部门 " + name + "***");
    for (IStaff staff : mems
    ) {
      staff.information();
    }
  }
}
