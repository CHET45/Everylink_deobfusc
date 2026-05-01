package com.tencent.cos.xml.model.p033ci;

import android.net.Uri;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.object.SaveLocalRequest;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CiSaveLocalRequest extends CosXmlRequest implements SaveLocalRequest {
    public String saveLocalPath;
    public Uri saveLocalUri;

    @Override // com.tencent.cos.xml.model.object.SaveLocalRequest
    public long getSaveLocalOffset() {
        return 0L;
    }

    @Override // com.tencent.cos.xml.model.object.SaveLocalRequest
    public String getSaveLocalPath() {
        return this.saveLocalPath;
    }

    @Override // com.tencent.cos.xml.model.object.SaveLocalRequest
    public Uri getSaveLocalUri() {
        return this.saveLocalUri;
    }
}
