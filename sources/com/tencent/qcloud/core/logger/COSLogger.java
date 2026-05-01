package com.tencent.qcloud.core.logger;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.qcloud.core.logger.channel.BaseLogChannel;
import com.tencent.qcloud.core.logger.channel.ClsChannel;
import com.tencent.qcloud.core.logger.channel.CosLogListener;
import com.tencent.qcloud.core.logger.channel.FileChannel;
import com.tencent.qcloud.core.logger.channel.ListenerChannel;
import com.tencent.qcloud.core.logger.channel.LogcatChannel;
import com.tencent.qcloud.core.util.ContextHolder;
import com.tencent.qcloud.track.cls.ClsLifecycleCredentialProvider;
import com.tencent.qcloud.track.service.ClsTrackService;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class COSLogger {
    private static COSLogger instance;
    private String appVersion;
    private final List<BaseLogChannel> channels;
    private final Context context;
    private String deviceID;
    private String deviceModel;
    private Map<String, String> extras;
    private final FileChannel fileChannel;
    private final LogcatChannel logcatChannel;
    private LogLevel minLevel;
    private final Map<String, String> sensitivePatterns;

    private COSLogger(boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        this.channels = arrayList;
        HashMap map = new HashMap();
        this.sensitivePatterns = map;
        this.extras = new HashMap();
        Context appContext = ContextHolder.getAppContext();
        this.context = appContext;
        this.minLevel = LogLevel.VERBOSE;
        LogcatChannel logcatChannel = new LogcatChannel();
        this.logcatChannel = logcatChannel;
        logcatChannel.setEnabled(z);
        arrayList.add(logcatChannel);
        FileChannel fileChannel = FileChannel.getInstance(appContext);
        this.fileChannel = fileChannel;
        fileChannel.setEnabled(z2);
        arrayList.add(fileChannel);
        map.put("(q-ak=)[^&\\\\s]+", "$1***");
        map.put("(x-cos-security-token:\\s*)[^\\s]+", "$1***");
    }

    protected static COSLogger getInstance() {
        COSLogger cOSLogger;
        synchronized (COSLogger.class) {
            if (instance == null) {
                instance = new COSLogger(false, false);
            }
            cOSLogger = instance;
        }
        return cOSLogger;
    }

    public void log(LogLevel logLevel, LogCategory logCategory, String str, String str2, Throwable th) {
        try {
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            if (TextUtils.isEmpty(str)) {
                str = "COSLogger";
            }
            String str3 = str;
            String strDesensitize = desensitize(str2);
            this.extras.put("qcloud_platform", "Android");
            if (!TextUtils.isEmpty(this.deviceID)) {
                this.extras.put("deviceID", this.deviceID);
            }
            if (!TextUtils.isEmpty(this.deviceModel)) {
                this.extras.put("deviceModel", this.deviceModel);
            }
            if (!TextUtils.isEmpty(this.appVersion)) {
                this.extras.put("appVersion", this.appVersion);
            }
            LogEntity logEntity = new LogEntity(logLevel, logCategory, str3, strDesensitize, this.extras, th);
            for (BaseLogChannel baseLogChannel : this.channels) {
                if (baseLogChannel.isEnabled()) {
                    LogLevel minLevel = baseLogChannel.getMinLevel();
                    if (minLevel == null) {
                        minLevel = this.minLevel;
                    }
                    if (logLevel.isLoggable(minLevel)) {
                        baseLogChannel.log(logEntity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dProcess(String str, String str2) {
        getInstance().log(LogLevel.DEBUG, LogCategory.PROCESS, str, str2, null);
    }

    public static void dProcess(String str, String str2, Object... objArr) {
        getInstance().log(LogLevel.DEBUG, LogCategory.PROCESS, str, formatMessage(str2, objArr), null);
    }

    public static void dProcess(String str, String str2, Throwable th) {
        getInstance().log(LogLevel.DEBUG, LogCategory.PROCESS, str, str2, th);
    }

    public static void iProcess(String str, String str2) {
        getInstance().log(LogLevel.INFO, LogCategory.PROCESS, str, str2, null);
    }

    public static void iProcess(String str, String str2, Object... objArr) {
        getInstance().log(LogLevel.INFO, LogCategory.PROCESS, str, formatMessage(str2, objArr), null);
    }

    public static void iProcess(String str, String str2, Throwable th) {
        getInstance().log(LogLevel.INFO, LogCategory.PROCESS, str, str2, th);
    }

    public static void iNetwork(String str, String str2) {
        getInstance().log(LogLevel.INFO, LogCategory.NETWORK, str, str2, null);
    }

    public static void iNetwork(String str, String str2, Object... objArr) {
        getInstance().log(LogLevel.INFO, LogCategory.NETWORK, str, formatMessage(str2, objArr), null);
    }

    public static void iProbe(String str, String str2) {
        getInstance().log(LogLevel.INFO, LogCategory.PROBE, str, str2, null);
    }

    public static void wProcess(String str, String str2) {
        getInstance().log(LogLevel.WARN, LogCategory.PROCESS, str, str2, null);
    }

    public static void wProcess(String str, String str2, Object... objArr) {
        getInstance().log(LogLevel.WARN, LogCategory.PROCESS, str, formatMessage(str2, objArr), null);
    }

    public static void wNetwork(String str, String str2) {
        getInstance().log(LogLevel.WARN, LogCategory.NETWORK, str, str2, null);
    }

    public static void wNetwork(String str, String str2, Object... objArr) {
        getInstance().log(LogLevel.WARN, LogCategory.NETWORK, str, formatMessage(str2, objArr), null);
    }

    private static String formatMessage(String str, Object... objArr) {
        if (objArr == null) {
            return str;
        }
        try {
            return objArr.length > 0 ? String.format(str, objArr) : str;
        } catch (Exception unused) {
            return str + ": !!!! Log format exception: ";
        }
    }

    public static void enableLogcat(boolean z) {
        getInstance().logcatChannel.setEnabled(z);
    }

    public static void enableLogFile(boolean z) {
        getInstance().fileChannel.setEnabled(z);
    }

    public static void addLogListener(CosLogListener cosLogListener) {
        getInstance().channels.add(new ListenerChannel(cosLogListener));
    }

    public static void removeLogListener(CosLogListener cosLogListener) {
        for (BaseLogChannel baseLogChannel : getInstance().channels) {
            if ((baseLogChannel instanceof ListenerChannel) && ((ListenerChannel) baseLogChannel).getListener() == cosLogListener) {
                getInstance().channels.remove(baseLogChannel);
                return;
            }
        }
    }

    public static void setMinLevel(LogLevel logLevel) {
        getInstance().minLevel = logLevel;
    }

    public static void setLogcatMinLevel(LogLevel logLevel) {
        getInstance().logcatChannel.setMinLevel(logLevel);
    }

    public static void setFileMinLevel(LogLevel logLevel) {
        getInstance().fileChannel.setMinLevel(logLevel);
    }

    public static void setClsMinLevel(LogLevel logLevel) {
        for (BaseLogChannel baseLogChannel : getInstance().channels) {
            if (baseLogChannel instanceof ClsChannel) {
                ((ClsChannel) baseLogChannel).setMinLevel(logLevel);
            }
        }
    }

    public static void setExtras(Map<String, String> map) {
        getInstance().extras = map;
    }

    public static void setDeviceID(String str) {
        getInstance().deviceID = str;
    }

    public static void setDeviceModel(String str) {
        getInstance().deviceModel = str;
    }

    public static void setAppVersion(String str) {
        getInstance().appVersion = str;
    }

    public static void setLogFileEncryptionKey(byte[] bArr, byte[] bArr2) {
        getInstance().fileChannel.setEncryptionKey(bArr, bArr2);
    }

    public static File[] getLogFiles(int i) {
        return getInstance().fileChannel.getLogFilesDesc(i);
    }

    public static String getLogRootDir() {
        return getInstance().fileChannel.getLogRootDir();
    }

    public static void setCLsChannel(String str, String str2) {
        if (!ClsTrackService.isInclude()) {
            throw new IllegalStateException("Please quote the cls library first: com.tencentcloudapi.cls:tencentcloud-cls-sdk-android:x.x.x");
        }
        ClsTrackService clsTrackService = new ClsTrackService();
        clsTrackService.init(getInstance().context, str, str2);
        clsTrackService.setSecurityCredential("secretId", "secretKey");
        getInstance().channels.add(new ClsChannel(clsTrackService));
    }

    public static void setCLsChannel(String str, String str2, String str3, String str4) {
        if (!ClsTrackService.isInclude()) {
            throw new IllegalStateException("Please quote the cls library first: com.tencentcloudapi.cls:tencentcloud-cls-sdk-android:x.x.x");
        }
        ClsTrackService clsTrackService = new ClsTrackService();
        clsTrackService.init(getInstance().context, str, str2);
        clsTrackService.setSecurityCredential(str3, str4);
        getInstance().channels.add(new ClsChannel(clsTrackService));
    }

    public static void setCLsChannel(String str, String str2, ClsLifecycleCredentialProvider clsLifecycleCredentialProvider) {
        if (!ClsTrackService.isInclude()) {
            throw new IllegalStateException("Please quote the cls library first: com.tencentcloudapi.cls:tencentcloud-cls-sdk-android:x.x.x");
        }
        ClsTrackService clsTrackService = new ClsTrackService();
        clsTrackService.init(getInstance().context, str, str2);
        clsTrackService.setCredentialProvider(clsLifecycleCredentialProvider);
        getInstance().channels.add(new ClsChannel(clsTrackService));
    }

    public static void addSensitiveRule(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return;
        }
        getInstance().sensitivePatterns.put(str, str2);
    }

    public static void removeSensitiveRule(String str) {
        getInstance().sensitivePatterns.remove(str);
    }

    private String desensitize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        for (String str2 : this.sensitivePatterns.keySet()) {
            str = str.replaceAll(str2, this.sensitivePatterns.get(str2));
        }
        return str;
    }
}
