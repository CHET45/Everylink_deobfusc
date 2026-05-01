package com.aivox.jieliota.tool.bluetooth;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CallbackRunnable.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B#\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u000e\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, m1901d2 = {"Lcom/aivox/jieliota/tool/bluetooth/CallbackRunnable;", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/lang/Runnable;", "callbacks", "", "impl", "Lcom/aivox/jieliota/tool/bluetooth/CallbackImpl;", "(Ljava/util/List;Lcom/aivox/jieliota/tool/bluetooth/CallbackImpl;)V", "run", "", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class CallbackRunnable<T> implements Runnable {
    private final List<T> callbacks;
    private final CallbackImpl<T> impl;

    public CallbackRunnable(List<T> callbacks, CallbackImpl<T> callbackImpl) {
        Intrinsics.checkNotNullParameter(callbacks, "callbacks");
        this.callbacks = callbacks;
        this.impl = callbackImpl;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.callbacks.isEmpty() || this.impl == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.callbacks);
        Iterator<T> it = arrayList.iterator();
        while (it.hasNext()) {
            this.impl.onCallback(it.next());
        }
    }
}
