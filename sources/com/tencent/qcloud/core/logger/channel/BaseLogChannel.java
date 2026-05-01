package com.tencent.qcloud.core.logger.channel;

import com.tencent.qcloud.core.logger.LogEntity;
import com.tencent.qcloud.core.logger.LogLevel;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseLogChannel {
    private boolean enabled = true;
    private LogLevel minLevel = null;

    public abstract void log(LogEntity logEntity);

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public LogLevel getMinLevel() {
        return this.minLevel;
    }

    public void setMinLevel(LogLevel logLevel) {
        this.minLevel = logLevel;
    }
}
