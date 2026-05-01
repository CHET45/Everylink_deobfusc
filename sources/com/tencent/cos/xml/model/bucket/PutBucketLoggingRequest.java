package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.tag.BucketLoggingStatus;
import com.tencent.cos.xml.transfer.XmlBuilder;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketLoggingRequest extends BucketRequest {
    private BucketLoggingStatus bucketLoggingStatus;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public boolean isNeedMD5() {
        return true;
    }

    public PutBucketLoggingRequest(String str) {
        super(str);
        this.bucketLoggingStatus = new BucketLoggingStatus();
    }

    public void setTargetBucket(String str) {
        if (this.bucketLoggingStatus.loggingEnabled == null) {
            this.bucketLoggingStatus.loggingEnabled = new BucketLoggingStatus.LoggingEnabled();
        }
        this.bucketLoggingStatus.loggingEnabled.targetBucket = str;
    }

    public void setTargetPrefix(String str) {
        if (this.bucketLoggingStatus.loggingEnabled == null) {
            this.bucketLoggingStatus.loggingEnabled = new BucketLoggingStatus.LoggingEnabled();
        }
        this.bucketLoggingStatus.loggingEnabled.targetPrefix = str;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("logging", null);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.string("application/xml", XmlBuilder.buildBucketLogging(this.bucketLoggingStatus));
    }
}
