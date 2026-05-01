package org.minidns.idna;

import java.net.IDN;
import org.minidns.dnsname.DnsName;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultIdnaTransformator implements IdnaTransformator {
    @Override // org.minidns.idna.IdnaTransformator
    public String toASCII(String str) {
        if (DnsName.ROOT.ace.equals(str)) {
            return DnsName.ROOT.ace;
        }
        return IDN.toASCII(str);
    }

    @Override // org.minidns.idna.IdnaTransformator
    public String toUnicode(String str) {
        return IDN.toUnicode(str);
    }
}
