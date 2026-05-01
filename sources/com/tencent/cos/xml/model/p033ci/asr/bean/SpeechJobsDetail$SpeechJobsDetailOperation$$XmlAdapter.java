package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.p033ci.asr.bean.SpeechJobsDetail;
import com.tencent.cos.xml.model.tag.Locator;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechJobsDetail$SpeechJobsDetailOperation$$XmlAdapter extends IXmlAdapter<SpeechJobsDetail.SpeechJobsDetailOperation> {
    private HashMap<String, ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation>> childElementBinders;

    public SpeechJobsDetail$SpeechJobsDetailOperation$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("SpeechRecognition", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailOperation speechJobsDetailOperation, String str) throws XmlPullParserException, IOException {
                speechJobsDetailOperation.speechRecognition = (SpeechRecognition) QCloudXml.fromXml(xmlPullParser, SpeechRecognition.class, "SpeechRecognition");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailOperation speechJobsDetailOperation, String str) throws XmlPullParserException, IOException {
                speechJobsDetailOperation.output = (Locator) QCloudXml.fromXml(xmlPullParser, Locator.class, "Output");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailOperation speechJobsDetailOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetailOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailOperation speechJobsDetailOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetailOperation.jobLevel = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("TemplateId", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailOperation speechJobsDetailOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetailOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpeechRecognitionResult", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailOperation speechJobsDetailOperation, String str) throws XmlPullParserException, IOException {
                speechJobsDetailOperation.speechRecognitionResult = (SpeechJobsDetail.SpeechRecognitionResult) QCloudXml.fromXml(xmlPullParser, SpeechJobsDetail.SpeechRecognitionResult.class, "SpeechRecognitionResult");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechJobsDetail.SpeechJobsDetailOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechJobsDetail.SpeechJobsDetailOperation speechJobsDetailOperation = new SpeechJobsDetail.SpeechJobsDetailOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, speechJobsDetailOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return speechJobsDetailOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return speechJobsDetailOperation;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SpeechJobsDetail.SpeechJobsDetailOperation speechJobsDetailOperation, String str) throws XmlPullParserException, IOException {
        if (speechJobsDetailOperation == null) {
            return;
        }
        if (str == null) {
            str = "Operation";
        }
        xmlSerializer.startTag("", str);
        if (speechJobsDetailOperation.speechRecognition != null) {
            QCloudXml.toXml(xmlSerializer, speechJobsDetailOperation.speechRecognition, "SpeechRecognition");
        }
        if (speechJobsDetailOperation.output != null) {
            QCloudXml.toXml(xmlSerializer, speechJobsDetailOperation.output, "Output");
        }
        if (speechJobsDetailOperation.userData != null) {
            xmlSerializer.startTag("", "UserData");
            xmlSerializer.text(String.valueOf(speechJobsDetailOperation.userData));
            xmlSerializer.endTag("", "UserData");
        }
        xmlSerializer.startTag("", "JobLevel");
        xmlSerializer.text(String.valueOf(speechJobsDetailOperation.jobLevel));
        xmlSerializer.endTag("", "JobLevel");
        if (speechJobsDetailOperation.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(speechJobsDetailOperation.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (speechJobsDetailOperation.speechRecognitionResult != null) {
            QCloudXml.toXml(xmlSerializer, speechJobsDetailOperation.speechRecognitionResult, "SpeechRecognitionResult");
        }
        xmlSerializer.endTag("", str);
    }
}
