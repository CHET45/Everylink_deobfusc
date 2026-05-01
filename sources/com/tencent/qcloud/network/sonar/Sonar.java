package com.tencent.qcloud.network.sonar;

/* JADX INFO: loaded from: classes4.dex */
public interface Sonar<T> {
    public static final String ERROR_MSG_HOST_IS_EMPTY = "request host is null";
    public static final String ERROR_MSG_IP_IS_EMPTY = "request ip is null";
    public static final String ERROR_MSG_NO_NETWORK = "Network is not available";
    public static final String ERROR_MSG_PING_PROCESS_IS_NULL = "ping process is null";

    SonarResult<T> start(SonarRequest sonarRequest);

    void stop();
}
