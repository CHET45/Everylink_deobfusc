package com.aivox.common.util;

import com.aivox.common.http.ApiService;

/* JADX INFO: loaded from: classes.dex */
public class BaseUtil {
    private static ApiService mApiService;

    public static ApiService getApiService() {
        return mApiService;
    }

    public static void setApiService(ApiService apiService) {
        mApiService = apiService;
    }
}
