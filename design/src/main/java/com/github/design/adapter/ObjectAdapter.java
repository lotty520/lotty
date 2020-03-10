package com.github.design.adapter;

public class ObjectAdapter implements ICharge {

  private AppleCharge appleCharge;

  public ObjectAdapter(AppleCharge appleCharge) {
    this.appleCharge = appleCharge;
  }

  public double get5Voltage() {
    return appleCharge.get5Voltage();
  }

  @Override public double get6Voltage() {
    return 6;
  }
}
