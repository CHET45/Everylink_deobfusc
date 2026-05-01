package org.minidns.dnslabel;

/* JADX INFO: loaded from: classes4.dex */
public abstract class NonLdhLabel extends DnsLabel {
    protected NonLdhLabel(String str) {
        super(str);
    }

    protected static DnsLabel fromInternal(String str) {
        if (UnderscoreLabel.isUnderscoreLabelInternal(str)) {
            return new UnderscoreLabel(str);
        }
        if (LeadingOrTrailingHyphenLabel.isLeadingOrTrailingHypenLabelInternal(str)) {
            return new LeadingOrTrailingHyphenLabel(str);
        }
        return new OtherNonLdhLabel(str);
    }
}
