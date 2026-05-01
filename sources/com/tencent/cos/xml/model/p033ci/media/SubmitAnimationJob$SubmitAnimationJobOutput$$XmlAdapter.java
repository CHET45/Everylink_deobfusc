package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitAnimationJob$SubmitAnimationJobOutput$$XmlAdapter extends IXmlAdapter<SubmitAnimationJob.SubmitAnimationJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobOutput>> childElementBinders;

    public SubmitAnimationJob$SubmitAnimationJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJob$SubmitAnimationJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJob.SubmitAnimationJobOutput submitAnimationJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJob$SubmitAnimationJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJob.SubmitAnimationJobOutput submitAnimationJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJob$SubmitAnimationJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJob.SubmitAnimationJobOutput submitAnimationJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitAnimationJobOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitAnimationJob.SubmitAnimationJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitAnimationJob.SubmitAnimationJobOutput submitAnimationJobOutput = new SubmitAnimationJob.SubmitAnimationJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitAnimationJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitAnimationJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitAnimationJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitAnimationJob.SubmitAnimationJobOutput submitAnimationJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitAnimationJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitAnimationJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitAnimationJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitAnimationJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitAnimationJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitAnimationJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitAnimationJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
