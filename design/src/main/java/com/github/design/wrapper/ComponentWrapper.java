package com.github.design.wrapper;

/**
 * 装饰对象类
 */
public class ComponentWrapper implements IComponent {

  private IComponent realComponent;

  public ComponentWrapper(IComponent realComponent) {
    this.realComponent = realComponent;
  }

  /**
   * 需要装饰的方法
   */
  @Override public void jump() {
    step1();
    realComponent.jump();
    step2();
  }

  /**
   * 装饰1
   */
  protected void step1() {
    System.out.println("方法前面调用包装类的包装方法1");
  }

  /**
   * 装饰2
   */
  protected void step2() {
    System.out.println("方法后面调用包装类的包装方法2");

  }


}
