package com.github.design.mediate;

public class MainT {

  public static void main(String[] args) {
    // 构建中介者模式星型结构
    Mediator mediator = new Mediator();
    Purchaser purchaser = new Purchaser(mediator);
    Seller seller = new Seller(mediator);
    mediator.setPurchaser(purchaser);
    mediator.setSeller(seller);
    // 准备卖房
    seller.upload();
  }
}
