package com.github.algorithm.queue;

/**
 * 数组实现循环队列
 *
 * 要点是控制收尾索引值
 */
class CircularQueue {

  private int head = -1;
  private int tail = -1;
  private int[] mQueue;
  private int size;

  /** Initialize your data structure here. Set the size of the queue to be k. */
  public CircularQueue(int k) {
    mQueue = new int[k];
    head = -1;
    tail = -1;
    size = k;
  }

  public static void main(String[] args) {
    CircularQueue circularQueue = new CircularQueue(6);
    circularQueue.enQueue(6);
    circularQueue.Rear();
    circularQueue.Rear();
    circularQueue.deQueue();
    circularQueue.deQueue();
    circularQueue.Rear();
    circularQueue.deQueue();
    circularQueue.Front();
    circularQueue.deQueue();
    circularQueue.deQueue();
    circularQueue.deQueue();
  }

  /** Insert an element into the circular queue. Return true if the operation is successful. */
  public boolean enQueue(int value) {
    if (isFull()) {
      return false;
    }
    if (isEmpty()) {
      head = 0;
    }
    tail = (tail + 1) % size;
    mQueue[tail] = value;
    return true;
  }

  /** Delete an element from the circular queue. Return true if the operation is successful. */
  public boolean deQueue() {
    if (isEmpty()) {
      return false;
    }
    if (head == tail) {
      head = -1;
      tail = -1;
      return true;
    }
    head = (head + 1) % size;
    return true;
  }

  /** Get the front item from the queue. */
  public int Front() {
    if (!isEmpty()) {
      return mQueue[head];
    }
    return -1;
  }

  /** Get the last item from the queue. */
  public int Rear() {
    if (!isEmpty()) {
      return mQueue[tail];
    }
    return -1;
  }

  /** Checks whether the circular queue is empty or not. */
  public boolean isEmpty() {
    return head == -1;
  }

  /** Checks whether the circular queue is full or not. */
  public boolean isFull() {
    return ((tail + 1) % size) == head;
  }
}