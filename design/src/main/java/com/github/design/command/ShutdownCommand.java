package com.github.design.command;

/**
 * 命令实现着:关机命令
 */
public class ShutdownCommand implements ICommand {

  private MainBoard mReceiver;

  public ShutdownCommand(MainBoard receiver) {
    this.mReceiver = receiver;
  }

  @Override public void execute() {
    mReceiver.shutdown();
  }
}
