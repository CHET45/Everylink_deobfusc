package org.minidns.dnslabel;

/* JADX INFO: loaded from: classes4.dex */
public abstract class LdhLabel extends DnsLabel {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected LdhLabel(String str) {
        super(str);
    }

    public static boolean isLdhLabel(String str) {
        if (str.isEmpty() || LeadingOrTrailingHyphenLabel.isLeadingOrTrailingHypenLabelInternal(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if ((cCharAt < 'a' || cCharAt > 'z') && ((cCharAt < 'A' || cCharAt > 'Z') && ((cCharAt < '0' || cCharAt > '9') && cCharAt != '-'))) {
                return false;
            }
        }
        return true;
    }

    protected static LdhLabel fromInternal(String str) {
        if (ReservedLdhLabel.isReservedLdhLabel(str)) {
            if (XnLabel.isXnLabelInternal(str)) {
                return XnLabel.fromInternal(str);
            }
            return new ReservedLdhLabel(str);
        }
        return new NonReservedLdhLabel(str);
    }
}
