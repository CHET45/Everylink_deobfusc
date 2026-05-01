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
public class SubmitTranscodeJob$SubmitTranscodeJobRemoveWatermark$$XmlAdapter extends IXmlAdapter<SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark> {
    private HashMap<String, ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark>> childElementBinders;

    public SubmitTranscodeJob$SubmitTranscodeJobRemoveWatermark$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Dx", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobRemoveWatermark$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark submitTranscodeJobRemoveWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobRemoveWatermark.f1823dx = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Dy", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobRemoveWatermark$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark submitTranscodeJobRemoveWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobRemoveWatermark.f1824dy = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobRemoveWatermark$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark submitTranscodeJobRemoveWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobRemoveWatermark.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitTranscodeJob$SubmitTranscodeJobRemoveWatermark$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark submitTranscodeJobRemoveWatermark, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitTranscodeJobRemoveWatermark.height = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark submitTranscodeJobRemoveWatermark = new SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitTranscodeJobRemoveWatermark, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "RemoveWatermark" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitTranscodeJobRemoveWatermark;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitTranscodeJobRemoveWatermark;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitTranscodeJob.SubmitTranscodeJobRemoveWatermark submitTranscodeJobRemoveWatermark, String str) throws XmlPullParserException, IOException {
        if (submitTranscodeJobRemoveWatermark == null) {
            return;
        }
        if (str == null) {
            str = "RemoveWatermark";
        }
        xmlSerializer.startTag("", str);
        if (submitTranscodeJobRemoveWatermark.f1823dx != null) {
            xmlSerializer.startTag("", "Dx");
            xmlSerializer.text(String.valueOf(submitTranscodeJobRemoveWatermark.f1823dx));
            xmlSerializer.endTag("", "Dx");
        }
        if (submitTranscodeJobRemoveWatermark.f1824dy != null) {
            xmlSerializer.startTag("", "Dy");
            xmlSerializer.text(String.valueOf(submitTranscodeJobRemoveWatermark.f1824dy));
            xmlSerializer.endTag("", "Dy");
        }
        if (submitTranscodeJobRemoveWatermark.width != null) {
            xmlSerializer.startTag("", "Width");
            xmlSerializer.text(String.valueOf(submitTranscodeJobRemoveWatermark.width));
            xmlSerializer.endTag("", "Width");
        }
        if (submitTranscodeJobRemoveWatermark.height != null) {
            xmlSerializer.startTag("", "Height");
            xmlSerializer.text(String.valueOf(submitTranscodeJobRemoveWatermark.height));
            xmlSerializer.endTag("", "Height");
        }
        xmlSerializer.endTag("", str);
    }
}
