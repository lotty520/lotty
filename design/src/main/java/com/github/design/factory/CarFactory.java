package com.github.design.factory;

/**
 * 汽车装配工厂
 */
public interface CarFactory {

  String create();

  Tire createTire();

  Engine createEngine();

  interface Tire {
    String create();
  }

  interface Engine {
    String create();
  }

}
