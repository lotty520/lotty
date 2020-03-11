package com.github.design.component;

/**
 * 组合模式抽象根节点
 */
public interface IStaff {

  /**
   * 枝干节点获取子节点的方法
   */
  IStaff getChild(int index);

  /**
   * 枝干节点增加子节点的方法
   */
  void addChild(IStaff child);

  /**
   * 枝干节点移除子节点的方法
   */
  void removeChild(IStaff child);

  /**
   * 组和对象共有的行为方法
   */
  void information();
}
