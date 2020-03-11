package com.github.design.component;

public class MainT {

  public static void main(String[] args) {
    // 构建组合对象树形结构，仅构建了两级
    IStaff dep = new Department("技术产品部");
    dep.addChild(new Staff("robin","算法工程师"));
    dep.addChild(new Staff("martin","产品经理"));
    dep.addChild(new Staff("jackMa","销售顾问"));

    dep.information();
  }
}
