package org.minidns.hla.srv;

import com.github.houbb.heaven.constant.PunctuationConst;
import org.minidns.dnslabel.DnsLabel;

/* JADX INFO: loaded from: classes4.dex */
public enum SrvService {
    xmpp_client,
    xmpp_server,
    xmpps_client,
    xmpps_server;

    public final DnsLabel dnsLabel = DnsLabel.from(PunctuationConst.UNDERLINE + name().replaceAll(PunctuationConst.UNDERLINE, "-"));

    SrvService() {
    }
}
