package com.github.houbb.heaven.util.lang;

/* JADX INFO: loaded from: classes3.dex */
public final class ThreadUtil {
    private ThreadUtil() {
    }

    private static int cpuNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static int bestThreadNum() {
        return cpuNum() * 3;
    }

    public static int bestThreadNum(int i) {
        int iBestThreadNum = bestThreadNum();
        return i < iBestThreadNum ? i : iBestThreadNum;
    }
}
