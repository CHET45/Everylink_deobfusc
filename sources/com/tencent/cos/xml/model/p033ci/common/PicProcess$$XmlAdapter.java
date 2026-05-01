package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PicProcess$$XmlAdapter extends IXmlAdapter<PicProcess> {
    private HashMap<String, ChildElementBinder<PicProcess>> childElementBinders;

    public PicProcess$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PicProcess>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("IsPicInfo", new ChildElementBinder<PicProcess>() { // from class: com.tencent.cos.xml.model.ci.common.PicProcess$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicProcess picProcess, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picProcess.isPicInfo = Boolean.parseBoolean(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("ProcessRule", new ChildElementBinder<PicProcess>() { // from class: com.tencent.cos.xml.model.ci.common.PicProcess$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicProcess picProcess, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picProcess.processRule = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PicProcess fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PicProcess picProcess = new PicProcess();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PicProcess> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, picProcess, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PicProcess" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return picProcess;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return picProcess;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PicProcess picProcess, String str) throws XmlPullParserException, IOException {
        if (picProcess == null) {
            return;
        }
        if (str == null) {
            str = "PicProcess";
        }
        xmlSerializer.startTag("", str);
        xmlSerializer.startTag("", "IsPicInfo");
        xmlSerializer.text(String.valueOf(picProcess.isPicInfo));
        xmlSerializer.endTag("", "IsPicInfo");
        if (picProcess.processRule != null) {
            xmlSerializer.startTag("", "ProcessRule");
            xmlSerializer.text(String.valueOf(picProcess.processRule));
            xmlSerializer.endTag("", "ProcessRule");
        }
        xmlSerializer.endTag("", str);
    }
}
