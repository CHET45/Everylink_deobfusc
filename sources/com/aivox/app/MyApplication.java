package com.aivox.app;

import com.aivox.base.common.BaseGlobalConfig;
import com.aivox.common.base.AppApplication;

/* JADX INFO: loaded from: classes.dex */
public class MyApplication extends AppApplication {
    @Override // com.aivox.common.base.AppApplication, android.app.Application
    public void onCreate() {
        BaseGlobalConfig.setDebug(false);
        BaseGlobalConfig.setMainland(false);
        BaseGlobalConfig.setApiHost(BuildConfig.API_HOST);
        BaseGlobalConfig.setWsHost(BuildConfig.WS_HOST);
        BaseGlobalConfig.setH5Host(BuildConfig.H5_HOST);
        BaseGlobalConfig.setGoogleServiceClientId(BuildConfig.GOOGLE_SERVICE_CLIENT_ID);
        BaseGlobalConfig.setPolicyVersion("1");
        BaseGlobalConfig.setSmsKey(BuildConfig.SMS_KEY);
        super.onCreate();
    }
}
