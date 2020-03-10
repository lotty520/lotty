package com.github.design.proxy;

import java.lang.reflect.Proxy;

/**
 * 静态代理类角色
 */
public class ProxyClick {

  private RealClick realClick;

  public ProxyClick(RealClick realClick) {
    this.realClick = realClick;
  }

  /**
   * 代理类方法直接调用真实对象的对应方法
   */
  public void onClick() {
    realClick.onClick();
  }

  /**
   * 模拟客户端
   */
  public void main() {
    RealClick rc = new RealClick();
    ProxyClick pc = new ProxyClick(rc);
    pc.onClick();

  }
}
