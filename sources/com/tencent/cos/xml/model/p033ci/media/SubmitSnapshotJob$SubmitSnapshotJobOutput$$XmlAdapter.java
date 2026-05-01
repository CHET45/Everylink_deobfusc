package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitSnapshotJob$SubmitSnapshotJobOutput$$XmlAdapter extends IXmlAdapter<SubmitSnapshotJob.SubmitSnapshotJobOutput> {
    private HashMap<String, ChildElementBinder<SubmitSnapshotJob.SubmitSnapshotJobOutput>> childElementBinders;

    public SubmitSnapshotJob$SubmitSnapshotJobOutput$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitSnapshotJob.SubmitSnapshotJobOutput>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Region", new ChildElementBinder<SubmitSnapshotJob.SubmitSnapshotJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJob$SubmitSnapshotJobOutput$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJob.SubmitSnapshotJobOutput submitSnapshotJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobOutput.region = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Bucket", new ChildElementBinder<SubmitSnapshotJob.SubmitSnapshotJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJob$SubmitSnapshotJobOutput$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJob.SubmitSnapshotJobOutput submitSnapshotJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobOutput.bucket = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<SubmitSnapshotJob.SubmitSnapshotJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJob$SubmitSnapshotJobOutput$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJob.SubmitSnapshotJobOutput submitSnapshotJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobOutput.object = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpriteObject", new ChildElementBinder<SubmitSnapshotJob.SubmitSnapshotJobOutput>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJob$SubmitSnapshotJobOutput$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJob.SubmitSnapshotJobOutput submitSnapshotJobOutput, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobOutput.spriteObject = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSnapshotJob.SubmitSnapshotJobOutput fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSnapshotJob.SubmitSnapshotJobOutput submitSnapshotJobOutput = new SubmitSnapshotJob.SubmitSnapshotJobOutput();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSnapshotJob.SubmitSnapshotJobOutput> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSnapshotJobOutput, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Output" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSnapshotJobOutput;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSnapshotJobOutput;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitSnapshotJob.SubmitSnapshotJobOutput submitSnapshotJobOutput, String str) throws XmlPullParserException, IOException {
        if (submitSnapshotJobOutput == null) {
            return;
        }
        if (str == null) {
            str = "Output";
        }
        xmlSerializer.startTag("", str);
        if (submitSnapshotJobOutput.region != null) {
            xmlSerializer.startTag("", "Region");
            xmlSerializer.text(String.valueOf(submitSnapshotJobOutput.region));
            xmlSerializer.endTag("", "Region");
        }
        if (submitSnapshotJobOutput.bucket != null) {
            xmlSerializer.startTag("", "Bucket");
            xmlSerializer.text(String.valueOf(submitSnapshotJobOutput.bucket));
            xmlSerializer.endTag("", "Bucket");
        }
        if (submitSnapshotJobOutput.object != null) {
            xmlSerializer.startTag("", "Object");
            xmlSerializer.text(String.valueOf(submitSnapshotJobOutput.object));
            xmlSerializer.endTag("", "Object");
        }
        if (submitSnapshotJobOutput.spriteObject != null) {
            xmlSerializer.startTag("", "SpriteObject");
            xmlSerializer.text(String.valueOf(submitSnapshotJobOutput.spriteObject));
            xmlSerializer.endTag("", "SpriteObject");
        }
        xmlSerializer.endTag("", str);
    }
}
