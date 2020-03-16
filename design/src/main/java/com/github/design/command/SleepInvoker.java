package com.github.design.command;

/**
 * 请求者角色:休眠请求
 */
public class SleepInvoker {

  private ICommand command;

  public SleepInvoker(ICommand command) {
    this.command = command;
  }

  public void sleepComputer() {
    System.out.println("请求休眠计算机...");
    this.command.execute();
  }
}
