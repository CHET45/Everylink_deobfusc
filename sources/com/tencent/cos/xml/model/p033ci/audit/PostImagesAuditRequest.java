package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.audit.bean.AuditConf;
import com.tencent.cos.xml.model.tag.audit.post.PostImagesAudit;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostImagesAuditRequest extends BasePostAuditRequest {
    private final PostImagesAudit postImagesAudit;

    public PostImagesAuditRequest(String str) {
        super(str);
        this.postImagesAudit = new PostImagesAudit();
    }

    public void addImage(PostImagesAudit.ImagesAuditInput imagesAuditInput) {
        this.postImagesAudit.input.add(imagesAuditInput);
    }

    public void setConfig(AuditConf auditConf) {
        this.postImagesAudit.conf = auditConf;
    }

    @Deprecated
    public void setDetectType(String str) {
        this.postImagesAudit.conf.detectType = str;
    }

    @Deprecated
    public void setBizType(String str) {
        this.postImagesAudit.conf.bizType = str;
    }

    @Deprecated
    public void setAsync(int i) {
        this.postImagesAudit.conf.async = i;
    }

    @Deprecated
    public void setCallback(String str) {
        this.postImagesAudit.conf.callback = str;
    }

    @Deprecated
    public void setFreeze(AuditConf.Freeze freeze) {
        this.postImagesAudit.conf.freeze = freeze;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/image/auditing";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.postImagesAudit).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (this.postImagesAudit.input != null) {
            this.postImagesAudit.input.size();
        }
    }
}
