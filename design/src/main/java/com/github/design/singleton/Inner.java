package com.github.design.singleton;

public class Inner {

  private Inner() {
  }

  public static Inner getInstance() {
    return SingletonHolder.instance;
  }

  private static class SingletonHolder {
    private static Inner instance = new Inner();
  }
}
