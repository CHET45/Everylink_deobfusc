package com.tencent.qcloud.core.http;

import com.tencent.qcloud.core.task.SelfConstraintRetryStrategy;

/* JADX INFO: loaded from: classes4.dex */
public class HttpSelConstraintRetryStrategy extends SelfConstraintRetryStrategy {
    public HttpSelConstraintRetryStrategy(int i, int i2, int i3) {
        super(i, i2, i3);
    }

    @Override // com.tencent.qcloud.core.task.SelfConstraintRetryStrategy
    protected boolean shouldIncreaseDelay(Exception exc) {
        return HttpUtil.isNetworkTimeoutError(exc);
    }
}
