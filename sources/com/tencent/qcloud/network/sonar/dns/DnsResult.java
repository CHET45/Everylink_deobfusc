package com.tencent.qcloud.network.sonar.dns;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class DnsResult {

    /* JADX INFO: renamed from: a */
    public String f1874a;
    public String cname;

    /* JADX INFO: renamed from: ip */
    public String f1875ip;
    public List<String> localHosts;
    public long lookupTime;
    public String response;

    public String toString() {
        return "DnsResult{lookupTime=" + this.lookupTime + ", ip='" + this.f1875ip + "', a='" + this.f1874a + "', cname='" + this.cname + "', response='" + this.response + "'}";
    }
}
