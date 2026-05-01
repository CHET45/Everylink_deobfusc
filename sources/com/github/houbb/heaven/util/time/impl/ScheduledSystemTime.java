package com.github.houbb.heaven.util.time.impl;

import com.github.houbb.heaven.util.time.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes3.dex */
public class ScheduledSystemTime implements Time {
    private final AtomicLong now;
    private final long period;

    private ScheduledSystemTime(long j) {
        this.period = j;
        this.now = new AtomicLong(System.currentTimeMillis());
        scheduleClockUpdating();
    }

    public ScheduledSystemTime() {
        this(1L);
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() { // from class: com.github.houbb.heaven.util.time.impl.ScheduledSystemTime.1
            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "System Clock");
                thread.setDaemon(true);
                return thread;
            }
        });
        Runnable runnable = new Runnable() { // from class: com.github.houbb.heaven.util.time.impl.ScheduledSystemTime.2
            @Override // java.lang.Runnable
            public void run() {
                ScheduledSystemTime.this.now.incrementAndGet();
            }
        };
        long j = this.period;
        scheduledExecutorServiceNewSingleThreadScheduledExecutor.scheduleAtFixedRate(runnable, j, j, TimeUnit.MILLISECONDS);
    }

    @Override // com.github.houbb.heaven.util.time.Time
    public long time() {
        return this.now.get();
    }
}
