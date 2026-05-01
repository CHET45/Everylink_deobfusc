package com.tencent.qcloud.core.http;

import android.text.TextUtils;
import java.net.InetAddress;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class HttpTaskMetrics {
    private long calculateMD5STookTime;
    private long calculateMD5StartTime;
    InetAddress connectAddress;
    long connectStartTimestamp;
    long connectTookTime;
    long dnsLookupTookTime;
    long dnsStartTimestamp;
    String domainName;
    private long fullTaskStartTime;
    private long fullTaskTookTime;
    private long httpTaskStartTime;
    private long httpTaskTookTime;
    private boolean isClockSkewedRetry;
    long readResponseBodyStartTimestamp;
    long readResponseBodyTookTime;
    long readResponseHeaderStartTimestamp;
    long readResponseHeaderTookTime;
    List<InetAddress> remoteAddress;
    long requestBodyByteCount;
    long responseBodyByteCount;
    private int retryCount;
    long secureConnectStartTimestamp;
    long secureConnectTookTime;
    private long signRequestStartTime;
    private long signRequestTookTime;
    long writeRequestBodyStartTimestamp;
    long writeRequestBodyTookTime;
    long writeRequestHeaderStartTimestamp;
    long writeRequestHeaderTookTime;

    private double toSeconds(long j) {
        return j / 1.0E9d;
    }

    public void onDataReady() {
    }

    void onTaskStart() {
        this.fullTaskStartTime = System.nanoTime();
    }

    void onTaskEnd() {
        this.fullTaskTookTime = System.nanoTime() - this.fullTaskStartTime;
        onDataReady();
    }

    void onHttpTaskStart() {
        this.httpTaskStartTime = System.nanoTime();
    }

    void onHttpTaskEnd() {
        if (this.httpTaskStartTime != 0) {
            this.httpTaskTookTime = System.nanoTime() - this.httpTaskStartTime;
        }
    }

    void onCalculateMD5Start() {
        this.calculateMD5StartTime = System.nanoTime();
    }

    void onCalculateMD5End() {
        this.calculateMD5STookTime += System.nanoTime() - this.calculateMD5StartTime;
    }

    void onSignRequestStart() {
        this.signRequestStartTime = System.nanoTime();
    }

    void onSignRequestEnd() {
        this.signRequestTookTime += System.nanoTime() - this.signRequestStartTime;
    }

    public long requestBodyByteCount() {
        return this.requestBodyByteCount;
    }

    public long responseBodyByteCount() {
        return this.responseBodyByteCount;
    }

    public double httpTaskFullTime() {
        return toSeconds(this.httpTaskTookTime);
    }

    public double dnsLookupTookTime() {
        return toSeconds(this.dnsLookupTookTime);
    }

    public double connectTookTime() {
        return toSeconds(this.connectTookTime);
    }

    public double secureConnectTookTime() {
        return toSeconds(this.secureConnectTookTime);
    }

    public double calculateMD5STookTime() {
        return toSeconds(this.calculateMD5STookTime);
    }

    public double signRequestTookTime() {
        return toSeconds(this.signRequestTookTime);
    }

    public double readResponseHeaderTookTime() {
        return toSeconds(this.readResponseHeaderTookTime);
    }

    public double readResponseBodyTookTime() {
        return toSeconds(this.readResponseBodyTookTime);
    }

    public double writeRequestBodyTookTime() {
        return toSeconds(this.writeRequestBodyTookTime);
    }

    public double writeRequestHeaderTookTime() {
        return toSeconds(this.writeRequestHeaderTookTime);
    }

    public double fullTaskTookTime() {
        return toSeconds(this.fullTaskTookTime);
    }

    public List<InetAddress> getRemoteAddress() {
        return this.remoteAddress;
    }

    public InetAddress getConnectAddress() {
        return this.connectAddress;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public void setDomainName(String str) {
        this.domainName = str;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int i) {
        this.retryCount = i;
    }

    public boolean isClockSkewedRetry() {
        return this.isClockSkewedRetry;
    }

    public void setClockSkewedRetry(boolean z) {
        this.isClockSkewedRetry = z;
    }

    public static HttpTaskMetrics createMetricsWithHost(String str) {
        HttpTaskMetrics httpTaskMetrics = new HttpTaskMetrics();
        httpTaskMetrics.domainName = str;
        return httpTaskMetrics;
    }

    public void recordConnectAddress(InetAddress inetAddress) {
        if (inetAddress != null) {
            this.connectAddress = inetAddress;
        }
    }

    public synchronized HttpTaskMetrics merge(HttpTaskMetrics httpTaskMetrics) {
        String str;
        if (!TextUtils.isEmpty(this.domainName) && !TextUtils.isEmpty(httpTaskMetrics.domainName) && !this.domainName.equals(httpTaskMetrics.domainName)) {
            return this;
        }
        if (TextUtils.isEmpty(this.domainName) && (str = httpTaskMetrics.domainName) != null) {
            this.domainName = str;
        }
        this.dnsLookupTookTime = Math.max(httpTaskMetrics.dnsLookupTookTime, this.dnsLookupTookTime);
        this.connectTookTime = Math.max(httpTaskMetrics.connectTookTime, this.connectTookTime);
        this.secureConnectTookTime = Math.max(httpTaskMetrics.secureConnectTookTime, this.secureConnectTookTime);
        this.writeRequestHeaderTookTime += httpTaskMetrics.writeRequestHeaderTookTime;
        this.writeRequestBodyTookTime += httpTaskMetrics.writeRequestBodyTookTime;
        this.readResponseHeaderTookTime += httpTaskMetrics.readResponseHeaderTookTime;
        this.readResponseBodyTookTime += httpTaskMetrics.readResponseBodyTookTime;
        this.requestBodyByteCount += httpTaskMetrics.requestBodyByteCount;
        this.responseBodyByteCount += httpTaskMetrics.responseBodyByteCount;
        this.fullTaskTookTime += httpTaskMetrics.fullTaskTookTime;
        this.httpTaskTookTime += httpTaskMetrics.httpTaskTookTime;
        this.calculateMD5STookTime += httpTaskMetrics.calculateMD5STookTime;
        this.signRequestTookTime += httpTaskMetrics.signRequestTookTime;
        if (httpTaskMetrics.getRemoteAddress() != null) {
            this.remoteAddress = httpTaskMetrics.getRemoteAddress();
        }
        if (httpTaskMetrics.connectAddress != null) {
            this.connectAddress = httpTaskMetrics.getConnectAddress();
        }
        this.retryCount += httpTaskMetrics.retryCount;
        if (!this.isClockSkewedRetry) {
            this.isClockSkewedRetry = httpTaskMetrics.isClockSkewedRetry;
        }
        return this;
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("Http Metrics: \ndomain : ").append(this.domainName).append("\nconnectAddress : ");
        InetAddress inetAddress = this.connectAddress;
        StringBuilder sbAppend2 = sbAppend.append(inetAddress != null ? inetAddress.getHostAddress() : "null").append("\nretryCount : ").append(this.retryCount).append("\nisClockSkewedRetry : ").append(this.isClockSkewedRetry).append("\ndns : ");
        List<InetAddress> list = this.remoteAddress;
        return sbAppend2.append(list != null ? list : "null").append("\nfullTaskTookTime : ").append(fullTaskTookTime()).append("\nhttpTaskTookTime : ").append(httpTaskFullTime()).append("\ncalculateMD5STookTime : ").append(calculateMD5STookTime()).append("\nsignRequestTookTime : ").append(signRequestTookTime()).append("\ndnsStartTimestamp : ").append(this.dnsStartTimestamp).append("\ndnsLookupTookTime : ").append(dnsLookupTookTime()).append("\nconnectStartTimestamp : ").append(this.connectStartTimestamp).append("\nconnectTookTime : ").append(connectTookTime()).append("\nsecureConnectStartTimestamp : ").append(this.secureConnectStartTimestamp).append("\nsecureConnectTookTime : ").append(secureConnectTookTime()).append("\nwriteRequestHeaderStartTimestamp : ").append(this.writeRequestHeaderStartTimestamp).append("\nwriteRequestHeaderTookTime : ").append(writeRequestHeaderTookTime()).append("\nwriteRequestBodyStartTimestamp : ").append(this.writeRequestBodyStartTimestamp).append("\nwriteRequestBodyTookTime : ").append(writeRequestBodyTookTime()).append("\nreadResponseHeaderStartTimestamp : ").append(this.readResponseHeaderStartTimestamp).append("\nreadResponseHeaderTookTime : ").append(readResponseHeaderTookTime()).append("\nreadResponseBodyStartTimestamp : ").append(this.readResponseBodyStartTimestamp).append("readResponseBodyTookTime : ").append(readResponseBodyTookTime()).toString();
    }
}
