package com.github.algorithm.exp;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author lotty
 */
public class SequentialAccessTest {

    private final static int MAX = 100000;

    private static LinkedList<Integer> LIST = new LinkedList<>();

    static {
        for (int i = 0; i < MAX; i++) {
            LIST.add(i);
        }
    }

    public static void main(String[] args) {
        loop();
        iterator();
    }

    public static void loop() {
        int size = LIST.size();
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            Integer integer = LIST.get(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("for loop cost " + (end - start));
    }

    public static void iterator() {
        long start = System.currentTimeMillis();
        Iterator<Integer> iterator = LIST.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
        }
        long end = System.currentTimeMillis();
        System.out.println("iterator loop cost " + (end - start));
    }
}
