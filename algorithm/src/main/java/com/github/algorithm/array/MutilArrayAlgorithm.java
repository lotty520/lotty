package com.github.algorithm.array;

import java.util.ArrayList;
import java.util.List;

/**
 * 多维数组相关算法
 *
 * @author lotty
 */
public class MutilArrayAlgorithm {

  /**
   * 螺旋输出：1 2 3 6 9 8 7 5 4
   */
  final static int[][] DT =
      new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };

  /**
   * 螺旋输出为： 1 2 3 4 8 12 11 10 9 5 6 7
   */
  final static int[][] DTY =
      new int[][] { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };

  /**
   * 螺旋输出为： 2 3 4 7 10 13 16 15 14 11 8 5 6 9 12
   */
  final static int[][] HG =
      new int[][] { { 2, 3, 4 }, { 5, 6, 7 }, { 8, 9, 10 }, { 11, 12, 13 }, { 14, 15, 16 } };

  /**
   * 螺旋输出为：1 2 3 6 5 4
   */

  final static int[][] DG =
      new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };

  /**
   * 螺旋输出为：1 2 4 6 5 3
   */
  final static int[][] DH =
      new int[][] { { 1, 2 }, { 3, 4 }, { 5, 6 } };

  /**
   * 螺旋输出为：1 2 3
   */
  final static int[][] DL =
      new int[][] { { 1, 2, 3 } };

  /**
   * 螺旋输出为：1 2 3
   */
  final static int[][] DV =
      new int[][] { { 1 }, { 2 }, { 3 } };

  public static void main(String[] args) {
    MutilArrayAlgorithm maa = new MutilArrayAlgorithm();
    //int[] order = maa.findDiagonalOrder(DT);
    //System.out.println(Arrays.toString(order));

    //List<Integer> integers = maa.spiralOrder(DT);r
    //System.out.println(integers.toString());

    //int i = maa.trailingZeroes(10);
    //System.out.println(" ----> " + i);

    List<List<Integer>> generate = maa.generate(5);
    System.out.println(generate.toString());
  }

  /**
   * 给定一个含有 M x N 个元素的矩阵（M 行，N 列），请以对角线遍历的顺序返回这个矩阵中的所有元素，对角线遍历如下图所示。
   *
   * 题解：
   * 1、在一条对角线上的元素，行左边与列坐标的和都一样。
   * 2、然后区分奇数偶数来判定从前向后还是从后向前:
   * <li>首先确定返回数组的长度</li>
   * <li>其次确定对角线行列坐标之和的最大值：循环上限</li>
   * <li>一个游标，确定当前返回数组的位置</li>
   * <li>判断奇偶轮：奇数轮行坐标递增；偶数轮行坐标递减</li>
   * <li>为了降低时间复杂度：降低内循环中每次循环的起点和终点</li>
   */
  public int[] findDiagonalOrder(int[][] matrix) {
    // 行
    int m = matrix.length;
    if (m == 0) {
      return new int[0];
    }
    // 列
    int n = matrix[0].length;

    int len = m * n;
    int[] result = new int[len];
    System.out.println("二维数组长度 " + len);

    int total = m + n - 2;
    int index = 0;
    for (int i = 0; i <= total; i++) {
      System.out.println(" i ---> " + i);
      if ((i & 1) == 0) {
        // move m0 from
        int end = Math.min(n - 1, i);
        int begin = Math.max(0, i - m + 1);
        for (int j = begin; j <= end; j++) {
          System.out.println("偶数：：" + j + "," + (i - j));
          result[index] = matrix[i - j][j];
          index++;
        }
      } else {
        int end = Math.min(m - 1, i);
        int begin = Math.max(0, i - n + 1);
        for (int j = begin; j <= end; j++) {
          System.out.println("奇数：：" + (i - j) + "," + j);
          result[index] = matrix[j][i - j];
          index++;
        }
      }
    }
    return result;
  }

  /**
   * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
   *
   * 输入:
   * [
   * [ 1, 2, 3 ],
   * [ 4, 5, 6 ],
   * [ 7, 8, 9 ]
   * ]
   * 输出: [1,2,3,6,9,8,7,4,5]
   *
   *
   * 题解：
   *
   * 从[0,0] - [corner,corner] 进行遍历
   * 其中 corner = Math.min((m + 1) / 2, n + 1 / 2)
   * 在循环内部对四条边进行遍历
   *
   * 需要注意的是：
   * 1、当能判定只剩下单行或者单列时，应该累加并停止
   * 2、零行零列、单行单列 的特殊情况
   */
  public List<Integer> spiralOrder(int[][] matrix) {

    List<Integer> list = new ArrayList<>();
    // 行
    int m = matrix.length;
    // 0行0列
    if (m == 0) {
      return list;
    }
    // 列
    int n = matrix[0].length;

    //单行
    if (m == 1) {
      int[] temp = matrix[0];
      for (int i = 0; i < n; i++) {
        list.add(temp[i]);
      }
      return list;
    }

    // 单列
    if (n == 1) {
      for (int i = 0; i < m; i++) {
        list.add(matrix[i][0]);
      }
      return list;
    }

    // 起点
    int corner = Math.min((m + 1) / 2, (n + 1) / 2);

    for (int i = 0; i < corner; i++) {
      // 单行
      if (i == m / 2) {
        for (int j = i; j <= n - 1 - i; j++) {
          list.add(matrix[i][j]);
        }
        return list;
      }

      if (i == n / 2) {
        for (int j = i; j <= m - 1 - i; j++) {
          list.add(matrix[j][i]);
        }
        return list;
      }

      System.out.println("当前循环 ---> " + i);
      for (int j = i; j <= n - 1 - i; j++) {
        System.out.println("循环一添加 " + matrix[i][j]);
        list.add(matrix[i][j]);
      }

      for (int j = i + 1; j <= m - 1 - i; j++) {
        System.out.println("循环二添加 " + matrix[j][n - 1 - i]);
        list.add(matrix[j][n - 1 - i]);
      }

      for (int j = n - 2 - i; j >= i; j--) {
        System.out.println("循环三添加 " + matrix[m - 1 - i][j]);
        list.add(matrix[m - 1 - i][j]);
      }
      for (int j = m - 2 - i; j > i; j--) {
        System.out.println("循环四添加 " + matrix[j][i]);
        list.add(matrix[j][i]);
      }
    }
    return list;
  }

  /**
     * 杨辉三角
     *
     * 题解：
     * 考虑特殊情况的第一行
     * 然后按照规则计算每个位置的数值
     *
     * 需要注意的是：边界检查
     */
      public List<List<Integer>> generate(int numRows) {
      List<List<Integer>> list = new ArrayList<>();

      if (numRows == 0) {
        return list;
      }
      List<Integer> lineOne = new ArrayList<>(1);
      lineOne.add(1);
      list.add(lineOne);

      for (int i = 1; i < numRows; i++) {
      List<Integer> integers = list.get(i - 1);
      List<Integer> temp = new ArrayList<>(i + 1);
      for (int j = 0; j <= i; j++) {
        Integer leftAbove = 0;
        if (j >= 0 && j < integers.size()) {
          leftAbove = integers.get(j);
        }
        Integer rightAbove = 0;
        int po = j - 1;
        if (po >= 0 && po < integers.size()) {
          rightAbove = integers.get(j - 1);
        }
        temp.add(leftAbove + rightAbove);
      }
      list.add(temp);
    }

    return list;
  }

  /**
   * 给定一个整数 n，返回 n! 结果尾数中零的数量。
   *
   * 示例 1:
   *
   * 输入: 3
   * 输出: 0
   * 解释: 3! = 6, 尾数中没有零。
   * 示例 2:
   *
   * 输入: 5
   * 输出: 1
   * 解释: 5! = 120, 尾数中有 1 个零.
   *
   * 题解：
   *
   * 其实只需要判断含有多少个5以及5的N次幂就行。
   *
   * 5 * n  -> n
   * 25 * m -> m
   * ...
   *
   * 最终结果为：n + m + ...
   */
  public int trailingZeroes(int n) {
    int p = 0;
    int nn = n;
    // 然后计算最少是5的几次幂
    while (n / 5 > 0) {
      n = n / 5;
      p++;
    }
    int count = 0;
    int mm = 1;
    System.out.println("p ---> " + p);
    for (int i = 1; i <= p; i++) {
      mm *= 5;
      System.out.println("i nn mm ---> " + i + " , " + nn + " , " + mm);
      System.out.println("count ---> " + count);

      count += nn / mm;
    }
    return count;
  }
}
