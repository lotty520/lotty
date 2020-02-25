package com.github.design.singleton;

import java.util.HashMap;
import java.util.Map;

public class Container {

  private static Map<String, Object> container = new HashMap();

  private Container() {
  }

  public static Object getInstance(String key) {
    return container.get(key);
  }

  public static void addInstance(String key, Object obj) {
    if (!container.containsKey(key)) {
      container.put(key, obj);
    }
  }
}
