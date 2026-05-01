package com.tencent.qcloud.track;

import android.content.Context;
import android.os.Build;
import com.tencent.qcloud.track.utils.NetworkUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class PrivacyService {
    public static Map<String, String> getPrivacyParams(Context context) {
        HashMap map = new HashMap();
        map.put("os_version", Build.VERSION.RELEASE);
        map.put("client_local_ip", NetworkUtils.getLocalMachineIP());
        map.put("network_type", NetworkUtils.getNetworkType(context));
        return map;
    }

    public static void closeReport() {
        QCloudTrackService.getInstance().setIsCloseReport(true);
    }
}
