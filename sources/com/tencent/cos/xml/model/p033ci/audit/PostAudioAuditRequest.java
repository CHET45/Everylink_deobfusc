package com.tencent.cos.xml.model.p033ci.audit;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.audit.bean.AuditInput;
import com.tencent.cos.xml.model.tag.audit.post.PostAudioAudit;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostAudioAuditRequest extends BasePostAuditRequest {
    private final PostAudioAudit postAudioAudit;

    public PostAudioAuditRequest(String str) {
        super(str);
        this.postAudioAudit = new PostAudioAudit();
    }

    public void setInput(AuditInput auditInput) {
        this.postAudioAudit.input = auditInput;
    }

    public void setConfig(PostAudioAudit.AudioAuditConf audioAuditConf) {
        this.postAudioAudit.conf = audioAuditConf;
    }

    @Deprecated
    public void setObject(String str) {
        this.postAudioAudit.input.object = str;
    }

    @Deprecated
    public void setUrl(String str) {
        this.postAudioAudit.input.url = str;
    }

    @Deprecated
    public void setDataId(String str) {
        this.postAudioAudit.input.dataId = str;
    }

    @Deprecated
    public void setDetectType(String str) {
        this.postAudioAudit.conf.detectType = str;
    }

    @Deprecated
    public void setCallback(String str) {
        this.postAudioAudit.conf.callback = str;
    }

    @Deprecated
    public void setCallbackVersion(String str) {
        this.postAudioAudit.conf.callbackVersion = str;
    }

    @Deprecated
    public void setBizType(String str) {
        this.postAudioAudit.conf.bizType = str;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/audio/auditing";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.postAudioAudit).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (TextUtils.isEmpty(this.postAudioAudit.input.object)) {
            TextUtils.isEmpty(this.postAudioAudit.input.url);
        }
    }
}
