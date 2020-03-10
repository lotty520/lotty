package com.github.design.adapter;

public class ChargeAdapter extends AppleCharge implements ICharge {

  @Override public double get6Voltage() {
    return 6.0D;
  }

  @Override public double get5Voltage() {
    return 5.0D;
  }
}
