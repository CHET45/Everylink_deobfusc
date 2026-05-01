package com.github.houbb.heaven.util.lang;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class MathUtil {
    public static final double GOLD_SECTION = 0.6180339887498949d;

    private MathUtil() {
    }

    public static int gcd(int i, int i2) {
        return i2 == 0 ? i : gcd(i2, i % i2);
    }

    public static int ngcd(List<Integer> list) {
        return ngcd(list, list.size());
    }

    private static int ngcd(List<Integer> list, int i) {
        if (i == 1) {
            return list.get(0).intValue();
        }
        int i2 = i - 1;
        return gcd(list.get(i2).intValue(), ngcd(list, i2));
    }

    public static int lcm(int i, int i2) {
        return (i * i2) / gcd(i, i2);
    }

    public static int nlcm(List<Integer> list) {
        return nlcm(list, list.size());
    }

    private static int nlcm(List<Integer> list, int i) {
        if (i == 1) {
            return list.get(i - 1).intValue();
        }
        int i2 = i - 1;
        return lcm(list.get(i2).intValue(), nlcm(list, i2));
    }
}
