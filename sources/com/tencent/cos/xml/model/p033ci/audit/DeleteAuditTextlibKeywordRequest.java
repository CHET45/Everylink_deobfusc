package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DeleteAuditTextlibKeywordRequest extends BucketRequest {
    private DeleteAuditTextlibKeyword deleteAuditTextlibKeyword;
    private final String libID;

    public DeleteAuditTextlibKeywordRequest(String str, String str2) {
        super(str);
        DeleteAuditTextlibKeyword deleteAuditTextlibKeyword = new DeleteAuditTextlibKeyword();
        this.deleteAuditTextlibKeyword = deleteAuditTextlibKeyword;
        this.libID = str2;
        deleteAuditTextlibKeyword.keywordIDs = new ArrayList();
    }

    public void addKeywordID(String str) {
        this.deleteAuditTextlibKeyword.keywordIDs.add(str);
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return String.format("/audit/textlib/%s/keyword", this.libID);
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.deleteAuditTextlibKeyword).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "DELETE";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getRequestHost(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getRequestHost(this.region, this.bucket, CosXmlServiceConfig.CI_HOST_FORMAT);
    }
}
