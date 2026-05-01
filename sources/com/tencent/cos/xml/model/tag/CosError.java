package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public class CosError {
    public String code;
    public String message;
    public String requestId;
    public String resource;
    public String traceId;

    public static CosError fromJson(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        CosError cosError = new CosError();
        cosError.code = jSONObject.optString(Constants.ERROR_CODE);
        cosError.message = jSONObject.optString(Constants.ERROR_MESSAGE);
        cosError.requestId = jSONObject.optString("RequestId");
        cosError.traceId = jSONObject.optString("TraceId");
        return cosError;
    }
}
