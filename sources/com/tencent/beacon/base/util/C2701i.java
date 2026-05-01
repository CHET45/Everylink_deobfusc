package com.tencent.beacon.base.util;

import android.text.TextUtils;
import com.azure.xml.implementation.aalto.util.CharsetNames;
import java.nio.charset.Charset;

/* JADX INFO: renamed from: com.tencent.beacon.base.util.i */
/* JADX INFO: compiled from: StringUtil.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2701i {
    /* JADX INFO: renamed from: a */
    public static boolean m1489a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        try {
            return Charset.forName(CharsetNames.CS_ISO_LATIN1).newEncoder().canEncode(str);
        } catch (Exception e) {
            C2695c.m1465a(e);
            return false;
        }
    }
}
