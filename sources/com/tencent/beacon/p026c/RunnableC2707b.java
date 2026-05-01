package com.tencent.beacon.p026c;

import android.content.SharedPreferences;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;

/* JADX INFO: renamed from: com.tencent.beacon.c.b */
/* JADX INFO: compiled from: Heartbeat.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2707b implements Runnable {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ String f1416a;

    /* JADX INFO: renamed from: b */
    final /* synthetic */ SharedPreferencesC2686a f1417b;

    /* JADX INFO: renamed from: c */
    final /* synthetic */ C2708c f1418c;

    RunnableC2707b(C2708c c2708c, String str, SharedPreferencesC2686a sharedPreferencesC2686a) {
        this.f1418c = c2708c;
        this.f1416a = str;
        this.f1417b = sharedPreferencesC2686a;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (C2669d.m1344d()) {
            boolean zM1505c = this.f1418c.m1505c();
            if (this.f1418c.f1421c && zM1505c) {
                C2695c.m1464a("[event] rqd_heartbeat A85=Y report success : " + this.f1416a, new Object[0]);
                SharedPreferencesC2686a.a aVarEdit = this.f1417b.edit();
                if (C2694b.m1441a((SharedPreferences.Editor) aVarEdit)) {
                    aVarEdit.putString("active_user_date", this.f1416a).commit();
                    aVarEdit.putString("HEART_DENGTA", this.f1416a).commit();
                }
            }
        }
    }
}
