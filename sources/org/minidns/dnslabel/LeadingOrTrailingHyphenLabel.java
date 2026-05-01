package org.minidns.dnslabel;

/* JADX INFO: loaded from: classes4.dex */
public final class LeadingOrTrailingHyphenLabel extends NonLdhLabel {
    protected LeadingOrTrailingHyphenLabel(String str) {
        super(str);
    }

    protected static boolean isLeadingOrTrailingHypenLabelInternal(String str) {
        if (str.isEmpty()) {
            return false;
        }
        return str.charAt(0) == '-' || str.charAt(str.length() - 1) == '-';
    }
}
