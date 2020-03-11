package com.github.design.flyweight;

import java.util.Vector;

public class LocationFractory {

  private final static int MAX_CAPACITY = 5;

  private static Vector<ILocation> sPool = new Vector<>(MAX_CAPACITY);

  /**
   * 提供从缓存池中获取对象的方法
   */
  public static ILocation obtain() {
    ILocation location;
    if (sPool.size() > 0) {
      // TODO: 2020-03-10 判定缓存中的对象是否在使用中，不再使用中则返回。
      location = sPool.remove(0);
    } else {
      location = new Location();
      // 缓存对象
      if (sPool.size() < MAX_CAPACITY) {
        sPool.add(location);
      }
    }
    return location;
  }
}
