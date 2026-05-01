package com.aivox.base.common;

/* JADX INFO: loaded from: classes.dex */
public class BaseGlobalConfig {
    private static String apiHost = "";
    private static boolean debug = false;
    private static String googleServiceClientId = "";
    private static String h5Host = "";
    private static boolean mainland = false;
    private static String policyVersion = "";
    private static String smsKey = "";
    private static String wsHost = "";

    public static String getApiHost() {
        return apiHost;
    }

    public static void setApiHost(String str) {
        apiHost = str;
    }

    public static String getWsHost() {
        return wsHost;
    }

    public static void setWsHost(String str) {
        wsHost = str;
    }

    public static String getGoogleServiceClientId() {
        return googleServiceClientId;
    }

    public static void setGoogleServiceClientId(String str) {
        googleServiceClientId = str;
    }

    public static String getPolicyVersion() {
        return policyVersion;
    }

    public static void setPolicyVersion(String str) {
        policyVersion = str;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean z) {
        debug = z;
    }

    public static boolean isMainland() {
        return mainland;
    }

    public static void setMainland(boolean z) {
        mainland = z;
    }

    public static String getH5Host() {
        return h5Host;
    }

    public static void setH5Host(String str) {
        h5Host = str;
    }

    public static String getSmsKey() {
        return smsKey;
    }

    public static void setSmsKey(String str) {
        smsKey = str;
    }
}
