package com.aivox.base.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;
import kotlin.UByte;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class MiscUtil {
    public static final String KEY_MD = "MD";
    public static final String KEY_SHA = "SHA";

    public static int COLOR_B(int i) {
        return (i << 8) >>> 24;
    }

    public static int COLOR_G(int i) {
        return (i << 16) >>> 24;
    }

    public static int COLOR_R(int i) {
        return (i << 24) >>> 24;
    }

    public static int COLOR_RGB(int i, int i2, int i3) {
        return ((char) i) | (((short) ((char) i2)) << 8) | (((char) i3) << 16);
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

    public static String sha(String str) {
        try {
            return convertByteToHexString(MessageDigest.getInstance(KEY_SHA).digest(str.getBytes()));
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m338i("sha1错误:" + e.getMessage());
            return "";
        }
    }

    public static String sha256(String str) {
        try {
            return convertByteToHexString(MessageDigest.getInstance("SHA-256").digest(str.getBytes()));
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m338i("sha256错误:" + e.getMessage());
            return "";
        }
    }

    public static String sha384(String str) {
        try {
            return convertByteToHexString(MessageDigest.getInstance("SHA-384").digest(str.getBytes()));
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m338i("sha384错误:" + e.getMessage());
            return "";
        }
    }

    public static String sha512(String str) {
        try {
            return convertByteToHexString(MessageDigest.getInstance("SHA-512").digest(str.getBytes()));
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m338i("sha512错误:" + e.getMessage());
            return "";
        }
    }

    public static String convertByteToHexString(byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & UByte.MAX_VALUE);
            if (hexString.length() < 2) {
                str = str + "0" + hexString;
            } else {
                str = str + hexString;
            }
        }
        return str;
    }

    public static String SHA(String str) {
        BigInteger bigInteger;
        LogUtil.m338i("=======加密前的数据:" + str);
        byte[] bytes = str.getBytes();
        BigInteger bigInteger2 = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
            messageDigest.update(bytes);
            bigInteger = new BigInteger(messageDigest.digest());
        } catch (Exception e) {
            e = e;
        }
        try {
            LogUtil.m338i("SHA加密后:" + bigInteger.toString());
        } catch (Exception e2) {
            e = e2;
            bigInteger2 = bigInteger;
            BaseAppUtils.printErrorMsg(e);
            bigInteger = bigInteger2;
        }
        return bigInteger.toString();
    }

    /* JADX INFO: renamed from: MD */
    public static String m342MD(String str) {
        BigInteger bigInteger;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(KEY_MD);
            messageDigest.update(str.getBytes());
            bigInteger = new BigInteger(messageDigest.digest());
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            LogUtil.m338i("MD报错:" + e.getMessage());
            bigInteger = null;
        }
        return bigInteger.toString();
    }

    public static String[] StringSplit(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        Vector vector = new Vector();
        String[] strArr = new String[1];
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            strArr[0] = str;
            return strArr;
        }
        int length = 0;
        while (iIndexOf < str.length() && iIndexOf != -1) {
            vector.addElement(str.substring(length, iIndexOf));
            length = str2.length() + iIndexOf;
            iIndexOf = str.indexOf(str2, iIndexOf + str2.length());
        }
        vector.addElement(str.substring(length));
        String[] strArr2 = new String[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            strArr2[i] = (String) vector.elementAt(i);
        }
        return strArr2;
    }

    public static String getRandomString(int i) {
        StringBuffer stringBuffer = new StringBuffer("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        StringBuffer stringBuffer2 = new StringBuffer();
        Random random = new Random();
        int length = stringBuffer.length();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer2.append(stringBuffer.charAt(random.nextInt(length)));
        }
        return stringBuffer2.toString();
    }

    public static long getRandomInt(int i) {
        StringBuffer stringBuffer = new StringBuffer("0123456789");
        StringBuffer stringBuffer2 = new StringBuffer();
        Random random = new Random();
        int length = stringBuffer.length();
        for (int i2 = 0; i2 < i; i2++) {
            stringBuffer2.append(stringBuffer.charAt(random.nextInt(length)));
        }
        return Long.parseLong(stringBuffer2.toString());
    }

    public static BigDecimal getBigDecimalValue(String str) {
        BigDecimal bigDecimal = new BigDecimal("0.0");
        if (str != null) {
            try {
                if (!str.equals("")) {
                    return new BigDecimal(str);
                }
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
            }
        }
        return bigDecimal;
    }

    public static long getLongValue(String str) {
        if (str == null) {
            return 0L;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return 0L;
        }
    }

    public static int getIntValue(String str, int i) {
        if (str == null) {
            return 0;
        }
        try {
            return Integer.parseInt(str, i);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return 0;
        }
    }

    public static double getDoubleValue(String str) {
        try {
            return Double.valueOf(str).doubleValue();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return 0.0d;
        }
    }

    public static final String get5Radom() {
        double dRandom = Math.random() * 100000.0d;
        if (dRandom >= 100000.0d) {
            dRandom = 99999.0d;
        }
        String str = "" + ((int) Math.ceil(dRandom));
        while (str.length() < 5) {
            str = "0" + str;
        }
        return str;
    }

    public static String getMD5(byte[] bArr) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(bArr);
            return toHexString(messageDigest.digest(), "");
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static String toHexString(byte[] bArr, String str) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            int i = b & UByte.MAX_VALUE;
            if (Integer.toHexString(i).length() == 1) {
                sb.append("0").append(Integer.toHexString(i));
            } else {
                sb.append(Integer.toHexString(i));
            }
        }
        return sb.toString();
    }

    public static boolean CopyStream(InputStream inputStream, OutputStream outputStream) {
        int i;
        boolean z;
        try {
            byte[] bArr = new byte[1024];
            i = 0;
            while (true) {
                try {
                    int i2 = inputStream.read(bArr, 0, 1024);
                    if (i2 == -1) {
                        break;
                    }
                    outputStream.write(bArr, 0, i2);
                    i += i2;
                } catch (Exception e) {
                    e = e;
                    BaseAppUtils.printErrorMsg(e);
                    z = false;
                }
            }
            z = true;
        } catch (Exception e2) {
            e = e2;
            i = 0;
        }
        if (i <= 0) {
            return false;
        }
        return z;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int i, int i2) {
        int iComputeInitialSampleSize = computeInitialSampleSize(options, i, i2);
        if (iComputeInitialSampleSize > 8) {
            return 8 * ((iComputeInitialSampleSize + 7) / 8);
        }
        int i3 = 1;
        while (i3 < iComputeInitialSampleSize) {
            i3 <<= 1;
        }
        return i3;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int i, int i2) {
        int iMin;
        double d = options.outWidth;
        double d2 = options.outHeight;
        int iCeil = i2 == -1 ? 1 : (int) Math.ceil(Math.sqrt((d * d2) / ((double) i2)));
        if (i == -1) {
            iMin = 128;
        } else {
            double d3 = i;
            iMin = (int) Math.min(Math.floor(d / d3), Math.floor(d2 / d3));
        }
        if (iMin < iCeil) {
            return iCeil;
        }
        if (i2 == -1 && i == -1) {
            return 1;
        }
        return i == -1 ? iCeil : iMin;
    }

    public static Drawable decodeDrawableFile(String str, int i) {
        try {
            Bitmap bitmapDecodeBitmapFile = decodeBitmapFile(new File(str), i);
            if (bitmapDecodeBitmapFile != null) {
                return new BitmapDrawable(bitmapDecodeBitmapFile);
            }
            return null;
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        }
    }

    public static Bitmap decodeBitmapFile(String str, int i) {
        try {
            return decodeBitmapFile(new File(str), i);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        }
    }

    public static Bitmap decodeBitmapFile(File file, int i) {
        if (file == null) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(fileInputStream, null, options);
            options.inSampleSize = computeSampleSize(options, -1, i);
            options.inJustDecodeBounds = false;
            try {
                return BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            } catch (OutOfMemoryError e) {
                BaseAppUtils.printErrorMsg(e);
                fileInputStream.close();
                return null;
            }
        } catch (Exception e2) {
            BaseAppUtils.printErrorMsg(e2);
            return null;
        }
    }

    public static Bitmap decodeBitmapFile(InputStream inputStream, int i) {
        if (inputStream == null) {
            return null;
        }
        try {
            if (i <= 0) {
                return BitmapFactory.decodeStream(inputStream);
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            options.inSampleSize = computeSampleSize(options, -1, i);
            options.inJustDecodeBounds = false;
            try {
                return BitmapFactory.decodeStream(inputStream, null, options);
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
                return null;
            }
        } catch (Exception e2) {
            BaseAppUtils.printErrorMsg(e2);
            return null;
        }
    }

    public static boolean existSDcard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static JSONObject getNodeJSonItem(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        try {
            return (JSONObject) jSONObject.get(str);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        }
    }

    public static JSONObject getNodeJSonItem(JSONArray jSONArray, int i) {
        if (jSONArray == null) {
            return null;
        }
        try {
            return jSONArray.getJSONObject(i);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        }
    }

    public static JSONArray getNodeJSonArray(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        try {
            return jSONObject.getJSONArray(str);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        }
    }

    public static JSONObject getNodeJSonObject(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        try {
            return jSONObject.getJSONObject(str);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return null;
        }
    }

    public static String getNodeJSonValue(JSONObject jSONObject, String str) {
        String string;
        if (jSONObject == null) {
            return "";
        }
        try {
            string = jSONObject.getString(str);
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            string = "";
        }
        return string == null ? "" : string;
    }

    public static boolean deleteFile(String str) {
        if (str == null) {
            return true;
        }
        try {
            if (str.equals("")) {
                return true;
            }
            File file = new File(str);
            if (!file.exists()) {
                return true;
            }
            file.delete();
            return true;
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return false;
        }
    }

    public static String getLowerString(String str) {
        if (str == null || str.equals("")) {
            return "";
        }
        int length = str.length();
        char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            char c = charArray[i];
            if (c <= 'Z' && c >= 'A') {
                charArray[i] = (char) (c + ' ');
            } else if (c <= 'z' && c >= 'a') {
                charArray[i] = (char) (c - ' ');
            }
        }
        return new String(charArray);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList<java.lang.String> arrayStringToArrayList(java.lang.String r4) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            if (r4 == 0) goto L45
            java.lang.String r1 = ""
            boolean r2 = r4.equals(r1)
            if (r2 == 0) goto L10
            goto L45
        L10:
            if (r4 == 0) goto L3b
            int r2 = r4.length()     // Catch: java.lang.Exception -> L39
            if (r2 <= 0) goto L3b
            int r2 = r4.length()     // Catch: java.lang.Exception -> L39
            r3 = 1
            int r2 = r2 - r3
            java.lang.String r4 = r4.substring(r3, r2)     // Catch: java.lang.Exception -> L39
            int r2 = r4.length()     // Catch: java.lang.Exception -> L39
            if (r2 <= 0) goto L3b
            java.lang.String r2 = " "
            java.lang.String r4 = r4.replace(r2, r1)     // Catch: java.lang.Exception -> L39
            java.lang.String r1 = ","
            java.lang.String[] r4 = r4.split(r1)     // Catch: java.lang.Exception -> L39
            java.util.List r4 = java.util.Arrays.asList(r4)     // Catch: java.lang.Exception -> L39
            goto L3c
        L39:
            r4 = move-exception
            goto L42
        L3b:
            r4 = 0
        L3c:
            if (r4 == 0) goto L45
            r0.addAll(r4)     // Catch: java.lang.Exception -> L39
            goto L45
        L42:
            com.aivox.base.util.BaseAppUtils.printErrorMsg(r4)
        L45:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aivox.base.util.MiscUtil.arrayStringToArrayList(java.lang.String):java.util.ArrayList");
    }

    public static String vectorToString(Vector<String> vector, String str) {
        if (vector == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            if (vector.size() > 0) {
                sb.append(vector.get(0));
                for (int i = 1; i < vector.size(); i++) {
                    sb.append(str);
                    sb.append(vector.get(i));
                }
            }
            return sb.toString();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static String listToString(ArrayList<String> arrayList, String str) {
        if (arrayList == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            if (arrayList.size() > 0) {
                sb.append(arrayList.get(0));
                for (int i = 1; i < arrayList.size(); i++) {
                    sb.append(str);
                    sb.append(arrayList.get(i));
                }
            }
            return sb.toString();
        } catch (Exception e) {
            BaseAppUtils.printErrorMsg(e);
            return "";
        }
    }

    public static Vector<String> stringToVector(String str, String str2) {
        Vector<String> vector = new Vector<>();
        if (str != null) {
            try {
                if (!str.equals("") && str2 != null && !str2.equals("")) {
                    int length = str2.length();
                    int iIndexOf = 0;
                    int i = 0;
                    while (true) {
                        iIndexOf = str.indexOf(str2, iIndexOf + length);
                        if (iIndexOf == -1) {
                            break;
                        }
                        vector.add(str.substring(i, iIndexOf));
                        i = iIndexOf + length;
                    }
                }
            } catch (Exception e) {
                BaseAppUtils.printErrorMsg(e);
            }
        }
        return vector;
    }
}
