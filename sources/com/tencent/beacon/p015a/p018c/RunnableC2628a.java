package com.tencent.beacon.p015a.p018c;

import android.content.SharedPreferences;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;

/* JADX INFO: renamed from: com.tencent.beacon.a.c.a */
/* JADX INFO: compiled from: AppInfo.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2628a implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ SharedPreferencesC2686a f1066a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ String f1067b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ long f1068c;

    RunnableC2628a(SharedPreferencesC2686a sharedPreferencesC2686a, String str, long j) {
        this.f1066a = sharedPreferencesC2686a;
        this.f1067b = str;
        this.f1068c = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        SharedPreferencesC2686a.a aVarEdit = this.f1066a.edit();
        if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
            aVarEdit.putLong(this.f1067b, this.f1068c);
        }
    }
}
