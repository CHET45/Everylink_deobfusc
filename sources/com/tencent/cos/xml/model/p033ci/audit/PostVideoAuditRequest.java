package com.tencent.cos.xml.model.p033ci.audit;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.audit.post.PostVideoAudit;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PostVideoAuditRequest extends BasePostAuditRequest {
    private final PostVideoAudit postVideoAudit;

    public PostVideoAuditRequest(String str) {
        super(str);
        this.postVideoAudit = new PostVideoAudit();
    }

    public void setInput(PostVideoAudit.VideoAuditInput videoAuditInput) {
        this.postVideoAudit.input = videoAuditInput;
    }

    public void setConfig(PostVideoAudit.VideoAuditConf videoAuditConf) {
        this.postVideoAudit.conf = videoAuditConf;
    }

    @Deprecated
    public void setObject(String str) {
        this.postVideoAudit.input.object = str;
    }

    @Deprecated
    public void setUrl(String str) {
        this.postVideoAudit.input.url = str;
    }

    @Deprecated
    public void setDataId(String str) {
        this.postVideoAudit.input.dataId = str;
    }

    @Deprecated
    public void setDetectType(String str) {
        this.postVideoAudit.conf.detectType = str;
    }

    @Deprecated
    public void setCallback(String str) {
        this.postVideoAudit.conf.callback = str;
    }

    @Deprecated
    public void setCallbackVersion(String str) {
        this.postVideoAudit.conf.callbackVersion = str;
    }

    @Deprecated
    public void setBizType(String str) {
        this.postVideoAudit.conf.bizType = str;
    }

    @Deprecated
    public void setDetectContent(int i) {
        this.postVideoAudit.conf.detectContent = i;
    }

    @Deprecated
    public void setMode(String str) {
        this.postVideoAudit.conf.snapshot.mode = str;
    }

    @Deprecated
    public void setCount(int i) {
        this.postVideoAudit.conf.snapshot.count = i;
    }

    @Deprecated
    public void setTimeInterval(float f) {
        this.postVideoAudit.conf.snapshot.timeInterval = f;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/video/auditing";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.postVideoAudit).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (TextUtils.isEmpty(this.postVideoAudit.input.object)) {
            TextUtils.isEmpty(this.postVideoAudit.input.url);
        }
        int i = this.postVideoAudit.conf.snapshot.count;
    }
}
