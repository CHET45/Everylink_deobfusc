package com.tencent.cos.xml.model.p033ci.p034ai;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.bean.CreateWordsGeneralizeJob;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CreateWordsGeneralizeJobRequest extends BucketRequest {
    private final CreateWordsGeneralizeJob createWordsGeneralizeJob;

    public CreateWordsGeneralizeJobRequest(String str) {
        super(str);
        this.createWordsGeneralizeJob = new CreateWordsGeneralizeJob();
    }

    public void setInputObject(String str) {
        this.createWordsGeneralizeJob.input.object = str;
    }

    public void setQueueId(String str) {
        this.createWordsGeneralizeJob.queueId = str;
    }

    public void setCallBack(String str) {
        this.createWordsGeneralizeJob.callBack = str;
    }

    public void setCallBackFormat(String str) {
        this.createWordsGeneralizeJob.callBackFormat = str;
    }

    public void setUserData(String str) {
        this.createWordsGeneralizeJob.operation.userData = str;
    }

    public void setJobLevel(int i) {
        this.createWordsGeneralizeJob.operation.jobLevel = i;
    }

    public void setNerMethod(String str) {
        this.createWordsGeneralizeJob.operation.wordsGeneralize.nerMethod = str;
    }

    public void setSegMethod(String str) {
        this.createWordsGeneralizeJob.operation.wordsGeneralize.segMethod = str;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/ai_jobs";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.createWordsGeneralizeJob).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (TextUtils.isEmpty(this.createWordsGeneralizeJob.input.object)) {
            throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "input must be non-empty");
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "POST";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getRequestHost(CosXmlServiceConfig cosXmlServiceConfig) {
        return cosXmlServiceConfig.getRequestHost(this.region, this.bucket, CosXmlServiceConfig.CI_HOST_FORMAT);
    }
}
