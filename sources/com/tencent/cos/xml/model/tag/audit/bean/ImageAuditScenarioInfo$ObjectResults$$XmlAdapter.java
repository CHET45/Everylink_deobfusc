package com.tencent.cos.xml.model.tag.audit.bean;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class ImageAuditScenarioInfo$ObjectResults$$XmlAdapter extends IXmlAdapter<ImageAuditScenarioInfo.ObjectResults> {
    private HashMap<String, ChildElementBinder<ImageAuditScenarioInfo.ObjectResults>> childElementBinders;

    public ImageAuditScenarioInfo$ObjectResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<ImageAuditScenarioInfo.ObjectResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<ImageAuditScenarioInfo.ObjectResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$ObjectResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo.ObjectResults objectResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                objectResults.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<ImageAuditScenarioInfo.ObjectResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$ObjectResults$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo.ObjectResults objectResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                objectResults.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<ImageAuditScenarioInfo.ObjectResults>() { // from class: com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo$ObjectResults$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, ImageAuditScenarioInfo.ObjectResults objectResults, String str) throws XmlPullParserException, IOException {
                objectResults.location = (AuditOcrLocation) QCloudXml.fromXml(xmlPullParser, AuditOcrLocation.class, "Location");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public ImageAuditScenarioInfo.ObjectResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        ImageAuditScenarioInfo.ObjectResults objectResults = new ImageAuditScenarioInfo.ObjectResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<ImageAuditScenarioInfo.ObjectResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, objectResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ObjectResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return objectResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return objectResults;
    }
}
