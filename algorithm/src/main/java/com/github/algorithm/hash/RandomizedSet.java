package com.github.algorithm.hash;

import java.util.HashSet;
import java.util.Set;

class RandomizedSet {
  //
  //["RandomizedSet","insert","remove","insert","getRandom","remove","insert","getRandom"]
  //    [[],[1],[2],[2],[],[1],[2],[]]

  public static void main(String[] args) {
    RandomizedSet ss = new RandomizedSet();
    ss.insert(1);
    ss.remove(1);
    ss.insert(2);
    System.out.println(ss.getRandom());
    ss.remove(1);
    ss.insert(2);
    System.out.println(ss.getRandom());

  }

  private Set<Integer> set;
  /** Initialize your data structure here. */
  public RandomizedSet() {
    set = new HashSet();
  }

  /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
  public boolean insert(int val) {
    if(set.contains(val)){
      return false;
    }
    set.add(val);
    return true;
  }

  /** Removes a value from the set. Returns true if the set contained the specified element. */
  public boolean remove(int val) {
    if(set.contains(val)){
      return false;
    }
    set.remove(val);
    return true;
  }

  /** Get a random element from the set. */
  public int getRandom() {
    Object[] objects = set.toArray();
    int po = (int) (Math.random() * objects.length);
    return (int) objects[po];
  }
}

/**
 * Your RandomizedSet object will be instantiated and called as such:
 * RandomizedSet obj = new RandomizedSet();
 * boolean param_1 = obj.insert(val);
 * boolean param_2 = obj.remove(val);
 * int param_3 = obj.getRandom();
 */
