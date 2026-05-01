package com.aivox.base.exts;

import com.aivox.base.util.NumberUtils;
import java.math.RoundingMode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: StringExts.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a/\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007\u001a/\u0010\b\u001a\u00020\u0001*\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007\u001a/\u0010\t\u001a\u00020\u0001*\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007\u001a/\u0010\n\u001a\u00020\u0001*\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\u000b"}, m1901d2 = {"divide", "", "value", "precision", "", "mode", "Ljava/math/RoundingMode;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/math/RoundingMode;)Ljava/lang/String;", "minus", "multiply", "plus", "base_release"}, m1902k = 2, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class StringExtsKt {
    public static /* synthetic */ String plus$default(String str, String str2, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return plus(str, str2, num, roundingMode);
    }

    public static final String plus(String str, String str2, Integer num, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        String string = NumberUtils.plus(str, str2, num, mode).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static /* synthetic */ String minus$default(String str, String str2, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return minus(str, str2, num, roundingMode);
    }

    public static final String minus(String str, String str2, Integer num, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        String string = NumberUtils.minus(str, str2, num, mode).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static /* synthetic */ String multiply$default(String str, String str2, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return multiply(str, str2, num, roundingMode);
    }

    public static final String multiply(String str, String str2, Integer num, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        String string = NumberUtils.multiply(str, str2, num, mode).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static /* synthetic */ String divide$default(String str, String str2, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return divide(str, str2, num, roundingMode);
    }

    public static final String divide(String str, String str2, Integer num, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        Intrinsics.checkNotNullParameter(mode, "mode");
        String string = NumberUtils.divide(str, str2, num, mode).toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
