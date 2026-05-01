package com.tencent.qcloud.track.service;

import android.content.Context;
import android.util.Log;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import com.tencent.qcloud.track.cls.ClsAuthenticationException;
import com.tencent.qcloud.track.cls.ClsLifecycleCredentialProvider;
import com.tencent.qcloud.track.cls.ClsSessionCredentials;
import com.tencentcloudapi.cls.android.producer.AsyncProducerClient;
import com.tencentcloudapi.cls.android.producer.AsyncProducerConfig;
import com.tencentcloudapi.cls.android.producer.Callback;
import com.tencentcloudapi.cls.android.producer.Result;
import com.tencentcloudapi.cls.android.producer.common.LogItem;
import com.tencentcloudapi.cls.android.producer.errors.ProducerException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes4.dex */
public class ClsTrackService extends ATrackService {
    private static final String TAG = "ClsTrackService";
    private AsyncProducerClient clsClient;
    private ClsLifecycleCredentialProvider clsLifecycleCredentialProvider;
    private ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
    private String topicId;

    public void init(Context context, String str, String str2) {
        this.isInit = true;
        this.topicId = str;
        this.clsClient = new AsyncProducerClient(new AsyncProducerConfig(context.getApplicationContext(), str2, "secretId", "secretKey", "", (String) null));
    }

    public void setSecurityCredential(String str, String str2) {
        this.clsClient.getProducerConfig().resetSecurityToken(str, str2, "");
    }

    public void setCredentialProvider(ClsLifecycleCredentialProvider clsLifecycleCredentialProvider) {
        this.clsLifecycleCredentialProvider = clsLifecycleCredentialProvider;
    }

    @Override // com.tencent.qcloud.track.IReport
    public void report(final String str, final Map<String, String> map) {
        if (!isCloseReport() && this.isInit && isInclude()) {
            if (this.clsLifecycleCredentialProvider == null) {
                putLogs(str, map);
            } else {
                this.singleThreadExecutor.submit(new Runnable() { // from class: com.tencent.qcloud.track.service.ClsTrackService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2700lambda$report$0$comtencentqcloudtrackserviceClsTrackService(str, map);
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: lambda$report$0$com-tencent-qcloud-track-service-ClsTrackService, reason: not valid java name */
    /* synthetic */ void m2700lambda$report$0$comtencentqcloudtrackserviceClsTrackService(String str, Map map) {
        ClsSessionCredentials credentials;
        try {
            credentials = this.clsLifecycleCredentialProvider.getCredentials();
        } catch (ClsAuthenticationException e) {
            e.printStackTrace();
            credentials = null;
        }
        if (credentials != null) {
            this.clsClient.getProducerConfig().resetSecurityToken(credentials.getSecretId(), credentials.getSecretKey(), credentials.getToken());
            putLogs(str, map);
        }
    }

    private void putLogs(final String str, final Map<String, String> map) {
        ArrayList arrayList = new ArrayList();
        LogItem logItem = new LogItem();
        for (String str2 : map.keySet()) {
            logItem.PushBack(str2, map.get(str2));
        }
        arrayList.add(logItem);
        try {
            this.clsClient.putLogs(this.topicId, arrayList, new Callback() { // from class: com.tencent.qcloud.track.service.ClsTrackService$$ExternalSyntheticLambda0
                public final void onCompletion(Result result) {
                    this.f$0.m1880x37ddde3b(map, str, result);
                }
            });
        } catch (ProducerException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: lambda$putLogs$1$com-tencent-qcloud-track-service-ClsTrackService */
    /* synthetic */ void m1880x37ddde3b(Map map, String str, Result result) {
        if (isDebug()) {
            StringBuilder sb = new StringBuilder("{");
            for (String str2 : map.keySet()) {
                sb.append(str2).append(PunctuationConst.EQUAL).append((String) map.get(str2)).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            Log.i(TAG, String.format("eventCode: %s, topicId: %s, params: %s => result: %s", str, this.topicId, sb, result.toString()));
        }
    }

    public static boolean isInclude() {
        try {
            Class.forName("com.tencentcloudapi.cls.android.producer.AsyncProducerClient");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }
}
