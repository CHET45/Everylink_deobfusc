package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVoiceSeparateJob$SubmitVoiceSeparateJobOutput$$XmlAdapter extends IXmlAdapter<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput>> childElementBinders;

    public SubmitVoiceSeparateJob$SubmitVoiceSeparateJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJob$SubmitVoiceSeparateJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput submitVoiceSeparateJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJob$SubmitVoiceSeparateJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput submitVoiceSeparateJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJob$SubmitVoiceSeparateJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput submitVoiceSeparateJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobOutput.object = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AuObject", new ChildElementBinder<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVoiceSeparateJob$SubmitVoiceSeparateJobOutput$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput submitVoiceSeparateJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVoiceSeparateJobOutput.auObject = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput submitVoiceSeparateJobOutput = new SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVoiceSeparateJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVoiceSeparateJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVoiceSeparateJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVoiceSeparateJob.SubmitVoiceSeparateJobOutput submitVoiceSeparateJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitVoiceSeparateJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitVoiceSeparateJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitVoiceSeparateJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitVoiceSeparateJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (submitVoiceSeparateJobOutput.auObject != null) {
            xmlSerializer.startTag("", "AuObject");
            xmlSerializer.text(String.valueOf(submitVoiceSeparateJobOutput.auObject));
            xmlSerializer.endTag("", "AuObject");
        }
        xmlSerializer.endTag("", str);
    }
}
