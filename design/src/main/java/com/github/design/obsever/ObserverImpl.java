package com.github.design.obsever;

/**
 * 具体订阅者
 */
public class ObserverImpl implements IObsever {
  @Override public void change(int type) {
    System.out.println("该更新网络显示图标了，类型为；" + type);
  }
}
