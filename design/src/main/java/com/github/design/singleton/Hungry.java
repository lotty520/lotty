package com.github.design.singleton;

public class Hungry {

  private static Hungry sInstance = new Hungry();

  private Hungry() {
  }

  public static Hungry getInstance() {
    return sInstance;
  }
}
