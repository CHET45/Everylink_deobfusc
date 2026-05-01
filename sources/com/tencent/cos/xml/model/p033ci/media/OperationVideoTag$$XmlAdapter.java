package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class OperationVideoTag$$XmlAdapter extends IXmlAdapter<OperationVideoTag> {
    private HashMap<String, ChildElementBinder<OperationVideoTag>> childElementBinders;

    public OperationVideoTag$$XmlAdapter() {
        HashMap<String, ChildElementBinder<OperationVideoTag>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Scenario", new ChildElementBinder<OperationVideoTag>() { // from class: com.tencent.cos.xml.model.ci.media.OperationVideoTag$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, OperationVideoTag operationVideoTag, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                operationVideoTag.scenario = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public OperationVideoTag fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        OperationVideoTag operationVideoTag = new OperationVideoTag();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<OperationVideoTag> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, operationVideoTag, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "VideoTag" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return operationVideoTag;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return operationVideoTag;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, OperationVideoTag operationVideoTag, String str) throws XmlPullParserException, IOException {
        if (operationVideoTag == null) {
            return;
        }
        if (str == null) {
            str = "VideoTag";
        }
        xmlSerializer.startTag("", str);
        if (operationVideoTag.scenario != null) {
            xmlSerializer.startTag("", "Scenario");
            xmlSerializer.text(String.valueOf(operationVideoTag.scenario));
            xmlSerializer.endTag("", "Scenario");
        }
        xmlSerializer.endTag("", str);
    }
}
