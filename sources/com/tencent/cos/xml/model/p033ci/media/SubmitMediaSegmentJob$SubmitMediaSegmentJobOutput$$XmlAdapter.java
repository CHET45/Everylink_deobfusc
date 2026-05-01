package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitMediaSegmentJob$SubmitMediaSegmentJobOutput$$XmlAdapter extends IXmlAdapter<SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput>> childElementBinders;

    public SubmitMediaSegmentJob$SubmitMediaSegmentJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput submitMediaSegmentJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput submitMediaSegmentJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput submitMediaSegmentJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput submitMediaSegmentJobOutput = new SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaSegmentJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaSegmentJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaSegmentJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput submitMediaSegmentJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitMediaSegmentJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitMediaSegmentJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitMediaSegmentJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitMediaSegmentJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
