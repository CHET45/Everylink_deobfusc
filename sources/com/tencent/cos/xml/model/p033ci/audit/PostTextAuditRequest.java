package com.tencent.cos.xml.model.p033ci.audit;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.audit.post.PostTextAudit;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostTextAuditRequest extends BasePostAuditRequest {
    protected final PostTextAudit postTextAudit;

    public PostTextAuditRequest(String str) {
        super(str);
        this.postTextAudit = new PostTextAudit();
    }

    public void setInput(PostTextAudit.TextAuditInput textAuditInput) {
        this.postTextAudit.input = textAuditInput;
    }

    public void setConfig(PostTextAudit.TextAuditConf textAuditConf) {
        this.postTextAudit.conf = textAuditConf;
    }

    @Deprecated
    public void setContent(String str) {
        this.postTextAudit.input.content = str;
    }

    @Deprecated
    public void setObject(String str) {
        this.postTextAudit.input.object = str;
    }

    @Deprecated
    public void setUrl(String str) {
        this.postTextAudit.input.url = str;
    }

    @Deprecated
    public void setDataId(String str) {
        this.postTextAudit.input.dataId = str;
    }

    @Deprecated
    public void setDetectType(String str) {
        this.postTextAudit.conf.detectType = str;
    }

    @Deprecated
    public void setCallback(String str) {
        this.postTextAudit.conf.callback = str;
    }

    @Deprecated
    public void setBizType(String str) {
        this.postTextAudit.conf.bizType = str;
    }

    @Deprecated
    public void setCallbackVersion(String str) {
        this.postTextAudit.conf.callbackVersion = str;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/text/auditing";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.postTextAudit).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (TextUtils.isEmpty(this.postTextAudit.input.object) && TextUtils.isEmpty(this.postTextAudit.input.url)) {
            TextUtils.isEmpty(this.postTextAudit.input.content);
        }
    }
}
