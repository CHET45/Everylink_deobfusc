package com.aivox.base.util;

import android.bluetooth.BluetoothDevice;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;
import com.aivox.base.C0874R;
import com.aivox.base.common.Constant;
import com.aivox.base.language.MultiLanguageUtil;
import com.github.houbb.heaven.constant.PunctuationConst;
import com.github.houbb.heaven.util.util.PlaceholderUtil;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.UByte;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public class BaseStringUtil {
    public static String CHAR_ENCODE = "UTF-8";
    public static String WECHAT_APP_PACKAGE = "com.tencent.mm";
    public static String WECHAT_LAUNCHER_UI_CLASS = "com.tencent.mm.ui.LauncherUI";
    public static String WECHAT_OPEN_SCANER_NAME = "LauncherUI.From.Scaner.Shortcut";
    public static double clickTime = 0.0d;
    public static final String secretKey = "123456";

    public static String getLanguage(int i) {
        if (i == 1) {
            return "en";
        }
        if (i == 2) {
            return "jp";
        }
        return "cn";
    }

    public static int getLanguage(String str) {
        if (str.equals("jp")) {
            return 2;
        }
        return str.equals("en") ? 1 : 0;
    }

    public static String msTimeFormat(long j) {
        if (j < 1000) {
            return "1秒";
        }
        if (j > 1000 && j < 60000) {
            return (j / 1000) + "秒";
        }
        if (j > 60000 && j < 3600000) {
            return (j / 60000) + "分";
        }
        if (j > 3600000 && j < 86400000) {
            return (j / 3600000) + "小时";
        }
        return (j / 86400000) + "天";
    }

    public static int expString(Integer num, Integer num2) {
        String strMultiString = "1";
        for (int i = 0; i < num2.intValue(); i++) {
            strMultiString = multiString(num, strMultiString);
        }
        return Integer.parseInt(strMultiString);
    }

    private static String multiString(Integer num, String str) {
        char[] charArray = str.toCharArray();
        char[] cArr = new char[charArray.length + 1];
        int i = 0;
        for (int length = charArray.length - 1; length >= 0; length--) {
            int iIntValue = (charArray[length] - '0') * num.intValue();
            cArr[length + 1] = (char) ((((iIntValue % 10) + i) % 10) + 48);
            i = (iIntValue + i) / 10;
        }
        if (i != 0) {
            cArr[0] = (char) (i + 48);
        }
        return String.valueOf(cArr).replaceAll("\u0000", "");
    }

    public static String getWan(double d) {
        if (d == 0.0d) {
            return "0";
        }
        if (d < 10000.0d) {
            return getZeroPoint(d);
        }
        if (d >= 10000.0d && d < 1.0E8d) {
            return getZeroPoint(d / 10000.0d) + "万";
        }
        return getZeroPoint(d / 1.0E8d) + "亿";
    }

    public static void putTextIntoClip(Context context, String str) {
        ToastUtil.showTextToast(context, context.getResources().getString(C0874R.string.copy_success));
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("wx", str));
    }

    public static String getTextFromClip(Context context) {
        return ((ClipboardManager) context.getSystemService("clipboard")).getPrimaryClip().getItemAt(0).getText().toString();
    }

    public static boolean fliterClick() {
        if (System.currentTimeMillis() - clickTime <= 500.0d) {
            return false;
        }
        clickTime = System.currentTimeMillis();
        return true;
    }

    public static String bToMb(double d) {
        if (d < 1048576.0d) {
            return getTwoPoint(String.valueOf(d / 1024.0d)) + "KB";
        }
        if (d < 1.073741824E9d) {
            return getTwoPoint(String.valueOf(d / 1048576.0d)) + "MB";
        }
        return getTwoPoint(String.valueOf(d / 1.073741824E9d)) + "GB";
    }

    public static int bToKb(long j) {
        if (j < 1024) {
            return 1;
        }
        return (int) (j / 1024);
    }

    public static String MbFormat(int i) {
        double d = i;
        if (d < 1024.0d) {
            return i + "MB";
        }
        return getTwoPoint(String.valueOf(d / 1024.0d)) + "GB";
    }

    public static String kbToMb(double d) {
        if (d < 1024.0d) {
            return getTwoPoint(String.valueOf(d / 1024.0d)) + "KB";
        }
        if (d < 1048576.0d) {
            return getTwoPoint(String.valueOf(d / 1024.0d)) + "MB";
        }
        return getTwoPoint(String.valueOf(d / 1048576.0d)) + "GB";
    }

    public static String kbToMbFormat(double d) {
        if (d < 1024.0d) {
            return getTwoPoint(String.valueOf(d)) + "KB";
        }
        if (d < 1048576.0d) {
            return getTwoPoint(String.valueOf(d / 1024.0d)) + "MB";
        }
        return getTwoPoint(String.valueOf(d / 1048576.0d)) + "GB";
    }

    public static String kbToGb(long j) {
        try {
            return stripTrailingZeros(getTwoPoint(String.valueOf(j / 1048576.0d))) + " GB";
        } catch (Exception unused) {
            return "";
        }
    }

    public static String sHA1(Context context) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("SHA1").digest(context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bArrDigest) {
                String upperCase = Integer.toHexString(b & UByte.MAX_VALUE).toUpperCase(Locale.US);
                if (upperCase.length() == 1) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(upperCase);
                stringBuffer.append(":");
            }
            String string = stringBuffer.toString();
            return string.substring(0, string.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            BaseAppUtils.printErrorMsg(e2);
            return null;
        }
    }

    public static String cutTime(String str) {
        if (isEmpty(str) && str.length() > 10) {
            return "";
        }
        return str.substring(0, 10);
    }

    public static List<? extends Object> getListByArrayInt(int i) {
        TypedArray typedArrayObtainTypedArray = BaseAppUtils.getContext().getResources().obtainTypedArray(i);
        int length = typedArrayObtainTypedArray.length();
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < length; i2++) {
            arrayList.add(Integer.valueOf(typedArrayObtainTypedArray.getResourceId(i2, 0)));
        }
        return arrayList;
    }

    public static List<? extends Object> getListByArrayString(int i) {
        String[] stringArray = BaseAppUtils.getContext().getResources().getStringArray(i);
        ArrayList arrayList = new ArrayList();
        for (String str : stringArray) {
            arrayList.add(str);
        }
        return arrayList;
    }

    public static String listToString(List list, char c) {
        if (list == null || list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString()).append(c);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("") || str.equals("null") || str.equals("(null)");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmptyOrZero(String str) {
        return isEmpty(str) || str.equals("0");
    }

    public static String isNameEmpty(String str) {
        return isEmpty(str) ? "匿名" : str;
    }

    public static String replaceHtml(String str) {
        LogUtil.m338i("========================");
        return isEmpty(str) ? str : str.substring(str.indexOf("{"), str.lastIndexOf(PlaceholderUtil.DEFAULT_PLACEHOLDER_SUFFIX) + 1);
    }

    public static String replaceNullToEmptyStr(String str) {
        return isEmpty(str) ? "" : str;
    }

    public static String replaceBlank(String str) {
        if (str == null) {
            return null;
        }
        return Pattern.compile("\\s*|\t|\r|\n").matcher(str.trim()).replaceAll("");
    }

    public static String replaceBlock(String str) {
        if (str == null) {
            return null;
        }
        return Pattern.compile("\r|\n").matcher(str.trim()).replaceAll("\\\\n");
    }

    public static String replaceSpacial(String str) {
        if (str == null) {
            return null;
        }
        return Pattern.compile("\\[|\\]").matcher(str.trim()).replaceAll("\\\\n");
    }

    public static String refreshBankCard(String str) {
        return str.replaceAll("(.{4})", "$1 ");
    }

    public static void refreshBankCard(EditText editText, CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null || charSequence.length() == 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i4 = 0; i4 < charSequence.length(); i4++) {
            if (i4 == 4 || i4 == 9 || charSequence.charAt(i4) != ' ') {
                sb.append(charSequence.charAt(i4));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (sb.toString().equals(charSequence.toString())) {
            return;
        }
        int i5 = i + 1;
        if (sb.charAt(i) == ' ') {
            if (i2 == 0) {
                i += 2;
            }
        } else if (i2 != 1) {
            i = i5;
        }
        editText.setText(sb.toString());
        editText.setSelection(i);
    }

    public static String getBankEndFourNum(String str) {
        return str.substring(str.length() - 4);
    }

    public static void refreshPhone(EditText editText, CharSequence charSequence, int i, int i2, int i3) {
        if (charSequence == null || charSequence.length() == 0) {
            return;
        }
        if (String.valueOf(charSequence.toString().charAt(charSequence.length() - 1)).equals(" ")) {
            editText.setText(charSequence.subSequence(0, charSequence.length() - 1));
            editText.setSelection(editText.length());
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i4 = 0; i4 < charSequence.length(); i4++) {
            if (i4 == 3 || i4 == 8 || charSequence.charAt(i4) != ' ') {
                sb.append(charSequence.charAt(i4));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (sb.toString().equals(charSequence.toString())) {
            return;
        }
        int i5 = i + 1;
        if (sb.charAt(i) == ' ') {
            if (i2 == 0) {
                i += 2;
            }
        } else if (i2 != 1) {
            i = i5;
        }
        editText.setText(sb.toString());
        editText.setSelection(i);
    }

    public static String getDynamicPassword(String str) {
        Matcher matcher = Pattern.compile("[0-9\\.]+").matcher(str);
        String strGroup = "";
        while (matcher.find()) {
            if (matcher.group().length() == 5) {
                strGroup = matcher.group();
            }
        }
        return strGroup;
    }

    public static String getMinUrl(String str) {
        if (isEmpty(str) || !str.contains(".")) {
            return str;
        }
        int iLastIndexOf = str.lastIndexOf(".");
        return str.substring(0, iLastIndexOf - 1) + "1" + str.substring(iLastIndexOf);
    }

    public static int getIntByString(String str) {
        if (str == null) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return 0;
        }
    }

    public static String replaceImg(String str) {
        Matcher matcher = Pattern.compile("<img.*?>", 2).matcher(str);
        while (matcher.find()) {
            str = str.replaceAll(matcher.group(), "");
        }
        return str;
    }

    public static byte[] shortArr2byteArr(short[] sArr, int i) {
        byte[] bArr = new byte[i * 2];
        ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(sArr);
        return bArr;
    }

    public static short[] toShortArray(byte[] bArr) {
        int length = bArr.length >> 1;
        short[] sArr = new short[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            sArr[i] = (short) ((bArr[i2 + 1] & UByte.MAX_VALUE) | (bArr[i2] << 8));
        }
        return sArr;
    }

    public static short[] toShortArrayLittle(byte[] bArr) {
        int length = bArr.length >> 1;
        short[] sArr = new short[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            sArr[i] = (short) ((bArr[i2] & UByte.MAX_VALUE) | (bArr[i2 + 1] << 8));
        }
        return sArr;
    }

    public static byte[] toByteArray(short[] sArr) {
        int length = sArr.length;
        byte[] bArr = new byte[length << 1];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            short s = sArr[i];
            bArr[i2] = (byte) (s >> 8);
            bArr[i2 + 1] = (byte) s;
        }
        return bArr;
    }

    public static byte[] StringToByte(String str, String str2) {
        if (str != null) {
            try {
                if (!str.trim().equals("")) {
                    return str.getBytes(str2);
                }
            } catch (UnsupportedEncodingException e) {
                BaseAppUtils.printErrorMsg(e);
                return null;
            }
        }
        return new byte[0];
    }

    public static String ByteToString(byte[] bArr, String str) {
        String str2;
        try {
            str2 = new String(bArr, str);
        } catch (UnsupportedEncodingException e) {
            BaseAppUtils.printErrorMsg(e);
            str2 = null;
        }
        return str2.replaceAll("\u0000", " ");
    }

    public static String phoneNoShow(String str) {
        return (isEmpty(str) || str.length() != 11) ? str : str.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static String idCardNoShow(String str) {
        return (isEmpty(str) || str.length() != 18) ? str : str.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
    }

    public static String getHourStr(float f, String str) {
        if (str == null) {
            str = "";
        }
        try {
            return BaseStringUtil$$ExternalSyntheticBackportWithForwarding0.m333m(new BigDecimal(f).divide(BigDecimal.valueOf(3600L), 1, RoundingMode.HALF_UP)).toPlainString() + str;
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getWithoutZeroPrice(double d) {
        try {
            return PunctuationConst.DOLLAR + BaseStringUtil$$ExternalSyntheticBackportWithForwarding0.m333m(new BigDecimal(d).setScale(2, RoundingMode.HALF_UP)).toPlainString();
        } catch (Exception unused) {
            return "";
        }
    }

    public static String getTwoPoint(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        try {
            return new DecimalFormat("0.00").format(Double.parseDouble(str));
        } catch (Exception unused) {
            return str;
        }
    }

    public static String getZeroPoint(double d) {
        try {
            return new DecimalFormat("0").format(d);
        } catch (Exception unused) {
            return "";
        }
    }

    public static double roundTypeD(double d, RoundingMode roundingMode) {
        return roundTypeD(d, 0, roundingMode);
    }

    public static double roundTypeD(double d, int i, RoundingMode roundingMode) {
        return BigDecimal.valueOf(d).setScale(i, roundingMode).doubleValue();
    }

    public static String roundType(double d, RoundingMode roundingMode) {
        return roundType(d, 2, roundingMode);
    }

    public static String roundType(double d, RoundingMode roundingMode, boolean z) {
        return roundType(d, 2, roundingMode, true);
    }

    public static String roundType(double d, int i, RoundingMode roundingMode) {
        return BigDecimal.valueOf(d).setScale(i, roundingMode).toString();
    }

    public static String roundType(double d, int i, RoundingMode roundingMode, boolean z) {
        BigDecimal scale = BigDecimal.valueOf(d).setScale(i, roundingMode);
        if (z) {
            return stripTrailingZeros(BaseStringUtil$$ExternalSyntheticBackportWithForwarding0.m333m(scale).toPlainString());
        }
        return scale.toString();
    }

    public static String getPercent(String str, String str2, int i) {
        if (str.equals("0")) {
            return "0";
        }
        int iIntValue = Integer.valueOf(str).intValue();
        int iIntValue2 = Integer.valueOf(str).intValue() + Integer.valueOf(str2).intValue();
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(i);
        return numberFormat.format((iIntValue / iIntValue2) * 100.0f);
    }

    public static String getPercent(int i, int i2, int i3) {
        if (i == 0) {
            return "0";
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(i3);
        String str = numberFormat.format((i / i2) * 100.0f);
        LogUtil.m338i("----getPercent-----" + str);
        return str;
    }

    public static String roundFormat(double d, int i) {
        try {
            return new BigDecimal(String.valueOf(d)).setScale(i, 1).toString();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static String roundFormat(float f, int i) {
        try {
            return new BigDecimal(String.valueOf(f)).setScale(i, 1).toString();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static String roundFormat(String str, int i) {
        if (str != null) {
            try {
                if (str.equals("")) {
                    return "";
                }
                return new BigDecimal(str).setScale(i, 1).toString();
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
                return "";
            }
        }
        return "";
    }

    public static String roundFormat(BigDecimal bigDecimal, int i) {
        try {
            return bigDecimal.setScale(i, 1).toString();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static String getThousandSeparator(String str) {
        String strSubstring;
        try {
            if (!str.startsWith(Marker.ANY_NON_NULL_MARKER) && !str.startsWith("-")) {
                strSubstring = "";
            } else {
                strSubstring = str.substring(0, 1);
                str = str.substring(1);
            }
            StringBuilder sb = new StringBuilder();
            String[] strArrSplit = str.split("\\.");
            char[] charArray = strArrSplit[0].toCharArray();
            for (int length = charArray.length - 1; length >= 0; length--) {
                sb.append(charArray[length]);
                if ((charArray.length - length) % 3 == 0 && length != 0) {
                    sb.append(PunctuationConst.COMMA);
                }
            }
            char[] charArray2 = sb.toString().toCharArray();
            StringBuilder sb2 = new StringBuilder();
            for (int length2 = charArray2.length - 1; length2 >= 0; length2--) {
                sb2.append(charArray2[length2]);
            }
            if (strArrSplit.length > 1) {
                sb2.append(".").append(strArrSplit[1]);
            }
            StringBuilder sb3 = new StringBuilder();
            if (!isEmpty(strSubstring)) {
                sb3.append(strSubstring);
            }
            sb3.append((CharSequence) sb2);
            return sb3.toString();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static String addComma(String str) {
        return addComma(str, 2);
    }

    public static String addComma(String str, int i) {
        if (Double.valueOf(str).equals(Double.valueOf(0.0d)) || str.indexOf(".") == 1) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (i == 0) {
            stringBuffer.append("#,###");
        } else {
            stringBuffer.append("#,###.");
        }
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("0");
        }
        return new DecimalFormat(stringBuffer.toString()).format(new BigDecimal(str));
    }

    public static String addComma(String str, int i, boolean z) {
        if (Double.valueOf(str).equals(Double.valueOf(0.0d)) || str.indexOf(".") == 1) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (i == 0) {
            stringBuffer.append("#,###");
        } else {
            stringBuffer.append("#,###.");
        }
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(stringBuffer.toString());
        if (z) {
            return stripTrailingZeros(BaseStringUtil$$ExternalSyntheticBackportWithForwarding0.m333m(new BigDecimal(str)).toPlainString());
        }
        return decimalFormat.format(new BigDecimal(str));
    }

    public static String stripTrailingZeros(Object obj) {
        boolean z = obj instanceof String;
        Double dValueOf = Double.valueOf(0.0d);
        if (z) {
            if (Double.valueOf(obj.toString().replace(PunctuationConst.COMMA, ".")).equals(dValueOf)) {
                return "0";
            }
            return BaseStringUtil$$ExternalSyntheticBackportWithForwarding0.m333m(new BigDecimal(obj.toString())).toPlainString();
        }
        if (obj instanceof BigDecimal) {
            if (Double.valueOf(obj.toString().replace(PunctuationConst.COMMA, ".")).equals(dValueOf)) {
                return "0";
            }
            return BaseStringUtil$$ExternalSyntheticBackportWithForwarding0.m333m((BigDecimal) obj).toPlainString();
        }
        if (!(obj instanceof Double) || obj.equals(dValueOf)) {
            return "0";
        }
        return BaseStringUtil$$ExternalSyntheticBackportWithForwarding0.m333m(BigDecimal.valueOf(((Double) obj).doubleValue())).toPlainString();
    }

    public static void initEdit_num(final EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter() { // from class: com.aivox.base.util.BaseStringUtil.1
            @Override // android.text.InputFilter
            public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                if (charSequence.equals(".") && spanned.toString().length() == 0) {
                    return "0.";
                }
                if (!spanned.toString().equals("0")) {
                    return null;
                }
                if (charSequence.equals("0")) {
                    return "";
                }
                if (charSequence.equals(".")) {
                    return null;
                }
                editText.setText(charSequence);
                EditText editText2 = editText;
                editText2.setSelection(editText2.length());
                return charSequence;
            }
        }});
    }

    public static void setEditInputTwo(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter() { // from class: com.aivox.base.util.BaseStringUtil.2
            @Override // android.text.InputFilter
            public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                if (charSequence.equals(".") && spanned.toString().length() == 0) {
                    return "0.";
                }
                if (!charSequence.equals("0") || !spanned.toString().equals("0")) {
                    if (!spanned.toString().contains(".")) {
                        return null;
                    }
                    if (spanned.toString().substring(spanned.toString().indexOf(".")).length() >= 3) {
                        return "";
                    }
                    return null;
                }
                return "0";
            }
        }});
    }

    public static String parseParam(Object obj, Context context) {
        if (obj instanceof Integer) {
            return context.getString(((Integer) obj).intValue());
        }
        if (obj instanceof String) {
            return obj.toString();
        }
        return null;
    }

    public static int getMoneyType() {
        String language = MultiLanguageUtil.getInstance().getLanguage();
        if (language.equalsIgnoreCase("ja")) {
            return C0874R.string.money_jp;
        }
        if (language.equalsIgnoreCase("en")) {
            return C0874R.string.money_dollar;
        }
        return C0874R.string.money_rmb;
    }

    private static String map2Str(HashMap<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            try {
                if (map.size() > 0) {
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        sb.append(entry.getKey()).append(PunctuationConst.EQUAL).append(entry.getValue()).append(PunctuationConst.AND);
                    }
                }
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
            }
        }
        if (sb.length() > 0) {
            sb.substring(0, sb.length() - 1);
        }
        LogUtil.m338i("sb:" + sb.toString());
        return sb.toString();
    }

    public static int checkMargin(String str, String str2) {
        return Long.parseLong(str) >= Long.parseLong(str2) ? 2 : 0;
    }

    public static long usedStorage(String str) {
        if (isEmptyOrZero(str)) {
            str = "1";
        }
        return Long.parseLong(str);
    }

    public static long totalStorage(String str) {
        if (isEmptyOrZero(str)) {
            str = "0";
        }
        return Long.parseLong(str);
    }

    public static int percentStorage(String str, String str2) {
        if (isEmptyOrZero(str2)) {
            return new BigDecimal("100").intValue();
        }
        return new BigDecimal(str).multiply(new BigDecimal(100)).divide(new BigDecimal(str2), 2, 1).intValue();
    }

    public static String getUserName(String str) {
        return isEmpty(str) ? (String) SPUtil.get(SPUtil.LOGIN_ACCOUNT, "") : str;
    }

    public static boolean isBTContains(BluetoothDevice bluetoothDevice) {
        boolean z = false;
        for (String str : Constant.DEVICE_NAME.split(" ")) {
            if (isNotEmpty(bluetoothDevice.getName()) && bluetoothDevice.getName().contains(str)) {
                z = true;
            }
        }
        return z;
    }

    public static String cutMultibyte(String str, int i) {
        byte[] bytes = str.getBytes();
        LogUtil.m335d("bytes:lengthss", bytes.length + PunctuationConst.SINGLE_QUOTES);
        if (i >= bytes.length) {
            return str;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (bytes[i3] < 0) {
                i2++;
            }
        }
        if (i2 % 2 != 0) {
            i--;
        }
        String str2 = new String(bytes, 0, i);
        LogUtil.m335d("bytes:lengthaa", str2.getBytes().length + PunctuationConst.SINGLE_QUOTES);
        return str2;
    }

    public static String getPaymentByType(int i) {
        if (i == 1) {
            return "APPLE";
        }
        if (i == 2) {
            return "PAYPAL";
        }
        if (i == 3) {
            return "GOOGLE";
        }
        return "";
    }

    public static String getHexStringByteArray(byte[] bArr) {
        StringBuilder sb = new StringBuilder("[");
        for (byte b : bArr) {
            sb.append(String.format("%02X ", Byte.valueOf(b))).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
