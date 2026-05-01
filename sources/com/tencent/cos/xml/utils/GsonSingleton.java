package com.tencent.cos.xml.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/* JADX INFO: loaded from: classes4.dex */
public class GsonSingleton {
    private static Gson gsonInstance;

    private GsonSingleton() {
    }

    public static synchronized Gson getInstance() {
        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        }
        return gsonInstance;
    }
}
