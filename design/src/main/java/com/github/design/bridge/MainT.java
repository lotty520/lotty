package com.github.design.bridge;

public class MainT {

  public static void main(String[] args) {
    // 全糖
    HoleSugar holeSugar = new HoleSugar();

    // 半糖
    HalfSugar halfSugar = new HalfSugar();

    // 无糖
    NoSugar noSugar = new NoSugar();

    // 香草拿铁全糖
    VanillaLatte vanillaLatteWithHoleSugar = new VanillaLatte(holeSugar);
    vanillaLatteWithHoleSugar.coffeeType();

    // 香草拿铁半糖
    VanillaLatte vanillaLatteWithHalfSugar = new VanillaLatte(halfSugar);
    vanillaLatteWithHalfSugar.coffeeType();

    // 香草拿铁无糖
    VanillaLatte vanillaLatteWithNoSugar = new VanillaLatte(noSugar);
    vanillaLatteWithNoSugar.coffeeType();

    // 榛果拿铁全糖
    HazelnutLatte hazelnutLatteWithHoleSugar = new HazelnutLatte(holeSugar);
    hazelnutLatteWithHoleSugar.coffeeType();

    // 榛果拿铁半糖
    HazelnutLatte hazelnutLatteWithHalfSugar = new HazelnutLatte(halfSugar);
    hazelnutLatteWithHalfSugar.coffeeType();

    // 榛果拿铁无糖
    HazelnutLatte hazelnutLatteWithNoSugar = new HazelnutLatte(noSugar);
    hazelnutLatteWithNoSugar.coffeeType();
  }
}
