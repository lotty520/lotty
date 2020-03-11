package com.github.design.flyweight;

/**
 * 抽象享元表示
 *
 * 在很多情况下，可以直接用具体享元对象即可
 *
 * 省略抽象享元这一层
 */
public abstract class ILocation {

  protected String country;
  protected String province;
  protected String city;
  /**
   * 经度
   */
  protected double longitude;
  /**
   * 维度
   */
  protected double latitude;

  /**
   * 返回该点位的数据信息
   */
  public abstract String info();
}
