package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitTranscodeJob$SubmitTranscodeJobOutput$$XmlAdapter extends IXmlAdapter<SubmitTranscodeJob.SubmitTranscodeJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobOutput>> childElementBinders;

    public SubmitTranscodeJob$SubmitTranscodeJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobOutput submitTranscodeJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobOutput submitTranscodeJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobOutput submitTranscodeJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitTranscodeJob.SubmitTranscodeJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitTranscodeJob.SubmitTranscodeJobOutput submitTranscodeJobOutput = new SubmitTranscodeJob.SubmitTranscodeJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitTranscodeJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitTranscodeJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitTranscodeJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitTranscodeJob.SubmitTranscodeJobOutput submitTranscodeJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitTranscodeJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitTranscodeJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitTranscodeJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitTranscodeJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitTranscodeJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitTranscodeJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitTranscodeJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
