package org.oiakushev.counter;

public class Counter {
    private final int n, maxValue;
    private final int[] values;

    private boolean isFirst;
    private boolean isNotLast;

    public Counter(int n, int maxValue) {
        this.n = n;
        this.maxValue = maxValue;

        values = new int[n];
        for (int i=0;i<n;i++) {
            values[i] = i;
        }

        isFirst = true;
        isNotLast = true;
    }

    private boolean check(int[] values) {
        for (int i1=0;i1<values.length-1; i1++) {
            for (int i2=i1+1;i2<values.length;i2++) {
                if (values[i1] == values[i2]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void checkLast() {
        isNotLast = false;
        for (int i=0;i<n;i++) {
            if (values[i] != n-i-1) {
                isNotLast = true;
                break;
            }
        }
    }

    public int[] next() {
        if (isFirst) {
            isFirst = false;
            return values;
        }

        while (isNotLast) {
            int k = n-1;
            values[k]++;

            while (values[k] >= maxValue) {
                values[k] = 0;
                k--;
                values[k]++;
            }

            if (check(values)) {
                checkLast();
                return values;
            }
        }

        return values;
    }

    public boolean isNotLast() {
        return isNotLast;
    }
}
