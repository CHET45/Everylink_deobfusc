package com.tencent.beacon.event.open;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.beacon.base.net.C2671c;
import com.tencent.beacon.base.net.p021b.C2667b;
import com.tencent.beacon.base.store.KeyValueStorage;
import com.tencent.beacon.base.store.SharedPreferencesC2686a;
import com.tencent.beacon.base.util.BeaconLogger;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.base.util.C2700h;
import com.tencent.beacon.core.info.BeaconPubParams;
import com.tencent.beacon.event.immediate.IBeaconImmediateReport;
import com.tencent.beacon.event.open.EventResult;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.event.quic.IBeaconQuicReport;
import com.tencent.beacon.module.BeaconModule;
import com.tencent.beacon.module.EventModule;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.p015a.p016a.C2612b;
import com.tencent.beacon.p015a.p016a.C2613c;
import com.tencent.beacon.p015a.p017b.AbstractC2616b;
import com.tencent.beacon.p015a.p017b.C2623i;
import com.tencent.beacon.p015a.p017b.C2624j;
import com.tencent.beacon.p015a.p018c.C2629b;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p015a.p018c.C2635h;
import com.tencent.beacon.p015a.p018c.C2636i;
import com.tencent.beacon.p015a.p018c.InterfaceC2633f;
import com.tencent.beacon.p019b.C2637a;
import com.tencent.beacon.p028d.C2710b;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes4.dex */
public final class BeaconReport {

    /* JADX INFO: renamed from: a */
    private static volatile BeaconReport f1695a = null;

    /* JADX INFO: renamed from: b */
    private static String f1696b = "";

    /* JADX INFO: renamed from: c */
    private static boolean f1697c = false;

    /* JADX INFO: renamed from: d */
    private Context f1698d;

    /* JADX INFO: renamed from: e */
    private boolean f1699e;

    /* JADX INFO: renamed from: f */
    private IBeaconImmediateReport f1700f;

    /* JADX INFO: renamed from: g */
    private IBeaconQuicReport f1701g;

    /* JADX INFO: renamed from: h */
    private EventModule f1702h;

    private BeaconReport() {
        synchronized (BeaconReport.class) {
            if (f1697c) {
                throw new RuntimeException("单例模式被侵犯！");
            }
            f1697c = true;
        }
    }

    /* JADX INFO: renamed from: b */
    private void m1741b(BeaconConfig beaconConfig) {
        HashMap map = new HashMap();
        map.put("u_c_r_p", Long.valueOf(beaconConfig.getRealtimePollingTime()));
        map.put("u_c_n_p", Long.valueOf(beaconConfig.getNormalPollingTIme()));
        C2612b.m991a().m999b(new C2613c(11, map));
    }

    /* JADX INFO: renamed from: c */
    private void m1742c(BeaconConfig beaconConfig) {
        if (beaconConfig != null) {
            try {
                HashMap map = new HashMap();
                map.put("u_c_a_e", Boolean.valueOf(beaconConfig.isAuditEnable()));
                map.put("u_c_b_e", Boolean.valueOf(beaconConfig.isBidEnable()));
                map.put("u_c_d_s", Integer.valueOf(beaconConfig.getMaxDBCount()));
                map.put("u_c_p_s", Boolean.valueOf(beaconConfig.isPagePathEnable()));
                map.put("u_s_o_h", Boolean.valueOf(beaconConfig.isSocketMode()));
                C2612b.m991a().m999b(new C2613c(8, map));
            } catch (Throwable th) {
                C2624j.m1031e().m1024a("202", "sdk init error! package name: " + C2629b.m1047b() + " , msg:" + th.getMessage(), th);
                C2695c.m1465a(th);
            }
        }
    }

    /* JADX INFO: renamed from: d */
    private void m1743d(BeaconConfig beaconConfig) {
        C2634g c2634gM1115e = C2634g.m1115e();
        if (!TextUtils.isEmpty(beaconConfig.getAndroidID())) {
            c2634gM1115e.m1117a(beaconConfig.getAndroidID());
        }
        if (!TextUtils.isEmpty(beaconConfig.getImei())) {
            c2634gM1115e.m1119b(beaconConfig.getImei());
        }
        if (!TextUtils.isEmpty(beaconConfig.getImei2())) {
            c2634gM1115e.m1121c(beaconConfig.getImei2());
        }
        if (!TextUtils.isEmpty(beaconConfig.getImsi())) {
            c2634gM1115e.m1123d(beaconConfig.getImsi());
        }
        if (!TextUtils.isEmpty(beaconConfig.getMeid())) {
            c2634gM1115e.m1126f(beaconConfig.getMeid());
        }
        if (!TextUtils.isEmpty(beaconConfig.getModel())) {
            c2634gM1115e.m1128g(beaconConfig.getModel());
        }
        if (!TextUtils.isEmpty(beaconConfig.getMac())) {
            c2634gM1115e.m1124e(beaconConfig.getMac());
        }
        if (!TextUtils.isEmpty(beaconConfig.getWifiMacAddress())) {
            c2634gM1115e.m1132i(beaconConfig.getWifiMacAddress());
        }
        if (!TextUtils.isEmpty(beaconConfig.getWifiSSID())) {
            c2634gM1115e.m1134j(beaconConfig.getWifiSSID());
        }
        if (TextUtils.isEmpty(beaconConfig.getOaid())) {
            return;
        }
        c2634gM1115e.m1130h(beaconConfig.getOaid());
    }

    public static BeaconReport getInstance() {
        if (f1695a == null) {
            synchronized (BeaconReport.class) {
                if (f1695a == null) {
                    f1695a = new BeaconReport();
                }
            }
        }
        return f1695a;
    }

    public static String getSoPath() {
        return f1696b;
    }

    public static void setSoPath(String str) {
        f1696b = str;
    }

    public void disableReport() {
        C2710b.m1518d().m1531a(false, true);
    }

    public void enableReport() {
        C2710b.m1518d().m1531a(true, true);
    }

    public IBeaconQuicReport getBeaconQuicReport() {
        return this.f1701g;
    }

    public BeaconPubParams getCommonParams(Context context) {
        if (context == null) {
            return null;
        }
        return BeaconPubParams.getPubParams(context);
    }

    public IBeaconImmediateReport getImmediateReport() {
        return this.f1700f;
    }

    @Deprecated
    public String getOAID() {
        C2695c.m1468b("this method do not collect OAID, use method in qmsp instead", new Object[0]);
        return C2634g.m1115e().m1131i();
    }

    public C2635h getOstar(String str) {
        return C2636i.m1142a(str);
    }

    public C2635h getRtOstar(Context context, String str) {
        return C2636i.m1141a(context, str);
    }

    public String getSDKVersion() {
        return "4.2.86.12-external";
    }

    public boolean isInitBeacon() {
        return this.f1699e;
    }

    public void pauseUpload(boolean z) {
        EventModule eventModule = this.f1702h;
        if (eventModule == null || !eventModule.m1765e()) {
            return;
        }
        this.f1702h.m1762b(z);
    }

    public EventResult report(BeaconEvent beaconEvent) {
        try {
            if (!C2710b.m1518d().m1563q()) {
                C2695c.m1476e("BeaconReport not enable report! event: %s", beaconEvent.getCode());
                return EventResult.C2742a.m1744a(102);
            }
            if (TextUtils.isEmpty(beaconEvent.getCode())) {
                return EventResult.C2742a.m1744a(106);
            }
            BeaconEvent beaconEventBuild = BeaconEvent.newBuilder(beaconEvent).build();
            EventModule eventModule = this.f1702h;
            if (eventModule != null && eventModule.m1765e()) {
                return this.f1702h.m1755a(beaconEventBuild);
            }
            HashMap map = new HashMap();
            map.put("b_e", beaconEventBuild);
            C2612b.m991a().m998a(new C2613c(6, map));
            return new EventResult(0, -1L, "Beacon SDK not init beaconEvent add to cache!");
        } catch (Throwable th) {
            C2695c.m1465a(th);
            C2624j.m1031e().m1024a("598", "error while report", th);
            return new EventResult(199, -1L, "REPORT ERROR");
        }
    }

    @Deprecated
    public void resumeReport() {
        resumeUpload();
    }

    public void resumeUpload() {
        EventModule eventModule = this.f1702h;
        if (eventModule == null || !eventModule.m1765e()) {
            return;
        }
        this.f1702h.m1766f();
    }

    @Deprecated
    public void setAdditionalParams(Map<String, String> map) {
        setAdditionalParams(C2630c.m1059c().m1072e(), map);
    }

    public void setAndroidID(String str) {
        C2634g.m1115e().m1117a(str);
    }

    public void setAppVersion(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        C2629b.f1069a = str;
    }

    public void setBeaconQuicReport(IBeaconQuicReport iBeaconQuicReport) {
        this.f1701g = iBeaconQuicReport;
    }

    @Deprecated
    public boolean setChannelID(String str) {
        return setChannelID(C2630c.m1059c().m1072e(), str);
    }

    @Deprecated
    public void setCollectAndroidID(boolean z) {
        C2695c.m1468b("setCollectAndroidID has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectImei(boolean z) {
        C2695c.m1468b("setCollectImei has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectMac(boolean z) {
        C2695c.m1468b("setCollectMac has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectModel(boolean z) {
        C2695c.m1468b("setCollectModel has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectOAID(boolean z) {
        C2695c.m1468b("setCollectOAID has been Deprecated", new Object[0]);
    }

    @Deprecated
    public void setCollectPersonalInfo(boolean z) {
        C2695c.m1468b("setCollectPersonalInfo has been Deprecated", new Object[0]);
    }

    public void setCollectProcessInfo(boolean z) {
        C2710b.m1518d().m1550g(z);
    }

    public void setFieldsNotReportInSensitiveMode(Set<SensitiveField> set, Set<String> set2) {
        HashSet hashSet = new HashSet();
        if (set != null && !set.isEmpty()) {
            Iterator<SensitiveField> it = set.iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().toStringValue());
            }
        }
        C2710b.m1518d().m1529a(hashSet, set2);
    }

    public void setImei(String str) {
        C2634g.m1115e().m1119b(str);
    }

    public void setImei2(String str) {
        C2634g.m1115e().m1121c(str);
    }

    public void setImmediateReport(IBeaconImmediateReport iBeaconImmediateReport) {
        this.f1700f = iBeaconImmediateReport;
    }

    public void setImsi(String str) {
        C2634g.m1115e().m1123d(str);
    }

    public void setIsSensitiveMode(boolean z) {
        C2710b.m1518d().m1537b(z);
    }

    public void setKvStorage(KeyValueStorage keyValueStorage) {
        SharedPreferencesC2686a.m1391a().m1394a(keyValueStorage);
    }

    public void setLogAble(boolean z) {
        C2695c.m1466a(z);
    }

    public void setLogger(BeaconLogger beaconLogger) {
        C2695c.m1460a(beaconLogger);
    }

    public void setMac(String str) {
        C2634g.m1115e().m1124e(str);
    }

    public void setMeid(String str) {
        C2634g.m1115e().m1126f(str);
    }

    public void setModel(String str) {
        C2634g.m1115e().m1128g(str);
    }

    public void setNetworkInfoEnable(boolean z) {
        C2710b.m1518d().m1548f(z);
    }

    public void setOAID(String str) {
        C2634g.m1115e().m1130h(str);
    }

    @Deprecated
    public void setOaid(String str) {
        C2634g.m1115e().m1130h(str);
        C2695c.m1468b("setOaid has been Deprecated, please use setOAID", new Object[0]);
    }

    public void setOmgID(String str) {
        C2630c.m1059c().m1071d(str);
    }

    public void setOpenID(String str, String str2) {
        HashMap map = new HashMap();
        map.put("i_c_ak", str);
        map.put("i_c_o_i", str2);
        C2612b.m991a().m999b(new C2613c(5, map));
    }

    public void setOstar(String str, String str2) {
        C2636i.m1145a(str, str2);
    }

    public void setOstarSdkVersion(String str) {
        C2636i.m1148b(str);
    }

    public void setQQ(String str) {
        C2629b.m1044a(str);
    }

    public void setStrictMode(boolean z) {
        C2700h.f1400a.set(z);
    }

    public void setSystemFileEnable(boolean z) {
        C2710b.m1518d().m1554i(z);
    }

    @Deprecated
    public void setUserID(String str) {
        setUserID(C2630c.m1059c().m1072e(), str);
    }

    public void setUserSampleEvents(String str, Map<String, Integer> map) {
        C2710b.m1518d().m1526a(str, map);
    }

    public void setWifiMacAddress(String str) {
        C2634g.m1115e().m1132i(str);
    }

    public void setWifiSSID(String str) {
        C2634g.m1115e().m1134j(str);
    }

    public synchronized void start(Context context, String str, BeaconConfig beaconConfig) throws BeaconInitException {
        if (this.f1699e) {
            m1737a(context, str, String.format("don't init beacon twice!! 1: %s, 2: %s", C2630c.m1059c().m1072e(), str), "206");
            return;
        }
        C2700h.m1488a("Context", context);
        boolean z = false;
        if (context == null) {
            C2695c.m1468b("fail to start beacon, context is null", new Object[0]);
            return;
        }
        Context applicationContext = context.getApplicationContext();
        this.f1698d = applicationContext;
        C2700h.m1488a("ApplicationContext", applicationContext);
        if (this.f1698d == null) {
            C2695c.m1468b("fail to start beacon, application context is null", new Object[0]);
            return;
        }
        C2630c.m1059c().m1064a(this.f1698d);
        C2700h.m1488a("AppKey", str);
        if (TextUtils.isEmpty(str)) {
            C2695c.m1468b("fail to start beacon, appkey is empty", new Object[0]);
            return;
        }
        C2630c.m1059c().m1069c(m1735a(context, str));
        Log.i("beacon", "logAble: " + C2695c.m1467a() + " , SDKVersion: " + getSDKVersion());
        C2624j.m1031e().m1026a(beaconConfig != null && beaconConfig.isForceEnableAtta());
        C2623i c2623iM1030e = C2623i.m1030e();
        if (beaconConfig != null && beaconConfig.isForceEnableAtta()) {
            z = true;
        }
        c2623iM1030e.m1026a(z);
        ((Application) this.f1698d).registerActivityLifecycleCallbacks(new C2637a());
        m1742c(beaconConfig);
        if (beaconConfig != null) {
            m1743d(beaconConfig);
            C2636i.m1146a(beaconConfig.isNeedInitOstar());
            C2710b.m1518d().m1546e(beaconConfig.needReportRqdEvent());
            C2710b.m1518d().m1541c(beaconConfig.needAttaReport());
            C2710b.m1518d().m1530a(beaconConfig.isHighPerformanceMode());
            C2710b.m1518d().m1543d(beaconConfig.isNeedLifeCycleListener());
            C2710b.m1518d().m1535b(beaconConfig.getLongConnectionType());
            C2710b.m1518d().m1551h(beaconConfig.getRetryAfterQuicFailImme());
        }
        AbstractC2616b.m1001a().mo1011a(new RunnableC2744b(this, beaconConfig));
        this.f1699e = true;
        if (beaconConfig != null) {
            C2695c.m1462a(str, context.getPackageName());
        }
    }

    @Deprecated
    public void stopReport(boolean z) {
        pauseUpload(z);
    }

    public void getOstar(String str, Context context, InterfaceC2633f interfaceC2633f) {
        C2636i.m1144a(str, context, interfaceC2633f);
    }

    public void setAdditionalParams(String str, Map<String, String> map) {
        HashMap map2 = new HashMap();
        map2.put("i_c_ad", new HashMap(map));
        map2.put("i_c_ak", str);
        C2612b.m991a().m999b(new C2613c(3, map2));
    }

    public boolean setChannelID(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        C2630c.m1059c().m1065a(str, str2);
        return true;
    }

    public void setUserID(String str, String str2) {
        HashMap map = new HashMap();
        map.put("i_c_ak", str);
        map.put("i_c_u_i", str2);
        C2612b.m991a().m999b(new C2613c(4, map));
    }

    /* JADX INFO: renamed from: a */
    private String m1735a(Context context, String str) throws BeaconInitException {
        String string;
        if (!BeaconReport.class.getPackage().getName().startsWith("com.tencent.beacon")) {
            return str;
        }
        try {
            string = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString("BEACON_MAIN_APPKEY");
        } catch (Exception unused) {
            C2695c.m1476e("we strongly recommend you add mainAppkey to manifest file.", new Object[0]);
            string = "";
        }
        if (TextUtils.isEmpty(string)) {
            return str;
        }
        if (!str.equals(string)) {
            m1737a(context, str, "appKey in manifest.xml and start-params is conflicted", "205");
        }
        return string;
    }

    /* JADX INFO: renamed from: a */
    private void m1737a(Context context, String str, String str2, String str3) throws BeaconInitException {
        if (!C2695c.m1467a()) {
            Log.e("BeaconReport", str2);
            C2624j.m1031e().m1022a(context, str, str3, str2);
            return;
        }
        throw new BeaconInitException(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1738a(BeaconConfig beaconConfig) {
        if (beaconConfig != null) {
            C2695c.m1463a("BeaconReport", beaconConfig.toString(), new Object[0]);
            C2667b.m1320a(beaconConfig.getConfigHost(), beaconConfig.getUploadHost());
            m1741b(beaconConfig);
            C2630c.m1059c().m1066a(beaconConfig.isEnableQmsp());
            C2710b.m1518d().m1542d(beaconConfig.getRealtimeUploadNum());
            C2710b.m1518d().m1540c(beaconConfig.getNormalUploadNum());
            C2710b.m1518d().m1524a(beaconConfig.getCompressType());
            C2710b.m1518d().m1545e(beaconConfig.getRsaPubKeyType());
            C2710b.m1518d().m1556j(beaconConfig.isUseAccurateDpi());
        }
        C2671c.m1351d().m1357a(this.f1698d, beaconConfig == null ? null : beaconConfig.getHttpAdapter());
        SharedPreferencesC2686a.m1391a().m1393a(this.f1698d);
        C2629b.m1054f();
        C2632e.m1082l().m1085C();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a */
    public void m1736a() {
        ModuleName[] moduleNameArrValues = ModuleName.values();
        for (ModuleName moduleName : moduleNameArrValues) {
            try {
                BeaconModule.f1707a.put(moduleName, C2732d.m1646f(moduleName.getClassName()));
            } catch (Exception e) {
                C2695c.m1468b("init Module error: " + e.getMessage(), new Object[0]);
                C2695c.m1465a(e);
            }
        }
        for (ModuleName moduleName2 : moduleNameArrValues) {
            BeaconModule beaconModule = BeaconModule.f1707a.get(moduleName2);
            if (beaconModule != null) {
                beaconModule.mo1746a(this.f1698d);
            }
        }
        this.f1702h = (EventModule) C2630c.m1059c().m1060a(ModuleName.EVENT);
    }
}
