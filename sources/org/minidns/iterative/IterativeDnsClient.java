package org.minidns.iterative;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.minidns.AbstractDnsClient;
import org.minidns.DnsCache;
import org.minidns.constants.DnsRootServer;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.iterative.IterativeClientException;
import org.minidns.record.AAAA;
import org.minidns.record.C5085A;
import org.minidns.record.C5089NS;
import org.minidns.record.Data;
import org.minidns.record.RRWithTarget;
import org.minidns.record.Record;
import org.minidns.util.MultipleIoException;

/* JADX INFO: loaded from: classes4.dex */
public class IterativeDnsClient extends AbstractDnsClient {
    int maxSteps;

    public IterativeDnsClient() {
        this.maxSteps = 128;
    }

    public IterativeDnsClient(DnsCache dnsCache) {
        super(dnsCache);
        this.maxSteps = 128;
    }

    @Override // org.minidns.AbstractDnsClient
    protected DnsQueryResult query(DnsMessage.Builder builder) throws IOException {
        return queryRecursive(new ResolutionState(this), builder.build());
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0055 A[EDGE_INSN: B:28:0x0055->B:22:0x0055 BREAK  A[LOOP:1: B:14:0x0034->B:18:0x0044], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.net.InetAddress[] getTargets(java.util.Collection<? extends org.minidns.record.InternetAddressRR<? extends java.net.InetAddress>> r5, java.util.Collection<? extends org.minidns.record.InternetAddressRR<? extends java.net.InetAddress>> r6) {
        /*
            r0 = 2
            java.net.InetAddress[] r0 = new java.net.InetAddress[r0]
            java.util.Iterator r5 = r5.iterator()
        L7:
            boolean r1 = r5.hasNext()
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L30
            java.lang.Object r1 = r5.next()
            org.minidns.record.InternetAddressRR r1 = (org.minidns.record.InternetAddressRR) r1
            r4 = r0[r3]
            if (r4 != 0) goto L26
            java.net.InetAddress r4 = r1.getInetAddress()
            r0[r3] = r4
            boolean r4 = r6.isEmpty()
            if (r4 == 0) goto L26
            goto L7
        L26:
            r5 = r0[r2]
            if (r5 != 0) goto L30
            java.net.InetAddress r5 = r1.getInetAddress()
            r0[r2] = r5
        L30:
            java.util.Iterator r5 = r6.iterator()
        L34:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L55
            java.lang.Object r6 = r5.next()
            org.minidns.record.InternetAddressRR r6 = (org.minidns.record.InternetAddressRR) r6
            r1 = r0[r3]
            if (r1 != 0) goto L4b
            java.net.InetAddress r6 = r6.getInetAddress()
            r0[r3] = r6
            goto L34
        L4b:
            r5 = r0[r2]
            if (r5 != 0) goto L55
            java.net.InetAddress r5 = r6.getInetAddress()
            r0[r2] = r5
        L55:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.minidns.iterative.IterativeDnsClient.getTargets(java.util.Collection, java.util.Collection):java.net.InetAddress[]");
    }

    private DnsQueryResult queryRecursive(ResolutionState resolutionState, DnsMessage dnsMessage) throws IOException {
        InetAddress inetAddress;
        InetAddress inetAddress2;
        DnsName parent = dnsMessage.getQuestion().name.getParent();
        int i = C50821.$SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[this.ipVersionSetting.ordinal()];
        if (i == 1) {
            inetAddress = null;
            for (C5085A c5085a : getCachedIPv4NameserverAddressesFor(parent)) {
                if (inetAddress == null) {
                    inetAddress = c5085a.getInetAddress();
                } else {
                    inetAddress2 = c5085a.getInetAddress();
                    break;
                }
            }
            inetAddress2 = null;
        } else if (i == 2) {
            inetAddress = null;
            for (AAAA aaaa : getCachedIPv6NameserverAddressesFor(parent)) {
                if (inetAddress == null) {
                    inetAddress = aaaa.getInetAddress();
                } else {
                    inetAddress2 = aaaa.getInetAddress();
                    break;
                }
            }
            inetAddress2 = null;
        } else if (i == 3) {
            InetAddress[] targets = getTargets(getCachedIPv4NameserverAddressesFor(parent), getCachedIPv6NameserverAddressesFor(parent));
            inetAddress = targets[0];
            inetAddress2 = targets[1];
        } else if (i == 4) {
            InetAddress[] targets2 = getTargets(getCachedIPv6NameserverAddressesFor(parent), getCachedIPv4NameserverAddressesFor(parent));
            inetAddress = targets2[0];
            inetAddress2 = targets2[1];
        } else {
            throw new AssertionError();
        }
        if (inetAddress == null) {
            parent = DnsName.ROOT;
            int i2 = C50821.$SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[this.ipVersionSetting.ordinal()];
            if (i2 == 1) {
                inetAddress = DnsRootServer.getRandomIpv4RootServer(this.insecureRandom);
            } else if (i2 == 2) {
                inetAddress = DnsRootServer.getRandomIpv6RootServer(this.insecureRandom);
            } else if (i2 == 3) {
                inetAddress = DnsRootServer.getRandomIpv4RootServer(this.insecureRandom);
                inetAddress2 = DnsRootServer.getRandomIpv6RootServer(this.insecureRandom);
            } else if (i2 == 4) {
                inetAddress = DnsRootServer.getRandomIpv6RootServer(this.insecureRandom);
                inetAddress2 = DnsRootServer.getRandomIpv4RootServer(this.insecureRandom);
            }
        }
        LinkedList linkedList = new LinkedList();
        try {
            return queryRecursive(resolutionState, dnsMessage, inetAddress, parent);
        } catch (IOException e) {
            abortIfFatal(e);
            linkedList.add(e);
            if (inetAddress2 != null) {
                try {
                    return queryRecursive(resolutionState, dnsMessage, inetAddress2, parent);
                } catch (IOException e2) {
                    linkedList.add(e2);
                    MultipleIoException.throwIfRequired(linkedList);
                    return null;
                }
            }
            MultipleIoException.throwIfRequired(linkedList);
            return null;
        }
    }

    private DnsQueryResult queryRecursive(ResolutionState resolutionState, DnsMessage dnsMessage, InetAddress inetAddress, DnsName dnsName) throws IOException {
        IpResultSet ipResultSetResolveIpRecursive;
        resolutionState.recurse(inetAddress, dnsMessage);
        DnsQueryResult dnsQueryResultQuery = query(dnsMessage, inetAddress);
        DnsMessage dnsMessage2 = dnsQueryResultQuery.response;
        if (dnsMessage2.authoritativeAnswer) {
            return dnsQueryResultQuery;
        }
        if (this.cache != null) {
            this.cache.offer(dnsMessage, dnsQueryResultQuery, dnsName);
        }
        List<Record<? extends Data>> listCopyAuthority = dnsMessage2.copyAuthority();
        LinkedList linkedList = new LinkedList();
        Iterator<Record<? extends Data>> it = listCopyAuthority.iterator();
        while (it.hasNext()) {
            Record<E> recordIfPossibleAs = it.next().ifPossibleAs(C5089NS.class);
            if (recordIfPossibleAs == 0) {
                it.remove();
            } else {
                Iterator<InetAddress> it2 = searchAdditional(dnsMessage2, ((C5089NS) recordIfPossibleAs.payloadData).target).addresses.iterator();
                while (it2.hasNext()) {
                    try {
                        return queryRecursive(resolutionState, dnsMessage, it2.next(), recordIfPossibleAs.name);
                    } catch (IOException e) {
                        abortIfFatal(e);
                        LOGGER.log(Level.FINER, "Exception while recursing", (Throwable) e);
                        resolutionState.decrementSteps();
                        linkedList.add(e);
                        if (!it2.hasNext()) {
                            it.remove();
                        }
                    }
                }
            }
        }
        for (Record<? extends Data> record : listCopyAuthority) {
            Question question = dnsMessage.getQuestion();
            DnsName dnsName2 = ((C5089NS) record.payloadData).target;
            if (!question.name.equals(dnsName2) || (question.type != Record.TYPE.A && question.type != Record.TYPE.AAAA)) {
                try {
                    ipResultSetResolveIpRecursive = resolveIpRecursive(resolutionState, dnsName2);
                } catch (IOException e2) {
                    resolutionState.decrementSteps();
                    linkedList.add(e2);
                    ipResultSetResolveIpRecursive = null;
                }
                if (ipResultSetResolveIpRecursive == null) {
                    continue;
                } else {
                    Iterator<InetAddress> it3 = ipResultSetResolveIpRecursive.addresses.iterator();
                    while (it3.hasNext()) {
                        try {
                            return queryRecursive(resolutionState, dnsMessage, it3.next(), record.name);
                        } catch (IOException e3) {
                            resolutionState.decrementSteps();
                            linkedList.add(e3);
                        }
                    }
                }
            }
        }
        MultipleIoException.throwIfRequired(linkedList);
        throw new IterativeClientException.NotAuthoritativeNorGlueRrFound(dnsMessage, dnsQueryResultQuery, dnsName);
    }

    private IpResultSet resolveIpRecursive(ResolutionState resolutionState, DnsName dnsName) throws IOException {
        IpResultSet.Builder builderNewIpResultSetBuilder = newIpResultSetBuilder();
        if (this.ipVersionSetting.f2043v4) {
            Question question = new Question(dnsName, Record.TYPE.A);
            DnsQueryResult dnsQueryResultQueryRecursive = queryRecursive(resolutionState, getQueryFor(question));
            DnsMessage dnsMessage = dnsQueryResultQueryRecursive != null ? dnsQueryResultQueryRecursive.response : null;
            if (dnsMessage != null) {
                for (Record<? extends Data> record : dnsMessage.answerSection) {
                    if (!record.isAnswer(question)) {
                        if (record.type == Record.TYPE.CNAME && record.name.equals(dnsName)) {
                            return resolveIpRecursive(resolutionState, ((RRWithTarget) record.payloadData).target);
                        }
                    } else {
                        builderNewIpResultSetBuilder.ipv4Addresses.add(inetAddressFromRecord(dnsName.ace, (C5085A) record.payloadData));
                    }
                }
            }
        }
        if (this.ipVersionSetting.f2044v6) {
            Question question2 = new Question(dnsName, Record.TYPE.AAAA);
            DnsQueryResult dnsQueryResultQueryRecursive2 = queryRecursive(resolutionState, getQueryFor(question2));
            DnsMessage dnsMessage2 = dnsQueryResultQueryRecursive2 != null ? dnsQueryResultQueryRecursive2.response : null;
            if (dnsMessage2 != null) {
                for (Record<? extends Data> record2 : dnsMessage2.answerSection) {
                    if (!record2.isAnswer(question2)) {
                        if (record2.type == Record.TYPE.CNAME && record2.name.equals(dnsName)) {
                            return resolveIpRecursive(resolutionState, ((RRWithTarget) record2.payloadData).target);
                        }
                    } else {
                        builderNewIpResultSetBuilder.ipv6Addresses.add(inetAddressFromRecord(dnsName.ace, (AAAA) record2.payloadData));
                    }
                }
            }
        }
        return builderNewIpResultSetBuilder.build();
    }

    private IpResultSet searchAdditional(DnsMessage dnsMessage, DnsName dnsName) {
        IpResultSet.Builder builderNewIpResultSetBuilder = newIpResultSetBuilder();
        for (Record<? extends Data> record : dnsMessage.additionalSection) {
            if (record.name.equals(dnsName)) {
                int i = C50821.$SwitchMap$org$minidns$record$Record$TYPE[record.type.ordinal()];
                if (i == 1) {
                    builderNewIpResultSetBuilder.ipv4Addresses.add(inetAddressFromRecord(dnsName.ace, (C5085A) record.payloadData));
                } else if (i == 2) {
                    builderNewIpResultSetBuilder.ipv6Addresses.add(inetAddressFromRecord(dnsName.ace, (AAAA) record.payloadData));
                }
            }
        }
        return builderNewIpResultSetBuilder.build();
    }

    /* JADX INFO: renamed from: org.minidns.iterative.IterativeDnsClient$1 */
    static /* synthetic */ class C50821 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting;
        static final /* synthetic */ int[] $SwitchMap$org$minidns$record$Record$TYPE;

        static {
            int[] iArr = new int[Record.TYPE.values().length];
            $SwitchMap$org$minidns$record$Record$TYPE = iArr;
            try {
                iArr[Record.TYPE.A.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$minidns$record$Record$TYPE[Record.TYPE.AAAA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[AbstractDnsClient.IpVersionSetting.values().length];
            $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting = iArr2;
            try {
                iArr2[AbstractDnsClient.IpVersionSetting.v4only.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v6only.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v4v6.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v6v4.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private static InetAddress inetAddressFromRecord(String str, C5085A c5085a) {
        try {
            return InetAddress.getByAddress(str, c5085a.getIp());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private static InetAddress inetAddressFromRecord(String str, AAAA aaaa) {
        try {
            return InetAddress.getByAddress(str, aaaa.getIp());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<InetAddress> getRootServer(char c) {
        return getRootServer(c, DEFAULT_IP_VERSION_SETTING);
    }

    public static List<InetAddress> getRootServer(char c, AbstractDnsClient.IpVersionSetting ipVersionSetting) {
        Inet4Address ipv4RootServerById = DnsRootServer.getIpv4RootServerById(c);
        Inet6Address ipv6RootServerById = DnsRootServer.getIpv6RootServerById(c);
        ArrayList arrayList = new ArrayList(2);
        int i = C50821.$SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[ipVersionSetting.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    if (ipv4RootServerById != null) {
                        arrayList.add(ipv4RootServerById);
                    }
                    if (ipv6RootServerById != null) {
                        arrayList.add(ipv6RootServerById);
                    }
                } else if (i == 4) {
                    if (ipv6RootServerById != null) {
                        arrayList.add(ipv6RootServerById);
                    }
                    if (ipv4RootServerById != null) {
                        arrayList.add(ipv4RootServerById);
                    }
                }
            } else if (ipv6RootServerById != null) {
                arrayList.add(ipv6RootServerById);
            }
        } else if (ipv4RootServerById != null) {
            arrayList.add(ipv4RootServerById);
        }
        return arrayList;
    }

    @Override // org.minidns.AbstractDnsClient
    protected boolean isResponseCacheable(Question question, DnsQueryResult dnsQueryResult) {
        return dnsQueryResult.response.authoritativeAnswer;
    }

    @Override // org.minidns.AbstractDnsClient
    protected DnsMessage.Builder newQuestion(DnsMessage.Builder builder) {
        builder.setRecursionDesired(false);
        builder.getEdnsBuilder().setUdpPayloadSize(this.dataSource.getUdpPayloadSize());
        return builder;
    }

    private IpResultSet.Builder newIpResultSetBuilder() {
        return new IpResultSet.Builder(this.insecureRandom, null);
    }

    private static final class IpResultSet {
        final List<InetAddress> addresses;

        /* synthetic */ IpResultSet(List list, List list2, Random random, C50821 c50821) {
            this(list, list2, random);
        }

        private IpResultSet(List<InetAddress> list, List<InetAddress> list2, Random random) {
            int size;
            int i = C50821.$SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[IterativeDnsClient.DEFAULT_IP_VERSION_SETTING.ordinal()];
            if (i == 1) {
                size = list.size();
            } else if (i == 2) {
                size = list2.size();
            } else {
                size = list.size() + list2.size();
            }
            if (size != 0) {
                if (IterativeDnsClient.DEFAULT_IP_VERSION_SETTING.f2043v4) {
                    Collections.shuffle(list, random);
                }
                if (IterativeDnsClient.DEFAULT_IP_VERSION_SETTING.f2044v6) {
                    Collections.shuffle(list2, random);
                }
                ArrayList arrayList = new ArrayList(size);
                int i2 = C50821.$SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[IterativeDnsClient.DEFAULT_IP_VERSION_SETTING.ordinal()];
                if (i2 == 1) {
                    arrayList.addAll(list);
                } else if (i2 == 2) {
                    arrayList.addAll(list2);
                } else if (i2 == 3) {
                    arrayList.addAll(list);
                    arrayList.addAll(list2);
                } else if (i2 == 4) {
                    arrayList.addAll(list2);
                    arrayList.addAll(list);
                }
                this.addresses = Collections.unmodifiableList(arrayList);
                return;
            }
            this.addresses = Collections.emptyList();
        }

        private static final class Builder {
            private final List<InetAddress> ipv4Addresses;
            private final List<InetAddress> ipv6Addresses;
            private final Random random;

            /* synthetic */ Builder(Random random, C50821 c50821) {
                this(random);
            }

            private Builder(Random random) {
                this.ipv4Addresses = new ArrayList(8);
                this.ipv6Addresses = new ArrayList(8);
                this.random = random;
            }

            public IpResultSet build() {
                return new IpResultSet(this.ipv4Addresses, this.ipv6Addresses, this.random, null);
            }
        }
    }

    protected static void abortIfFatal(IOException iOException) throws IOException {
        if (iOException instanceof IterativeClientException.LoopDetected) {
            throw iOException;
        }
    }
}
