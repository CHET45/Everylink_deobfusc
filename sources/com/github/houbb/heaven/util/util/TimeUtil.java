package com.github.houbb.heaven.util.util;

import com.github.houbb.heaven.response.exception.CommonRuntimeException;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public final class TimeUtil {
    private TimeUtil() {
    }

    public static void sleep(long j) {
        if (j <= 0) {
            return;
        }
        try {
            Thread.sleep(j);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CommonRuntimeException(e);
        }
    }
}
