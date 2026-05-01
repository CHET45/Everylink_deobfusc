package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitConcatJob$SubmitConcatJobOutput$$XmlAdapter extends IXmlAdapter<SubmitConcatJob.SubmitConcatJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitConcatJob.SubmitConcatJobOutput>> childElementBinders;

    public SubmitConcatJob$SubmitConcatJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitConcatJob.SubmitConcatJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobOutput submitConcatJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobOutput submitConcatJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitConcatJob.SubmitConcatJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitConcatJob$SubmitConcatJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitConcatJob.SubmitConcatJobOutput submitConcatJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitConcatJobOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitConcatJob.SubmitConcatJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitConcatJob.SubmitConcatJobOutput submitConcatJobOutput = new SubmitConcatJob.SubmitConcatJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitConcatJob.SubmitConcatJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitConcatJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitConcatJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitConcatJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitConcatJob.SubmitConcatJobOutput submitConcatJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitConcatJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitConcatJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitConcatJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitConcatJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitConcatJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitConcatJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitConcatJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
