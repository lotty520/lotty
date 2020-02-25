package com.github.design.factory;

public class X5 {

  public void main() {
    X5Factory x5Factory = new X5Factory();
    x5Factory.createEngine();
    x5Factory.createTire();
    x5Factory.create();
  }
}
