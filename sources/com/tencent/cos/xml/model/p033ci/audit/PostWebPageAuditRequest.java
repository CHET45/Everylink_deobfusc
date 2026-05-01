package com.tencent.cos.xml.model.p033ci.audit;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.audit.bean.AuditInput;
import com.tencent.cos.xml.model.tag.audit.post.PostWebPageAudit;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostWebPageAuditRequest extends BasePostAuditRequest {
    private final PostWebPageAudit postWebPageAudit;

    public PostWebPageAuditRequest(String str) {
        super(str);
        this.postWebPageAudit = new PostWebPageAudit();
    }

    public void setInput(AuditInput auditInput) {
        this.postWebPageAudit.input = auditInput;
    }

    public void setConfig(PostWebPageAudit.WebPageAuditConf webPageAuditConf) {
        this.postWebPageAudit.conf = webPageAuditConf;
    }

    @Deprecated
    public void setUrl(String str) {
        this.postWebPageAudit.input.url = str;
    }

    @Deprecated
    public void setDetectType(String str) {
        this.postWebPageAudit.conf.detectType = str;
    }

    @Deprecated
    public void setCallback(String str) {
        this.postWebPageAudit.conf.callback = str;
    }

    @Deprecated
    public void setReturnHighlightHtml(boolean z) {
        this.postWebPageAudit.conf.returnHighlightHtml = z;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/webpage/auditing";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.postWebPageAudit).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        TextUtils.isEmpty(this.postWebPageAudit.input.url);
        TextUtils.isEmpty(this.postWebPageAudit.conf.detectType);
    }
}
