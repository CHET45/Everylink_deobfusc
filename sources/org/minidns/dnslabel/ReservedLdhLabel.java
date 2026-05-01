package org.minidns.dnslabel;

/* JADX INFO: loaded from: classes4.dex */
public class ReservedLdhLabel extends LdhLabel {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected ReservedLdhLabel(String str) {
        super(str);
    }

    public static boolean isReservedLdhLabel(String str) {
        if (isLdhLabel(str)) {
            return isReservedLdhLabelInternal(str);
        }
        return false;
    }

    static boolean isReservedLdhLabelInternal(String str) {
        return str.length() >= 4 && str.charAt(2) == '-' && str.charAt(3) == '-';
    }
}
