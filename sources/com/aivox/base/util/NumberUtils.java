package com.aivox.base.util;

import android.widget.EditText;
import com.github.houbb.heaven.constant.CharConst;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: NumberUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(m1900d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J7\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\fJD\u0010\r\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\u0011\u001a\u00020\t2\b\b\u0002\u0010\u0012\u001a\u00020\u0006H\u0007J5\u0010\u0013\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\u0015J0\u0010\u0013\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007J+\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0007¢\u0006\u0002\u0010\u001cJ5\u0010\u001d\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u001e\u001a\u00020\t2\b\b\u0002\u0010\u001f\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\u0015J0\u0010\u001d\u001a\u00020\u00062\b\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007J$\u0010 \u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010!\u001a\u00020\u000bH\u0007J\u001c\u0010\"\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0007J7\u0010\"\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\fJ7\u0010#\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\fJ7\u0010$\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\f¨\u0006%"}, m1901d2 = {"Lcom/aivox/base/util/NumberUtils;", "", "()V", "divide", "Ljava/math/BigDecimal;", "v1", "", "v2", "precision", "", "mode", "Ljava/math/RoundingMode;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/math/RoundingMode;)Ljava/math/BigDecimal;", "format", "digit", "maxPrecision", "minPrecision", "minIntegerBits", "integerFormat", "formatINT", "", "(Ljava/lang/Double;IILjava/math/RoundingMode;)Ljava/lang/String;", "formatInput", "", "et", "Landroid/widget/EditText;", "negative", "", "(Landroid/widget/EditText;Ljava/lang/Integer;Z)V", "formatPrecision", "maxDecimalPlaces", "minDecimalPlaces", "formatValidPrecision", "precisionMode", "minus", "multiply", "plus", "base_release"}, m1902k = 1, m1903mv = {1, 9, 0}, m1905xi = 48)
public final class NumberUtils {
    public static final NumberUtils INSTANCE = new NumberUtils();

    private NumberUtils() {
    }

    public static /* synthetic */ String formatINT$default(Double d, int i, int i2, RoundingMode roundingMode, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 2;
        }
        if ((i3 & 4) != 0) {
            i2 = 2;
        }
        if ((i3 & 8) != 0) {
            roundingMode = RoundingMode.DOWN;
        }
        return formatINT(d, i, i2, roundingMode);
    }

    @JvmStatic
    public static final String formatINT(Double digit, int maxPrecision, int minPrecision, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return formatINT(digit == null ? null : digit.toString(), maxPrecision, minPrecision, mode);
    }

    public static /* synthetic */ String formatINT$default(String str, int i, int i2, RoundingMode roundingMode, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 2;
        }
        if ((i3 & 4) != 0) {
            i2 = 2;
        }
        if ((i3 & 8) != 0) {
            roundingMode = RoundingMode.DOWN;
        }
        return formatINT(str, i, i2, roundingMode);
    }

    @JvmStatic
    public static final String formatINT(String digit, int maxPrecision, int minPrecision, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return format$default(digit == null ? null : digit.toString(), maxPrecision, minPrecision, mode, 0, null, 48, null);
    }

    public static /* synthetic */ String formatPrecision$default(Double d, int i, int i2, RoundingMode roundingMode, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 2;
        }
        if ((i3 & 4) != 0) {
            i2 = 2;
        }
        if ((i3 & 8) != 0) {
            roundingMode = RoundingMode.HALF_UP;
        }
        return formatPrecision(d, i, i2, roundingMode);
    }

    @JvmStatic
    public static final String formatPrecision(Double digit, int maxDecimalPlaces, int minDecimalPlaces, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return formatPrecision(digit == null ? null : digit.toString(), maxDecimalPlaces, minDecimalPlaces, mode);
    }

    public static /* synthetic */ String formatPrecision$default(String str, int i, int i2, RoundingMode roundingMode, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 2;
        }
        if ((i3 & 4) != 0) {
            i2 = 2;
        }
        if ((i3 & 8) != 0) {
            roundingMode = RoundingMode.HALF_UP;
        }
        return formatPrecision(str, i, i2, roundingMode);
    }

    @JvmStatic
    public static final String formatPrecision(String digit, int maxPrecision, int minPrecision, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return format$default(digit, maxPrecision, minPrecision, mode, 0, "0", 16, null);
    }

    public static /* synthetic */ String format$default(String str, int i, int i2, RoundingMode roundingMode, int i3, String str2, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 2;
        }
        if ((i4 & 4) != 0) {
            i2 = 2;
        }
        if ((i4 & 8) != 0) {
            roundingMode = RoundingMode.HALF_UP;
        }
        if ((i4 & 16) != 0) {
            i3 = 1;
        }
        if ((i4 & 32) != 0) {
            str2 = "";
        }
        return format(str, i, i2, roundingMode, i3, str2);
    }

    @JvmStatic
    public static final String format(String digit, int maxPrecision, int minPrecision, RoundingMode mode, int minIntegerBits, String integerFormat) {
        BigDecimal bigDecimal;
        String str;
        Intrinsics.checkNotNullParameter(mode, "mode");
        Intrinsics.checkNotNullParameter(integerFormat, "integerFormat");
        String str2 = digit;
        if (str2 == null || str2.length() == 0 || StringsKt.toDoubleOrNull(digit) == null) {
            bigDecimal = new BigDecimal("0");
        } else {
            bigDecimal = new BigDecimal(digit);
        }
        if (minPrecision > maxPrecision) {
            minPrecision = maxPrecision;
        }
        String str3 = "";
        for (int i = 0; i < maxPrecision; i++) {
            if (i < minPrecision) {
                str3 = str3 + '0';
            } else {
                str3 = str3 + '#';
            }
        }
        if (integerFormat.length() == 0) {
            if (minIntegerBits == 0) {
                integerFormat = "#,###";
            } else if (1 <= minIntegerBits) {
                int i2 = 1;
                while (true) {
                    if (i2 / 4 == 1) {
                        integerFormat = "0," + integerFormat;
                    } else {
                        integerFormat = "0" + integerFormat;
                    }
                    if (i2 == minIntegerBits) {
                        break;
                    }
                    i2++;
                }
            }
            if (integerFormat.length() < 4) {
                int length = 4 - integerFormat.length();
                for (int i3 = 0; i3 < length; i3++) {
                    if (integerFormat.length() == 3) {
                        str = "#," + integerFormat;
                    } else {
                        str = PunctuationConst.SHAPE + integerFormat;
                    }
                    integerFormat = str;
                }
            }
        }
        if (str3.length() > 0) {
            integerFormat = integerFormat + CharConst.DOT + str3;
        }
        DecimalFormat decimalFormat = new DecimalFormat(integerFormat);
        decimalFormat.setRoundingMode(mode);
        decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
        String str4 = decimalFormat.format(bigDecimal);
        Intrinsics.checkNotNullExpressionValue(str4, "format(...)");
        return str4;
    }

    public static /* synthetic */ BigDecimal formatValidPrecision$default(String str, int i, RoundingMode roundingMode, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            roundingMode = RoundingMode.HALF_UP;
        }
        return formatValidPrecision(str, i, roundingMode);
    }

    @JvmStatic
    public static final BigDecimal formatValidPrecision(String digit, int precision, RoundingMode precisionMode) {
        Intrinsics.checkNotNullParameter(precisionMode, "precisionMode");
        if (digit == null) {
            digit = "0";
        }
        BigDecimal bigDecimal = new BigDecimal(digit);
        BigDecimal ONE = BigDecimal.ONE;
        Intrinsics.checkNotNullExpressionValue(ONE, "ONE");
        BigDecimal bigDecimalDivide = bigDecimal.divide(ONE, new MathContext(precision, precisionMode));
        Intrinsics.checkNotNullExpressionValue(bigDecimalDivide, "divide(...)");
        return bigDecimalDivide;
    }

    public static /* synthetic */ void formatInput$default(EditText editText, Integer num, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            num = null;
        }
        if ((i & 4) != 0) {
            z = true;
        }
        formatInput(editText, num, z);
    }

    @JvmStatic
    public static final void formatInput(EditText et, Integer precision, boolean negative) {
        Intrinsics.checkNotNullParameter(et, "et");
        String string = et.getText().toString();
        if (StringsKt.startsWith$default(string, ".", false, 2, (Object) null)) {
            String str = (precision == null || precision.intValue() != 0) ? "0" + string : "0";
            et.setText(str);
            et.setSelection(str.length());
        } else {
            String str2 = string;
            if (StringsKt.startsWith$default(StringsKt.trim((CharSequence) str2).toString(), "0", false, 2, (Object) null) && StringsKt.trim((CharSequence) str2).toString().length() > 1) {
                String strSubstring = string.substring(1, 2);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                if (!strSubstring.equals(".")) {
                    String strSubstring2 = string.substring(0, 1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
                    et.setText(strSubstring2);
                    et.setSelection(1);
                }
            } else if (StringsKt.startsWith$default(string, "-", false, 2, (Object) null) && !negative) {
                et.setText("");
            }
        }
        if (precision != null) {
            String str3 = string;
            if (StringsKt.contains$default((CharSequence) str3, (CharSequence) ".", false, 2, (Object) null)) {
                if (precision.intValue() <= 0) {
                    String strSubstring3 = string.substring(0, StringsKt.indexOf$default((CharSequence) str3, ".", 0, false, 6, (Object) null));
                    Intrinsics.checkNotNullExpressionValue(strSubstring3, "substring(...)");
                    et.setText(strSubstring3);
                    et.setSelection(strSubstring3.length());
                    return;
                }
                if (string.length() - StringsKt.indexOf$default((CharSequence) str3, ".", 0, false, 6, (Object) null) > precision.intValue() + 1) {
                    String strSubstring4 = string.substring(0, StringsKt.indexOf$default((CharSequence) str3, ".", 0, false, 6, (Object) null) + precision.intValue() + 1);
                    Intrinsics.checkNotNullExpressionValue(strSubstring4, "substring(...)");
                    et.setText(strSubstring4);
                    et.setSelection(strSubstring4.length());
                }
            }
        }
    }

    public static /* synthetic */ BigDecimal plus$default(String str, String str2, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 4) != 0) {
            num = null;
        }
        if ((i & 8) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return plus(str, str2, num, roundingMode);
    }

    @JvmStatic
    public static final BigDecimal plus(String v1, String v2, Integer precision, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        String str = v1;
        if (str == null || str.length() == 0) {
            v1 = "0";
        }
        String str2 = v2;
        if (str2 == null || str2.length() == 0) {
            v2 = "0";
        }
        BigDecimal bigDecimalAdd = new BigDecimal(v1).add(new BigDecimal(v2));
        if (precision != null) {
            BigDecimal scale = bigDecimalAdd.setScale(precision.intValue(), mode);
            Intrinsics.checkNotNullExpressionValue(scale, "setScale(...)");
            return scale;
        }
        Intrinsics.checkNotNull(bigDecimalAdd);
        return bigDecimalAdd;
    }

    public static /* synthetic */ BigDecimal minus$default(String str, String str2, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 4) != 0) {
            num = null;
        }
        if ((i & 8) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return minus(str, str2, num, roundingMode);
    }

    @JvmStatic
    public static final BigDecimal minus(String v1, String v2, Integer precision, RoundingMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        String str = v1;
        if (str == null || str.length() == 0) {
            v1 = "0";
        }
        String str2 = v2;
        if (str2 == null || str2.length() == 0) {
            v2 = "0";
        }
        BigDecimal bigDecimalSubtract = new BigDecimal(v1).subtract(new BigDecimal(v2));
        if (precision != null) {
            BigDecimal scale = bigDecimalSubtract.setScale(precision.intValue(), mode);
            Intrinsics.checkNotNullExpressionValue(scale, "setScale(...)");
            return scale;
        }
        Intrinsics.checkNotNull(bigDecimalSubtract);
        return bigDecimalSubtract;
    }

    @JvmStatic
    public static final BigDecimal minus(String v1, String v2) {
        String str = v1;
        if (str == null || str.length() == 0) {
            v1 = "0";
        }
        String str2 = v2;
        if (str2 == null || str2.length() == 0) {
            v2 = "0";
        }
        BigDecimal bigDecimalSubtract = new BigDecimal(v1).subtract(new BigDecimal(v2));
        Intrinsics.checkNotNullExpressionValue(bigDecimalSubtract, "subtract(...)");
        return bigDecimalSubtract;
    }

    public static /* synthetic */ BigDecimal multiply$default(String str, String str2, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 4) != 0) {
            num = null;
        }
        if ((i & 8) != 0) {
            roundingMode = RoundingMode.FLOOR;
        }
        return multiply(str, str2, num, roundingMode);
    }

    @JvmStatic
    public static final BigDecimal multiply(String v1, String v2, Integer precision, RoundingMode mode) {
        String str;
        Intrinsics.checkNotNullParameter(mode, "mode");
        String str2 = v1;
        if (str2 == null || str2.length() == 0 || (str = v2) == null || str.length() == 0) {
            return new BigDecimal("0");
        }
        BigDecimal bigDecimalMultiply = new BigDecimal(v1).multiply(new BigDecimal(v2));
        if (precision != null) {
            BigDecimal scale = bigDecimalMultiply.setScale(precision.intValue(), mode);
            Intrinsics.checkNotNullExpressionValue(scale, "setScale(...)");
            return scale;
        }
        Intrinsics.checkNotNull(bigDecimalMultiply);
        return bigDecimalMultiply;
    }

    public static /* synthetic */ BigDecimal divide$default(String str, String str2, Integer num, RoundingMode roundingMode, int i, Object obj) {
        if ((i & 4) != 0) {
            num = null;
        }
        if ((i & 8) != 0) {
            roundingMode = RoundingMode.HALF_EVEN;
        }
        return divide(str, str2, num, roundingMode);
    }

    @JvmStatic
    public static final BigDecimal divide(String v1, String v2, Integer precision, RoundingMode mode) {
        String str;
        Intrinsics.checkNotNullParameter(mode, "mode");
        String str2 = v1;
        if (str2 == null || str2.length() == 0 || (str = v2) == null || str.length() == 0 || StringsKt.toDoubleOrNull(v2) == null || Double.parseDouble(v2) == 0.0d) {
            return new BigDecimal("0");
        }
        BigDecimal bigDecimal = new BigDecimal(v1);
        BigDecimal bigDecimal2 = new BigDecimal(v2);
        if (precision == null) {
            BigDecimal bigDecimalDivide = bigDecimal.divide(bigDecimal2);
            Intrinsics.checkNotNullExpressionValue(bigDecimalDivide, "divide(...)");
            return bigDecimalDivide;
        }
        BigDecimal bigDecimalDivide2 = bigDecimal.divide(bigDecimal2, precision.intValue(), mode);
        Intrinsics.checkNotNullExpressionValue(bigDecimalDivide2, "divide(...)");
        return bigDecimalDivide2;
    }
}
