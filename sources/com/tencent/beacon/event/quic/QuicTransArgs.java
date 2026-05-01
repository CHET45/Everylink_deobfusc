package com.tencent.beacon.event.quic;

import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.event.EventBean;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class QuicTransArgs {
    private String appkey;
    private byte[] data;
    private String eventCode;
    private Map<String, String> headers = C2669d.m1345e();
    private String url;

    public QuicTransArgs(String str, EventBean eventBean, byte[] bArr) {
        this.url = str;
        this.data = bArr;
        this.appkey = eventBean.getAppKey();
        this.eventCode = eventBean.getEventCode();
    }

    public String getAppkey() {
        return this.appkey;
    }

    public byte[] getData() {
        return this.data;
    }

    public String getEventCode() {
        return this.eventCode;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public String getUrl() {
        return this.url;
    }

    public void setAppkey(String str) {
        this.appkey = str;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public void setEventCode(String str) {
        this.eventCode = str;
    }

    public void setHeaders(Map<String, String> map) {
        this.headers = map;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
