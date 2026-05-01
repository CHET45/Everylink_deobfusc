package com.github.houbb.heaven.util.p009id.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.p009id.InterfaceC1503Id;
import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.heaven.util.util.RandomUtil;
import java.util.Date;

/* JADX INFO: loaded from: classes3.dex */
@ThreadSafe
@Deprecated
public class RandomNumId implements InterfaceC1503Id {
    @Override // com.github.houbb.heaven.util.p009id.InterfaceC1503Id
    public String genId() {
        return DateUtil.getDateFormat(new Date(), DateUtil.TIMESTAMP_FORMAT_15) + RandomUtil.randomNumber(10);
    }
}
