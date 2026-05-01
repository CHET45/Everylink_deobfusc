package com.aivox.base.util;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public final class ThreadPoolManager extends ThreadPoolExecutor {
    private static volatile ThreadPoolManager sInstance;

    public ThreadPoolManager() {
        super(0, 200, 30L, TimeUnit.MILLISECONDS, new SynchronousQueue());
    }

    public static ThreadPoolManager getInstance() {
        if (sInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (sInstance == null) {
                    sInstance = new ThreadPoolManager();
                }
            }
        }
        return sInstance;
    }
}
