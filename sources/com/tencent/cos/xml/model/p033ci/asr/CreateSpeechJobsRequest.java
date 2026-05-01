package com.tencent.cos.xml.model.p033ci.asr;

import android.text.TextUtils;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.bucket.BucketRequest;
import com.tencent.cos.xml.model.p033ci.asr.bean.CreateSpeechJobs;
import com.tencent.cos.xml.model.tag.CallBackMqConfig;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CreateSpeechJobsRequest extends BucketRequest {
    private final CreateSpeechJobs createSpeechJobs;

    public CreateSpeechJobsRequest(String str) {
        super(str);
        this.createSpeechJobs = new CreateSpeechJobs();
    }

    public void setTemplateId(String str) {
        this.createSpeechJobs.operation.templateId = str;
    }

    public void setInputObject(String str) {
        this.createSpeechJobs.input.object = str;
    }

    public void setInputUrl(String str) {
        this.createSpeechJobs.input.url = str;
    }

    public void setQueueId(String str) {
        this.createSpeechJobs.queueId = str;
    }

    public void setOutput(String str, String str2, String str3) {
        this.createSpeechJobs.operation.output.region = str;
        this.createSpeechJobs.operation.output.bucket = str2;
        this.createSpeechJobs.operation.output.object = str3;
    }

    public void setEngineModelType(String str) {
        this.createSpeechJobs.operation.speechRecognition.engineModelType = str;
    }

    public void setChannelNum(int i) {
        this.createSpeechJobs.operation.speechRecognition.channelNum = i;
    }

    public void setResTextFormat(int i) {
        this.createSpeechJobs.operation.speechRecognition.resTextFormat = i;
    }

    public void setFilterDirty(int i) {
        this.createSpeechJobs.operation.speechRecognition.filterDirty = i;
    }

    public void setFilterModal(int i) {
        this.createSpeechJobs.operation.speechRecognition.filterModal = i;
    }

    public void setConvertNumMode(int i) {
        this.createSpeechJobs.operation.speechRecognition.convertNumMode = i;
    }

    public void setSpeakerDiarization(int i) {
        this.createSpeechJobs.operation.speechRecognition.speakerDiarization = i;
    }

    public void setSpeakerNumber(int i) {
        this.createSpeechJobs.operation.speechRecognition.speakerNumber = i;
    }

    public void setFilterPunc(int i) {
        this.createSpeechJobs.operation.speechRecognition.filterPunc = i;
    }

    public void setOutputFileType(String str) {
        this.createSpeechJobs.operation.speechRecognition.outputFileType = str;
    }

    public void setFlashAsr(boolean z) {
        this.createSpeechJobs.operation.speechRecognition.flashAsr = z;
    }

    public void setFormat(String str) {
        this.createSpeechJobs.operation.speechRecognition.format = str;
    }

    public void setFirstChannelOnly(int i) {
        this.createSpeechJobs.operation.speechRecognition.firstChannelOnly = i;
    }

    public void setWordInfo(int i) {
        this.createSpeechJobs.operation.speechRecognition.wordInfo = i;
    }

    public void setUserData(String str) {
        this.createSpeechJobs.operation.userData = str;
    }

    public void setJobLevel(int i) {
        this.createSpeechJobs.operation.jobLevel = i;
    }

    public void setCallBack(String str) {
        this.createSpeechJobs.callBack = str;
    }

    public void setCallBackFormat(String str) {
        this.createSpeechJobs.callBackFormat = str;
    }

    public void setCallBackType(String str) {
        this.createSpeechJobs.callBackType = str;
    }

    public void setCallBackMqConfig(CallBackMqConfig callBackMqConfig) {
        this.createSpeechJobs.callBackMqConfig = callBackMqConfig;
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/asr_jobs";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException, CosXmlClientException {
        return RequestBodySerializer.bytes("application/xml", QCloudXmlUtils.toXml(this.createSpeechJobs).getBytes("utf-8"));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (TextUtils.isEmpty(this.createSpeechJobs.input.object)) {
            TextUtils.isEmpty(this.createSpeechJobs.input.url);
        }
        if (!TextUtils.isEmpty(this.createSpeechJobs.operation.output.region) && !TextUtils.isEmpty(this.createSpeechJobs.operation.output.bucket)) {
            TextUtils.isEmpty(this.createSpeechJobs.operation.output.object);
        }
        TextUtils.isEmpty(this.createSpeechJobs.operation.speechRecognition.engineModelType);
        int i = this.createSpeechJobs.operation.speechRecognition.channelNum;
        int i2 = this.createSpeechJobs.operation.speechRecognition.resTextFormat;
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
