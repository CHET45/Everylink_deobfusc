package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitPicProcessJob$SubmitPicProcessJobOutput$$XmlAdapter extends IXmlAdapter<SubmitPicProcessJob.SubmitPicProcessJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitPicProcessJob.SubmitPicProcessJobOutput>> childElementBinders;

    public SubmitPicProcessJob$SubmitPicProcessJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitPicProcessJob.SubmitPicProcessJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitPicProcessJob.SubmitPicProcessJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJob$SubmitPicProcessJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJob.SubmitPicProcessJobOutput submitPicProcessJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitPicProcessJob.SubmitPicProcessJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJob$SubmitPicProcessJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJob.SubmitPicProcessJobOutput submitPicProcessJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitPicProcessJob.SubmitPicProcessJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitPicProcessJob$SubmitPicProcessJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitPicProcessJob.SubmitPicProcessJobOutput submitPicProcessJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitPicProcessJobOutput.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitPicProcessJob.SubmitPicProcessJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitPicProcessJob.SubmitPicProcessJobOutput submitPicProcessJobOutput = new SubmitPicProcessJob.SubmitPicProcessJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitPicProcessJob.SubmitPicProcessJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitPicProcessJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitPicProcessJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitPicProcessJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitPicProcessJob.SubmitPicProcessJobOutput submitPicProcessJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitPicProcessJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitPicProcessJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitPicProcessJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitPicProcessJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitPicProcessJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitPicProcessJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitPicProcessJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        xmlSerializer.endTag("", str);
    }
}
