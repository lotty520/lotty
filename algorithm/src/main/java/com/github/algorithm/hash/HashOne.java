package com.github.algorithm.hash;

import java.util.HashSet;
import java.util.Iterator;
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
      if (set.contains(nums2[i])) {
        list.add(nums2[i]);
      }
    }
    int[] ans = new int[list.size()];
    Iterator<Integer> iterator = list.iterator();

    int i = 0;
    while (iterator.hasNext()) {
      ans[i] = iterator.next();
      i++;
    }
    return ans;
  }

  public String[] findRestaurant(String[] list1, String[] list2) {
    if (list1.length == 0 || list2.length == 0) {
      return new String[0];
    }
    Set<String> read = new HashSet<>();
    int first = 0;
    int second = 0;
    int min = Math.min(list1.length, list2.length);
    read.add(list1[0]);
    while (first < min) {
      if (first <= second) {
        if (read.contains(list2[second])) {
          String[] ans = new String[1];
          ans[0] = list2[second];
          return ans;
        } else {
          first++;
          read.add(list2[second]);
        }
      } else {
        if (read.contains(list1[first])) {
          String[] ans = new String[1];
          ans[0] = list1[first];
          return ans;
        } else {
          second++;
          read.add(list1[first]);
        }
      }
    }
    String[] ss = list1.length > list2.length ? list1 : list2;
    int max = Math.max(list1.length, list2.length);
    for (int i = min; i < max; i++) {
      if (read.contains(ss[i])) {
        String[] ans = new String[1];
        ans[0] = ss[i];
        return ans;
      } else {
        read.add(ss[i]);
      }
    }
    return new String[0];
  }
}
