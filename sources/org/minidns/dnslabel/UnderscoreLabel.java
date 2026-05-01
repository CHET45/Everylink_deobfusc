package org.minidns.dnslabel;

/* JADX INFO: loaded from: classes4.dex */
public final class UnderscoreLabel extends NonLdhLabel {
    protected UnderscoreLabel(String str) {
        super(str);
    }

    protected static boolean isUnderscoreLabelInternal(String str) {
        return str.charAt(0) == '_';
    }
}
