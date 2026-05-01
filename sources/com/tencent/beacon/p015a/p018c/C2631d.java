package com.tencent.beacon.p015a.p018c;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.tencent.beacon.a.c.d */
/* JADX INFO: compiled from: DeviceInfo.java */
/* JADX INFO: loaded from: classes4.dex */
class C2631d implements FileFilter {

    /* JADX INFO: renamed from: a */
    final /* synthetic */ C2632e f1093a;

    C2631d(C2632e c2632e) {
        this.f1093a = c2632e;
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        return Pattern.matches("cpu[0-9]", file.getName());
    }
}
