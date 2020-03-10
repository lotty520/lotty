package com.github.design.state;

public class Car {
  private ICarState mState;


  public void setState(ICarState state) {
    this.mState = state;
  }

  public void sppedUp(){
    // TODO: 2020-02-25 添加默认状态
    mState.speedUp();
  }
}
