package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.cos.xml.model.p033ci.asr.bean.SpeechJobsDetail;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SpeechJobsDetail$SpeechJobsDetailInput$$XmlAdapter extends IXmlAdapter<SpeechJobsDetail.SpeechJobsDetailInput> {
    private HashMap<String, ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailInput>> childElementBinders;

    public SpeechJobsDetail$SpeechJobsDetailInput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailInput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailInput>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailInput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailInput speechJobsDetailInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetailInput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailInput>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailInput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailInput speechJobsDetailInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetailInput.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailInput>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.SpeechJobsDetail$SpeechJobsDetailInput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SpeechJobsDetail.SpeechJobsDetailInput speechJobsDetailInput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                speechJobsDetailInput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SpeechJobsDetail.SpeechJobsDetailInput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SpeechJobsDetail.SpeechJobsDetailInput speechJobsDetailInput = new SpeechJobsDetail.SpeechJobsDetailInput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SpeechJobsDetail.SpeechJobsDetailInput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, speechJobsDetailInput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SpeechJobsDetailInput" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return speechJobsDetailInput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return speechJobsDetailInput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SpeechJobsDetail.SpeechJobsDetailInput speechJobsDetailInput, String str) throws XmlPullParserException, IOException {
        if (speechJobsDetailInput == null) {
            return;
        }
        if (str == null) {
            str = "SpeechJobsDetailInput";
        }
        xmlSerializer.startTag("", str);
        if (speechJobsDetailInput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(speechJobsDetailInput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (speechJobsDetailInput.bucketId != null) {
            xmlSerializer.startTag("", "BucketId");
            xmlSerializer.text(String.valueOf(speechJobsDetailInput.bucketId));
            xmlSerializer.endTag("", "BucketId");
        }
        if (speechJobsDetailInput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(speechJobsDetailInput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
