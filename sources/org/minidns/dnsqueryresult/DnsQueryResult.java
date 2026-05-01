package org.minidns.dnsqueryresult;

import org.minidns.dnsmessage.DnsMessage;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DnsQueryResult {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final DnsMessage query;
    public final QueryMethod queryMethod;
    public final DnsMessage response;

    public enum QueryMethod {
        udp,
        tcp,
        asyncUdp,
        asyncTcp,
        cachedDirect,
        cachedSynthesized,
        testWorld
    }

    protected DnsQueryResult(QueryMethod queryMethod, DnsMessage dnsMessage, DnsMessage dnsMessage2) {
        this.queryMethod = queryMethod;
        this.query = dnsMessage;
        this.response = dnsMessage2;
    }

    public String toString() {
        return this.response.toString();
    }

    public boolean wasSuccessful() {
        return this.response.responseCode == DnsMessage.RESPONSE_CODE.NO_ERROR;
    }
}
