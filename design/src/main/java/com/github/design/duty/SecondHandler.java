package com.github.design.duty;

import java.util.Observable;

/**
 * 具体处理者2
 */
public class SecondHandler extends IHandler {

  private String deal = "DR";

  @Override public boolean handle(IDuty duty) {
    boolean done = this.deal.equals(duty.deal());

    // 如果当前处理者没有处理掉请求，当还有处理者时，将请求传递给下一个处理者
    if (!done && next != null) {
      return next.handle(duty);
    }
    return done;
  }
}
