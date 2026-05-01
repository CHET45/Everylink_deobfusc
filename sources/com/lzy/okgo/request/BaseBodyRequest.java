package com.lzy.okgo.request;

import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseBodyRequest;
import com.lzy.okgo.utils.HttpUtils;
import java.io.File;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes4.dex */
public abstract class BaseBodyRequest<R extends BaseBodyRequest> extends BaseRequest<R> implements HasBody<R> {

    /* JADX INFO: renamed from: bs */
    protected byte[] f891bs;
    protected String content;
    protected boolean isMultipart;
    protected MediaType mediaType;
    protected RequestBody requestBody;

    @Override // com.lzy.okgo.request.HasBody
    public /* bridge */ /* synthetic */ Object addFileParams(String str, List list) {
        return addFileParams(str, (List<File>) list);
    }

    @Override // com.lzy.okgo.request.HasBody
    public /* bridge */ /* synthetic */ Object addFileWrapperParams(String str, List list) {
        return addFileWrapperParams(str, (List<HttpParams.FileWrapper>) list);
    }

    public BaseBodyRequest(String str) {
        super(str);
        this.isMultipart = false;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R isMultipart(boolean z) {
        this.isMultipart = z;
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R params(String str, File file) {
        this.params.put(str, file);
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R addFileParams(String str, List<File> list) {
        this.params.putFileParams(str, list);
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R addFileWrapperParams(String str, List<HttpParams.FileWrapper> list) {
        this.params.putFileWrapperParams(str, list);
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R params(String str, File file, String str2) {
        this.params.put(str, file, str2);
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R params(String str, File file, String str2, MediaType mediaType) {
        this.params.put(str, file, str2, mediaType);
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R upString(String str) {
        this.content = str;
        this.mediaType = HttpParams.MEDIA_TYPE_PLAIN;
        return this;
    }

    public R upString(String str, MediaType mediaType) {
        this.content = str;
        this.mediaType = mediaType;
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R upJson(String str) {
        this.content = str;
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R upJson(JSONObject jSONObject) {
        this.content = jSONObject.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R upJson(JSONArray jSONArray) {
        this.content = jSONArray.toString();
        this.mediaType = HttpParams.MEDIA_TYPE_JSON;
        return this;
    }

    @Override // com.lzy.okgo.request.HasBody
    public R upBytes(byte[] bArr) {
        this.f891bs = bArr;
        this.mediaType = HttpParams.MEDIA_TYPE_STREAM;
        return this;
    }

    @Override // com.lzy.okgo.request.BaseRequest
    public RequestBody generateRequestBody() {
        MediaType mediaType;
        MediaType mediaType2;
        RequestBody requestBody = this.requestBody;
        if (requestBody != null) {
            return requestBody;
        }
        String str = this.content;
        if (str != null && (mediaType2 = this.mediaType) != null) {
            return RequestBody.create(mediaType2, str);
        }
        byte[] bArr = this.f891bs;
        return (bArr == null || (mediaType = this.mediaType) == null) ? HttpUtils.generateMultipartRequestBody(this.params, this.isMultipart) : RequestBody.create(mediaType, bArr);
    }
}
