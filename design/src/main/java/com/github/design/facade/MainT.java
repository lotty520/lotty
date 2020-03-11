package com.github.design.facade;

public class MainT {

  public static void main(String[] args) {
    MobilePhone mp = new MobilePhone();
    mp.init();
    mp.playGame();
    mp.playVideo();
    mp.dial();
  }
}
