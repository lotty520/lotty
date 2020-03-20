package com.github.algorithm.array;

import java.util.Arrays;

/**
 * 多维数组相关算法
 *
 * @author lotty
 */
public class MutilArrayAlgorithm {

  final static int[][] DT =
      new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };


  public static void main(String[] args) {
    MutilArrayAlgorithm maa = new MutilArrayAlgorithm();
    int[] order = maa.findDiagonalOrder(DT);
    System.out.println(Arrays.toString(order));
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
}
