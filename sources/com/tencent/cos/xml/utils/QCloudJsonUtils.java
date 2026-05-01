package com.tencent.cos.xml.utils;

import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class QCloudJsonUtils {
    public static <T> T fromJson(HttpResponse httpResponse, Class<T> cls) throws CosXmlClientException {
        try {
            return (T) GsonSingleton.getInstance().fromJson(httpResponse.string(), (Class) cls);
        } catch (IOException e) {
            throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e);
        }
    }

    public static String toJson(Object obj) {
        return GsonSingleton.getInstance().toJson(obj);
    }
}
