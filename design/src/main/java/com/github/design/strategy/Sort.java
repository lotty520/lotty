package com.github.design.strategy;

public class Sort {

  private ISortStrategy mStrategy;

  public static void action(int[] nums) {
    Sort sort = new Sort();
    sort.setStrategy(new QuickSortStrategy());
    sort.sort(nums);
  }

  public void setStrategy(ISortStrategy strategy) {
    mStrategy = strategy;
  }

  public void sort(int[] toSort) {
    // TODO: 2020-02-25 检测是否有默认策略
    mStrategy.sort(toSort);
  }
}
