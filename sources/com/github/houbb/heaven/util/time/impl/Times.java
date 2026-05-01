package com.github.houbb.heaven.util.time.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.time.Time;

/* JADX INFO: loaded from: classes3.dex */
public final class Times {
    private Times() {
    }

    public static Time defaults() {
        return DefaultSystemTime.getInstance();
    }

    public static Time scheduled() {
        return new ScheduledSystemTime();
    }

    public static long systemTime() {
        return DefaultSystemTime.getInstance().time();
    }

    public static long scheduledSystemTime() {
        return ((ScheduledSystemTime) Instances.singleton(ScheduledSystemTime.class)).time();
    }
}
