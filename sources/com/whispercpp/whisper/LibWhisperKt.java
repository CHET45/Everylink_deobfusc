package com.whispercpp.whisper;

import android.os.Build;
import android.util.Log;
import com.blankj.utilcode.constant.TimeConstants;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.p036io.CloseableKt;
import kotlin.p036io.TextStreamsKt;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: LibWhisper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(m1900d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\u001a\n\u0010\u0002\u001a\u0004\u0018\u00010\u0001H\u0002\u001a\b\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\b\u0010\u0005\u001a\u00020\u0004H\u0002\u001a\u001a\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0004H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, m1901d2 = {"LOG_TAG", "", "cpuInfo", "isArmEabiV7a", "", "isArmEabiV8a", "toTimestamp", "t", "", "comma", "lib_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class LibWhisperKt {
    private static final String LOG_TAG = "LibWhisper";

    static /* synthetic */ String toTimestamp$default(long j, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return toTimestamp(j, z);
    }

    private static final String toTimestamp(long j, boolean z) {
        long j2 = j * ((long) 10);
        long j3 = TimeConstants.HOUR;
        long j4 = j2 / j3;
        long j5 = j2 - (j3 * j4);
        long j6 = 60000;
        long j7 = j5 / j6;
        long j8 = j5 - (j6 * j7);
        long j9 = 1000;
        long j10 = j8 / j9;
        long j11 = j8 - (j9 * j10);
        String str = z ? PunctuationConst.COMMA : ".";
        StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
        String str2 = String.format("%02d:%02d:%02d%s%03d", Arrays.copyOf(new Object[]{Long.valueOf(j4), Long.valueOf(j7), Long.valueOf(j10), str, Long.valueOf(j11)}, 5));
        Intrinsics.checkNotNullExpressionValue(str2, "format(format, *args)");
        return str2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isArmEabiV7a() {
        return Build.SUPPORTED_ABIS[0].equals("armeabi-v7a");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isArmEabiV8a() {
        return Build.SUPPORTED_ABIS[0].equals("arm64-v8a");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String cpuInfo() {
        try {
            Reader inputStreamReader = new InputStreamReader(new FileInputStream(new File("/proc/cpuinfo")), Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                String text = TextStreamsKt.readText(bufferedReader);
                CloseableKt.closeFinally(bufferedReader, null);
                return text;
            } finally {
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, "Couldn't read /proc/cpuinfo", e);
            return null;
        }
    }
}
