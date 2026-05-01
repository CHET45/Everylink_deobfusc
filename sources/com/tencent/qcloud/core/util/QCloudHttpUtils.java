package com.tencent.qcloud.core.util;

import android.text.TextUtils;
import com.github.houbb.heaven.constant.PunctuationConst;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLHandshakeException;

/* JADX INFO: loaded from: classes4.dex */
public class QCloudHttpUtils {
    public static String urlEncodeWithSlash(String str) {
        if (str == null || str.length() <= 0 || str.equals("/")) {
            return str;
        }
        String[] strArrSplit = str.split("/");
        for (int i = 0; i < strArrSplit.length; i++) {
            strArrSplit[i] = urlEncodeString(strArrSplit[i]);
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArrSplit) {
            sb.append(str2);
            sb.append("/");
        }
        if (!str.endsWith("/")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static Map<String, List<String>> getDecodedQueryPair(URL url) {
        int i;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (url.getQuery() != null) {
            for (String str : url.getQuery().split(PunctuationConst.AND)) {
                int iIndexOf = str.indexOf(PunctuationConst.EQUAL);
                String strUrlDecodeString = iIndexOf > 0 ? urlDecodeString(str.substring(0, iIndexOf)) : str;
                if (!linkedHashMap.containsKey(strUrlDecodeString)) {
                    linkedHashMap.put(strUrlDecodeString, new LinkedList());
                }
                ((List) linkedHashMap.get(strUrlDecodeString)).add((iIndexOf <= 0 || str.length() <= (i = iIndexOf + 1)) ? null : urlDecodeString(str.substring(i)));
            }
        }
        return linkedHashMap;
    }

    public static Map<String, List<String>> getQueryPair(URL url) {
        int i;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (url.getQuery() != null) {
            for (String str : url.getQuery().split(PunctuationConst.AND)) {
                int iIndexOf = str.indexOf(PunctuationConst.EQUAL);
                String strSubstring = iIndexOf > 0 ? str.substring(0, iIndexOf) : str;
                if (!linkedHashMap.containsKey(strSubstring)) {
                    linkedHashMap.put(strSubstring, new LinkedList());
                }
                ((List) linkedHashMap.get(strSubstring)).add((iIndexOf <= 0 || str.length() <= (i = iIndexOf + 1)) ? null : str.substring(i));
            }
        }
        return linkedHashMap;
    }

    public static Map<String, List<String>> transformToMultiMap(Map<String, String> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(entry.getValue());
            map2.put(entry.getKey(), arrayList);
        }
        return map2;
    }

    public static long[] parseContentRange(String str) {
        if (QCloudStringUtils.isEmpty(str)) {
            return null;
        }
        int iLastIndexOf = str.lastIndexOf(" ");
        int iIndexOf = str.indexOf("-");
        int iIndexOf2 = str.indexOf("/");
        if (iLastIndexOf == -1 || iIndexOf == -1 || iIndexOf2 == -1) {
            return null;
        }
        return new long[]{Long.parseLong(str.substring(iLastIndexOf + 1, iIndexOf)), Long.parseLong(str.substring(iIndexOf + 1, iIndexOf2)), Long.parseLong(str.substring(iIndexOf2 + 1))};
    }

    public static String urlEncodeString(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return str;
            }
            StringBuilder sb = new StringBuilder();
            String[] strArrSplit = str.split(" ", -1);
            int length = strArrSplit.length;
            for (int i = 0; i < length; i++) {
                if (i == 0 && "".equals(strArrSplit[i])) {
                    sb.append("%20");
                } else {
                    if (length > 1 && i == length - 1 && "".equals(strArrSplit[i])) {
                        break;
                    }
                    sb.append(URLEncoder.encode(strArrSplit[i], "UTF-8").replace("%7E", "~"));
                    if (i != length - 1) {
                        sb.append("%20");
                    }
                }
            }
            return sb.toString().replaceAll("\\*", "%2A");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String urlDecodeString(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String queryParametersString(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!z) {
                sb.append(PunctuationConst.AND);
            }
            sb.append(entry.getKey() + PunctuationConst.EQUAL + entry.getValue());
            z = false;
        }
        return sb.toString();
    }

    public static boolean isNetworkConditionException(Throwable th) {
        return (th instanceof UnknownHostException) || (th instanceof SocketTimeoutException) || (th instanceof ConnectException) || (th instanceof HttpRetryException) || (th instanceof NoRouteToHostException) || ((th instanceof SSLHandshakeException) && !(th.getCause() instanceof CertificateException));
    }
}
