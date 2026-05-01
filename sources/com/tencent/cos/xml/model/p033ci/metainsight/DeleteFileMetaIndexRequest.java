package com.tencent.cos.xml.model.p033ci.metainsight;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.model.appid.AppIdRequest;
import com.tencent.cos.xml.utils.QCloudJsonUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteFileMetaIndexRequest extends AppIdRequest {
    private DeleteFileMetaIndex deleteFileMetaIndex;

    public DeleteFileMetaIndexRequest(String str) {
        super(str);
        addHeader("Accept", "application/json");
    }

    public void setDeleteFileMetaIndex(DeleteFileMetaIndex deleteFileMetaIndex) {
        this.deleteFileMetaIndex = deleteFileMetaIndex;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/filemeta";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.bytes("application/json", QCloudJsonUtils.toJson(this.deleteFileMetaIndex).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "DELETE";
    }
}
