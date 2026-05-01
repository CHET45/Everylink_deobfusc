package com.tencent.beacon.p028d;

import android.content.SharedPreferences;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;

/* JADX INFO: renamed from: com.tencent.beacon.d.g */
/* JADX INFO: compiled from: StrategyHolder.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2715g implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ String f1487a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ C2716h f1488b;

    RunnableC2715g(C2716h c2716h, String str) {
        this.f1488b = c2716h;
        this.f1487a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        SharedPreferencesC2686a.a aVarEdit = SharedPreferencesC2686a.m1391a().edit();
        if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
            aVarEdit.putString("ias_cookie", this.f1487a);
        }
    }
}
