package com.github.algorithm.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数求和
 *
 * 找出指定数组中和为目标值的两个数
 *
 *
 * 题解：
 *
 * 利用HashMap 一次遍历处理
 *
 * 注意值为Key,索引为Value
 */
public class Summation {

  public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>(nums.length);
    int[] result = new int[2];
    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(target - nums[i])) {
        result[0] = map.get(target - nums[i]);
        result[1] = i;
        return result;
      } else {
        map.put(nums[i], i);
      }
    }
    throw new IllegalArgumentException(" not find ...");
  }
}
