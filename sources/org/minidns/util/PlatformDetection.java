package org.minidns.util;

/* JADX INFO: loaded from: classes4.dex */
public class PlatformDetection {

    /* JADX INFO: renamed from: android, reason: collision with root package name */
    private static Boolean f2371android;

    public static boolean isAndroid() {
        if (f2371android == null) {
            try {
                Class.forName("android.Manifest");
                f2371android = true;
            } catch (Exception unused) {
                f2371android = false;
            }
        }
        return f2371android.booleanValue();
    }
}
