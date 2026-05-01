package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJob;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobHlsEncrypt$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3576x5d6b54b6 extends IXmlAdapter<SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt> {
    private HashMap<String, ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt>> childElementBinders;

    public C3576x5d6b54b6() {
        HashMap<String, ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("IsHlsEncrypt", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobHlsEncrypt$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt submitMediaSegmentJobHlsEncrypt, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobHlsEncrypt.isHlsEncrypt = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UriKey", new ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJob$SubmitMediaSegmentJobHlsEncrypt$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt submitMediaSegmentJobHlsEncrypt, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobHlsEncrypt.uriKey = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt submitMediaSegmentJobHlsEncrypt = new SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaSegmentJobHlsEncrypt, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "HlsEncrypt" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaSegmentJobHlsEncrypt;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaSegmentJobHlsEncrypt;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitMediaSegmentJob.SubmitMediaSegmentJobHlsEncrypt submitMediaSegmentJobHlsEncrypt, String str) throws XmlPullParserException, IOException {
        if (submitMediaSegmentJobHlsEncrypt == null) {
            return;
        }
        if (str == null) {
            str = "HlsEncrypt";
        }
        xmlSerializer.startTag("", str);
        if (submitMediaSegmentJobHlsEncrypt.isHlsEncrypt != null) {
            xmlSerializer.startTag("", "IsHlsEncrypt");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobHlsEncrypt.isHlsEncrypt));
            xmlSerializer.endTag("", "IsHlsEncrypt");
        }
        if (submitMediaSegmentJobHlsEncrypt.uriKey != null) {
            xmlSerializer.startTag("", "UriKey");
            xmlSerializer.text(String.valueOf(submitMediaSegmentJobHlsEncrypt.uriKey));
            xmlSerializer.endTag("", "UriKey");
        }
        xmlSerializer.endTag("", str);
    }
}
