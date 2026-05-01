package com.tencent.beacon.event;

import android.content.Context;
import android.util.Log;
import com.tencent.beacon.base.net.p021b.C2667b;
import com.tencent.beacon.base.util.C2695c;
import com.tencent.beacon.core.info.BeaconPubParams;
import com.tencent.beacon.event.open.BeaconConfig;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.open.BeaconInitException;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.event.open.EventType;
import com.tencent.beacon.event.p031c.C2732d;
import com.tencent.beacon.module.EventModule;
import com.tencent.beacon.module.ModuleName;
import com.tencent.beacon.p015a.p018c.C2630c;
import com.tencent.beacon.p015a.p018c.C2632e;
import com.tencent.beacon.p015a.p018c.C2634g;
import com.tencent.beacon.p026c.C2702a;
import com.tencent.beacon.upload.InitHandleListener;
import com.tencent.beacon.upload.TunnelInfo;
import com.tencent.beacon.upload.UploadHandleListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class UserAction {

    /* JADX INFO: renamed from: a */
    private static String f1506a;

    /* JADX INFO: renamed from: c */
    private static String f1508c;
    public static Context mContext;

    /* JADX INFO: renamed from: b */
    private static BeaconConfig.Builder f1507b = BeaconConfig.builder();

    /* JADX INFO: renamed from: d */
    private static boolean f1509d = true;

    @Deprecated
    public static void closeUseInfoEvent() {
    }

    public static void doUploadRecords() {
        EventModule eventModule = (EventModule) C2630c.m1059c().m1060a(ModuleName.EVENT);
        if (eventModule != null) {
            eventModule.m1759a(false);
        }
    }

    public static void flushObjectsToDB(boolean z) {
    }

    public static Map<String, String> getAdditionalInfo() {
        return getAdditionalInfo(null);
    }

    public static String getAppKey() {
        return f1506a;
    }

    public static String getCloudParas(String str) {
        return "";
    }

    public static BeaconPubParams getCommonParams() {
        return BeaconReport.getInstance().getCommonParams(mContext);
    }

    public static String getEventDomain() {
        return C2667b.f1312c;
    }

    public static String getOpenID(String str) {
        EventModule eventModule = (EventModule) C2630c.m1059c().m1060a(ModuleName.EVENT);
        return eventModule != null ? eventModule.m1760b(str) : "";
    }

    public static String getSDKVersion() {
        return BeaconReport.getInstance().getSDKVersion();
    }

    public static String getStrategyDomain() {
        return C2667b.f1313d;
    }

    public static String getUserID(String str) {
        EventModule eventModule = (EventModule) C2630c.m1059c().m1060a(ModuleName.EVENT);
        return eventModule != null ? eventModule.m1763c(str) : "";
    }

    public static void initUserAction(Context context) throws BeaconInitException {
        initUserAction(context, true);
    }

    public static boolean loginEvent(boolean z, long j, Map<String, String> map) {
        if (mContext != null) {
            map.put("A19", C2632e.m1082l().m1105q());
        }
        return onUserAction("rqd_wgLogin", z, j, 0L, map, true);
    }

    public static boolean onDTUserAction(Context context, String str, boolean z, long j, long j2, Map<String, String> map, boolean z2, boolean z3) {
        if (context == null) {
            return false;
        }
        if (map == null) {
            return onUserAction(str, z, j, j2, null, z2, z3);
        }
        C2630c.m1059c().m1064a(context);
        C2632e c2632eM1082l = C2632e.m1082l();
        C2634g c2634gM1115e = C2634g.m1115e();
        HashMap map2 = new HashMap(map);
        map2.put("dt_imei2", "" + c2634gM1115e.m1120c());
        map2.put("dt_meid", "" + c2634gM1115e.m1127g());
        map2.put("dt_mf", "" + c2632eM1082l.m1103o());
        return onUserAction(str, z, j, j2, map2, z2, z3);
    }

    public static boolean onDTUserActionToTunnel(Context context, String str, String str2, Map<String, String> map, boolean z, boolean z2) {
        if (context == null) {
            return false;
        }
        if (map == null) {
            return onUserActionToTunnel(str, str2, null, z, z2);
        }
        C2630c.m1059c().m1064a(context);
        C2632e c2632eM1082l = C2632e.m1082l();
        C2634g c2634gM1115e = C2634g.m1115e();
        HashMap map2 = new HashMap(map);
        map2.put("dt_imei2", "" + c2634gM1115e.m1120c());
        map2.put("dt_meid", "" + c2634gM1115e.m1127g());
        map2.put("dt_mf", "" + c2632eM1082l.m1103o());
        return onUserActionToTunnel(str, str2, map2, z, z2);
    }

    public static void onPageIn(String str) {
        C2702a.m1490a(C2732d.m1644d(str));
    }

    public static void onPageOut(String str) {
        C2702a.m1491b(C2732d.m1644d(str));
    }

    public static boolean onUserAction(String str, boolean z, long j, long j2, Map<String, String> map, boolean z2) {
        return onUserAction(str, z, j, j2, map, z2, false);
    }

    public static boolean onUserActionToTunnel(String str, String str2, boolean z, long j, long j2, Map<String, String> map, boolean z2, boolean z3) {
        return BeaconReport.getInstance().report(BeaconEvent.builder().withCode(str2).withType(z2 ? EventType.REALTIME : EventType.NORMAL).withParams(map).withAppKey(str).withIsSucceed(z).build()).isSuccess();
    }

    public static void registerTunnel(TunnelInfo tunnelInfo) {
    }

    public static void setAdditionalInfo(String str, Map<String, String> map) {
        BeaconReport.getInstance().setAdditionalParams(str, map);
    }

    public static void setAppKey(String str) {
        f1506a = str;
    }

    public static void setAppVersion(String str) {
        f1508c = str;
    }

    @Deprecated
    public static void setAutoLaunchEventUsable(boolean z) {
    }

    public static void setChannelID(String str) {
        BeaconReport.getInstance().setChannelID(str);
    }

    public static void setJsClientId(String str) {
    }

    public static void setLogAble(boolean z, boolean z2) {
        C2695c.m1466a(z);
        C2695c.m1469b(z);
    }

    public static void setOAID(String str) {
        BeaconReport.getInstance().setOAID(str);
    }

    @Deprecated
    public static void setOldInitMethodEnable(boolean z) {
        f1509d = z;
    }

    public static void setOmgId(String str) {
        BeaconReport.getInstance().setOmgID(str);
    }

    public static void setOpenID(String str) {
        BeaconReport.getInstance().setOpenID(null, str);
    }

    public static void setQQ(String str) {
        BeaconReport.getInstance().setQQ(str);
    }

    public static void setReportDomain(String str, String str2) {
        C2667b.m1320a(str, str2);
    }

    public static void setReportIP(String str, String str2) {
        C2667b.m1323b(str, str2);
    }

    public static void setScheduledService(ScheduledExecutorService scheduledExecutorService) {
        f1507b.setExecutorService(scheduledExecutorService);
    }

    public static void setStrictMode(boolean z) {
        BeaconReport.getInstance().setStrictMode(z);
    }

    public static void setUploadMode(boolean z) {
        if (z) {
            BeaconReport.getInstance().pauseUpload(true);
        } else {
            BeaconReport.getInstance().resumeUpload();
        }
    }

    public static void setUserID(String str, String str2) {
        BeaconReport.getInstance().setUserID(str, str2);
    }

    public static Map<String, String> getAdditionalInfo(String str) {
        EventModule eventModule = (EventModule) C2630c.m1059c().m1060a(ModuleName.EVENT);
        if (eventModule != null) {
            return eventModule.m1756a(str);
        }
        return null;
    }

    public static void initUserAction(Context context, boolean z) throws BeaconInitException {
        initUserAction(context, z, 0L);
    }

    public static boolean onUserAction(String str, Map<String, String> map, boolean z, boolean z2) {
        return onUserAction(str, true, -1L, -1L, map, z, z2);
    }

    public static void setAdditionalInfo(Map<String, String> map) {
        setAdditionalInfo(null, map);
    }

    public static void setUserID(String str) {
        setUserID(null, str);
    }

    public static void initUserAction(Context context, boolean z, long j) throws BeaconInitException {
        initUserAction(context, z, j, null, null);
    }

    public static boolean onUserAction(String str, boolean z, long j, long j2, Map<String, String> map, boolean z2, boolean z3) {
        return BeaconReport.getInstance().report(BeaconEvent.builder().withCode(str).withType(z2 ? EventType.REALTIME : EventType.NORMAL).withParams(map).withAppKey(f1506a).withIsSucceed(z).build()).isSuccess();
    }

    public static void initUserAction(Context context, boolean z, long j, InitHandleListener initHandleListener, UploadHandleListener uploadHandleListener) throws BeaconInitException {
        if (f1509d) {
            mContext = context;
            BeaconReport.getInstance().start(context, f1506a, f1507b.build());
        } else {
            Log.e("beacon", "UserAction.initUserAction is not available");
        }
    }

    public static boolean onUserActionToTunnel(String str, String str2, Map<String, String> map, boolean z, boolean z2) {
        return onUserActionToTunnel(str, str2, true, -1L, -1L, map, z, z2);
    }
}
