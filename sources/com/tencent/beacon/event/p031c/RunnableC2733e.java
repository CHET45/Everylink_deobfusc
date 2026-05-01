package com.tencent.beacon.event.p031c;

import android.content.SharedPreferences;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;

/* JADX INFO: renamed from: com.tencent.beacon.event.c.e */
/* JADX INFO: compiled from: LogIDGenerator.java */
/* JADX INFO: loaded from: classes4.dex */
class RunnableC2733e implements Runnable {

    /* JADX INFO: renamed from: a */
    private volatile long f1542a = 0;

    /* JADX INFO: renamed from: b */
    private volatile long f1543b = 0;

    /* JADX INFO: renamed from: c */
    private volatile long f1544c = 0;

    /* JADX INFO: renamed from: d */
    final /* synthetic */ C2735g f1545d;

    RunnableC2733e(C2735g c2735g) {
        this.f1545d = c2735g;
    }

    @Override // java.lang.Runnable
    public void run() {
        synchronized (this.f1545d) {
            long j = this.f1545d.f1565r.get();
            long j2 = this.f1545d.f1564q.get();
            long j3 = this.f1545d.f1566s.get();
            if (this.f1542a == j && this.f1543b == j2 && this.f1544c == j3) {
                return;
            }
            this.f1542a = j;
            this.f1543b = j2;
            this.f1544c = j3;
            SharedPreferences.Editor editorEdit = SharedPreferencesC2686a.m1391a().edit();
            if (C2694b.m1441a(editorEdit)) {
                editorEdit.putLong("on_date", this.f1545d.f1563p).putLong("realtime_log_id", this.f1542a).putLong("normal_log_id", this.f1543b).putLong("immediate_log_id", this.f1544c).putLong("realtime_min_log_id", this.f1545d.f1567t).putLong("realtime_max_log_id", this.f1545d.f1568u).putLong("normal_min_log_id", this.f1545d.f1569v).putLong("normal_max_log_id", this.f1545d.f1570w).putLong("immediate_min_log_id", this.f1545d.f1571x).putLong("immediate_max_log_id", this.f1545d.f1572y).apply();
            }
            C2695c.m1463a("[LogID " + this.f1545d.f1561n + "]", "  write serial to sp, date: %s ,realtime: %d, normal: %d, immediate: %d ", Long.valueOf(this.f1545d.f1563p), Long.valueOf(this.f1542a), Long.valueOf(this.f1543b), Long.valueOf(this.f1544c));
        }
    }
}
