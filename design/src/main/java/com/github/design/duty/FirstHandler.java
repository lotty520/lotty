package com.github.design.duty;

/**
 * 具体处理者1
 */
public class FirstHandler extends IHandler {

  private String deal = "HD";

  /**
   * 实现处理的方法
   * @param duty 请求
   * @return 是否处理成功
   */
  @Override public boolean handle(IDuty duty) {
    boolean done = this.deal.equals(duty.deal());
    // 如果当前处理者没有处理掉请求，当还有处理者时，将请求传递给下一个处理者
    if (!done && next != null) {
      return next.handle(duty);
    }
    return done;
  }
}
