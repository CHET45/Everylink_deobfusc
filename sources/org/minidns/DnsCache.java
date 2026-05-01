package org.minidns;

import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsname.DnsName;
import org.minidns.dnsqueryresult.CachedDnsQueryResult;
import org.minidns.dnsqueryresult.DnsQueryResult;

/* JADX INFO: loaded from: classes4.dex */
public abstract class DnsCache {
    public static final int DEFAULT_CACHE_SIZE = 512;

    protected abstract CachedDnsQueryResult getNormalized(DnsMessage dnsMessage);

    public abstract void offer(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult, DnsName dnsName);

    protected abstract void putNormalized(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult);

    public final void put(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult) {
        putNormalized(dnsMessage.asNormalizedVersion(), dnsQueryResult);
    }

    public final CachedDnsQueryResult get(DnsMessage dnsMessage) {
        return getNormalized(dnsMessage.asNormalizedVersion());
    }
}
