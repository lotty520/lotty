package com.github.algorithm.exp.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForTest {

    private static List<String> LIST = new ArrayList<>();


    static {
        for (int i = 0; i < 10; i++) {
            LIST.add("this is the " + i + "th string.");
        }
    }

    public static void main(String[] args) {
        Iterator<String> iterator;
        for (iterator = LIST.iterator(); iterator.hasNext(); ) {
            System.out.println("---> " + iterator.next());
        }
    }

}
