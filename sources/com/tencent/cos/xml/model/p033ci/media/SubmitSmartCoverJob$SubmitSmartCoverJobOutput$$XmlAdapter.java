package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSmartCoverJob$SubmitSmartCoverJobOutput$$XmlAdapter extends IXmlAdapter<SubmitSmartCoverJob.SubmitSmartCoverJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitSmartCoverJob.SubmitSmartCoverJobOutput>> childElementBinders;

    public SubmitSmartCoverJob$SubmitSmartCoverJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitSmartCoverJob.SubmitSmartCoverJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitSmartCoverJob.SubmitSmartCoverJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJob$SubmitSmartCoverJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJob.SubmitSmartCoverJobOutput submitSmartCoverJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitSmartCoverJob.SubmitSmartCoverJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJob$SubmitSmartCoverJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJob.SubmitSmartCoverJobOutput submitSmartCoverJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitSmartCoverJob.SubmitSmartCoverJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJob$SubmitSmartCoverJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJob.SubmitSmartCoverJobOutput submitSmartCoverJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSmartCoverJob.SubmitSmartCoverJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSmartCoverJob.SubmitSmartCoverJobOutput submitSmartCoverJobOutput = new SubmitSmartCoverJob.SubmitSmartCoverJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSmartCoverJob.SubmitSmartCoverJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSmartCoverJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSmartCoverJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSmartCoverJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitSmartCoverJob.SubmitSmartCoverJobOutput submitSmartCoverJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitSmartCoverJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitSmartCoverJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitSmartCoverJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitSmartCoverJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitSmartCoverJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitSmartCoverJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitSmartCoverJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
