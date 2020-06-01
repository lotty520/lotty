package com.github.algorithm.exp.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {

    private final static int MAX = 5000000;
    private static HashMap<String, String> HASH = new HashMap<>();
    private static ConcurrentHashMap<String, String> CONCURRENT = new ConcurrentHashMap<>();
    private static Hashtable<String, String> TABLE = new Hashtable<>();

    static {
//        insertTest();
    }

    public static void main(String[] args) {
//        insertTest();
//        getTest();
//        traverseTest();
        HASH.put(null, "this is null key");
        CONCURRENT.put(null, "this is null key");
        TABLE.put(null, "this is null key");
    }

    private static void traverseTest() {
        traverseHashMap();
        traverseHashTable();
        traverseConcurrent();
    }

    private static void traverseHashMap() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            String key = "key" + i;
            HASH.get(key);
        }
        long end = System.currentTimeMillis();
        System.out.println("traverseHashMap cost : " + (end - start));
    }

    private static void traverseHashTable() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            String key = "key" + i;
            TABLE.get(key);
        }
        long end = System.currentTimeMillis();
        System.out.println("traverseHashTable cost : " + (end - start));
    }

    private static void traverseConcurrent() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            String key = "key" + i;
            CONCURRENT.get(key);
        }
        long end = System.currentTimeMillis();
        System.out.println("traverseConcurrent cost : " + (end - start));
    }

    private static void getTest() {
        hashMapGetTest();
        hashTableGetTest();
        concurrentGetTest();
    }

    private static void hashMapGetTest() {
        long start = System.currentTimeMillis();
        HASH.get("key555555");
        long end = System.currentTimeMillis();
        System.out.println("hash get cost : " + (end - start));
    }

    private static void hashTableGetTest() {
        long start = System.currentTimeMillis();
        TABLE.get("key555555");
        long end = System.currentTimeMillis();
        System.out.println("table get cost : " + (end - start));
    }

    private static void concurrentGetTest() {
        long start = System.currentTimeMillis();
        CONCURRENT.get("key555555");
        long end = System.currentTimeMillis();
        System.out.println("concurrent get cost : " + (end - start));
    }

    private static void insertTest() {
        hashMapTest();
        hashTableTest();
        concurrentMapTest();
    }

    private static void hashMapTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            String key = "key" + i;
            String value = "value" + i;
            HASH.put(key, value);
        }
        long end = System.currentTimeMillis();
        System.out.println("hashMap cost : " + (end - start));

    }

    private static void hashTableTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            String key = "key" + i;
            String value = "value" + i;
            TABLE.put(key, value);
        }
        long end = System.currentTimeMillis();
        System.out.println("hashTable cost : " + (end - start));
    }

    private static void concurrentMapTest() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            String key = "key" + i;
            String value = "value" + i;
            CONCURRENT.put(key, value);
        }
        long end = System.currentTimeMillis();
        System.out.println("concurrentMap cost : " + (end - start));
    }
}
