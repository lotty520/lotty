package com.github.design.singleton;

public enum SingletonEnum {
  INSTANCE;

  public int sum(int x, int y) {
    return x + y;
  }
}
