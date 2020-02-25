package com.github.design.factory;

public class SimpleFactory {

  public static IProduction create(String production) {
    if ("Apple".equals(production)) {
      return new Apple();
    } else if ("Pear".equals(production)) {
      return new Pear();
    }
    throw new IllegalArgumentException("no production found.");
  }

  public static class Apple implements IProduction {

    @Override public double price() {
      return 5.5;
    }
  }

  public static class Pear implements IProduction {
    @Override public double price() {
      return 4.5;
    }
  }
}
