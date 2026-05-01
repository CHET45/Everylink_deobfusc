package com.tencent.beacon.base.store;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: renamed from: com.tencent.beacon.base.store.b */
/* JADX INFO: compiled from: DBConst.java */
/* JADX INFO: loaded from: classes4.dex */
public final class C2687b {

    /* JADX INFO: renamed from: a */
    public static final List<String> f1376a;

    static {
        ArrayList arrayList = new ArrayList();
        f1376a = arrayList;
        arrayList.add("CREATE TABLE t_r_e ( _id INTEGER PRIMARY KEY AUTOINCREMENT, _appKey text ,_time int ,_length int ,_data  blob)");
        arrayList.add("CREATE TABLE t_n_e ( _id INTEGER PRIMARY KEY AUTOINCREMENT, _appKey text ,_time int ,_length int ,_data  blob)");
    }
}
