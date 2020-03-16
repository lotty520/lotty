package com.github.algorithm.queue;

import java.util.Queue;

public class MainTest {

  public static void main(String[] args) {
    CircularQueue circularQueue = new CircularQueue(6);
    circularQueue.enQueue(6);
    // [6,0,0,0,0,0]

    circularQueue.Rear();
    // 6

    circularQueue.Rear();
    // 6


    circularQueue.deQueue();
    //[]

    circularQueue.deQueue();
    //[]

    circularQueue.Rear();
    // 0

    circularQueue.deQueue();
    circularQueue.Front();
    circularQueue.deQueue();
    circularQueue.deQueue();
    circularQueue.deQueue();

  }
}
