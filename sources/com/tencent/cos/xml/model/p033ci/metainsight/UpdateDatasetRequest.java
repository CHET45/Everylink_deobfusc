package com.tencent.cos.xml.model.p033ci.metainsight;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.model.appid.AppIdRequest;
import com.tencent.cos.xml.utils.QCloudJsonUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateDatasetRequest extends AppIdRequest {
    private UpdateDataset updateDataset;

    public UpdateDatasetRequest(String str) {
        super(str);
        addHeader("Accept", "application/json");
    }

    public void setUpdateDataset(UpdateDataset updateDataset) {
        this.updateDataset = updateDataset;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/dataset";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.bytes("application/json", QCloudJsonUtils.toJson(this.updateDataset).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }
}
