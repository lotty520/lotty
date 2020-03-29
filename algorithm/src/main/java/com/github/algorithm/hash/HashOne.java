package com.github.algorithm.hash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class HashOne {

  public boolean containsDuplicate(int[] nums) {

    Set<Integer> set = new HashSet();
    for (int i = 0; i < nums.length; i++) {
      if (set.contains(nums[i])) {
        return true;
      } else {
        set.add(nums[i]);
      }
    }
    return false;
  }

  public int[] intersection(int[] nums1, int[] nums2) {
    Set<Integer> set = new HashSet();
    for (int i = 0; i < nums1.length; i++) {
      set.add(nums1[i]);
    }
    Set<Integer> list = new HashSet<>();
    for (int i = 0; i < nums2.length; i++) {
      if (set.contains(nums2[i])){
        list.add(nums2[i]);
      }
    }
    int[] ans = new int[list.size()];
    Iterator<Integer> iterator = list.iterator();
    int i= 0;
    while (iterator.hasNext()){
      ans[i] = iterator.next();
      i++;
    }
    return ans;
  }
}
