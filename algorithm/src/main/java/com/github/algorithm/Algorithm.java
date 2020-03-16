package com.github.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lotty
 */
public class Algorithm {

  public static void main(String[] args) {

  }

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
