package org.minidns.dnsqueryresult;

import java.net.InetAddress;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsqueryresult.DnsQueryResult;

/* JADX INFO: loaded from: classes4.dex */
public class StandardDnsQueryResult extends DnsQueryResult {
    public final int port;
    public final InetAddress serverAddress;

    public StandardDnsQueryResult(InetAddress inetAddress, int i, DnsQueryResult.QueryMethod queryMethod, DnsMessage dnsMessage, DnsMessage dnsMessage2) {
        super(queryMethod, dnsMessage, dnsMessage2);
        this.serverAddress = inetAddress;
        this.port = i;
    }
}
