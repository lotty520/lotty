package com.github.design.duty;

/**
 * 具体请求
 */
public class DutyRequest implements IDuty {

  /**
   * 返回DR，在本次测试中，标记只有第二个处理者可以处理
   */
  @Override public String deal() {
    return "DR";
  }
}
