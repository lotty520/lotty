package com.github.design.mediate;

import java.util.HashSet;
import java.util.Set;

/**
 * 抽象者具体实现
 */
public class Mediator extends AbstractMediator {

  /**
   * 模块一实现
   */
  private Seller seller;
  /**
   * 模块二实现
   */
  private Purchaser purchaser;

  public void setSeller(Seller seller) {
    this.seller = seller;
  }

  public void setPurchaser(Purchaser purchaser) {
    this.purchaser = purchaser;
  }

  @Override public void coordinate(ICustomer customer) {
    // 如果是买方传递过来的消息，则通知买方
    if (customer == seller) {
      purchaser.search();
      purchaser.buy();

      Set set = new HashSet();
      set.iterator();
    } else if (customer == purchaser) {
      if (purchaser.getPrice() >= seller.getLowestPrice()) {
        seller.sell(purchaser.getPrice());
      } else {
        System.out.println("双方价格没有谈拢，终止交易");
      }
    }
  }
}
