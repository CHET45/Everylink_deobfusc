package com.tencent.qcloud.network.sonar;

import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public interface NetworkSonarCallback {
    void onFail(SonarResult sonarResult);

    void onFinish(List<SonarResult> list);

    void onStart(SonarType sonarType);

    void onSuccess(SonarResult sonarResult);
}
