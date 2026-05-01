package org.minidns.hla.srv;

import org.minidns.dnslabel.DnsLabel;

/* JADX INFO: loaded from: classes4.dex */
public class SrvServiceProto {
    public final DnsLabel proto;
    public final DnsLabel service;

    public SrvServiceProto(DnsLabel dnsLabel, DnsLabel dnsLabel2) {
        this.service = dnsLabel;
        this.proto = dnsLabel2;
    }
}
