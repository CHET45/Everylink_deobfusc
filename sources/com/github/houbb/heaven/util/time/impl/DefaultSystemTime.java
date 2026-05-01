package com.github.houbb.heaven.util.time.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.time.Time;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
class DefaultSystemTime implements Time {
    private static final DefaultSystemTime INSTANCE = new DefaultSystemTime();

    DefaultSystemTime() {
    }

    public static Time getInstance() {
        return INSTANCE;
    }

    @Override // com.github.houbb.heaven.util.time.Time
    public long time() {
        return System.currentTimeMillis();
    }
}
