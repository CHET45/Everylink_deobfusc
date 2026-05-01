package org.minidns.dnsserverlookup;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface DnsServerLookupMechanism extends Comparable<DnsServerLookupMechanism> {
    List<String> getDnsServerAddresses();

    String getName();

    int getPriority();

    boolean isAvailable();
}
