package com.github.algorithm.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组相关算法
 *
 * @author lotty
 */
public class ArrayAlgorithm {

  public static void main(String[] args) {
    ArrayAlgorithm algorithm = new ArrayAlgorithm();
    //System.out.println(algorithm.pivotIndex(new int[] { -1, -1, -1, 0, 1, 1 }));
    //System.out.println(algorithm.dominantIndex(new int[] { 1, 0 }));
    //System.out.println(Arrays.toString(algorithm.plusOne(new int[] { 9 })));
    //int[] ss = new int[100000];
    //for (int i = 0; i < ss.length; i++) {
    //  ss[i] = i;
    //}
    //long l = System.currentTimeMillis();
    //algorithm.rotate(ss, 19987);
    //System.out.println("耗时：" + (System.currentTimeMillis() - l));

    int[] ss = new int[] {
        1, 2, 3, 4, 4, 5, 5, 5
    };
    algorithm.removeDuplicates(ss);
    System.out.println(Arrays.toString(ss));
  }

  /**
   * 加一
   *
   * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
   *
   * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
   *
   * 你可以假设除了整数 0 之外，这个整数不会以零开头。
   *
   * 示例
   *
   * 输入: [1,2,3]
   * 输出: [1,2,4]
   * 解释: 输入数组表示数字 123。
   *
   * 题解：
   *
   * 从尾部向前遍历，遇到9则置为0，否则+1返回
   *
   * 特殊情况：
   * 1、数组长度为0
   * 2、如果一直到0号索引还是为9，则返回新数组：
   * <li>长度为 len + 1 第一位为1，其余为0</li>
   * <li>可以通过Arrays.copyOf(new int[] { 1 }, len + 1)完成</li>
   */
  public int[] plusOne(int[] digits) {
    int len = digits.length;
    if (len == 0) {
      return digits;
    }
    for (int i = len - 1; i >= 0; i--) {
      if (digits[i] != 9) {
        digits[i] = digits[i] + 1;
        return digits;
      } else {
        digits[i] = 0;
        if (i == 0) {
          return Arrays.copyOf(new int[] { 1 }, len + 1);
        }
      }
    }
    return new int[0];
  }

  /**
   * 中心索引问题
   *
   * 给定一个整数类型的数组 nums，请编写一个能够返回数组“中心索引”的方法。
   *
   * 我们是这样定义数组中心索引的：数组中心索引的左侧所有元素相加的和等于右侧所有元素相加的和。
   *
   * 如果数组不存在中心索引，那么我们应该返回 -1。如果数组有多个中心索引，那么我们应该返回最靠近左边的那一个。
   *
   * 示例
   *
   * 输入:
   * nums = [1, 7, 3, 6, 5, 6]
   * 输出: 3
   * 解释:
   * 索引3 (nums[3] = 6) 的左侧数之和(1 + 7 + 3 = 11)，与右侧数之和(5 + 6 = 11)相等。
   * 同时, 3 也是第一个符合要求的中心索引。
   *
   * 题解：
   * 1、第一次遍历计算所有数字之和total
   * 2、第二次从头开始遍历，并记录从头到当前位置i之前数字之和left，然后对比 total - i 是否等于 left * 2
   *
   * 时间复杂度：2*O(n)
   */
  public int pivotIndex(int[] nums) {
    int length = nums.length;
    int total = 0;
    for (int i = 0; i < length; i++) {
      total += nums[i];
    }

    int left = 0;
    for (int i = 0; i < length; i++) {
      if (left == total - nums[i] - left) {
        return i;
      }
      left += nums[i];
    }
    return -1;
  }

  /**
   * 至少是其他数字两倍的最大数的索引
   *
   * 在一个给定的数组nums中，总是存在一个最大元素 。
   *
   * 查找数组中的最大元素是否至少是数组中每个其他数字的两倍。
   *
   * 如果是，则返回最大元素的索引，否则返回-1。
   *
   * 示例
   *
   * 输入: nums = [3, 6, 1, 0]
   * 输出: 1
   * 解释: 6是最大的整数, 对于数组中的其他整数,
   * 6大于数组中其他元素的两倍。6的索引是1, 所以我们返回1.
   *
   * 题解：
   * 1、遍历一次，找出最大和第二大的数字并时刻记录最大数的索引
   * 2、遇到最大值相同时，不更新索引
   * 3、最后比较 max 和 lessMax * 2 的大小即可
   *
   * 时间复杂度：O(n)
   */
  public int dominantIndex(int[] nums) {
    int position = 0;
    int max = 0;
    int lessMax = 0;
    int length = nums.length;
    if (length == 0) {
      return -1;
    } else if (length == 1) {
      return 0;
    }
    if (nums[0] < nums[1]) {
      max = nums[1];
      position = 1;
      lessMax = nums[0];
    } else {
      max = nums[0];
      lessMax = nums[1];
    }
    for (int i = 2; i < length; i++) {
      if (nums[i] > max) {
        lessMax = max;
        max = nums[i];
        position = i;
      } else if (nums[i] == max) {
        lessMax = max;
      } else if (nums[i] >= lessMax) {
        lessMax = nums[i];
      }
    }
    if (lessMax * 2 <= max) {
      return position;
    } else {
      return -1;
    }
  }

  /**
   * 给定一个排序数组，你需要在 原地 删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
   *
   * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
   *
   * 示例 :
   *
   * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
   *
   * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
   *
   * 你不需要考虑数组中超出新长度后面的元素。
   *
   * 题解：
   *
   * 快慢指针:慢指针k = 1 , 快指针i = 1 因为第一个元素必须存在，而且只需要从第二个元素开始比较
   *
   * 如果num[i] != num[i-1] && i != k 则更新num[k] = num[i] , k++
   *
   * 最后返回k即可
   */
  public int removeDuplicates(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }
    int k = 1;
    for (int i = 1; i < nums.length; i++) {
      if (nums[i] != nums[i - 1]) {
        if (k != i) {
          nums[k] = nums[i];
        }
        k++;
      }
    }
    return k;
  }

  /**
   * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
   *
   * 示例:
   *
   * 输入: [0,1,0,3,12]
   * 输出: [1,3,12,0,0]
   *
   * 题解：
   *
   * 快慢指针 循环一次
   *
   * 快指针 i;
   * 慢指针 index;
   *
   * i从首位开始向后移动
   *
   * 遇到非0时：
   * 如果i!=index：则num[index] = num[i] , num[i] = 0 , index++
   * 如果i==index：index++
   *
   * 最后非零元素都按顺序移动到数组前端，后面的数字全被置为0
   */
  public void moveZeroes(int[] nums) {
    int index = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != 0) {
        if (i != index) {
          nums[index] = nums[i];
          nums[i] = 0;
        }
        index++;
      }
    }
  }

  /**
   * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
   *
   * 示例 :
   *
   * 输入: [1,2,3,4,5,6,7] 和 k = 3
   * 输出: [5,6,7,1,2,3,4]
   * 解释:
   * 向右旋转 1 步: [7,1,2,3,4,5,6]
   * 向右旋转 2 步: [6,7,1,2,3,4,5]
   * 向右旋转 3 步: [5,6,7,1,2,3,4]
   *
   *
   * 题解：
   */
  // TODO: 2020/3/23 该方法太耗时
  public void rotate(int[] nums, int k) {
    while (k > 0) {
      for (int i = nums.length - 1; i > 0; i--) {
        nums[i] = nums[i] + nums[i - 1];
        nums[i - 1] = nums[i] - nums[i - 1];
        nums[i] = nums[i] - nums[i - 1];
      }
      k--;
    }
  }

  public List<Integer> getRow(int rowIndex) {
    List<Integer> list = new ArrayList<>();
    return list;
  }

  public int minSubArrayLen(int s, int[] nums) {
    return 0;
  }
}
