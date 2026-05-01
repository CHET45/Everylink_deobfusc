package org.minidns.dnssec;

import java.util.Collections;
import java.util.Set;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.record.RRSIG;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class DnssecQueryResult {
    public final DnsQueryResult dnsQueryResult;
    private final Set<DnssecUnverifiedReason> dnssecUnverifiedReasons;
    private final Set<Record<RRSIG>> signatures;
    public final DnsMessage synthesizedResponse;

    DnssecQueryResult(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult, Set<Record<RRSIG>> set, Set<DnssecUnverifiedReason> set2) {
        this.synthesizedResponse = dnsMessage;
        this.dnsQueryResult = dnsQueryResult;
        this.signatures = Collections.unmodifiableSet(set);
        if (set2 == null) {
            this.dnssecUnverifiedReasons = Collections.emptySet();
        } else {
            this.dnssecUnverifiedReasons = Collections.unmodifiableSet(set2);
        }
    }

    public boolean isAuthenticData() {
        return this.dnssecUnverifiedReasons.isEmpty();
    }

    public Set<Record<RRSIG>> getSignatures() {
        return this.signatures;
    }

    public Set<DnssecUnverifiedReason> getUnverifiedReasons() {
        return this.dnssecUnverifiedReasons;
    }
}
