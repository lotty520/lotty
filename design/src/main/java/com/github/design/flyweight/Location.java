package com.github.design.flyweight;

/**
 * 具体享元对象
 */
public class Location extends ILocation {

  @Override public String info() {
    return country + " " + province + " " + city + " ";
  }
}
