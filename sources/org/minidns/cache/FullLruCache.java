package org.minidns.cache;

import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.record.Data;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class FullLruCache extends ExtendedLruCache {
    @Override // org.minidns.cache.ExtendedLruCache
    protected boolean shouldGather(Record<? extends Data> record, Question question, DnsName dnsName) {
        return true;
    }

    public FullLruCache() {
        this(512);
    }

    public FullLruCache(int i) {
        super(i);
    }

    public FullLruCache(int i, long j) {
        super(i, j);
    }
}
