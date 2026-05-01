package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJob$SubmitDigitalWatermarkJobOutput$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3559xdc6a47 extends IXmlAdapter<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput>> childElementBinders;

    public C3559xdc6a47() {
        HashMap<String, ChildElementBinder<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJob$SubmitDigitalWatermarkJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput submitDigitalWatermarkJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJob$SubmitDigitalWatermarkJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput submitDigitalWatermarkJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitDigitalWatermarkJob$SubmitDigitalWatermarkJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput submitDigitalWatermarkJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitDigitalWatermarkJobOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput submitDigitalWatermarkJobOutput = new SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitDigitalWatermarkJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitDigitalWatermarkJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitDigitalWatermarkJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitDigitalWatermarkJob.SubmitDigitalWatermarkJobOutput submitDigitalWatermarkJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitDigitalWatermarkJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitDigitalWatermarkJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitDigitalWatermarkJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitDigitalWatermarkJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitDigitalWatermarkJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
