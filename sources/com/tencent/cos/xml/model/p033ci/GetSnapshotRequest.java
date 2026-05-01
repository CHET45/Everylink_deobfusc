package com.tencent.cos.xml.model.p033ci;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.object.GetObjectRequest;

/* JADX INFO: loaded from: classes4.dex */
public class GetSnapshotRequest extends GetObjectRequest {
    private float time;

    public GetSnapshotRequest(String str, String str2, String str3, String str4, float f) {
        super(str, str2, str3, str4);
        this.time = f;
        this.queryParameters.put("ci-process", "snapshot");
        this.queryParameters.put("time", String.valueOf(f));
    }

    public void setWidth(int i) {
        this.queryParameters.put("width", String.valueOf(i));
    }

    public void setHeight(int i) {
        this.queryParameters.put("height", String.valueOf(i));
    }

    public void setFormat(String str) {
        this.queryParameters.put("format", str);
    }

    public void setRotate(String str) {
        this.queryParameters.put("rotate", str);
    }

    public void setMode(String str) {
        this.queryParameters.put("mode", str);
    }

    @Override // com.tencent.cos.xml.model.object.GetObjectRequest, com.tencent.cos.xml.model.object.ObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
    }

    @Override // com.tencent.cos.xml.model.object.GetObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
