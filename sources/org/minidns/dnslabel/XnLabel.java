package org.minidns.dnslabel;

import java.util.Locale;
import org.minidns.idna.MiniDnsIdna;

/* JADX INFO: loaded from: classes4.dex */
public abstract class XnLabel extends ReservedLdhLabel {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected XnLabel(String str) {
        super(str);
    }

    protected static LdhLabel fromInternal(String str) {
        if (str.equals(MiniDnsIdna.toUnicode(str))) {
            return new FakeALabel(str);
        }
        return new ALabel(str);
    }

    public static boolean isXnLabel(String str) {
        if (isLdhLabel(str)) {
            return isXnLabelInternal(str);
        }
        return false;
    }

    static boolean isXnLabelInternal(String str) {
        return str.substring(0, 2).toLowerCase(Locale.US).equals("xn");
    }
}
