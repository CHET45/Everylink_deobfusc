package com.whispercpp.whisper;

import kotlin.Metadata;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: WhisperCpuConfig.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(m1900d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, m1901d2 = {"Lcom/whispercpp/whisper/WhisperCpuConfig;", "", "()V", "preferredThreadCount", "", "getPreferredThreadCount", "()I", "lib_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class WhisperCpuConfig {
    public static final WhisperCpuConfig INSTANCE = new WhisperCpuConfig();

    private WhisperCpuConfig() {
    }

    public final int getPreferredThreadCount() {
        return RangesKt.coerceAtLeast(CpuInfo.INSTANCE.getHighPerfCpuCount(), 2);
    }
}
