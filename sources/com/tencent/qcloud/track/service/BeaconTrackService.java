package com.tencent.qcloud.track.service;

import android.content.Context;
import android.util.Log;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import com.tencent.beacon.event.open.BeaconEvent;
import com.tencent.beacon.event.open.BeaconReport;
import com.tencent.beacon.event.open.EventResult;
import com.tencent.beacon.event.open.EventType;
import com.tencent.qcloud.track.Constants;
import com.tencent.qcloud.track.TrackExecutors;
import com.tencent.qcloud.track.utils.EventFileUtils;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class BeaconTrackService extends ATrackService {
    private static final String TAG = "BeaconTrackService";
    private String beaconKey;
    private Context context;

    public void init(String str) {
        if (this.isInit) {
            return;
        }
        this.isInit = true;
        this.beaconKey = str;
        if (isInclude()) {
            BeaconReport.getInstance().setLogAble(isDebug());
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private boolean isSimpleDataTrackService() {
        return Constants.SIMPLE_DATA_BEACON_APP_KEY.equals(this.beaconKey);
    }

    @Override // com.tencent.qcloud.track.IReport
    public void report(final String str, final Map<String, String> map) {
        if (!this.isInit && !isSimpleDataTrackService()) {
            TrackExecutors.COMMAND_EXECUTOR.execute(new Runnable() { // from class: com.tencent.qcloud.track.service.BeaconTrackService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.m1878xf7128096(str, map);
                }
            });
        }
        if (!isCloseReport() && this.isInit && isInclude()) {
            if (!isSimpleDataTrackService() && EventFileUtils.eventFileExists(this.context)) {
                TrackExecutors.COMMAND_EXECUTOR.execute(new Runnable() { // from class: com.tencent.qcloud.track.service.BeaconTrackService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() throws Throwable {
                        this.f$0.m1879x844d3217();
                    }
                });
            }
            directReport(str, map);
        }
    }

    /* JADX INFO: renamed from: lambda$report$0$com-tencent-qcloud-track-service-BeaconTrackService */
    /* synthetic */ void m1878xf7128096(String str, Map map) throws Throwable {
        EventFileUtils.appendEventToFile(this.context, str, map);
    }

    /* JADX INFO: renamed from: lambda$report$1$com-tencent-qcloud-track-service-BeaconTrackService */
    /* synthetic */ void m1879x844d3217() throws Throwable {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        EventFileUtils.readEventFromFile(this.context, arrayList, arrayList2);
        for (int i = 0; i < arrayList.size(); i++) {
            directReport((String) arrayList.get(i), (Map) arrayList2.get(i));
        }
    }

    private void directReport(String str, Map<String, String> map) {
        EventResult eventResultReport = BeaconReport.getInstance().report(BeaconEvent.builder().withAppKey(this.beaconKey).withCode(str).withType(EventType.NORMAL).withParams(map).build());
        if (isDebug()) {
            StringBuilder sb = new StringBuilder("{");
            for (String str2 : map.keySet()) {
                sb.append(str2 + PunctuationConst.EQUAL + map.get(str2) + ", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX);
            Log.i(TAG, String.format("beaconKey: %s, eventCode: %s, params: %s => result{ eventID: %s, errorCode: %d, errorMsg: %s}", this.beaconKey, str, sb, Long.valueOf(eventResultReport.eventID), Integer.valueOf(eventResultReport.errorCode), eventResultReport.errMsg));
        }
    }

    @Override // com.tencent.qcloud.track.service.ATrackService
    public void setDebug(boolean z) {
        super.setDebug(z);
        if (isInclude()) {
            BeaconReport.getInstance().setLogAble(z);
        }
    }

    public static boolean isInclude() {
        try {
            Class.forName("com.tencent.beacon.event.open.BeaconReport");
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }
}
