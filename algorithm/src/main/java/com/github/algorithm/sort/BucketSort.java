package com.github.algorithm.sort;

import java.util.Arrays;

/**
 * <p>
 * 桶排序是计数排序的升级版。它利用了函数的映射关系，高效与否的关键就在于这个映射函数的确定。为了使桶排序更加高效，我们需要做到这两点：
 * 在额外空间充足的情况下，尽量增大桶的数量
 * 使用的映射函数能够将输入的 N 个数据均匀的分配到 K 个桶中
 * </p>
 *
 * <p>计数排序</p>
 * <p>
 * （1）找出待排序的数组中最大和最小的元素
 * （2）统计数组中每个值为i的元素出现的次数，存入数组C的第i项
 * （3）对所有的计数累加（从C中的第一个元素开始，每一项和前一项相加）
 * （4）反向填充目标数组：将每个元素i放在新数组的第C(i)项，每放一个元素就将C(i)减去1</p>
 */
public class BucketSort {

  private static final InsertSort insertSort = new InsertSort();

  public int[] sort(int[] sourceArray) throws Exception {
    // 对 arr 进行拷贝，不改变参数内容
    int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

    return bucketSort(arr, 5);
  }

  private int[] bucketSort(int[] arr, int bucketSize) throws Exception {
    if (arr.length == 0) {
      return arr;
    }

    int minValue = arr[0];
    int maxValue = arr[0];
    for (int value : arr) {
      if (value < minValue) {
        minValue = value;
      } else if (value > maxValue) {
        maxValue = value;
      }
    }

    int bucketCount = (int) Math.floor((maxValue - minValue) / bucketSize) + 1;
    int[][] buckets = new int[bucketCount][0];

    // 利用映射函数将数据分配到各个桶中
    for (int i = 0; i < arr.length; i++) {
      int index = (int) Math.floor((arr[i] - minValue) / bucketSize);
      buckets[index] = arrAppend(buckets[index], arr[i]);
    }

    int arrIndex = 0;
    for (int[] bucket : buckets) {
      if (bucket.length <= 0) {
        continue;
      }
      // 对每个桶进行排序，这里使用了插入排序
      bucket = insertSort.sort(bucket);
      for (int value : bucket) {
        arr[arrIndex++] = value;
      }
    }

    return arr;
  }

  /**
   * 自动扩容，并保存数据
   */
  private int[] arrAppend(int[] arr, int value) {
    arr = Arrays.copyOf(arr, arr.length + 1);
    arr[arr.length - 1] = value;
    return arr;
  }
}
