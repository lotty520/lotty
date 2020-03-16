package com.github.design.command;

/**
 * 接收者角色，执行具体的逻辑
 */
public class MainBoard {

  /**
   * 接收者行动方法1
   */
  public void shutdown() {
    System.out.println("准备关机...");
  }

  /**
   * 接收者行动方法2
   */
  public void sleep() {
    System.out.println("准备休眠...");
  }
}
