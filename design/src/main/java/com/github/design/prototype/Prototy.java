package com.github.design.prototype;

import java.util.ArrayList;
import java.util.List;

public class Prototy {

  private String name;

  private List<String> todos = new ArrayList<>();

  public Prototy copy() {
    Prototy p = new Prototy();
    p.name = name;
    p.todos = new ArrayList<>();
    if (todos.size() > 0) {
      p.todos.addAll(todos);
    }
    return p;
  }
}
