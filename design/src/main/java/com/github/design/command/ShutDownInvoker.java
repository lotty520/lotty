package com.github.design.command;

/**
 * 请求者角色；关机请求
 */
public class ShutDownInvoker {

  private ICommand command;

  public ShutDownInvoker(ICommand command) {
    this.command = command;
  }

  public void shutDownComputer() {
    System.out.println("请求关闭计算机...");
    this.command.execute();
  }

}
