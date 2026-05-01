package org.minidns.dnsserverlookup;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractDnsServerLookupMechanism implements DnsServerLookupMechanism {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDnsServerLookupMechanism.class.getName());
    private final String name;
    private final int priority;

    @Override // org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public abstract List<String> getDnsServerAddresses();

    protected AbstractDnsServerLookupMechanism(String str, int i) {
        this.name = str;
        this.priority = i;
    }

    @Override // org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public final String getName() {
        return this.name;
    }

    @Override // org.minidns.dnsserverlookup.DnsServerLookupMechanism
    public final int getPriority() {
        return this.priority;
    }

    @Override // java.lang.Comparable
    public final int compareTo(DnsServerLookupMechanism dnsServerLookupMechanism) {
        return Integer.compare(getPriority(), dnsServerLookupMechanism.getPriority());
    }

    protected static List<String> toListOfStrings(Collection<? extends InetAddress> collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<? extends InetAddress> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getHostAddress());
        }
        return arrayList;
    }
}
