package org.minidns;

import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.minidns.MiniDnsFuture;
import org.minidns.cache.LruCache;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.dnsqueryresult.CachedDnsQueryResult;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.record.AAAA;
import org.minidns.record.C5085A;
import org.minidns.record.C5089NS;
import org.minidns.record.Data;
import org.minidns.record.Record;
import org.minidns.source.DnsDataSource;
import org.minidns.source.NetworkDataSource;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractDnsClient {
    protected final DnsCache cache;
    protected DnsDataSource dataSource;
    protected final Random insecureRandom;
    protected IpVersionSetting ipVersionSetting;
    private final DnsDataSource.OnResponseCallback onResponseCallback;
    protected final Random random;
    protected static final LruCache DEFAULT_CACHE = new LruCache();
    protected static final Logger LOGGER = Logger.getLogger(AbstractDnsClient.class.getName());
    protected static IpVersionSetting DEFAULT_IP_VERSION_SETTING = IpVersionSetting.v4v6;

    protected abstract DnsMessage.Builder newQuestion(DnsMessage.Builder builder);

    protected abstract DnsQueryResult query(DnsMessage.Builder builder) throws IOException;

    public enum IpVersionSetting {
        v4only(true, false),
        v6only(false, true),
        v4v6(true, true),
        v6v4(true, true);


        /* JADX INFO: renamed from: v4 */
        public final boolean f2043v4;

        /* JADX INFO: renamed from: v6 */
        public final boolean f2044v6;

        IpVersionSetting(boolean z, boolean z2) {
            this.f2043v4 = z;
            this.f2044v6 = z2;
        }
    }

    public static void setDefaultIpVersion(IpVersionSetting ipVersionSetting) {
        if (ipVersionSetting == null) {
            throw new IllegalArgumentException();
        }
        DEFAULT_IP_VERSION_SETTING = ipVersionSetting;
    }

    public void setPreferedIpVersion(IpVersionSetting ipVersionSetting) {
        if (ipVersionSetting == null) {
            throw new IllegalArgumentException();
        }
        this.ipVersionSetting = ipVersionSetting;
    }

    public IpVersionSetting getPreferedIpVersion() {
        return this.ipVersionSetting;
    }

    protected AbstractDnsClient(DnsCache dnsCache) {
        SecureRandom secureRandom;
        this.onResponseCallback = new DnsDataSource.OnResponseCallback() { // from class: org.minidns.AbstractDnsClient.1
            @Override // org.minidns.source.DnsDataSource.OnResponseCallback
            public void onResponse(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult) {
                Question question = dnsMessage.getQuestion();
                if (AbstractDnsClient.this.cache == null || !AbstractDnsClient.this.isResponseCacheable(question, dnsQueryResult)) {
                    return;
                }
                AbstractDnsClient.this.cache.put(dnsMessage.asNormalizedVersion(), dnsQueryResult);
            }
        };
        this.insecureRandom = new Random();
        this.dataSource = new NetworkDataSource();
        this.ipVersionSetting = DEFAULT_IP_VERSION_SETTING;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException unused) {
            secureRandom = new SecureRandom();
        }
        this.random = secureRandom;
        this.cache = dnsCache;
    }

    protected AbstractDnsClient() {
        this(DEFAULT_CACHE);
    }

    public final DnsQueryResult query(String str, Record.TYPE type, Record.CLASS r4) throws IOException {
        return query(new Question(str, type, r4));
    }

    public final DnsQueryResult query(DnsName dnsName, Record.TYPE type) throws IOException {
        return query(new Question(dnsName, type, Record.CLASS.IN));
    }

    public final DnsQueryResult query(CharSequence charSequence, Record.TYPE type) throws IOException {
        return query(new Question(charSequence, type, Record.CLASS.IN));
    }

    public DnsQueryResult query(Question question) throws IOException {
        return query(buildMessage(question));
    }

    public final MiniDnsFuture<DnsQueryResult, IOException> queryAsync(CharSequence charSequence, Record.TYPE type) {
        return queryAsync(new Question(charSequence, type, Record.CLASS.IN));
    }

    public final MiniDnsFuture<DnsQueryResult, IOException> queryAsync(Question question) {
        return queryAsync(buildMessage(question));
    }

    protected MiniDnsFuture<DnsQueryResult, IOException> queryAsync(DnsMessage.Builder builder) {
        MiniDnsFuture.InternalMiniDnsFuture internalMiniDnsFuture = new MiniDnsFuture.InternalMiniDnsFuture();
        try {
            internalMiniDnsFuture.setResult(query(builder));
            return internalMiniDnsFuture;
        } catch (IOException e) {
            internalMiniDnsFuture.setException(e);
            return internalMiniDnsFuture;
        }
    }

    public final DnsQueryResult query(Question question, InetAddress inetAddress, int i) throws IOException {
        return query(getQueryFor(question), inetAddress, i);
    }

    public final DnsQueryResult query(DnsMessage dnsMessage, InetAddress inetAddress, int i) throws IOException {
        DnsCache dnsCache = this.cache;
        CachedDnsQueryResult cachedDnsQueryResult = dnsCache == null ? null : dnsCache.get(dnsMessage);
        if (cachedDnsQueryResult != null) {
            return cachedDnsQueryResult;
        }
        Question question = dnsMessage.getQuestion();
        Level level = Level.FINE;
        Logger logger = LOGGER;
        logger.log(level, "Asking {0} on {1} for {2} with:\n{3}", new Object[]{inetAddress, Integer.valueOf(i), question, dnsMessage});
        try {
            DnsQueryResult dnsQueryResultQuery = this.dataSource.query(dnsMessage, inetAddress, i);
            logger.log(level, "Response from {0} on {1} for {2}:\n{3}", new Object[]{inetAddress, Integer.valueOf(i), question, dnsQueryResultQuery});
            this.onResponseCallback.onResponse(dnsMessage, dnsQueryResultQuery);
            return dnsQueryResultQuery;
        } catch (IOException e) {
            LOGGER.log(level, "IOException {0} on {1} while resolving {2}: {3}", new Object[]{inetAddress, Integer.valueOf(i), question, e});
            throw e;
        }
    }

    public final MiniDnsFuture<DnsQueryResult, IOException> queryAsync(DnsMessage dnsMessage, InetAddress inetAddress, int i) {
        DnsCache dnsCache = this.cache;
        CachedDnsQueryResult cachedDnsQueryResult = dnsCache == null ? null : dnsCache.get(dnsMessage);
        if (cachedDnsQueryResult != null) {
            return MiniDnsFuture.from(cachedDnsQueryResult);
        }
        LOGGER.log(Level.FINE, "Asynchronusly asking {0} on {1} for {2} with:\n{3}", new Object[]{inetAddress, Integer.valueOf(i), dnsMessage.getQuestion(), dnsMessage});
        return this.dataSource.queryAsync(dnsMessage, inetAddress, i, this.onResponseCallback);
    }

    protected boolean isResponseCacheable(Question question, DnsQueryResult dnsQueryResult) {
        Iterator<Record<? extends Data>> it = dnsQueryResult.response.answerSection.iterator();
        while (it.hasNext()) {
            if (it.next().isAnswer(question)) {
                return true;
            }
        }
        return false;
    }

    final DnsMessage.Builder buildMessage(Question question) {
        DnsMessage.Builder builder = DnsMessage.builder();
        builder.setQuestion(question);
        builder.setId(this.random.nextInt());
        return newQuestion(builder);
    }

    public DnsQueryResult query(String str, Record.TYPE type, Record.CLASS r4, InetAddress inetAddress, int i) throws IOException {
        return query(new Question(str, type, r4), inetAddress, i);
    }

    public DnsQueryResult query(String str, Record.TYPE type, Record.CLASS r4, InetAddress inetAddress) throws IOException {
        return query(new Question(str, type, r4), inetAddress);
    }

    public DnsQueryResult query(String str, Record.TYPE type, InetAddress inetAddress) throws IOException {
        return query(new Question(str, type, Record.CLASS.IN), inetAddress);
    }

    public final DnsQueryResult query(DnsMessage dnsMessage, InetAddress inetAddress) throws IOException {
        return query(dnsMessage, inetAddress, 53);
    }

    public DnsQueryResult query(Question question, InetAddress inetAddress) throws IOException {
        return query(question, inetAddress, 53);
    }

    public final MiniDnsFuture<DnsQueryResult, IOException> queryAsync(DnsMessage dnsMessage, InetAddress inetAddress) {
        return queryAsync(dnsMessage, inetAddress, 53);
    }

    public DnsDataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DnsDataSource dnsDataSource) {
        if (dnsDataSource == null) {
            throw new IllegalArgumentException();
        }
        this.dataSource = dnsDataSource;
    }

    public DnsCache getCache() {
        return this.cache;
    }

    protected DnsMessage getQueryFor(Question question) {
        return buildMessage(question).build();
    }

    private <D extends Data> Set<D> getCachedRecordsFor(DnsName dnsName, Record.TYPE type) {
        if (this.cache == null) {
            return Collections.emptySet();
        }
        Question question = new Question(dnsName, type);
        CachedDnsQueryResult cachedDnsQueryResult = this.cache.get(getQueryFor(question));
        if (cachedDnsQueryResult == null) {
            return Collections.emptySet();
        }
        return cachedDnsQueryResult.response.getAnswersFor(question);
    }

    public Set<C5089NS> getCachedNameserverRecordsFor(DnsName dnsName) {
        return getCachedRecordsFor(dnsName, Record.TYPE.NS);
    }

    public Set<C5085A> getCachedIPv4AddressesFor(DnsName dnsName) {
        return getCachedRecordsFor(dnsName, Record.TYPE.A);
    }

    public Set<AAAA> getCachedIPv6AddressesFor(DnsName dnsName) {
        return getCachedRecordsFor(dnsName, Record.TYPE.AAAA);
    }

    private <D extends Data> Set<D> getCachedIPNameserverAddressesFor(DnsName dnsName, Record.TYPE type) {
        Collection cachedIPv4AddressesFor;
        Set<C5089NS> cachedNameserverRecordsFor = getCachedNameserverRecordsFor(dnsName);
        if (cachedNameserverRecordsFor.isEmpty()) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(cachedNameserverRecordsFor.size() * 3);
        for (C5089NS c5089ns : cachedNameserverRecordsFor) {
            int i = C50642.$SwitchMap$org$minidns$record$Record$TYPE[type.ordinal()];
            if (i == 1) {
                cachedIPv4AddressesFor = getCachedIPv4AddressesFor(c5089ns.target);
            } else if (i == 2) {
                cachedIPv4AddressesFor = getCachedIPv6AddressesFor(c5089ns.target);
            } else {
                throw new AssertionError();
            }
            hashSet.addAll(cachedIPv4AddressesFor);
        }
        return hashSet;
    }

    /* JADX INFO: renamed from: org.minidns.AbstractDnsClient$2 */
    static /* synthetic */ class C50642 {
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
        }
    }

    public Set<C5085A> getCachedIPv4NameserverAddressesFor(DnsName dnsName) {
        return getCachedIPNameserverAddressesFor(dnsName, Record.TYPE.A);
    }

    public Set<AAAA> getCachedIPv6NameserverAddressesFor(DnsName dnsName) {
        return getCachedIPNameserverAddressesFor(dnsName, Record.TYPE.AAAA);
    }
}
