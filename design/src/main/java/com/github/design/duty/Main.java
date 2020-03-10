package com.github.design.duty;

public class Main {

  public void action(){
    DutyRequest dr = new DutyRequest();

    // 构建处理者链路
    FirstHandler fh = new FirstHandler();
    SecondHandler sh = new SecondHandler();
    fh.next = sh;

    // 从第一个角色开始处理
    fh.handle(dr);
  }
}
