package com.github.design.prototype;

public class PrototypeUser {

  private String name;
  private int age;
  private Address address;

  @Override protected Object clone() throws CloneNotSupportedException {
    PrototypeUser clone = (PrototypeUser) super.clone();
    clone.address = (Address) address.clone();
    return clone;
  }

  public static class Address {

    @Override protected Object clone() throws CloneNotSupportedException {
      return super.clone();
    }
  }
}
