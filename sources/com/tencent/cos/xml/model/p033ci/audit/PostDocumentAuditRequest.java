package com.tencent.cos.xml.model.p033ci.audit;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.cos.xml.model.tag.audit.post.PostDocumentAudit;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostDocumentAuditRequest extends BasePostAuditRequest {
    private final PostDocumentAudit postDocumentAudit;

    public PostDocumentAuditRequest(String str) {
        super(str);
        this.postDocumentAudit = new PostDocumentAudit();
    }

    public void setInput(PostDocumentAudit.DocumentAuditInput documentAuditInput) {
        this.postDocumentAudit.input = documentAuditInput;
    }

    public void setConfig(AuditConf auditConf) {
        this.postDocumentAudit.conf = auditConf;
    }

    @Deprecated
    public void setObject(String str) {
        this.postDocumentAudit.input.object = str;
    }

    @Deprecated
    public void setUrl(String str) {
        this.postDocumentAudit.input.url = str;
    }

    @Deprecated
    public void setDataId(String str) {
        this.postDocumentAudit.input.dataId = str;
    }

    @Deprecated
    public void setType(String str) {
        this.postDocumentAudit.input.type = str;
    }

    @Deprecated
    public void setDetectType(String str) {
        this.postDocumentAudit.conf.detectType = str;
    }

    @Deprecated
    public void setCallback(String str) {
        this.postDocumentAudit.conf.callback = str;
    }

    @Deprecated
    public void setBizType(String str) {
        this.postDocumentAudit.conf.bizType = str;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/document/auditing";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.postDocumentAudit).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (TextUtils.isEmpty(this.postDocumentAudit.input.object)) {
            TextUtils.isEmpty(this.postDocumentAudit.input.url);
        }
    }
}
