package com.github.design.template;

/**
 * 抽象模板具体实现类
 */
public class AppLogin extends LoginTemp {

  /**
   * 输入用户名的实现
   */
  @Override public void inputName() {
    System.out.println("app 输入用户名");
  }

  @Override public void inputPassword() {
    System.out.println("app 输入密码");
  }

  @Override public void inputCaptcha() {
    System.out.println("app 输入验证码");
  }

  @Override public void clickToLogin() {
    System.out.println("app 点击登录");
  }
}
