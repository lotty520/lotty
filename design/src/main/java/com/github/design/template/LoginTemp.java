package com.github.design.template;

/**
 * 抽象模板类
 */
public abstract class LoginTemp {

  /**
   * 登录方法
   */
  public void login() {
    System.out.println("用户登录");
    // 登录流程 一共四个步骤
    inputName();
    inputPassword();
    inputCaptcha();
    clickToLogin();
  }

  /**
   * 输入用户名
   */
  protected abstract void inputName();

  /**
   * 输入密码
   */
  protected abstract void inputPassword();

  /**
   * 输入验证码
   */
  protected abstract void inputCaptcha();

  /**
   * 点击登录按钮
   */
  protected abstract void clickToLogin();
}
