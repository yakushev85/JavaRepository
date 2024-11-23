package org.oiakushev.counter;

import java.util.Date;

public class Main {
    private final static int N = 9;
    private final static int maxValue = 9;

    private static void printV(int[] values) {
        StringBuilder result = new StringBuilder();
        for (int value : values) {
            result.append(value + 1).append(" ");
        }
        System.out.println(result);
    }

    private static void testCounter() {
        Counter counter = new Counter(N, maxValue);
        while (counter.isNotLast()) {
            printV(counter.next());
        }
    }

    public static void main(String[] args) {
        long time1 = new Date().getTime();
        testCounter();
        long time2 = new Date().getTime();
        long diff = time2 - time1;
        System.out.println("Time elapsed: " + diff + " ms");
    }
}
