package com.aivox.jieliota.util;

import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OtaConstant.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, m1901d2 = {"Lcom/aivox/jieliota/util/OtaConstant;", "", "()V", "Companion", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class OtaConstant {
    public static final boolean AUTO_FAULT_TOLERANT = false;
    public static final int AUTO_FAULT_TOLERANT_COUNT = 1;
    public static final int AUTO_TEST_COUNT = 30;
    public static final boolean AUTO_TEST_OTA = false;
    public static final int CURRENT_PROTOCOL = 0;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String DIR_LOGCAT = "logcat";
    public static final String DIR_ROOT = "JieLiOTA";
    public static final String DIR_UPGRADE = "upgrade";
    public static final boolean HID_DEVICE_WAY = false;
    public static final boolean IS_NEED_DEVICE_AUTH = true;
    public static final boolean NEED_CUSTOM_RECONNECT_WAY = false;
    public static final int PROTOCOL_BLE = 0;
    public static final int PROTOCOL_SPP = 1;
    public static final long SCAN_TIMEOUT = 16000;
    public static final boolean USE_SPP_MULTIPLE_CHANNEL = false;
    private static final UUID UUID_A2DP;
    private static final UUID UUID_SPP;

    /* JADX INFO: compiled from: OtaConstant.kt */
    @Metadata(m1900d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019¨\u0006\u001c"}, m1901d2 = {"Lcom/aivox/jieliota/util/OtaConstant$Companion;", "", "()V", "AUTO_FAULT_TOLERANT", "", "AUTO_FAULT_TOLERANT_COUNT", "", "AUTO_TEST_COUNT", "AUTO_TEST_OTA", "CURRENT_PROTOCOL", "DIR_LOGCAT", "", "DIR_ROOT", "DIR_UPGRADE", "HID_DEVICE_WAY", "IS_NEED_DEVICE_AUTH", "NEED_CUSTOM_RECONNECT_WAY", "PROTOCOL_BLE", "PROTOCOL_SPP", "SCAN_TIMEOUT", "", "USE_SPP_MULTIPLE_CHANNEL", "UUID_A2DP", "Ljava/util/UUID;", "getUUID_A2DP", "()Ljava/util/UUID;", "UUID_SPP", "getUUID_SPP", "jieliota_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final UUID getUUID_A2DP() {
            return OtaConstant.UUID_A2DP;
        }

        public final UUID getUUID_SPP() {
            return OtaConstant.UUID_SPP;
        }
    }

    static {
        UUID uuidFromString = UUID.fromString("0000110b-0000-1000-8000-00805f9b34fb");
        Intrinsics.checkNotNullExpressionValue(uuidFromString, "fromString(...)");
        UUID_A2DP = uuidFromString;
        UUID uuidFromString2 = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        Intrinsics.checkNotNullExpressionValue(uuidFromString2, "fromString(...)");
        UUID_SPP = uuidFromString2;
    }
}
