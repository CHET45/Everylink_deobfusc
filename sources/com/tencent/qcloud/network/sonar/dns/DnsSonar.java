package com.tencent.qcloud.network.sonar.dns;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.qcloud.network.sonar.Sonar;
import com.tencent.qcloud.network.sonar.SonarRequest;
import com.tencent.qcloud.network.sonar.SonarResult;
import com.tencent.qcloud.network.sonar.SonarType;
import com.tencent.qcloud.network.sonar.utils.SonarLog;
import java.util.ArrayList;
import java.util.List;
import org.minidns.dnsmessage.Question;
import org.minidns.hla.DnssecResolverApi;
import org.minidns.hla.ResolverResult;
import org.minidns.record.CNAME;
import org.minidns.record.Data;
import org.minidns.record.Record;

/* JADX INFO: loaded from: classes4.dex */
public class DnsSonar implements Sonar<DnsResult> {
    @Override // com.tencent.qcloud.network.sonar.Sonar
    public void stop() {
    }

    @Override // com.tencent.qcloud.network.sonar.Sonar
    public SonarResult<DnsResult> start(SonarRequest sonarRequest) {
        if (!sonarRequest.isNetworkAvailable()) {
            return new SonarResult<>(SonarType.DNS, new Exception(Sonar.ERROR_MSG_NO_NETWORK));
        }
        DnsResult dnsResult = new DnsResult();
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            DnssecResolverApi.INSTANCE.getDnssecClient().setUseHardcodedDnsServers(false);
            ResolverResult resolverResultResolve = DnssecResolverApi.INSTANCE.resolve(new Question(sonarRequest.getHost(), Record.TYPE.A));
            List<Record<? extends Data>> list = resolverResultResolve.getRawAnswer().answerSection;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Record<? extends Data> record : list) {
                if (record.type == Record.TYPE.A) {
                    arrayList.add(record.getPayload().toString());
                } else if (record.type == Record.TYPE.CNAME) {
                    arrayList2.add(((CNAME) record.getPayload()).target.toString());
                }
            }
            dnsResult.f1874a = String.join(PunctuationConst.COMMA, arrayList);
            dnsResult.cname = String.join(PunctuationConst.COMMA, arrayList2);
            dnsResult.response = resolverResultResolve.getDnsQueryResult().response.toString();
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            dnsResult.localHosts = Dns.getLocalHost();
            dnsResult.lookupTime = jCurrentTimeMillis2 - jCurrentTimeMillis;
            if (!arrayList.isEmpty()) {
                dnsResult.f1875ip = (String) arrayList.get(arrayList.size() - 1);
            }
            return new SonarResult<>(SonarType.DNS, dnsResult);
        } catch (Exception e) {
            if (SonarLog.openLog) {
                e.printStackTrace();
            }
            return new SonarResult<>(SonarType.DNS, e);
        }
    }
}
