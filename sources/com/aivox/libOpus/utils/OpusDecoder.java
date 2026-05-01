package com.aivox.libOpus.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OpusDecoder.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0016\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\b\u0010\r\u001a\u00020\u000eH\u0004J\u0006\u0010\u000f\u001a\u00020\u000eR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, m1901d2 = {"Lcom/aivox/libOpus/utils/OpusDecoder;", "", "sampleRate", "", "channels", "(II)V", "nativeDecoderState", "", "decode", "inputOpus", "", "outputPcm", "", "finalize", "", "release", "Companion", "libOpus_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class OpusDecoder {
    private static final String TAG = "OpusDecoder";
    private long nativeDecoderState;

    public OpusDecoder(int i, int i2) {
        long jCreateDecoder = OpusUtils.INSTANCE.getInstant().createDecoder(i, i2);
        this.nativeDecoderState = jCreateDecoder;
        if (jCreateDecoder == 0) {
            throw new IllegalStateException("Failed to create Opus decoder. Check native logs.".toString());
        }
    }

    public final int decode(byte[] inputOpus, short[] outputPcm) {
        Intrinsics.checkNotNullParameter(inputOpus, "inputOpus");
        Intrinsics.checkNotNullParameter(outputPcm, "outputPcm");
        if (this.nativeDecoderState == 0) {
            return -1;
        }
        return OpusUtils.INSTANCE.getInstant().decode(this.nativeDecoderState, inputOpus, outputPcm);
    }

    public final void release() {
        if (this.nativeDecoderState != 0) {
            OpusUtils.INSTANCE.getInstant().destroyDecoder(this.nativeDecoderState);
            this.nativeDecoderState = 0L;
        }
    }

    protected final void finalize() throws Throwable {
        release();
    }
}
