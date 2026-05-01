package com.jieli.jl_bt_ota.tool;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes3.dex */
public class CallbackRunnable<T> implements Runnable {

    /* JADX INFO: renamed from: a */
    private final ArrayList<T> f703a;

    /* JADX INFO: renamed from: b */
    private final ICallbackHandler<T> f704b;

    public CallbackRunnable(ArrayList<T> arrayList, ICallbackHandler<T> iCallbackHandler) {
        this.f703a = arrayList;
        this.f704b = iCallbackHandler;
    }

    @Override // java.lang.Runnable
    public void run() {
        ArrayList<T> arrayList = this.f703a;
        if (arrayList == null || arrayList.isEmpty() || this.f704b == null) {
            return;
        }
        ArrayList arrayList2 = new ArrayList(this.f703a);
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            this.f704b.onHandle((T) obj);
        }
    }
}
