package com.github.design.mediate;

/**
 * 卖方
 */
public class Seller extends ICustomer {

  public Seller(AbstractMediator mediator) {
    super(mediator);
  }

  @Override public String getName() {
    return "卖方";
  }

  /**
   * 卖方行为
   */
  public void sell(double price) {
    System.out.println("卖家同意以" + price + "W的价格出售房屋");
  }

  public double getLowestPrice() {
    return 195D;
  }

  /**
   * 上架房源，通知中介者
   */
  public void upload() {
    System.out.println("卖家预以200W的价格出售房屋");
    mediator.coordinate(this);
  }
}
