package com.tencent.qcloud.core.logger.channel;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.qcloud.core.logger.LogEntity;
import com.tencent.qcloud.core.logger.LogLevel;

/* JADX INFO: loaded from: classes4.dex */
public class LogcatChannel extends BaseLogChannel {
    @Override // com.tencent.qcloud.core.logger.channel.BaseLogChannel
    public void log(LogEntity logEntity) {
        if (isLoggable(logEntity)) {
            String logcatString = logEntity.getLogcatString();
            int i = C44811.$SwitchMap$com$tencent$qcloud$core$logger$LogLevel[logEntity.getLevel().ordinal()];
            if (i == 1) {
                m1872v(logEntity.getTag(), logcatString, logEntity.getThrowable());
                return;
            }
            if (i == 2) {
                m1869d(logEntity.getTag(), logcatString, logEntity.getThrowable());
                return;
            }
            if (i == 3) {
                m1871i(logEntity.getTag(), logcatString, logEntity.getThrowable());
            } else if (i == 4) {
                m1873w(logEntity.getTag(), logcatString, logEntity.getThrowable());
            } else {
                if (i != 5) {
                    return;
                }
                m1870e(logEntity.getTag(), logcatString, logEntity.getThrowable());
            }
        }
    }

    /* JADX INFO: renamed from: com.tencent.qcloud.core.logger.channel.LogcatChannel$1 */
    static /* synthetic */ class C44811 {
        static final /* synthetic */ int[] $SwitchMap$com$tencent$qcloud$core$logger$LogLevel;

        static {
            int[] iArr = new int[LogLevel.values().length];
            $SwitchMap$com$tencent$qcloud$core$logger$LogLevel = iArr;
            try {
                iArr[LogLevel.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tencent$qcloud$core$logger$LogLevel[LogLevel.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tencent$qcloud$core$logger$LogLevel[LogLevel.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$tencent$qcloud$core$logger$LogLevel[LogLevel.WARN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$tencent$qcloud$core$logger$LogLevel[LogLevel.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX INFO: renamed from: v */
    private int m1872v(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.v(str, str2);
        }
        return Log.v(str, str2, th);
    }

    /* JADX INFO: renamed from: d */
    private int m1869d(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.d(str, str2);
        }
        return Log.d(str, str2, th);
    }

    /* JADX INFO: renamed from: i */
    private int m1871i(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.i(str, str2);
        }
        return Log.i(str, str2, th);
    }

    /* JADX INFO: renamed from: w */
    private int m1873w(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.w(str, str2);
        }
        return Log.w(str, str2, th);
    }

    /* JADX INFO: renamed from: e */
    private int m1870e(String str, String str2, Throwable th) {
        if (th == null) {
            return Log.e(str, str2);
        }
        return Log.e(str, str2, th);
    }

    private boolean isLoggable(LogEntity logEntity) {
        if (!isEnabled() || logEntity == null || !logEntity.getLevel().isLoggable(getMinLevel()) || TextUtils.isEmpty(logEntity.getTag()) || logEntity.getTag().length() >= 23) {
            return false;
        }
        return Log.isLoggable(logEntity.getTag(), logEntity.getLevel().getPriority() + 1);
    }
}
