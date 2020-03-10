package com.github.design.wrapper;

public class MainT {

  public static void main(String[] args) {
    // 包装前调用
    ComponentImpl component = new ComponentImpl();
    component.jump();

    // 装饰后调用
    ComponentWrapper wrapper = new ComponentWrapper(component);
    wrapper.jump();
  }
}
