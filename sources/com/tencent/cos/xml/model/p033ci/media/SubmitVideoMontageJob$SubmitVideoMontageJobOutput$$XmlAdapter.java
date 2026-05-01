package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVideoMontageJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitVideoMontageJob$SubmitVideoMontageJobOutput$$XmlAdapter extends IXmlAdapter<SubmitVideoMontageJob.SubmitVideoMontageJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobOutput>> childElementBinders;

    public SubmitVideoMontageJob$SubmitVideoMontageJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobOutput submitVideoMontageJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobOutput submitVideoMontageJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoMontageJob$SubmitVideoMontageJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoMontageJob.SubmitVideoMontageJobOutput submitVideoMontageJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoMontageJobOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoMontageJob.SubmitVideoMontageJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoMontageJob.SubmitVideoMontageJobOutput submitVideoMontageJobOutput = new SubmitVideoMontageJob.SubmitVideoMontageJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoMontageJob.SubmitVideoMontageJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoMontageJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoMontageJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoMontageJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitVideoMontageJob.SubmitVideoMontageJobOutput submitVideoMontageJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitVideoMontageJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitVideoMontageJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitVideoMontageJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitVideoMontageJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitVideoMontageJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitVideoMontageJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitVideoMontageJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
