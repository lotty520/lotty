package com.github.design.wrapper;

/**
 * 组件具体实现类
 */
public class ComponentImpl implements IComponent {
  /**
   * 需要包装的具体方法
   */
  @Override public void jump() {
    System.out.println("调用具体组件类的方法");
  }
}
