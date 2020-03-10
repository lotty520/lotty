package com.github.design.obsever;

import java.util.ArrayList;
import java.util.List;

/**
 * 订阅主题
 */
public class NetManager {

  private int mCurrentType;

  // 订阅者列表
  private List<IObsever> obsevers = new ArrayList<>();

  public void addObsever(IObsever obsever) {
    obsevers.add(obsever);
  }

  public void delObserver(IObsever obsever) {
    obsevers.remove(obsever);
  }

  public void notifyObservers() {
    for (int i = 0; i < obsevers.size(); i++) {
      obsevers.get(i).change(mCurrentType);
    }
  }

  /**
   * 刷新网络状态后，接收到当前真实的网络状态，通知所有订阅者
   */
  public void receive(int type) {
    if (type != mCurrentType) {
      mCurrentType = type;
      notifyObservers();
    }
  }
}
