package com.github.design.command;

public class MainT {

  /**
   * 模拟客户端调用
   */
  public static void main(String[] args) {
    // 首先要创建接收者
    MainBoard mb = new MainBoard();

    // 关机流程
    System.out.println("---关机流程---");
    ShutdownCommand shutdownCommand = new ShutdownCommand(mb);
    ShutDownInvoker shutDownInvoker = new ShutDownInvoker(shutdownCommand);
    shutDownInvoker.shutDownComputer();

    // 休眠流程
    System.out.println("---关机流程---");
    SleepCommand sleepCommand = new SleepCommand(mb);
    SleepInvoker sleepInvoker = new SleepInvoker(sleepCommand);
    sleepInvoker.sleepComputer();
  }
}
