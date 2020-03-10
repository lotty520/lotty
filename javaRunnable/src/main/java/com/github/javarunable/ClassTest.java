package com.github.javarunable;

public class ClassTest {

  /**
   * 检测一个类的Class对象数量
   */
  public static void main(String[] args) {

    Class cls = Task.class;
    System.out.println(cls.hashCode());
    try {
      Class task = Class.forName("com.github.javarunable.ClassTest$Task");
      System.out.println(task.hashCode());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    Task object = new Task("");
    System.out.println(object.getClass().hashCode());
  }

  private static class Task {

    private String name;

    public Task(String name) {
      this.name = name;
    }
  }
}
