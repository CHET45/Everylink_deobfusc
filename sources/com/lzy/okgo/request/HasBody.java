package com.lzy.okgo.request;

import com.lzy.okgo.model.HttpParams;
import java.io.File;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public interface HasBody<R> {
    R addFileParams(String str, List<File> list);

    R addFileWrapperParams(String str, List<HttpParams.FileWrapper> list);

    R isMultipart(boolean z);

    R params(String str, File file);

    R params(String str, File file, String str2);

    R params(String str, File file, String str2, MediaType mediaType);

    R requestBody(RequestBody requestBody);

    R upBytes(byte[] bArr);

    R upJson(String str);

    R upJson(JSONArray jSONArray);

    R upJson(JSONObject jSONObject);

    R upString(String str);
}
