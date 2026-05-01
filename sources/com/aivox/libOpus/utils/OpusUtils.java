package com.aivox.libOpus.utils;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OpusUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0017\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0086 J!\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0086 J!\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086 J\u0011\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\u0004H\u0086 J\u0011\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\u0004H\u0086 J)\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0086 ¨\u0006\u0016"}, m1901d2 = {"Lcom/aivox/libOpus/utils/OpusUtils;", "", "()V", "createDecoder", "", "sampleRateInHz", "", "channelConfig", "createEncoder", "complexity", "decode", "handle", "encoded", "", "lin", "", "destroyDecoder", "", "destroyEncoder", "encode", TypedValues.CycleType.S_WAVE_OFFSET, "Companion", "libOpus_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class OpusUtils {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static OpusUtils opusUtils;

    public final native long createDecoder(int sampleRateInHz, int channelConfig);

    public final native long createEncoder(int sampleRateInHz, int channelConfig, int complexity);

    public final native int decode(long handle, byte[] encoded, short[] lin);

    public final native void destroyDecoder(long handle);

    public final native void destroyEncoder(long handle);

    public final native int encode(long handle, short[] lin, int offset, byte[] encoded);

    public OpusUtils() {
        System.loadLibrary("opusJni");
    }

    /* JADX INFO: compiled from: OpusUtils.kt */
    @Metadata(m1900d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, m1901d2 = {"Lcom/aivox/libOpus/utils/OpusUtils$Companion;", "", "()V", "opusUtils", "Lcom/aivox/libOpus/utils/OpusUtils;", "getInstant", "libOpus_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final OpusUtils getInstant() {
            if (OpusUtils.opusUtils == null) {
                synchronized (OpusUtils.class) {
                    if (OpusUtils.opusUtils == null) {
                        Companion companion = OpusUtils.INSTANCE;
                        OpusUtils.opusUtils = new OpusUtils();
                    }
                    Unit unit = Unit.INSTANCE;
                }
            }
            OpusUtils opusUtils = OpusUtils.opusUtils;
            Intrinsics.checkNotNull(opusUtils);
            return opusUtils;
        }
    }
}
