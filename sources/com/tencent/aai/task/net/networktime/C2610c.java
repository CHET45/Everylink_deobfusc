package com.tencent.aai.task.net.networktime;

/* JADX INFO: renamed from: com.tencent.aai.task.net.networktime.c */
/* JADX INFO: loaded from: classes4.dex */
public class C2610c {

    /* JADX INFO: renamed from: a */
    public static volatile C2610c f1008a;

    /* JADX INFO: renamed from: b */
    public long f1009b = 0;

    /* JADX INFO: renamed from: c */
    public long f1010c = 0;

    /* JADX INFO: renamed from: com.tencent.aai.task.net.networktime.c$a */
    public class a implements InterfaceC2609b {
        public a() {
        }

        @Override // com.tencent.aai.task.net.networktime.InterfaceC2609b
        /* JADX INFO: renamed from: a */
        public void mo876a(long j) {
            C2610c.this.m988a(j != 0 ? j - (System.currentTimeMillis() / 1000) : 0L);
        }
    }

    /* JADX INFO: renamed from: c */
    public static C2610c m986c() {
        if (f1008a == null) {
            synchronized (C2610c.class) {
                if (f1008a == null) {
                    f1008a = new C2610c();
                }
            }
        }
        return f1008a;
    }

    /* JADX INFO: renamed from: a */
    public long m987a() {
        return this.f1009b;
    }

    /* JADX INFO: renamed from: a */
    public void m988a(long j) {
        this.f1009b = j;
        this.f1010c = System.currentTimeMillis() / 1000;
    }

    /* JADX INFO: renamed from: b */
    public long m989b() {
        return this.f1010c;
    }

    /* JADX INFO: renamed from: d */
    public void m990d() {
        if (Math.abs((System.currentTimeMillis() / 1000) - m989b()) < 36000) {
            return;
        }
        C2608a c2608a = new C2608a();
        c2608a.m984a(new a());
        c2608a.m983a();
    }
}
