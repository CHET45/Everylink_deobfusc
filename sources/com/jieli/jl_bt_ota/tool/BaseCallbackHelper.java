package com.jieli.jl_bt_ota.tool;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BaseCallbackHelper<T> {
    protected final ArrayList<T> callbacks = new ArrayList<>();

    /* JADX INFO: renamed from: a */
    private final Handler f702a = new Handler(Looper.getMainLooper());

    public boolean addCallback(T t) {
        if (t == null) {
            return false;
        }
        boolean zContains = this.callbacks.contains(t);
        return !zContains ? this.callbacks.add(t) : zContains;
    }

    protected void callbackEvent(ICallbackHandler<T> iCallbackHandler) {
        if (iCallbackHandler == null) {
            return;
        }
        CallbackRunnable callbackRunnable = new CallbackRunnable(this.callbacks, iCallbackHandler);
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            callbackRunnable.run();
        } else {
            this.f702a.post(callbackRunnable);
        }
    }

    public void release() {
        this.callbacks.clear();
        this.f702a.removeCallbacksAndMessages(null);
    }

    public boolean removeCallback(T t) {
        if (t == null || this.callbacks.isEmpty()) {
            return false;
        }
        return this.callbacks.remove(t);
    }
}
