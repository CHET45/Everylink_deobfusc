package org.minidns.hla;

import java.io.IOException;
import org.minidns.DnsCache;
import org.minidns.MiniDnsException;
import org.minidns.cache.LruCache;
import org.minidns.cache.MiniDnsCacheFactory;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.dnssec.DnssecClient;
import org.minidns.dnssec.DnssecQueryResult;
import org.minidns.iterative.ReliableDnsClient;
import org.minidns.record.Data;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class DnssecResolverApi extends ResolverApi {
    public static final DnssecResolverApi INSTANCE = new DnssecResolverApi();
    private final DnssecClient dnssecClient;
    private final DnssecClient iterativeOnlyDnssecClient;
    private final DnssecClient recursiveOnlyDnssecClient;

    public DnssecResolverApi() {
        this(new MiniDnsCacheFactory() { // from class: org.minidns.hla.DnssecResolverApi.1
            @Override // org.minidns.cache.MiniDnsCacheFactory
            public DnsCache newCache() {
                return new LruCache();
            }
        });
    }

    public DnssecResolverApi(MiniDnsCacheFactory miniDnsCacheFactory) {
        this(new DnssecClient(miniDnsCacheFactory.newCache()), miniDnsCacheFactory);
    }

    private DnssecResolverApi(DnssecClient dnssecClient, MiniDnsCacheFactory miniDnsCacheFactory) {
        super(dnssecClient);
        this.dnssecClient = dnssecClient;
        DnssecClient dnssecClient2 = new DnssecClient(miniDnsCacheFactory.newCache());
        this.iterativeOnlyDnssecClient = dnssecClient2;
        dnssecClient2.setMode(ReliableDnsClient.Mode.iterativeOnly);
        DnssecClient dnssecClient3 = new DnssecClient(miniDnsCacheFactory.newCache());
        this.recursiveOnlyDnssecClient = dnssecClient3;
        dnssecClient3.setMode(ReliableDnsClient.Mode.recursiveOnly);
    }

    @Override // org.minidns.hla.ResolverApi
    public <D extends Data> ResolverResult<D> resolve(Question question) throws IOException {
        return toResolverResult(question, this.dnssecClient.queryDnssec(question));
    }

    public <D extends Data> ResolverResult<D> resolveDnssecReliable(String str, Class<D> cls) throws IOException {
        return resolveDnssecReliable(DnsName.from(str), cls);
    }

    public <D extends Data> ResolverResult<D> resolveDnssecReliable(DnsName dnsName, Class<D> cls) throws IOException {
        return resolveDnssecReliable(new Question(dnsName, Record.TYPE.getType(cls)));
    }

    public <D extends Data> ResolverResult<D> resolveDnssecReliable(Question question) throws IOException {
        DnssecQueryResult dnssecQueryResultQueryDnssec = this.recursiveOnlyDnssecClient.queryDnssec(question);
        if (dnssecQueryResultQueryDnssec == null || !dnssecQueryResultQueryDnssec.isAuthenticData()) {
            dnssecQueryResultQueryDnssec = this.iterativeOnlyDnssecClient.queryDnssec(question);
        }
        return toResolverResult(question, dnssecQueryResultQueryDnssec);
    }

    public DnssecClient getDnssecClient() {
        return this.dnssecClient;
    }

    private static <D extends Data> ResolverResult<D> toResolverResult(Question question, DnssecQueryResult dnssecQueryResult) throws MiniDnsException.NullResultException {
        return new ResolverResult<>(question, dnssecQueryResult.dnsQueryResult, dnssecQueryResult.getUnverifiedReasons());
    }
}
