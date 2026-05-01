package org.minidns.dnsqueryresult;

import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsqueryresult.DnsQueryResult;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CachedDnsQueryResult extends DnsQueryResult {
    protected final DnsQueryResult cachedDnsQueryResult;

    protected CachedDnsQueryResult(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult) {
        super(DnsQueryResult.QueryMethod.cachedDirect, dnsMessage, dnsQueryResult.response);
        this.cachedDnsQueryResult = dnsQueryResult;
    }

    protected CachedDnsQueryResult(DnsMessage dnsMessage, DnsMessage dnsMessage2, DnsQueryResult dnsQueryResult) {
        super(DnsQueryResult.QueryMethod.cachedSynthesized, dnsMessage, dnsMessage2);
        this.cachedDnsQueryResult = dnsQueryResult;
    }
}
