package com.github.design.facade;

/**
 * 外观类，提供了对外的统一接口
 *
 * 会调用具体的实现类来完成调用流程
 */
public class MobilePhone {

  private Game game;

  private Phone phone;

  private Video video;

  /**
   * 初始化
   */
  public void init() {
    game = new Game();
    phone = new Phone();
    video = new Video();
  }

  /**
   * 对外打游戏
   * 实际上是调用Game的play方法
   */
  public void playGame() {
    this.game.play();
  }

  /**
   * 对外打电话
   * 实际上是调用Phone的dial方法
   */
  public void dial() {
    this.phone.dial();
  }

  /**
   * 对外播放视频
   * 实际上是调用Video的play方法
   */
  public void playVideo() {
    this.video.play();
  }
}
