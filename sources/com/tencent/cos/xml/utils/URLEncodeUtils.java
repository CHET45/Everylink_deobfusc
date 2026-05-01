package com.tencent.cos.xml.utils;

import android.text.TextUtils;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/* JADX INFO: loaded from: classes4.dex */
public class URLEncodeUtils {
    public static String cosPathEncode(String str) throws CosXmlClientException {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        try {
            String[] strArrSplit = str.split("/", -1);
            int length = strArrSplit.length;
            for (int i = 0; i < length; i++) {
                if (i != 0 || !"".equals(strArrSplit[i])) {
                    if (length > 1 && i == length - 1 && "".equals(strArrSplit[i])) {
                        break;
                    }
                    if (!"".equals(strArrSplit[i])) {
                        String[] strArrSplit2 = strArrSplit[i].split(" ", -1);
                        int length2 = strArrSplit2.length;
                        for (int i2 = 0; i2 < length2; i2++) {
                            if (i2 != 0 || !"".equals(strArrSplit2[i2])) {
                                if (length2 > 1 && i2 == length2 - 1 && "".equals(strArrSplit2[i2])) {
                                    break;
                                }
                                sb.append(URLEncoder.encode(strArrSplit2[i2], "utf-8"));
                                if (i2 != length2 - 1) {
                                    sb.append("%20");
                                }
                            } else {
                                sb.append("%20");
                            }
                        }
                    }
                    if (i != length - 1) {
                        sb.append("/");
                    }
                } else {
                    sb.append('/');
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new CosXmlClientException(ClientErrorCode.INTERNAL_ERROR.getCode(), e);
        }
    }
}
