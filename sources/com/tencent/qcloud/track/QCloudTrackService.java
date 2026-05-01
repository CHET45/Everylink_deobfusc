package com.tencent.qcloud.track;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.tencent.beacon.event.open.BeaconConfig;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.qcloud.track.service.ATrackService;
import com.tencent.qcloud.track.service.BeaconTrackService;
import com.tencent.qcloud.track.service.ClsTrackService;
import com.tencent.qcloud.track.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* JADX INFO: loaded from: classes4.dex */
public class QCloudTrackService {
    private static final String EVENT_KEY_CLS_REPORT = "cls_report";
    private static final String TAG = "TrackService";
    private static volatile QCloudTrackService instance;
    private Map<String, String> businessParams;
    private Map<String, String> commonParams;
    private Context context;
    private BeaconTrackService simpleDataTrackService;
    private boolean isInitialized = false;
    private final Map<String, List<ATrackService>> trackServiceMap = new HashMap();

    private QCloudTrackService() {
    }

    public static QCloudTrackService getInstance() {
        if (instance == null) {
            synchronized (QCloudTrackService.class) {
                if (instance == null) {
                    instance = new QCloudTrackService();
                }
            }
        }
        return instance;
    }

    public synchronized void addTrackService(String str, ATrackService aTrackService) {
        if (this.trackServiceMap.get(str) == null) {
            this.trackServiceMap.put(str, new ArrayList());
        }
        this.trackServiceMap.get(str).add(aTrackService);
    }

    public synchronized void init(Context context) {
        try {
            if (!this.isInitialized) {
                this.context = context.getApplicationContext();
                this.commonParams = getCommonParams();
                if (BeaconTrackService.isInclude()) {
                    initBeacon();
                    BeaconTrackService beaconTrackService = new BeaconTrackService();
                    this.simpleDataTrackService = beaconTrackService;
                    beaconTrackService.setContext(context);
                    this.simpleDataTrackService.init(Constants.SIMPLE_DATA_BEACON_APP_KEY);
                } else {
                    Log.i(TAG, "The beacon library is not referenced, cancel the beacon initialization");
                }
                this.isInitialized = true;
            } else {
                Log.d(TAG, "init has been called and the initialization code will not be executed again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void report(String str, Map<String, String> map) {
        try {
            Map<String, String> map2 = this.commonParams;
            if (map2 != null && map2.size() > 0) {
                map.putAll(this.commonParams);
            }
            Map<String, String> map3 = this.businessParams;
            if (map3 != null && map3.size() > 0) {
                map.putAll(this.businessParams);
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    map.put(entry.getKey(), "null");
                }
            }
            List<ATrackService> list = this.trackServiceMap.get(str);
            if (list != null) {
                Iterator<ATrackService> it = list.iterator();
                while (it.hasNext()) {
                    it.next().report(str, map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reportSimpleData(String str, Map<String, String> map) {
        if (this.simpleDataTrackService == null) {
            return;
        }
        try {
            Map<String, String> map2 = this.commonParams;
            if (map2 != null && map2.size() > 0) {
                map.putAll(this.commonParams);
            }
            Map<String, String> map3 = this.businessParams;
            if (map3 != null && map3.size() > 0) {
                map.putAll(this.businessParams);
            }
            map.put(EVENT_KEY_CLS_REPORT, String.valueOf(ClsTrackService.isInclude()));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue() == null) {
                    map.put(entry.getKey(), "null");
                }
            }
            this.simpleDataTrackService.report(str, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBusinessParams(Map<String, String> map) {
        this.businessParams = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.businessParams.put("business_" + entry.getKey(), entry.getValue());
        }
    }

    public void setIsCloseReport(boolean z) {
        BeaconTrackService beaconTrackService = this.simpleDataTrackService;
        if (beaconTrackService != null) {
            beaconTrackService.setIsCloseReport(z);
        }
        Iterator<String> it = this.trackServiceMap.keySet().iterator();
        while (it.hasNext()) {
            List<ATrackService> list = this.trackServiceMap.get(it.next());
            if (list != null) {
                Iterator<ATrackService> it2 = list.iterator();
                while (it2.hasNext()) {
                    it2.next().setIsCloseReport(z);
                }
            }
        }
    }

    public void setDebug(boolean z) {
        BeaconTrackService beaconTrackService = this.simpleDataTrackService;
        if (beaconTrackService != null) {
            beaconTrackService.setDebug(z);
        }
        Iterator<String> it = this.trackServiceMap.keySet().iterator();
        while (it.hasNext()) {
            List<ATrackService> list = this.trackServiceMap.get(it.next());
            if (list != null) {
                Iterator<ATrackService> it2 = list.iterator();
                while (it2.hasNext()) {
                    it2.next().setDebug(z);
                }
            }
        }
    }

    private void initBeacon() {
        BeaconConfig beaconConfigBuild = BeaconConfig.builder().auditEnable(false).bidEnable(false).qmspEnable(false).pagePathEnable(false).setNormalPollingTime(30000L).build();
        BeaconReport beaconReport = BeaconReport.getInstance();
        try {
            beaconReport.setCollectProcessInfo(false);
        } catch (NoSuchMethodError unused) {
        }
        try {
            String safeDeviceId = getSafeDeviceId();
            beaconReport.setOstar(safeDeviceId, safeDeviceId);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (NoSuchMethodError unused2) {
        }
        try {
            beaconReport.start(this.context, Constants.SIMPLE_DATA_BEACON_APP_KEY, beaconConfigBuild);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            beaconReport.setCollectProcessInfo(false);
        } catch (NoSuchMethodError unused3) {
        }
    }

    private Map<String, String> getCommonParams() {
        long longVersionCode;
        HashMap map = new HashMap();
        String packageName = this.context.getPackageName();
        map.put("boundle_id", packageName);
        try {
            PackageInfo packageInfo = this.context.getPackageManager().getPackageInfo(packageName, 0);
            String str = packageInfo.versionName;
            if (Build.VERSION.SDK_INT >= 28) {
                longVersionCode = packageInfo.getLongVersionCode();
            } else {
                longVersionCode = packageInfo.versionCode;
            }
            map.put("app_version_code", String.valueOf(longVersionCode));
            map.put("app_version_name", str);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("os_name", "Android");
        map.put("os_version", Build.VERSION.RELEASE);
        map.put("client_local_ip", NetworkUtils.getLocalMachineIP());
        map.put("client_proxy", String.valueOf(NetworkUtils.isProxy()));
        map.put("network_type", NetworkUtils.getNetworkType(this.context));
        return map;
    }

    public String getSafeDeviceId() {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("cossdk_device_info", 0);
        String string = sharedPreferences.getString("device_id", null);
        if (string != null) {
            return string;
        }
        String strCreateNewId = createNewId();
        sharedPreferences.edit().putString("device_id", strCreateNewId).apply();
        return strCreateNewId;
    }

    private String createNewId() {
        String string = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
        return (string == null || string.isEmpty()) ? UUID.randomUUID().toString() : string;
    }
}
