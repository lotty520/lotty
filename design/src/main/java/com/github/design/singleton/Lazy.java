package com.github.design.singleton;

public class Lazy {

  private static Lazy sInstance;

  private Lazy() {
  }

  public static Lazy getInstance() {
    synchronized (Lazy.class) {
      if (sInstance == null) {
        sInstance = new Lazy();
      }
    }
    return sInstance;
  }
}
