package com.github.design.factory;

/**
 * 具体的工厂实现类，创建具体的对象
 */
public class Factory implements IFactory {
  @Override public IProduction create() {
    return new Production();
  }
}
