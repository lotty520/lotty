package com.github.design.command;

/**
 * 命令实现者:休眠命令
 */
public class SleepCommand implements ICommand {

  private MainBoard mReceiver;

  public SleepCommand(MainBoard receiver) {
    this.mReceiver = receiver;
  }

  @Override public void execute() {
    mReceiver.sleep();
  }
}
