package com.tencent.qcloud.core.logger;

/* JADX INFO: loaded from: classes4.dex */
public enum LogLevel {
    VERBOSE(1),
    DEBUG(2),
    INFO(3),
    WARN(4),
    ERROR(5);

    private final int priority;

    public int getPriority() {
        return this.priority;
    }

    LogLevel(int i) {
        this.priority = i;
    }

    public boolean isLoggable(LogLevel logLevel) {
        return logLevel == null || this.priority >= logLevel.priority;
    }
}
