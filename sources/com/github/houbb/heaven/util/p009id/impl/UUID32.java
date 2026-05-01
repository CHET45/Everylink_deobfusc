package com.github.houbb.heaven.util.p009id.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.p009id.InterfaceC1503Id;
import java.util.UUID;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
@Deprecated
public class UUID32 implements InterfaceC1503Id {
    private static final InterfaceC1503Id INSTANCE = new UUID32();

    public static InterfaceC1503Id getInstance() {
        return INSTANCE;
    }

    @Override // com.github.houbb.heaven.util.p009id.InterfaceC1503Id
    public String genId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
