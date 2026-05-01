package org.minidns.idna;

/* JADX INFO: loaded from: classes4.dex */
public class MiniDnsIdna {
    private static IdnaTransformator idnaTransformator = new DefaultIdnaTransformator();

    public static String toASCII(String str) {
        return idnaTransformator.toASCII(str);
    }

    public static String toUnicode(String str) {
        return idnaTransformator.toUnicode(str);
    }

    public static void setActiveTransformator(IdnaTransformator idnaTransformator2) {
        if (idnaTransformator2 == null) {
            throw new IllegalArgumentException();
        }
        idnaTransformator = idnaTransformator2;
    }
}
