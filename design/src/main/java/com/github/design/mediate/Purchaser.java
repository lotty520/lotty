package com.github.design.mediate;

/**
 * 买家
 */
public class Purchaser extends ICustomer {

  private double offeredPrice = 190D;

  public Purchaser(AbstractMediator mediator) {
    super(mediator);
  }

  @Override public String getName() {
    return "买方";
  }

  public double getPrice() {
    return offeredPrice;
  }

  /**
   * 买家决定调价买入，让中介者告知卖方
   */
  public void buy() {
    offeredPrice = 195D;
    System.out.println(getName() + "调价到195W资金购买房屋");
    mediator.coordinate(this);
  }

  /**
   * 买家寻找房源的行为
   */
  public void search() {
    System.out.println(getName() + "准备190W资金购买房屋");
  }
}
