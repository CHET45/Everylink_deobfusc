package com.tencent.qcloud.core.logger.channel;

import android.util.Log;
import com.tencent.qcloud.core.logger.LogEntity;
import com.tencent.qcloud.track.service.ClsTrackService;
import java.util.HashMap;

/* JADX INFO: loaded from: classes4.dex */
public class ClsChannel extends BaseLogChannel {
    private static final String EVENT_CODE_TRACK_COS_SDK_LOG = "qcloud_track_cos_sdk_log";
    private final ClsTrackService clsTrackService;

    public ClsChannel(ClsTrackService clsTrackService) {
        this.clsTrackService = clsTrackService;
    }

    @Override // com.tencent.qcloud.core.logger.channel.BaseLogChannel
    public void log(LogEntity logEntity) {
        if (isLoggable(logEntity)) {
            HashMap map = new HashMap(logEntity.getExtras());
            map.put("timestamp", String.valueOf(logEntity.getTimestamp()));
            map.put("level", logEntity.getLevel().name());
            map.put("category", logEntity.getCategory().name());
            map.put("tag", logEntity.getTag());
            map.put("message", logEntity.getMessage());
            map.put("threadName", logEntity.getThreadName());
            if (logEntity.getThrowable() != null) {
                map.put("throwable", Log.getStackTraceString(logEntity.getThrowable()));
            }
            this.clsTrackService.report(EVENT_CODE_TRACK_COS_SDK_LOG, map);
        }
    }

    private boolean isLoggable(LogEntity logEntity) {
        if (!isEnabled() || logEntity == null) {
            return false;
        }
        return logEntity.getLevel().isLoggable(getMinLevel());
    }
}
