package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.p008io.JsonStringEncoder;
import com.microsoft.azure.storage.Constants;
import java.lang.ref.SoftReference;

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class BufferRecyclers {
    public static final String SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS = "com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers";
    private static final ThreadLocalBufferManager _bufferRecyclerTracker;
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;

    static {
        boolean zEquals;
        try {
            zEquals = Constants.TRUE.equals(System.getProperty(SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS));
        } catch (SecurityException unused) {
            zEquals = false;
        }
        _bufferRecyclerTracker = zEquals ? ThreadLocalBufferManager.instance() : null;
        _recyclerRef = new ThreadLocal<>();
    }

    @Deprecated
    public static BufferRecycler getBufferRecycler() {
        SoftReference<BufferRecycler> softReference;
        ThreadLocal<SoftReference<BufferRecycler>> threadLocal = _recyclerRef;
        SoftReference<BufferRecycler> softReference2 = threadLocal.get();
        BufferRecycler bufferRecycler = softReference2 == null ? null : softReference2.get();
        if (bufferRecycler == null) {
            bufferRecycler = new BufferRecycler();
            ThreadLocalBufferManager threadLocalBufferManager = _bufferRecyclerTracker;
            if (threadLocalBufferManager != null) {
                softReference = threadLocalBufferManager.wrapAndTrack(bufferRecycler);
            } else {
                softReference = new SoftReference<>(bufferRecycler);
            }
            threadLocal.set(softReference);
        }
        return bufferRecycler;
    }

    public static int releaseBuffers() {
        ThreadLocalBufferManager threadLocalBufferManager = _bufferRecyclerTracker;
        if (threadLocalBufferManager != null) {
            return threadLocalBufferManager.releaseBuffers();
        }
        return -1;
    }

    @Deprecated
    public static JsonStringEncoder getJsonStringEncoder() {
        return JsonStringEncoder.getInstance();
    }

    @Deprecated
    public static byte[] encodeAsUTF8(String str) {
        return JsonStringEncoder.getInstance().encodeAsUTF8(str);
    }

    @Deprecated
    public static char[] quoteAsJsonText(String str) {
        return JsonStringEncoder.getInstance().quoteAsString(str);
    }

    @Deprecated
    public static void quoteAsJsonText(CharSequence charSequence, StringBuilder sb) {
        JsonStringEncoder.getInstance().quoteAsString(charSequence, sb);
    }

    @Deprecated
    public static byte[] quoteAsJsonUTF8(String str) {
        return JsonStringEncoder.getInstance().quoteAsUTF8(str);
    }
}
