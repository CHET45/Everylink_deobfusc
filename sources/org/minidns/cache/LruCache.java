package org.minidns.cache;

import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.util.LinkedHashMap;
import java.util.Map;
import org.minidns.DnsCache;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsname.DnsName;
import org.minidns.dnsqueryresult.CachedDnsQueryResult;
import org.minidns.dnsqueryresult.DirectCachedDnsQueryResult;
import org.minidns.dnsqueryresult.DnsQueryResult;

/* JADX INFO: loaded from: classes4.dex */
public class LruCache extends DnsCache {
    protected LinkedHashMap<DnsMessage, CachedDnsQueryResult> backend;
    protected int capacity;
    protected long expireCount;
    protected long hitCount;
    protected long maxTTL;
    protected long missCount;

    @Override // org.minidns.DnsCache
    public void offer(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult, DnsName dnsName) {
    }

    public LruCache(final int i, long j) {
        this.missCount = 0L;
        this.expireCount = 0L;
        this.hitCount = 0L;
        this.capacity = i;
        this.maxTTL = j;
        this.backend = new LinkedHashMap<DnsMessage, CachedDnsQueryResult>(Math.min(((i + 3) / 4) + i + 2, 11), 0.75f, true) { // from class: org.minidns.cache.LruCache.1
            @Override // java.util.LinkedHashMap
            protected boolean removeEldestEntry(Map.Entry<DnsMessage, CachedDnsQueryResult> entry) {
                return size() > i;
            }
        };
    }

    public LruCache(int i) {
        this(i, Long.MAX_VALUE);
    }

    public LruCache() {
        this(512);
    }

    @Override // org.minidns.DnsCache
    protected synchronized void putNormalized(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult) {
        if (dnsQueryResult.response.receiveTimestamp <= 0) {
            return;
        }
        this.backend.put(dnsMessage, new DirectCachedDnsQueryResult(dnsMessage, dnsQueryResult));
    }

    @Override // org.minidns.DnsCache
    protected synchronized CachedDnsQueryResult getNormalized(DnsMessage dnsMessage) {
        CachedDnsQueryResult cachedDnsQueryResult = this.backend.get(dnsMessage);
        if (cachedDnsQueryResult == null) {
            this.missCount++;
            return null;
        }
        DnsMessage dnsMessage2 = cachedDnsQueryResult.response;
        if (dnsMessage2.receiveTimestamp + (Math.min(dnsMessage2.getAnswersMinTtl(), this.maxTTL) * 1000) < System.currentTimeMillis()) {
            this.missCount++;
            this.expireCount++;
            this.backend.remove(dnsMessage);
            return null;
        }
        this.hitCount++;
        return cachedDnsQueryResult;
    }

    public synchronized void clear() {
        this.backend.clear();
        this.missCount = 0L;
        this.hitCount = 0L;
        this.expireCount = 0L;
    }

    public long getMissCount() {
        return this.missCount;
    }

    public long getExpireCount() {
        return this.expireCount;
    }

    public long getHitCount() {
        return this.hitCount;
    }

    public String toString() {
        return "LRUCache{usage=" + this.backend.size() + "/" + this.capacity + ", hits=" + this.hitCount + ", misses=" + this.missCount + ", expires=" + this.expireCount + PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX;
    }
}
