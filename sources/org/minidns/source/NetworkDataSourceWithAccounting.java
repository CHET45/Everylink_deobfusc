package org.minidns.source;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import org.minidns.AbstractDnsClient;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsqueryresult.StandardDnsQueryResult;

/* JADX INFO: loaded from: classes4.dex */
public class NetworkDataSourceWithAccounting extends NetworkDataSource {
    private final AtomicInteger successfulQueries = new AtomicInteger();
    private final AtomicInteger responseSize = new AtomicInteger();
    private final AtomicInteger failedQueries = new AtomicInteger();
    private final AtomicInteger successfulUdpQueries = new AtomicInteger();
    private final AtomicInteger udpResponseSize = new AtomicInteger();
    private final AtomicInteger failedUdpQueries = new AtomicInteger();
    private final AtomicInteger successfulTcpQueries = new AtomicInteger();
    private final AtomicInteger tcpResponseSize = new AtomicInteger();
    private final AtomicInteger failedTcpQueries = new AtomicInteger();

    @Override // org.minidns.source.NetworkDataSource, org.minidns.source.AbstractDnsDataSource, org.minidns.source.DnsDataSource
    public StandardDnsQueryResult query(DnsMessage dnsMessage, InetAddress inetAddress, int i) throws Throwable {
        try {
            StandardDnsQueryResult standardDnsQueryResultQuery = super.query(dnsMessage, inetAddress, i);
            this.successfulQueries.incrementAndGet();
            this.responseSize.addAndGet(standardDnsQueryResultQuery.response.toArray().length);
            return standardDnsQueryResultQuery;
        } catch (IOException e) {
            this.failedQueries.incrementAndGet();
            throw e;
        }
    }

    @Override // org.minidns.source.NetworkDataSource
    protected DnsMessage queryUdp(DnsMessage dnsMessage, InetAddress inetAddress, int i) throws Throwable {
        try {
            DnsMessage dnsMessageQueryUdp = super.queryUdp(dnsMessage, inetAddress, i);
            this.successfulUdpQueries.incrementAndGet();
            this.udpResponseSize.addAndGet(dnsMessageQueryUdp.toArray().length);
            return dnsMessageQueryUdp;
        } catch (IOException e) {
            this.failedUdpQueries.incrementAndGet();
            throw e;
        }
    }

    @Override // org.minidns.source.NetworkDataSource
    protected DnsMessage queryTcp(DnsMessage dnsMessage, InetAddress inetAddress, int i) throws Throwable {
        try {
            DnsMessage dnsMessageQueryTcp = super.queryTcp(dnsMessage, inetAddress, i);
            this.successfulTcpQueries.incrementAndGet();
            this.tcpResponseSize.addAndGet(dnsMessageQueryTcp.toArray().length);
            return dnsMessageQueryTcp;
        } catch (IOException e) {
            this.failedTcpQueries.incrementAndGet();
            throw e;
        }
    }

    public Stats getStats() {
        return new Stats();
    }

    public static NetworkDataSourceWithAccounting from(AbstractDnsClient abstractDnsClient) {
        DnsDataSource dataSource = abstractDnsClient.getDataSource();
        if (dataSource instanceof NetworkDataSourceWithAccounting) {
            return (NetworkDataSourceWithAccounting) dataSource;
        }
        return null;
    }

    public static final class Stats {
        public final int averageResponseSize;
        public final int averageTcpResponseSize;
        public final int averageUdpResponseSize;
        public final int failedQueries;
        public final int failedTcpQueries;
        public final int failedUdpQueries;
        public final int responseSize;
        private String stringCache;
        public final int successfulQueries;
        public final int successfulTcpQueries;
        public final int successfulUdpQueries;
        public final int tcpResponseSize;
        public final int udpResponseSize;

        private Stats(NetworkDataSourceWithAccounting networkDataSourceWithAccounting) {
            int i = networkDataSourceWithAccounting.successfulQueries.get();
            this.successfulQueries = i;
            int i2 = networkDataSourceWithAccounting.responseSize.get();
            this.responseSize = i2;
            this.failedQueries = networkDataSourceWithAccounting.failedQueries.get();
            int i3 = networkDataSourceWithAccounting.successfulUdpQueries.get();
            this.successfulUdpQueries = i3;
            int i4 = networkDataSourceWithAccounting.udpResponseSize.get();
            this.udpResponseSize = i4;
            this.failedUdpQueries = networkDataSourceWithAccounting.failedUdpQueries.get();
            int i5 = networkDataSourceWithAccounting.successfulTcpQueries.get();
            this.successfulTcpQueries = i5;
            int i6 = networkDataSourceWithAccounting.tcpResponseSize.get();
            this.tcpResponseSize = i6;
            this.failedTcpQueries = networkDataSourceWithAccounting.failedTcpQueries.get();
            this.averageResponseSize = i > 0 ? i2 / i : 0;
            this.averageUdpResponseSize = i3 > 0 ? i4 / i3 : 0;
            this.averageTcpResponseSize = i5 > 0 ? i6 / i5 : 0;
        }

        public String toString() {
            String str = this.stringCache;
            if (str != null) {
                return str;
            }
            StringBuilder sb = new StringBuilder("Stats\t# Successful\t# Failed\tResp. Size\tAvg. Resp. Size\nTotal\t");
            sb.append(toString(this.successfulQueries)).append('\t').append(toString(this.failedQueries)).append('\t').append(toString(this.responseSize)).append('\t').append(toString(this.averageResponseSize)).append("\nUDP\t");
            sb.append(toString(this.successfulUdpQueries)).append('\t').append(toString(this.failedUdpQueries)).append('\t').append(toString(this.udpResponseSize)).append('\t').append(toString(this.averageUdpResponseSize)).append("\nTCP\t");
            sb.append(toString(this.successfulTcpQueries)).append('\t').append(toString(this.failedTcpQueries)).append('\t').append(toString(this.tcpResponseSize)).append('\t').append(toString(this.averageTcpResponseSize)).append('\n');
            String string = sb.toString();
            this.stringCache = string;
            return string;
        }

        private static String toString(int i) {
            return String.format(Locale.US, "%,09d", Integer.valueOf(i));
        }
    }
}
