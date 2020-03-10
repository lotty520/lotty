package com.github.javarunable;

public class ThreadTest {

  /**
   * 程序入口，主线程
   */
  public static void main(String[] args) {

    Thread thread = new Thread(new PrintTask("Thread"));
    //thread.start();

    //for (int i = 0; i < 1000; i++) {
    //  // 让主线程不要太快结束
    //  Math.random();
    //  if (i == 10) {
    //    System.out.println("主线程内中断自定义线程");
    //    thread.interrupt();
    //    Thread.yield();
    //    try {
    //      thread.join();
    //    } catch (InterruptedException e) {
    //      e.printStackTrace();
    //    }
    //  }
    //}
    ThreadLocal tl = new ThreadLocal();
    tl.set(123);
    Object o = tl.get();

    System.out.println("ThreadLocal --->  " + o.getClass().getName());
  }

  private static class Task implements Runnable {

    private String name;

    public Task(String name) {
      this.name = name;
    }

    @Override public void run() {
      for (int i = 0; i < 10000; i++) {
        //Math.random();
        //System.out.println(name + " ------> " + i);
      }
    }
  }

  /**
   * 子线程任务
   */
  private static class PrintTask implements Runnable {

    private String name;

    public PrintTask(String name) {
      this.name = name;
    }

    @Override public void run() {
      for (int i = 0; i < 1000; i++) {
        System.out.println("------ interrupt state: " + Thread.currentThread().isInterrupted());
      }
      System.out.println("-----thread finished. -----");
    }
  }
}
