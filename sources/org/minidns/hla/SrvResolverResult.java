package org.minidns.hla;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.minidns.AbstractDnsClient;
import org.minidns.MiniDnsException;
import org.minidns.dnsname.DnsName;
import org.minidns.hla.srv.SrvServiceProto;
import org.minidns.record.AAAA;
import org.minidns.record.C5085A;
import org.minidns.record.InternetAddressRR;
import org.minidns.record.SRV;
import org.minidns.util.SrvUtil;

/* JADX INFO: loaded from: classes4.dex */
public class SrvResolverResult extends ResolverResult<SRV> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final AbstractDnsClient.IpVersionSetting ipVersion;
    private final ResolverApi resolver;
    private List<ResolvedSrvRecord> sortedSrvResolvedAddresses;
    private final SrvServiceProto srvServiceProto;

    SrvResolverResult(ResolverResult<SRV> resolverResult, SrvServiceProto srvServiceProto, ResolverApi resolverApi) throws MiniDnsException.NullResultException {
        super(resolverResult.question, resolverResult.result, resolverResult.unverifiedReasons);
        this.resolver = resolverApi;
        this.ipVersion = resolverApi.getClient().getPreferedIpVersion();
        this.srvServiceProto = srvServiceProto;
    }

    public List<ResolvedSrvRecord> getSortedSrvResolvedAddresses() throws IOException {
        ResolverResult resolverResult;
        ResolverResult resolverResult2;
        List<ResolvedSrvRecord> list = this.sortedSrvResolvedAddresses;
        if (list != null) {
            return list;
        }
        throwIseIfErrorResponse();
        if (isServiceDecidedlyNotAvailableAtThisDomain()) {
            return null;
        }
        List<SRV> listSortSrvRecords = SrvUtil.sortSrvRecords(getAnswers());
        ArrayList arrayList = new ArrayList(listSortSrvRecords.size());
        for (SRV srv : listSortSrvRecords) {
            Set setEmptySet = Collections.emptySet();
            if (this.ipVersion.f2043v4) {
                ResolverResult resolverResultResolve = this.resolver.resolve(srv.target, C5085A.class);
                if (resolverResultResolve.wasSuccessful() && !resolverResultResolve.hasUnverifiedReasons()) {
                    setEmptySet = resolverResultResolve.getAnswers();
                }
                resolverResult = resolverResultResolve;
            } else {
                resolverResult = null;
            }
            Set setEmptySet2 = Collections.emptySet();
            if (this.ipVersion.f2044v6) {
                ResolverResult resolverResultResolve2 = this.resolver.resolve(srv.target, AAAA.class);
                if (resolverResultResolve2.wasSuccessful() && !resolverResultResolve2.hasUnverifiedReasons()) {
                    setEmptySet2 = resolverResultResolve2.getAnswers();
                }
                resolverResult2 = resolverResultResolve2;
            } else {
                resolverResult2 = null;
            }
            if (!setEmptySet.isEmpty() || !setEmptySet2.isEmpty()) {
                ArrayList arrayList2 = new ArrayList(setEmptySet.size() + setEmptySet2.size());
                int i = C50811.$SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[this.ipVersion.ordinal()];
                if (i == 1) {
                    arrayList2.addAll(setEmptySet);
                } else if (i == 2) {
                    arrayList2.addAll(setEmptySet2);
                } else if (i == 3) {
                    arrayList2.addAll(setEmptySet);
                    arrayList2.addAll(setEmptySet2);
                } else if (i == 4) {
                    arrayList2.addAll(setEmptySet2);
                    arrayList2.addAll(setEmptySet);
                }
                arrayList.add(new ResolvedSrvRecord(this.question.name, this.srvServiceProto, srv, arrayList2, resolverResult, resolverResult2, null));
            }
        }
        this.sortedSrvResolvedAddresses = arrayList;
        return arrayList;
    }

    /* JADX INFO: renamed from: org.minidns.hla.SrvResolverResult$1 */
    static /* synthetic */ class C50811 {
        static final /* synthetic */ int[] $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting;

        static {
            int[] iArr = new int[AbstractDnsClient.IpVersionSetting.values().length];
            $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting = iArr;
            try {
                iArr[AbstractDnsClient.IpVersionSetting.v4only.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v6only.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v4v6.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$minidns$AbstractDnsClient$IpVersionSetting[AbstractDnsClient.IpVersionSetting.v6v4.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public boolean isServiceDecidedlyNotAvailableAtThisDomain() {
        if (getAnswers().size() != 1) {
            return false;
        }
        return !r0.iterator().next().isServiceAvailable();
    }

    public static final class ResolvedSrvRecord {
        public final ResolverResult<C5085A> aRecordsResult;
        public final ResolverResult<AAAA> aaaaRecordsResult;
        public final List<InternetAddressRR<? extends InetAddress>> addresses;
        public final DnsName name;
        public final int port;
        public final SRV srv;
        public final SrvServiceProto srvServiceProto;

        /* synthetic */ ResolvedSrvRecord(DnsName dnsName, SrvServiceProto srvServiceProto, SRV srv, List list, ResolverResult resolverResult, ResolverResult resolverResult2, C50811 c50811) {
            this(dnsName, srvServiceProto, srv, list, resolverResult, resolverResult2);
        }

        private ResolvedSrvRecord(DnsName dnsName, SrvServiceProto srvServiceProto, SRV srv, List<InternetAddressRR<? extends InetAddress>> list, ResolverResult<C5085A> resolverResult, ResolverResult<AAAA> resolverResult2) {
            this.name = dnsName;
            this.srvServiceProto = srvServiceProto;
            this.srv = srv;
            this.addresses = Collections.unmodifiableList(list);
            this.port = srv.port;
            this.aRecordsResult = resolverResult;
            this.aaaaRecordsResult = resolverResult2;
        }
    }

    @SafeVarargs
    public static List<ResolvedSrvRecord> sortMultiple(Collection<ResolvedSrvRecord>... collectionArr) {
        int size = 0;
        for (Collection<ResolvedSrvRecord> collection : collectionArr) {
            if (collection != null) {
                size += collection.size();
            }
        }
        ArrayList arrayList = new ArrayList(size);
        IdentityHashMap identityHashMap = new IdentityHashMap(size);
        for (Collection<ResolvedSrvRecord> collection2 : collectionArr) {
            if (collection2 != null) {
                for (ResolvedSrvRecord resolvedSrvRecord : collection2) {
                    arrayList.add(resolvedSrvRecord.srv);
                    identityHashMap.put(resolvedSrvRecord.srv, resolvedSrvRecord);
                }
            }
        }
        List<SRV> listSortSrvRecords = SrvUtil.sortSrvRecords(arrayList);
        ArrayList arrayList2 = new ArrayList(size);
        Iterator<SRV> it = listSortSrvRecords.iterator();
        while (it.hasNext()) {
            arrayList2.add((ResolvedSrvRecord) identityHashMap.get(it.next()));
        }
        return arrayList2;
    }
}
