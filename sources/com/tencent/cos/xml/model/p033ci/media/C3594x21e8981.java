package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.PicProcess;
import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJob;
import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJobResponse;
import com.tencent.cos.xml.model.tag.pic.PicUploadResult;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3594x21e8981 extends IXmlAdapter<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation>> childElementBinders;

    public C3594x21e8981() {
        HashMap<String, ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation submitPicProcessJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation submitPicProcessJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PicProcess", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation submitPicProcessJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitPicProcessJobResponseOperation.picProcess = (PicProcess) QCloudXml.fromXml(xmlPullParser, PicProcess.class, "PicProcess");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation submitPicProcessJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitPicProcessJobResponseOperation.output = (SubmitPicProcessJob.SubmitPicProcessJobOutput) QCloudXml.fromXml(xmlPullParser, SubmitPicProcessJob.SubmitPicProcessJobOutput.class, "Output");
            }
        });
        this.childElementBinders.put("PicProcessResult", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation submitPicProcessJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitPicProcessJobResponseOperation.picProcessResult = (PicUploadResult) QCloudXml.fromXml(xmlPullParser, PicUploadResult.class, "PicProcessResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJobResponse$SubmitPicProcessJobResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation submitPicProcessJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation submitPicProcessJobResponseOperation = new SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitPicProcessJobResponse.SubmitPicProcessJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitPicProcessJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitPicProcessJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitPicProcessJobResponseOperation;
    }
}
