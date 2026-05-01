package com.tencent.qcloud.core.logger.channel;

import com.tencent.qcloud.core.logger.LogEntity;

/* JADX INFO: loaded from: classes4.dex */
public class ListenerChannel extends BaseLogChannel {
    private CosLogListener listener;

    public ListenerChannel(CosLogListener cosLogListener) {
        this.listener = cosLogListener;
    }

    @Override // com.tencent.qcloud.core.logger.channel.BaseLogChannel
    public void log(LogEntity logEntity) {
        if (isLoggable(logEntity)) {
            this.listener.onLog(logEntity);
        }
    }

    private boolean isLoggable(LogEntity logEntity) {
        if (!isEnabled() || logEntity == null) {
            return false;
        }
        return logEntity.getLevel().isLoggable(getMinLevel());
    }

    public CosLogListener getListener() {
        return this.listener;
    }
}
