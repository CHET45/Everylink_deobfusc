package org.minidns.cache;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.minidns.dnsmessage.DnsMessage;
import org.minidns.dnsmessage.Question;
import org.minidns.dnsname.DnsName;
import org.minidns.dnsqueryresult.DnsQueryResult;
import org.minidns.dnsqueryresult.SynthesizedCachedDnsQueryResult;
import org.minidns.record.Data;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class ExtendedLruCache extends LruCache {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public ExtendedLruCache() {
        this(512);
    }

    public ExtendedLruCache(int i) {
        super(i);
    }

    public ExtendedLruCache(int i, long j) {
        super(i, j);
    }

    @Override // org.minidns.cache.LruCache, org.minidns.DnsCache
    protected void putNormalized(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult) {
        super.putNormalized(dnsMessage, dnsQueryResult);
        DnsMessage dnsMessage2 = dnsQueryResult.response;
        HashMap map = new HashMap(dnsMessage2.additionalSection.size());
        gather(map, dnsMessage, dnsMessage2.answerSection, null);
        gather(map, dnsMessage, dnsMessage2.authoritySection, null);
        gather(map, dnsMessage, dnsMessage2.additionalSection, null);
        putExtraCaches(dnsQueryResult, map);
    }

    @Override // org.minidns.cache.LruCache, org.minidns.DnsCache
    public void offer(DnsMessage dnsMessage, DnsQueryResult dnsQueryResult, DnsName dnsName) {
        DnsMessage dnsMessage2 = dnsQueryResult.response;
        HashMap map = new HashMap(dnsMessage2.additionalSection.size());
        gather(map, dnsMessage, dnsMessage2.authoritySection, dnsName);
        gather(map, dnsMessage, dnsMessage2.additionalSection, dnsName);
        putExtraCaches(dnsQueryResult, map);
    }

    private void gather(Map<DnsMessage, List<Record<? extends Data>>> map, DnsMessage dnsMessage, List<Record<? extends Data>> list, DnsName dnsName) {
        DnsMessage.Builder questionMessage;
        for (Record<? extends Data> record : list) {
            if (shouldGather(record, dnsMessage.getQuestion(), dnsName) && (questionMessage = record.getQuestionMessage()) != null) {
                questionMessage.copyFlagsFrom(dnsMessage);
                questionMessage.setAdditionalResourceRecords(dnsMessage.additionalSection);
                DnsMessage dnsMessageBuild = questionMessage.build();
                if (!dnsMessageBuild.equals(dnsMessage)) {
                    List<Record<? extends Data>> linkedList = map.get(dnsMessageBuild);
                    if (linkedList == null) {
                        linkedList = new LinkedList<>();
                        map.put(dnsMessageBuild, linkedList);
                    }
                    linkedList.add(record);
                }
            }
        }
    }

    private void putExtraCaches(DnsQueryResult dnsQueryResult, Map<DnsMessage, List<Record<? extends Data>>> map) {
        DnsMessage dnsMessage = dnsQueryResult.response;
        for (Map.Entry<DnsMessage, List<Record<? extends Data>>> entry : map.entrySet()) {
            DnsMessage key = entry.getKey();
            SynthesizedCachedDnsQueryResult synthesizedCachedDnsQueryResult = new SynthesizedCachedDnsQueryResult(key, dnsMessage.asBuilder().setQuestion(key.getQuestion()).setAuthoritativeAnswer(true).addAnswers(entry.getValue()).build(), dnsQueryResult);
            synchronized (this) {
                this.backend.put(key, synthesizedCachedDnsQueryResult);
            }
        }
    }

    protected boolean shouldGather(Record<? extends Data> record, Question question, DnsName dnsName) {
        return record.name.isChildOf(question.name) || (dnsName != null ? record.name.isChildOf(dnsName) : false);
    }
}
