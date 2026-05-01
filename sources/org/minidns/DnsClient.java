package org.minidns;

import com.github.houbb.heaven.constant.CharConst;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import org.minidns.AbstractDnsClient;
import org.minidns.MiniDnsException;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.dnsqueryresult.CachedDnsQueryResult;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.dnsserverlookup.AndroidUsingExec;
import org.minidns.dnsserverlookup.AndroidUsingReflection;
import org.minidns.dnsserverlookup.DnsServerLookupMechanism;
import org.minidns.dnsserverlookup.UnixUsingEtcResolvConf;
import org.minidns.record.Record;
import org.minidns.util.CollectionsUtil;
import org.minidns.util.InetAddressUtil;
import org.minidns.util.MultipleIoException;

/* JADX INFO: loaded from: classes4.dex */
public class DnsClient extends AbstractDnsClient {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final List<DnsServerLookupMechanism> LOOKUP_MECHANISMS = new CopyOnWriteArrayList();
    static final Set<Inet4Address> STATIC_IPV4_DNS_SERVERS;
    static final Set<Inet6Address> STATIC_IPV6_DNS_SERVERS;
    private static final Set<String> blacklistedDnsServers;
    private boolean askForDnssec;
    private boolean disableResultFilter;
    private final Set<InetAddress> nonRaServers;
    private boolean useHardcodedDnsServers;

    static {
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
        STATIC_IPV4_DNS_SERVERS = copyOnWriteArraySet;
        STATIC_IPV6_DNS_SERVERS = new CopyOnWriteArraySet();
        addDnsServerLookupMechanism(AndroidUsingExec.INSTANCE);
        addDnsServerLookupMechanism(AndroidUsingReflection.INSTANCE);
        addDnsServerLookupMechanism(UnixUsingEtcResolvConf.INSTANCE);
        try {
            copyOnWriteArraySet.add(InetAddressUtil.ipv4From("8.8.8.8"));
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Could not add static IPv4 DNS Server", (Throwable) e);
        }
        try {
            STATIC_IPV6_DNS_SERVERS.add(InetAddressUtil.ipv6From("[2001:4860:4860::8888]"));
        } catch (IllegalArgumentException e2) {
            LOGGER.log(Level.WARNING, "Could not add static IPv6 DNS Server", (Throwable) e2);
        }
        blacklistedDnsServers = Collections.newSetFromMap(new ConcurrentHashMap(4));
    }

    public DnsClient() {
        this.nonRaServers = Collections.newSetFromMap(new ConcurrentHashMap(4));
        this.askForDnssec = false;
        this.disableResultFilter = false;
        this.useHardcodedDnsServers = true;
    }

    public DnsClient(DnsCache dnsCache) {
        super(dnsCache);
        this.nonRaServers = Collections.newSetFromMap(new ConcurrentHashMap(4));
        this.askForDnssec = false;
        this.disableResultFilter = false;
        this.useHardcodedDnsServers = true;
    }

    @Override // org.minidns.AbstractDnsClient
    protected DnsMessage.Builder newQuestion(DnsMessage.Builder builder) {
        builder.setRecursionDesired(true);
        builder.getEdnsBuilder().setUdpPayloadSize(this.dataSource.getUdpPayloadSize()).setDnssecOk(this.askForDnssec);
        return builder;
    }

    private List<InetAddress> getServerAddresses() {
        InetAddress randomHardcodedIpv4DnsServer;
        InetAddress randomHarcodedIpv6DnsServer;
        List<InetAddress> listFindDnsAddresses = findDnsAddresses();
        if (this.useHardcodedDnsServers) {
            int i = C50651.$SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[this.ipVersionSetting.ordinal()];
            if (i == 1) {
                randomHardcodedIpv4DnsServer = getRandomHardcodedIpv4DnsServer();
                randomHarcodedIpv6DnsServer = getRandomHarcodedIpv6DnsServer();
            } else if (i != 2) {
                randomHarcodedIpv6DnsServer = null;
                if (i == 3) {
                    randomHardcodedIpv4DnsServer = getRandomHardcodedIpv4DnsServer();
                } else if (i == 4) {
                    randomHardcodedIpv4DnsServer = getRandomHarcodedIpv6DnsServer();
                } else {
                    throw new AssertionError("Unknown ipVersionSetting: " + this.ipVersionSetting);
                }
            } else {
                randomHardcodedIpv4DnsServer = getRandomHarcodedIpv6DnsServer();
                randomHarcodedIpv6DnsServer = getRandomHardcodedIpv4DnsServer();
            }
            listFindDnsAddresses.add(randomHardcodedIpv4DnsServer);
            if (randomHarcodedIpv6DnsServer != null) {
                listFindDnsAddresses.add(randomHarcodedIpv6DnsServer);
            }
        }
        return listFindDnsAddresses;
    }

    @Override // org.minidns.AbstractDnsClient
    public DnsQueryResult query(DnsMessage.Builder builder) throws IOException {
        int i;
        DnsMessage dnsMessageBuild = newQuestion(builder).build();
        CachedDnsQueryResult cachedDnsQueryResult = this.cache == null ? null : this.cache.get(dnsMessageBuild);
        if (cachedDnsQueryResult != null) {
            return cachedDnsQueryResult;
        }
        List<InetAddress> serverAddresses = getServerAddresses();
        ArrayList arrayList = new ArrayList(serverAddresses.size());
        for (InetAddress inetAddress : serverAddresses) {
            if (this.nonRaServers.contains(inetAddress)) {
                LOGGER.finer("Skipping " + inetAddress + " because it was marked as \"recursion not available\"");
            } else {
                try {
                    DnsQueryResult dnsQueryResultQuery = query(dnsMessageBuild, inetAddress);
                    DnsMessage dnsMessage = dnsQueryResultQuery.response;
                    if (!dnsMessage.recursionAvailable) {
                        if (this.nonRaServers.add(inetAddress)) {
                            LOGGER.warning("The DNS server " + inetAddress + " returned a response without the \"recursion available\" (RA) flag set. This likely indicates a misconfiguration because the server is not suitable for DNS resolution");
                        }
                    } else {
                        if (this.disableResultFilter || (i = C50651.$SwitchMap$org$minidns$dnsmessage$DnsMessage$RESPONSE_CODE[dnsMessage.responseCode.ordinal()]) == 1 || i == 2) {
                            return dnsQueryResultQuery;
                        }
                        String str = "Response from " + inetAddress + " asked for " + dnsMessageBuild.getQuestion() + " with error code: " + dnsMessage.responseCode + CharConst.DOT;
                        if (!LOGGER.isLoggable(Level.FINE)) {
                            str = str + "\n" + dnsMessage;
                        }
                        LOGGER.warning(str);
                        arrayList.add(new MiniDnsException.ErrorResponseException(dnsMessageBuild, dnsQueryResultQuery));
                    }
                } catch (IOException e) {
                    arrayList.add(e);
                }
            }
        }
        MultipleIoException.throwIfRequired(arrayList);
        throw new MiniDnsException.NoQueryPossibleException(dnsMessageBuild);
    }

    /* JADX INFO: renamed from: org.minidns.DnsClient$1 */
    static /* synthetic */ class C50651 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting;
        static final /* synthetic */ int[] $SwitchMap$org$minidns$dnsmessage$DnsMessage$RESPONSE_CODE;

        static {
            int[] iArr = new int[DnsMessage.RESPONSE_CODE.values().length];
            $SwitchMap$org$minidns$dnsmessage$DnsMessage$RESPONSE_CODE = iArr;
            try {
                iArr[DnsMessage.RESPONSE_CODE.NO_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$minidns$dnsmessage$DnsMessage$RESPONSE_CODE[DnsMessage.RESPONSE_CODE.NX_DOMAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[AbstractDnsClient.IpVersionSetting.values().length];
            $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting = iArr2;
            try {
                iArr2[AbstractDnsClient.IpVersionSetting.v4v6.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v6v4.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v4only.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v6only.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    @Override // org.minidns.AbstractDnsClient
    protected MiniDnsFuture<DnsQueryResult, IOException> queryAsync(DnsMessage.Builder builder) {
        DnsMessage dnsMessageBuild = newQuestion(builder).build();
        CachedDnsQueryResult cachedDnsQueryResult = this.cache == null ? null : this.cache.get(dnsMessageBuild);
        if (cachedDnsQueryResult != null) {
            return MiniDnsFuture.from(cachedDnsQueryResult);
        }
        List<InetAddress> serverAddresses = getServerAddresses();
        Iterator<InetAddress> it = serverAddresses.iterator();
        while (it.hasNext()) {
            InetAddress next = it.next();
            if (this.nonRaServers.contains(next)) {
                it.remove();
                LOGGER.finer("Skipping " + next + " because it was marked as \"recursion not available\"");
            }
        }
        ArrayList arrayList = new ArrayList(serverAddresses.size());
        Iterator<InetAddress> it2 = serverAddresses.iterator();
        while (it2.hasNext()) {
            arrayList.add(queryAsync(dnsMessageBuild, it2.next()));
        }
        return MiniDnsFuture.anySuccessfulOf(arrayList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0026, code lost:
    
        r4 = r2.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0030, code lost:
    
        if (r4.hasNext() == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
    
        r5 = r4.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x003e, code lost:
    
        if (org.minidns.util.InetAddressUtil.isIpAddress(r5) != false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0040, code lost:
    
        org.minidns.DnsClient.LOGGER.warning("The DNS server lookup mechanism '" + r3.getName() + "' returned an invalid non-IP address result: '" + r5 + com.github.houbb.heaven.constant.PunctuationConst.SINGLE_QUOTES);
        r4.remove();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006e, code lost:
    
        if (org.minidns.DnsClient.blacklistedDnsServers.contains(r5) == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0070, code lost:
    
        org.minidns.DnsClient.LOGGER.fine("The DNS server lookup mechanism '" + r3.getName() + "' returned a blacklisted result: '" + r5 + com.github.houbb.heaven.constant.PunctuationConst.SINGLE_QUOTES);
        r4.remove();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x009c, code lost:
    
        if (r2.isEmpty() != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List<java.lang.String> findDNS() {
        /*
            java.util.List<org.minidns.dnsserverlookup.DnsServerLookupMechanism> r0 = org.minidns.DnsClient.LOOKUP_MECHANISMS
            java.util.Iterator r0 = r0.iterator()
            r1 = 0
        L7:
            r2 = r1
        L8:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto Lbd
            java.lang.Object r3 = r0.next()
            org.minidns.dnsserverlookup.DnsServerLookupMechanism r3 = (org.minidns.dnsserverlookup.DnsServerLookupMechanism) r3
            java.util.List r2 = r3.getDnsServerAddresses()     // Catch: java.lang.SecurityException -> L19
            goto L23
        L19:
            r4 = move-exception
            java.util.logging.Logger r5 = org.minidns.DnsClient.LOGGER
            java.util.logging.Level r6 = java.util.logging.Level.WARNING
            java.lang.String r7 = "Could not lookup DNS server"
            r5.log(r6, r7, r4)
        L23:
            if (r2 != 0) goto L26
            goto L8
        L26:
            java.util.Iterator r4 = r2.iterator()
        L2a:
            boolean r5 = r4.hasNext()
            java.lang.String r6 = "The DNS server lookup mechanism '"
            if (r5 == 0) goto L98
            java.lang.Object r5 = r4.next()
            java.lang.String r5 = (java.lang.String) r5
            boolean r7 = org.minidns.util.InetAddressUtil.isIpAddress(r5)
            java.lang.String r8 = "'"
            if (r7 != 0) goto L68
            java.util.logging.Logger r7 = org.minidns.DnsClient.LOGGER
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>(r6)
            java.lang.String r6 = r3.getName()
            java.lang.StringBuilder r6 = r9.append(r6)
            java.lang.String r9 = "' returned an invalid non-IP address result: '"
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            r7.warning(r5)
            r4.remove()
            goto L2a
        L68:
            java.util.Set<java.lang.String> r7 = org.minidns.DnsClient.blacklistedDnsServers
            boolean r7 = r7.contains(r5)
            if (r7 == 0) goto L2a
            java.util.logging.Logger r7 = org.minidns.DnsClient.LOGGER
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>(r6)
            java.lang.String r6 = r3.getName()
            java.lang.StringBuilder r6 = r9.append(r6)
            java.lang.String r9 = "' returned a blacklisted result: '"
            java.lang.StringBuilder r6 = r6.append(r9)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            r7.fine(r5)
            r4.remove()
            goto L2a
        L98:
            boolean r4 = r2.isEmpty()
            if (r4 != 0) goto L9f
            goto Lbd
        L9f:
            java.util.logging.Logger r2 = org.minidns.DnsClient.LOGGER
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r6)
            java.lang.String r3 = r3.getName()
            java.lang.StringBuilder r3 = r4.append(r3)
            java.lang.String r4 = "' returned not a single valid IP address after sanitazion"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.warning(r3)
            goto L7
        Lbd:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.minidns.DnsClient.findDNS():java.util.List");
    }

    public static List<InetAddress> findDnsAddresses() {
        List<String> listFindDNS = findDNS();
        if (listFindDNS == null) {
            return new ArrayList();
        }
        AbstractDnsClient.IpVersionSetting ipVersionSetting = DEFAULT_IP_VERSION_SETTING;
        ArrayList arrayList = ipVersionSetting.f2043v4 ? new ArrayList(listFindDNS.size()) : null;
        ArrayList arrayList2 = ipVersionSetting.f2044v6 ? new ArrayList(listFindDNS.size()) : null;
        int i = 0;
        for (String str : listFindDNS) {
            try {
                InetAddress byName = InetAddress.getByName(str);
                if (byName instanceof Inet4Address) {
                    if (ipVersionSetting.f2043v4) {
                        arrayList.add((Inet4Address) byName);
                        i++;
                    }
                } else if (byName instanceof Inet6Address) {
                    if (ipVersionSetting.f2044v6) {
                        arrayList2.add((Inet6Address) byName);
                        i++;
                    }
                } else {
                    throw new AssertionError("The address '" + byName + "' is neither of type Inet(4|6)Address");
                }
            } catch (UnknownHostException e) {
                LOGGER.log(Level.SEVERE, "Could not transform '" + str + "' to InetAddress", (Throwable) e);
            }
        }
        ArrayList arrayList3 = new ArrayList(i);
        int i2 = C50651.$SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[ipVersionSetting.ordinal()];
        if (i2 == 1) {
            arrayList3.addAll(arrayList);
            arrayList3.addAll(arrayList2);
        } else if (i2 == 2) {
            arrayList3.addAll(arrayList2);
            arrayList3.addAll(arrayList);
        } else if (i2 == 3) {
            arrayList3.addAll(arrayList);
        } else if (i2 == 4) {
            arrayList3.addAll(arrayList2);
        }
        return arrayList3;
    }

    public static void addDnsServerLookupMechanism(DnsServerLookupMechanism dnsServerLookupMechanism) {
        if (!dnsServerLookupMechanism.isAvailable()) {
            LOGGER.fine("Not adding " + dnsServerLookupMechanism.getName() + " as it is not available.");
            return;
        }
        List<DnsServerLookupMechanism> list = LOOKUP_MECHANISMS;
        synchronized (list) {
            ArrayList arrayList = new ArrayList(list.size() + 1);
            arrayList.addAll(list);
            arrayList.add(dnsServerLookupMechanism);
            Collections.sort(arrayList);
            list.clear();
            list.addAll(arrayList);
        }
    }

    public static boolean removeDNSServerLookupMechanism(DnsServerLookupMechanism dnsServerLookupMechanism) {
        boolean zRemove;
        List<DnsServerLookupMechanism> list = LOOKUP_MECHANISMS;
        synchronized (list) {
            zRemove = list.remove(dnsServerLookupMechanism);
        }
        return zRemove;
    }

    public static boolean addBlacklistedDnsServer(String str) {
        return blacklistedDnsServers.add(str);
    }

    public static boolean removeBlacklistedDnsServer(String str) {
        return blacklistedDnsServers.remove(str);
    }

    public boolean isAskForDnssec() {
        return this.askForDnssec;
    }

    public void setAskForDnssec(boolean z) {
        this.askForDnssec = z;
    }

    public boolean isDisableResultFilter() {
        return this.disableResultFilter;
    }

    public void setDisableResultFilter(boolean z) {
        this.disableResultFilter = z;
    }

    public boolean isUseHardcodedDnsServersEnabled() {
        return this.useHardcodedDnsServers;
    }

    public void setUseHardcodedDnsServers(boolean z) {
        this.useHardcodedDnsServers = z;
    }

    public InetAddress getRandomHardcodedIpv4DnsServer() {
        return (InetAddress) CollectionsUtil.getRandomFrom(STATIC_IPV4_DNS_SERVERS, this.insecureRandom);
    }

    public InetAddress getRandomHarcodedIpv6DnsServer() {
        return (InetAddress) CollectionsUtil.getRandomFrom(STATIC_IPV6_DNS_SERVERS, this.insecureRandom);
    }

    private static Question getReverseIpLookupQuestionFor(DnsName dnsName) {
        return new Question(dnsName, Record.TYPE.PTR);
    }

    public static Question getReverseIpLookupQuestionFor(Inet4Address inet4Address) {
        return getReverseIpLookupQuestionFor(DnsName.from(InetAddressUtil.reverseIpAddressOf(inet4Address), DnsName.IN_ADDR_ARPA));
    }

    public static Question getReverseIpLookupQuestionFor(Inet6Address inet6Address) {
        return getReverseIpLookupQuestionFor(DnsName.from(InetAddressUtil.reverseIpAddressOf(inet6Address), DnsName.IP6_ARPA));
    }

    public static Question getReverseIpLookupQuestionFor(InetAddress inetAddress) {
        if (inetAddress instanceof Inet4Address) {
            return getReverseIpLookupQuestionFor((Inet4Address) inetAddress);
        }
        if (inetAddress instanceof Inet6Address) {
            return getReverseIpLookupQuestionFor((Inet6Address) inetAddress);
        }
        throw new IllegalArgumentException("The provided inetAddress '" + inetAddress + "' is neither of type Inet4Address nor Inet6Address");
    }
}
