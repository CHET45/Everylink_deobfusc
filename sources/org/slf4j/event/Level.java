package org.slf4j.event;

import com.azure.core.implementation.logging.DefaultLogger;

/* JADX INFO: loaded from: classes4.dex */
public enum Level {
    ERROR(40, DefaultLogger.ERROR),
    WARN(30, DefaultLogger.WARN),
    INFO(20, DefaultLogger.INFO),
    DEBUG(10, DefaultLogger.DEBUG),
    TRACE(0, "TRACE");

    private int levelInt;
    private String levelStr;

    Level(int i, String str) {
        this.levelInt = i;
        this.levelStr = str;
    }

    public int toInt() {
        return this.levelInt;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.levelStr;
    }
}
