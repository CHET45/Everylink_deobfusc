package com.github.houbb.heaven.util.p009id.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.p009id.InterfaceC1503Id;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
@Deprecated
public class ConstId implements InterfaceC1503Id {

    /* JADX INFO: renamed from: id */
    private final String f539id;

    public ConstId(String str) {
        this.f539id = str;
    }

    @Override // com.github.houbb.heaven.util.p009id.InterfaceC1503Id
    public String genId() {
        return this.f539id;
    }
}
