package com.github.frameworkaly.service;

import android.util.ArrayMap;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

public class ArrayImpl {

  Pair<String, String> pair = Pair.create("A", "b");
  ArrayMap<String, String> map = new ArrayMap<>();
  /**
   * 两个数组实现的。
   * 利用二分查找来实现数据的查找
   * 利用数组拷贝来实现增删
   */
  private SparseArray sia = new SparseArray();
  private SparseBooleanArray sba = new SparseBooleanArray();

  public void T() {
  }
}
