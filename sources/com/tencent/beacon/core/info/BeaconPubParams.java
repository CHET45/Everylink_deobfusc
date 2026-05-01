package com.tencent.beacon.core.info;

import android.content.Context;
import android.os.Build;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p015a.p018c.C2636i;

/* JADX INFO: loaded from: classes4.dex */
public class BeaconPubParams {
    private static BeaconPubParams sParamsHolder;
    private String androidId;
    private String appFirstInstallTime;
    private String appLastUpdatedTime;
    private String appVersion;
    private String beaconId;
    private String boundleId;
    private String brand;
    private String cid;
    private Context context;
    private String dpi;
    private String dtImei2;
    private String dtMeid;
    private String dtMf;
    private String fingerprint;
    private String gpu;
    private String hardwareOs;
    private String imei;
    private String imsi;
    private String isRooted;
    private String language;
    private String mac;
    private String model;
    private String modelApn;
    private String networkType;
    private String osVersion;
    private String ostar;
    private String platform;
    private String productId;
    private String resolution;
    private String sdkId;
    private String sdkVersion;
    private String wifiMac;
    private String wifiSsid;

    private BeaconPubParams(Context context) {
        this.context = context;
        init(context);
    }

    public static synchronized BeaconPubParams getPubParams(Context context) {
        if (sParamsHolder == null) {
            synchronized (BeaconPubParams.class) {
                if (sParamsHolder == null) {
                    sParamsHolder = new BeaconPubParams(context);
                }
            }
        }
        sParamsHolder.refresh();
        return sParamsHolder;
    }

    private void init(Context context) {
        if (context != context.getApplicationContext()) {
            context = context.getApplicationContext();
        }
        C2630c c2630cM1059c = C2630c.m1059c();
        c2630cM1059c.m1064a(context);
        this.appVersion = C2629b.m1042a();
        this.boundleId = C2629b.m1047b();
        this.sdkId = c2630cM1059c.m1075h();
        this.sdkVersion = c2630cM1059c.m1076i();
        this.beaconId = C2636i.m1152f();
        this.appFirstInstallTime = C2629b.m1043a(context);
        C2632e c2632eM1082l = C2632e.m1082l();
        this.appLastUpdatedTime = c2632eM1082l.m1089a(context);
        this.platform = String.valueOf((int) c2630cM1059c.m1074g());
        this.dtMf = c2632eM1082l.m1103o();
        this.osVersion = c2632eM1082l.m1108t();
        this.hardwareOs = c2632eM1082l.m1095f() + PunctuationConst.UNDERLINE + c2632eM1082l.m1094e();
        this.brand = Build.BRAND;
        C2634g c2634gM1115e = C2634g.m1115e();
        this.model = c2634gM1115e.m1129h();
        this.language = c2632eM1082l.m1102n();
        this.resolution = c2632eM1082l.m1110v();
        this.dpi = String.valueOf(c2632eM1082l.m1114z());
        this.gpu = "";
        this.isRooted = c2632eM1082l.m1101m() ? "1" : "0";
        this.fingerprint = c2632eM1082l.m1111w();
        this.ostar = C2636i.m1147b();
        this.mac = c2634gM1115e.m1125f();
        this.wifiMac = c2634gM1115e.m1133j();
        this.wifiSsid = c2634gM1115e.m1135k();
        this.cid = c2632eM1082l.m1104p();
    }

    private void refresh() {
        C2632e c2632eM1082l = C2632e.m1082l();
        C2634g c2634gM1115e = C2634g.m1115e();
        this.networkType = c2632eM1082l.m1105q();
        this.modelApn = c2632eM1082l.m1106r();
        this.imei = c2634gM1115e.m1118b();
        this.dtImei2 = c2634gM1115e.m1120c();
        this.dtMeid = c2634gM1115e.m1127g();
        this.imsi = c2634gM1115e.m1122d();
        this.androidId = c2634gM1115e.m1116a();
        this.mac = c2634gM1115e.m1125f();
        this.wifiMac = c2634gM1115e.m1133j();
        this.wifiSsid = c2634gM1115e.m1135k();
    }

    public String getAndroidId() {
        return this.androidId;
    }

    public String getAppFirstInstallTime() {
        return this.appFirstInstallTime;
    }

    public String getAppLastUpdatedTime() {
        return this.appLastUpdatedTime;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public String getBeaconId() {
        return this.beaconId;
    }

    public String getBoundleId() {
        return this.boundleId;
    }

    public String getBrand() {
        return this.brand;
    }

    public String getCid() {
        return this.cid;
    }

    public String getDpi() {
        return this.dpi;
    }

    public String getDtImei2() {
        return this.dtImei2;
    }

    public String getDtMeid() {
        return this.dtMeid;
    }

    public String getDtMf() {
        return this.dtMf;
    }

    public String getFingerprint() {
        return this.fingerprint;
    }

    public String getHardwareOs() {
        return this.hardwareOs;
    }

    public String getImei() {
        return this.imei;
    }

    public String getImsi() {
        return this.imsi;
    }

    public String getIsRooted() {
        return this.isRooted;
    }

    public String getLanguage() {
        return this.language;
    }

    public String getMac() {
        return this.mac;
    }

    public String getModel() {
        return this.model;
    }

    public String getModelApn() {
        return this.modelApn;
    }

    public String getNetworkType() {
        return this.networkType;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public String getOstar() {
        return this.ostar;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getResolution() {
        return this.resolution;
    }

    public String getSdkId() {
        return this.sdkId;
    }

    public String getSdkVersion() {
        return this.sdkVersion;
    }

    public String getWifiMac() {
        return this.wifiMac;
    }

    public String getWifiSsid() {
        return this.wifiSsid;
    }

    public String toString() {
        return super.toString();
    }
}
