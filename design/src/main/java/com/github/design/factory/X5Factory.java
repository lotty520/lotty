package com.github.design.factory;

/**
 * X5装配工厂
 */
public class X5Factory implements CarFactory {
  @Override public String create() {
    return "X5汽车";
  }

  @Override public Tire createTire() {
    return new X5Tire();
  }

  @Override public Engine createEngine() {
    return new X5Engine();
  }

  public static class X5Tire implements CarFactory.Tire {
    @Override public String create() {
      return "X5轮胎";
    }
  }

  public static class X5Engine implements CarFactory.Engine {
    @Override public String create() {
      return "X5发动机";
    }
  }
}
