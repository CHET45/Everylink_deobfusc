package com.tencent.qcloud.core.task;

import java.util.concurrent.atomic.AtomicLong;

/* JADX INFO: loaded from: classes4.dex */
public abstract class SelfConstraintRetryStrategy extends RetryStrategy {
    private AtomicLong nextDelay;

    protected abstract boolean shouldIncreaseDelay(Exception exc);

    public SelfConstraintRetryStrategy(int i, int i2, int i3) {
        super(i, i2, i3);
        this.nextDelay = new AtomicLong(0L);
    }

    @Override // com.tencent.qcloud.core.task.RetryStrategy
    public long getNextDelay(int i) {
        return this.nextDelay.get();
    }

    @Override // com.tencent.qcloud.core.task.RetryStrategy
    public void onTaskEnd(boolean z, Exception exc) {
        if (z) {
            this.nextDelay.set(0L);
        } else if (shouldIncreaseDelay(exc)) {
            this.nextDelay.set(Math.max(Math.min(this.maxBackoff, this.nextDelay.get() * 2), this.initBackoff));
        }
    }
}
