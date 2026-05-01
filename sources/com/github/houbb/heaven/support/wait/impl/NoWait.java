package com.github.houbb.heaven.support.wait.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.support.wait.IWait;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
@Deprecated
public class NoWait implements IWait {
    @Override // com.github.houbb.heaven.support.wait.IWait
    public void waits(long j, TimeUnit timeUnit) {
    }
}
