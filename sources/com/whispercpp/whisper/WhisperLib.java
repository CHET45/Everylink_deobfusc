package com.whispercpp.whisper;

import android.content.res.AssetManager;
import com.aivox.base.common.Constant;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LibWhisper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(m1900d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0002\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, m1901d2 = {"Lcom/whispercpp/whisper/WhisperLib;", "", "()V", "Companion", "lib_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
final class WhisperLib {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: LibWhisper.kt */
    @Metadata(m1900d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u0014\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086 J\u0011\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086 J\u0011\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0086 J)\u0010\f\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0086 J\t\u0010\u0011\u001a\u00020\u0004H\u0086 J\u0019\u0010\u0012\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0006H\u0086 J\u0011\u0010\u0014\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0086 J\u0019\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0006H\u0086 J\u0019\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0006H\u0086 J\u0011\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0018\u001a\u00020\u0004H\u0086 J\u0019\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0004H\u0086 J\u0011\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0086 ¨\u0006 "}, m1901d2 = {"Lcom/whispercpp/whisper/WhisperLib$Companion;", "", "()V", "benchGgmlMulMat", "", "nthread", "", "benchMemcpy", "freeContext", "", "contextPtr", "", "fullTranscribe", "numThreads", "language", "audioData", "", "getSystemInfo", "getTextSegment", Constant.KEY_INDEX, "getTextSegmentCount", "getTextSegmentT0", "getTextSegmentT1", "initContext", "modelPath", "initContextFromAsset", "assetManager", "Landroid/content/res/AssetManager;", "assetPath", "initContextFromInputStream", "inputStream", "Ljava/io/InputStream;", "lib_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final native String benchGgmlMulMat(int nthread);

        public final native String benchMemcpy(int nthread);

        public final native void freeContext(long contextPtr);

        public final native void fullTranscribe(long contextPtr, int numThreads, String language, float[] audioData);

        public final native String getSystemInfo();

        public final native String getTextSegment(long contextPtr, int index);

        public final native int getTextSegmentCount(long contextPtr);

        public final native long getTextSegmentT0(long contextPtr, int index);

        public final native long getTextSegmentT1(long contextPtr, int index);

        public final native long initContext(String modelPath);

        public final native long initContextFromAsset(AssetManager assetManager, String assetPath);

        public final native long initContextFromInputStream(InputStream inputStream);

        private Companion() {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0086  */
    static {
        /*
            com.whispercpp.whisper.WhisperLib$Companion r0 = new com.whispercpp.whisper.WhisperLib$Companion
            r1 = 0
            r0.<init>(r1)
            com.whispercpp.whisper.WhisperLib.INSTANCE = r0
            java.lang.String[] r0 = android.os.Build.SUPPORTED_ABIS
            r2 = 0
            r0 = r0[r2]
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Primary ABI: "
            r3.<init>(r4)
            java.lang.StringBuilder r0 = r3.append(r0)
            java.lang.String r0 = r0.toString()
            java.lang.String r3 = "LibWhisper"
            android.util.Log.d(r3, r0)
            boolean r0 = com.whispercpp.whisper.LibWhisperKt.access$isArmEabiV7a()
            r4 = 1
            r5 = 2
            java.lang.String r6 = "CPU info: "
            if (r0 == 0) goto L58
            java.lang.String r0 = com.whispercpp.whisper.LibWhisperKt.access$cpuInfo()
            if (r0 == 0) goto L86
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r6)
            java.lang.StringBuilder r6 = r7.append(r0)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r3, r6)
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            java.lang.String r6 = "vfpv4"
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            boolean r0 = kotlin.text.StringsKt.contains$default(r0, r6, r2, r5, r1)
            if (r0 == 0) goto L53
            java.lang.String r0 = "CPU supports vfpv4"
            android.util.Log.d(r3, r0)
            goto L54
        L53:
            r4 = r2
        L54:
            r8 = r4
            r4 = r2
            r2 = r8
            goto L87
        L58:
            boolean r0 = com.whispercpp.whisper.LibWhisperKt.access$isArmEabiV8a()
            if (r0 == 0) goto L86
            java.lang.String r0 = com.whispercpp.whisper.LibWhisperKt.access$cpuInfo()
            if (r0 == 0) goto L86
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r6)
            java.lang.StringBuilder r6 = r7.append(r0)
            java.lang.String r6 = r6.toString()
            android.util.Log.d(r3, r6)
            java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            java.lang.String r6 = "fphp"
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            boolean r0 = kotlin.text.StringsKt.contains$default(r0, r6, r2, r5, r1)
            if (r0 == 0) goto L86
            java.lang.String r0 = "CPU supports fp16 arithmetic"
            android.util.Log.d(r3, r0)
            goto L87
        L86:
            r4 = r2
        L87:
            if (r2 == 0) goto L94
            java.lang.String r0 = "Loading libwhisper_vfpv4.so"
            android.util.Log.d(r3, r0)
            java.lang.String r0 = "whisper_vfpv4"
            java.lang.System.loadLibrary(r0)
            goto Lab
        L94:
            if (r4 == 0) goto La1
            java.lang.String r0 = "Loading libwhisper_v8fp16_va.so"
            android.util.Log.d(r3, r0)
            java.lang.String r0 = "whisper_v8fp16_va"
            java.lang.System.loadLibrary(r0)
            goto Lab
        La1:
            java.lang.String r0 = "Loading libwhisper.so"
            android.util.Log.d(r3, r0)
            java.lang.String r0 = "whisper"
            java.lang.System.loadLibrary(r0)
        Lab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.whispercpp.whisper.WhisperLib.<clinit>():void");
    }
}
