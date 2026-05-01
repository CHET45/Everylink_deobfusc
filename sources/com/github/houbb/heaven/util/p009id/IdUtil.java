package com.github.houbb.heaven.util.p009id;

import com.github.houbb.heaven.util.p009id.support.Sequence;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public final class IdUtil {
    private static final Sequence WORKER = new Sequence();

    public static long nextId() {
        return WORKER.nextId();
    }
}
