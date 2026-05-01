package org.minidns.hla.srv;

import com.github.houbb.heaven.constant.PunctuationConst;
import org.minidns.dnslabel.DnsLabel;

/* JADX INFO: loaded from: classes4.dex */
public enum SrvProto {
    tcp,
    udp;

    public final DnsLabel dnsLabel = DnsLabel.from(PunctuationConst.UNDERLINE + name());

    SrvProto() {
    }
}
