package com.github.algorithm.array;

import java.util.Arrays;

public class StringAlgo {

  static String[] CS = new String[] { "flower", "flow", "flight" };
  //static String[] CS = new String[] { "dog", "racecar", "car" };

  static char[] FD = new char[] { 'h', 'e', 'l', 'l', 'o' };

  static int[] NUM = new int[] { 2, 7, 11, 15 };

  static int[] ONE = new int[] { 1, 1, 0, 1, 1, 1 };

  public static void main(String[] args) {
    StringAlgo sa = new StringAlgo();
    //String s = sa.addBinary("11", "1");
    //System.out.println("---> " + s);

    //int i = sa.strStr("", "");
    //System.out.println("---> " + i);

    //String s = sa.longestCommonPrefix(CS);
    //System.out.println(s);

    //sa.reverseString(FD);
    //System.out.println(FD);

    //int i = sa.arrayPairSum(NUM);
    //System.out.println("---> " + i);

    //int[] ints = sa.twoSum(NUM, 17);
    //System.out.println(ints[0] + " , " + ints[1]);

    //int i = sa.removeElement(NUM, 11);
    //System.out.println("---> " + i);

    int maxConsecutiveOnes = sa.findMaxConsecutiveOnes(ONE);
    System.out.println("---> " + maxConsecutiveOnes);
  }

  /**
   * 给定两个二进制字符串，返回他们的和（用二进制表示）。
   *
   * 输入为非空字符串且只包含数字 1 和 0。
   *
   * 示例 1:
   *
   * 输入: a = "11", b = "1"
   * 输出: "100"
   * 示例 2:
   *
   * 输入: a = "1010", b = "1011"
   * 输出: "10101"
   *
   *
   * 题解：
   * 1、先确定长短
   * 2、利用StringBuilder来进行头部插入
   * 3、每轮计算，需要确定之前是否有进位，当次是否产生进位
   * 4、需要判断短字符的边界，越过边界不参与计算
   * 5、最后别忘了可能的进位导致字符长度+1
   */
  public String addBinary(String a, String b) {
    char[] charA = a.toCharArray();
    char[] charB = b.toCharArray();
    int min = Math.min(charA.length, charB.length);
    int max = Math.max(charA.length, charB.length);

    char[] shortOne = charB.length > charA.length ? charA : charB;
    char[] longOne = charB.length <= charA.length ? charA : charB;

    int longLength = longOne.length;
    int shortLength = shortOne.length;
    StringBuilder sb = new StringBuilder(longLength);

    boolean cast = false;
    for (int i = 0; i < longLength; i++) {

      int shortP = shortLength - 1 - i;
      int longP = longLength - 1 - i;

      if (shortP < 0) {
        if (cast) {
          if (longOne[longP] == '0') {
            sb.insert(0, '1');
            cast = false;
          } else {
            sb.insert(0, '0');
            cast = true;
          }
        } else {
          sb.insert(0, longOne[longP]);
        }
      } else {
        if (cast) {
          if (longOne[longP] - shortOne[shortP] != 0) {
            sb.insert(0, '0');
            cast = true;
          } else if (longOne[longP] == '0') {
            sb.insert(0, '1');
            cast = false;
          } else {
            sb.insert(0, '1');
            cast = true;
          }
        } else {
          if (longOne[longP] - shortOne[shortP] != 0) {
            sb.insert(0, '1');
          } else if (longOne[longP] == '0') {
            sb.insert(0, '0');
          } else {
            sb.insert(0, '0');
            cast = true;
          }
        }
      }
    }
    if (cast) {
      sb.insert(0, '1');
    }
    return sb.toString();
  }

  /**
   * 给定一个 haystack 字符串和一个 needle 字符串，在 haystack 字符串中找出 needle 字符串出现的第一个位置 (从0开始)。如果不存在，则返回  -1。
   *
   * 示例:
   *
   * 输入: haystack = "hello", needle = "ll"
   * 输出: 2
   *
   * 当 needle 是空字符串时我们应当返回 0
   *
   * 题解1：
   *
   * 循环
   *
   * 题解2：
   * 双指针，可以跳过在题解1中的部分访问
   */
  public int strStr(String haystack, String needle) {
    if (haystack == null) {
      return -1;
    }
    if (needle == null || "".equals(needle)) {
      return 0;
    }
    int hL = haystack.length();
    int nL = needle.length();
    if (hL < nL) {
      return -1;
    }
    for (int i = 0; i <= hL - nL; i++) {
      boolean shot = true;
      for (int j = 0; j < nL; j++) {
        if (haystack.charAt(i + j) != needle.charAt(j)) {
          shot = false;
          break;
        }
      }
      if (shot) {
        return i;
      }
    }
    return -1;
  }

  /**
   * 编写一个函数来查找字符串数组中的最长公共前缀。
   *
   * 如果不存在公共前缀，返回空字符串 ""。
   *
   * 示例 1:
   *
   * 输入: ["flower","flow","flight"]
   * 输出: "fl
   *
   * 题解1：暴力循环，及时中断
   *
   * 题解2：
   * 1、默认结果ans = strs[0]
   * 2、计算ans和后面字符串的结果，赋值给ans
   * 3、如果ans提前为空字符，则返回
   *
   * 优点，除了第一轮，后面的比较最多比较ans.length()次。
   */
  public String longestCommonPrefix(String[] strs) {
    if (strs == null || strs.length == 0) {
      return "";
    }
    if (strs.length == 1) {
      return strs[0];
    }

    String sb = strs[0];

    for (int i = 1; i < strs.length; i++) {
      int j = 0;
      for (; j < sb.length() && j < strs[i].length(); j++) {
        if (sb.charAt(j) != strs[i].charAt(j)) {
          break;
        }
      }
      sb = sb.substring(0, j);
      if ("".equals(sb)) {
        return sb;
      }
    }
    return sb;
  }

  /**
   * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
   *
   * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
   *
   * 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
   *
   * 题解：
   *
   * 首尾指针，移动到交叉位置
   */
  public void reverseString(char[] s) {
    if (s == null) {
      return;
    }
    int i = 0;
    int j = s.length - 1;
    while (j > i) {
      char T = s[i];
      s[i] = s[j];
      s[j] = T;
      j--;
      i++;
    }
  }

  /**
   * 给定长度为 2n 的数组, 你的任务是将这些数分成 n 对, 例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从1 到 n 的 min(ai, bi)
   * 总和最大。
   *
   * 示例 1:
   *
   * 输入: [1,4,3,2]
   *
   * 输出: 4
   * 解释: n 等于 2, 最大总和为 4 = min(1, 2) + min(3, 4).
   *
   * 题解：
   *
   * 排序后就很好解决
   */
  public int arrayPairSum(int[] nums) {
    if (nums.length == 2) {
      return Math.min(nums[0], nums[1]);
    }
    Arrays.sort(nums);
    int total = 0;
    int n = nums.length / 2;
    for (int i = 0; i < n; i++) {
      total += Math.min(nums[2 * i], nums[2 * i + 1]);
    }
    return total;
  }

  /**
   * 给定一个已按照升序排列 的有序数组，找到两个数使得它们相加之和等于目标数。
   *
   * 函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。
   *
   * 说明:
   *
   * 返回的下标值（index1 和 index2）不是从零开始的。
   * 你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。
   * 示例:
   *
   * 输入: numbers = [2, 7, 11, 15], target = 9
   * 输出: [1,2]
   * 解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
   */
  public int[] twoSum(int[] numbers, int target) {
    int[] ans = new int[2];
    int j = numbers.length - 1;
    int i = 0;
    if (j < 1) {
      return ans;
    }

    while (j > i) {
      if (numbers[i] + numbers[j] == target) {
        ans[0] = i + 1;
        ans[1] = j + 1;
        return ans;
      } else if (numbers[i] + numbers[j] < target) {
        i++;
      } else {
        j--;
      }
    }
    return ans;
  }

  /**
   * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
   *
   * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
   *
   * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
   *
   *
   *
   * 示例 1:
   *
   * 给定 nums = [3,2,2,3], val = 3,
   *
   * 函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。
   *
   * 你不需要考虑数组中超出新长度后面的元素。
   *
   * 题解：
   * 快慢指针
   *
   * 慢指针可以直接修改原始数组的值，而不会影响快指针的取值
   *
   * 注意慢指针的更新规则
   *
   * 优化点:如果命中规则，当快慢指针不在同一个位置时，才需要更新
   */
  public int removeElement(int[] nums, int val) {
    int ans = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] != val) {
        if (ans != i) {
          nums[ans] = nums[i];
        }
        ans++;
      }
    }
    return ans;
  }

  /**
   * 给定一个二进制数组， 计算其中最大连续1的个数。
   *
   * 示例 1:
   *
   * 输入: [1,1,0,1,1,1]
   * 输出: 3
   * 解释: 开头的两位和最后的三位都是连续1，所以最大连续1的个数是 3.
   *
   * 典型的快慢指针问题
   * 控制时间复杂度O(N)
   *
   * 慢指针记录次新生的序列的长度，新生序列结束后，更新max值
   *
   * 注意：新生序列的结束点有两个：
   * <li>遇到零</li>
   * <li>到达数组末尾</li>
   */
  public int findMaxConsecutiveOnes(int[] nums) {
    int max = 0;
    int k = 0;
    for (int i = 0; i < nums.length; i++) {
      if (nums[i] == 1) {
        k++;
      } else {
        if (k > max) {
          max = k;
        }
        k = 0;
      }
    }
    return Math.max(k, max);
  }

  /**
   * 给定一个含有 n 个正整数的数组和一个正整数 s ，找出该数组中满足其和 ≥ s 的长度最小的连续子数组。如果不存在符合条件的连续子数组，返回 0。
   *
   * 示例:
   *
   * 输入: s = 7, nums = [2,3,1,2,4,3]
   * 输出: 2
   * 解释: 子数组 [4,3] 是该条件下的长度最小的连续子数组。
   */
  public int minSubArrayLen(int s, int[] nums) {
    return 0;
  }

}
