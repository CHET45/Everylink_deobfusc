package org.minidns.source;

import java.io.IOException;
import java.net.InetAddress;
import org.minidns.DnsCache;
import org.minidns.MiniDnsFuture;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.source.DnsDataSource;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractDnsDataSource implements DnsDataSource {
    private DnsCache cache;
    protected int udpPayloadSize = 1024;
    protected int timeout = 5000;
    private QueryMode queryMode = QueryMode.dontCare;

    public enum QueryMode {
        dontCare,
        udpTcp,
        tcp
    }

    @Override // org.minidns.source.DnsDataSource
    public abstract DnsQueryResult query(DnsMessage dnsMessage, InetAddress inetAddress, int i) throws IOException;

    @Override // org.minidns.source.DnsDataSource
    public MiniDnsFuture<DnsQueryResult, IOException> queryAsync(DnsMessage dnsMessage, InetAddress inetAddress, int i, DnsDataSource.OnResponseCallback onResponseCallback) {
        MiniDnsFuture.InternalMiniDnsFuture internalMiniDnsFuture = new MiniDnsFuture.InternalMiniDnsFuture();
        try {
            internalMiniDnsFuture.setResult(query(dnsMessage, inetAddress, i));
            return internalMiniDnsFuture;
        } catch (IOException e) {
            internalMiniDnsFuture.setException(e);
            return internalMiniDnsFuture;
        }
    }

    @Override // org.minidns.source.DnsDataSource
    public int getTimeout() {
        return this.timeout;
    }

    @Override // org.minidns.source.DnsDataSource
    public void setTimeout(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Timeout must be greater than zero");
        }
        this.timeout = i;
    }

    @Override // org.minidns.source.DnsDataSource
    public int getUdpPayloadSize() {
        return this.udpPayloadSize;
    }

    public void setUdpPayloadSize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("UDP payload size must be greater than zero");
        }
        this.udpPayloadSize = i;
    }

    protected final void cacheResult(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult) {
        DnsCache dnsCache = this.cache;
        if (dnsCache == null) {
            return;
        }
        dnsCache.put(dnsMessage, dnsQueryResult);
    }

    public void setQueryMode(QueryMode queryMode) {
        if (queryMode == null) {
            throw new IllegalArgumentException();
        }
        this.queryMode = queryMode;
    }

    public QueryMode getQueryMode() {
        return this.queryMode;
    }
}
