package com.aivox.jieliota.tool.config;

import android.content.Context;
import android.content.SharedPreferences;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.jieliota.util.OtaConstant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConfigHelper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b$\b\u0016\u0018\u0000 32\u00020\u0001:\u00013B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u0011\u001a\u00020\rJ\b\u0010\u0012\u001a\u0004\u0018\u00010\u0010J\u0006\u0010\u0013\u001a\u00020\u000bJ\u0006\u0010\u0014\u001a\u00020\u000bJ\u0006\u0010\u0015\u001a\u00020\u000bJ\u0006\u0010\u0016\u001a\u00020\u000bJ\u0006\u0010\u0017\u001a\u00020\u000bJ\u0006\u0010\u0018\u001a\u00020\u000bJ\u0006\u0010\u0019\u001a\u00020\u000bJ\u0006\u0010\u001a\u001a\u00020\u000bJ\u0006\u0010\u001b\u001a\u00020\u000bJ\u000e\u0010\u001c\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000bJ\u000e\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\rJ\u0010\u0010\u001f\u001a\u00020\t2\b\b\u0001\u0010 \u001a\u00020\rJ\u000e\u0010!\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\u000bJ\u0010\u0010#\u001a\u00020\t2\b\u0010$\u001a\u0004\u0018\u00010\u0010J\u000e\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u000bJ\u000e\u0010'\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\u000bJ\u000e\u0010(\u001a\u00020\t2\u0006\u0010\u001e\u001a\u00020\rJ\u000e\u0010)\u001a\u00020\t2\u0006\u0010*\u001a\u00020\u000bJ\u0010\u0010+\u001a\u00020\t2\b\u0010,\u001a\u0004\u0018\u00010\u0010J\u000e\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\u000bJ\u000e\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\u000bJ\u000e\u00101\u001a\u00020\t2\u0006\u00102\u001a\u00020\u000bR\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00064"}, m1901d2 = {"Lcom/aivox/jieliota/tool/config/ConfigHelper;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "preferences", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "enableBroadcastBox", "", "enable", "", "getAutoTestCount", "", "getBleRequestMtu", "getCustomSppChannel", "", "getFaultTolerantCount", "getScanFilter", "isAutoTest", "isBleWay", "isDevelopMode", "isEnableBroadcastBox", "isFaultTolerant", "isHidDevice", "isUseCustomReConnectWay", "isUseDeviceAuth", "isUseMultiSppChannel", "setAutoTest", "setAutoTestCount", "count", "setBleRequestMtu", "mtu", "setBleWay", "isBle", "setCustomSppChannel", "uuid", "setDevelopMode", "developMode", "setFaultTolerant", "setFaultTolerantCount", "setHidDevice", "isHid", "setScanFilter", "scanFilter", "setUseCustomReConnectWay", "isCustom", "setUseDeviceAuth", "isAuth", "setUseMultiSppChannel", "isUseMulti", "Companion", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public class ConfigHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String KEY_AUTO_TEST_COUNT = "auto_test_count";
    private static final String KEY_AUTO_TEST_OTA = "auto_test_ota";
    private static final String KEY_BLE_MTU_VALUE = "ble_mtu_value";
    private static final String KEY_BROADCAST_BOX = "broadcast_box_switch";
    private static final String KEY_COMMUNICATION_WAY = "communication_way";
    private static final String KEY_DEVELOP_MODE = "develop_mode";
    private static final String KEY_DOWNLOAD_URI = "download_uri";
    private static final String KEY_FAULT_TOLERANT = "fault_tolerant";
    private static final String KEY_FAULT_TOLERANT_COUNT = "fault_tolerant_count";
    private static final String KEY_IS_HID_DEVICE = "is_hid_device";
    private static final String KEY_IS_USE_DEVICE_AUTH = "is_use_device_auth";
    private static final String KEY_SCAN_FILTER_STRING = "scan_filter_string";
    private static final String KEY_SPP_CUSTOM_UUID = "spp_custom_uuid";
    private static final String KEY_SPP_MULTIPLE_CHANNEL = "spp_multiple_channel";
    private static final String KEY_USE_CUSTOM_RECONNECT_WAY = "use_custom_reconnect_way";
    private static volatile ConfigHelper instance;
    private final SharedPreferences preferences;

    public /* synthetic */ ConfigHelper(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    private ConfigHelper(Context context) {
        this.preferences = context.getSharedPreferences("ota_config_data", 0);
    }

    /* JADX INFO: compiled from: ConfigHelper.kt */
    @Metadata(m1900d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u00148\u0002@\u0002X\u0083\u000e¢\u0006\u0002\n\u0000¨\u0006\u0016"}, m1901d2 = {"Lcom/aivox/jieliota/tool/config/ConfigHelper$Companion;", "", "()V", "KEY_AUTO_TEST_COUNT", "", "KEY_AUTO_TEST_OTA", "KEY_BLE_MTU_VALUE", "KEY_BROADCAST_BOX", "KEY_COMMUNICATION_WAY", "KEY_DEVELOP_MODE", "KEY_DOWNLOAD_URI", "KEY_FAULT_TOLERANT", "KEY_FAULT_TOLERANT_COUNT", "KEY_IS_HID_DEVICE", "KEY_IS_USE_DEVICE_AUTH", "KEY_SCAN_FILTER_STRING", "KEY_SPP_CUSTOM_UUID", "KEY_SPP_MULTIPLE_CHANNEL", "KEY_USE_CUSTOM_RECONNECT_WAY", "instance", "Lcom/aivox/jieliota/tool/config/ConfigHelper;", "getInstance", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ConfigHelper getInstance() {
            ConfigHelper configHelper = ConfigHelper.instance;
            if (configHelper == null) {
                synchronized (this) {
                    configHelper = ConfigHelper.instance;
                    if (configHelper == null) {
                        Context context = BaseAppUtils.getContext();
                        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
                        configHelper = new ConfigHelper(context, null);
                        Companion companion = ConfigHelper.INSTANCE;
                        ConfigHelper.instance = configHelper;
                    }
                }
            }
            return configHelper;
        }
    }

    public final boolean isBleWay() {
        return this.preferences.getInt(KEY_COMMUNICATION_WAY, 0) == 0;
    }

    public final void setBleWay(boolean isBle) {
        this.preferences.edit().putInt(KEY_COMMUNICATION_WAY, !isBle ? 1 : 0).apply();
    }

    public final boolean isUseDeviceAuth() {
        return this.preferences.getBoolean(KEY_IS_USE_DEVICE_AUTH, true);
    }

    public final void setUseDeviceAuth(boolean isAuth) {
        this.preferences.edit().putBoolean(KEY_IS_USE_DEVICE_AUTH, isAuth).apply();
    }

    public final boolean isHidDevice() {
        return this.preferences.getBoolean(KEY_IS_HID_DEVICE, false);
    }

    public final void setHidDevice(boolean isHid) {
        this.preferences.edit().putBoolean(KEY_IS_HID_DEVICE, isHid).apply();
    }

    public final boolean isUseCustomReConnectWay() {
        return this.preferences.getBoolean(KEY_USE_CUSTOM_RECONNECT_WAY, false);
    }

    public final void setUseCustomReConnectWay(boolean isCustom) {
        this.preferences.edit().putBoolean(KEY_USE_CUSTOM_RECONNECT_WAY, isCustom).apply();
    }

    public final int getBleRequestMtu() {
        return this.preferences.getInt(KEY_BLE_MTU_VALUE, 509);
    }

    public final void setBleRequestMtu(int mtu) {
        this.preferences.edit().putInt(KEY_BLE_MTU_VALUE, mtu).apply();
    }

    public final boolean isUseMultiSppChannel() {
        return this.preferences.getBoolean(KEY_SPP_MULTIPLE_CHANNEL, false);
    }

    public final void setUseMultiSppChannel(boolean isUseMulti) {
        this.preferences.edit().putBoolean(KEY_SPP_MULTIPLE_CHANNEL, isUseMulti).apply();
    }

    public final String getCustomSppChannel() {
        return this.preferences.getString(KEY_SPP_CUSTOM_UUID, OtaConstant.INSTANCE.getUUID_SPP().toString());
    }

    public final void setCustomSppChannel(String uuid) {
        this.preferences.edit().putString(KEY_SPP_CUSTOM_UUID, uuid).apply();
    }

    public final boolean isAutoTest() {
        return this.preferences.getBoolean(KEY_AUTO_TEST_OTA, false);
    }

    public final void setAutoTest(boolean isAutoTest) {
        this.preferences.edit().putBoolean(KEY_AUTO_TEST_OTA, isAutoTest).apply();
    }

    public final int getAutoTestCount() {
        return this.preferences.getInt(KEY_AUTO_TEST_COUNT, 30);
    }

    public final void setAutoTestCount(int count) {
        if (isAutoTest()) {
            this.preferences.edit().putInt(KEY_AUTO_TEST_COUNT, count).apply();
        }
    }

    public final boolean isFaultTolerant() {
        return this.preferences.getBoolean(KEY_FAULT_TOLERANT, false);
    }

    public final void setFaultTolerant(boolean isFaultTolerant) {
        this.preferences.edit().putBoolean(KEY_FAULT_TOLERANT, isFaultTolerant).apply();
    }

    public final int getFaultTolerantCount() {
        return this.preferences.getInt(KEY_FAULT_TOLERANT_COUNT, 1);
    }

    public final void setFaultTolerantCount(int count) {
        if (isFaultTolerant()) {
            this.preferences.edit().putInt(KEY_FAULT_TOLERANT_COUNT, count).apply();
        }
    }

    public final String getScanFilter() {
        return this.preferences.getString(KEY_SCAN_FILTER_STRING, "");
    }

    public final void setScanFilter(String scanFilter) {
        this.preferences.edit().putString(KEY_SCAN_FILTER_STRING, scanFilter).apply();
    }

    public final boolean isDevelopMode() {
        return this.preferences.getBoolean(KEY_DEVELOP_MODE, false);
    }

    public final void setDevelopMode(boolean developMode) {
        this.preferences.edit().putBoolean(KEY_DEVELOP_MODE, developMode).apply();
    }

    public final boolean isEnableBroadcastBox() {
        return this.preferences.getBoolean(KEY_BROADCAST_BOX, false);
    }

    public final void enableBroadcastBox(boolean enable) {
        this.preferences.edit().putBoolean(KEY_BROADCAST_BOX, enable).apply();
    }
}
