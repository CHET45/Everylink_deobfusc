package org.minidns.dnslabel;

/* JADX INFO: loaded from: classes4.dex */
public final class NonReservedLdhLabel extends LdhLabel {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected NonReservedLdhLabel(String str) {
        super(str);
    }

    public static boolean isNonReservedLdhLabel(String str) {
        if (isLdhLabel(str)) {
            return isNonReservedLdhLabelInternal(str);
        }
        return false;
    }

    static boolean isNonReservedLdhLabelInternal(String str) {
        return !ReservedLdhLabel.isReservedLdhLabelInternal(str);
    }
}
