package com.github.algorithm.binarysearch;

public class Solution {

  /**
   * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
   *
   * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
   *
   * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
   *
   * 你可以假设数组中不存在重复的元素。
   *
   * 你的算法时间复杂度必须是 O(log n) 级别。
   *
   * 示例 1:
   *
   * 输入: nums = [4,5,6,7,0,1,2], target = 0
   * 输出: 4
   * 示例 2:
   *
   * 输入: nums = [4,5,6,7,0,1,2], target = 3
   * 输出: -1
   */
  public int search(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    int mid = left + (right - left) / 2;

    while (left <= right) {
      if (nums[mid] == target) {
        return mid;
      }

      if (nums[left] <= nums[mid]) {  //左边升序
        if (target >= nums[left] && target <= nums[mid]) {//在左边范围内
          right = mid - 1;
        } else {//只能从右边找
          left = mid + 1;
        }
      } else { //右边升序
        if (target >= nums[mid] && target <= nums[right]) {//在右边范围内
          left = mid + 1;
        } else {//只能从左边找
          right = mid - 1;
        }
      }
      mid = left + (right - left) / 2;
    }

    return -1;  //没找到
  }
}
