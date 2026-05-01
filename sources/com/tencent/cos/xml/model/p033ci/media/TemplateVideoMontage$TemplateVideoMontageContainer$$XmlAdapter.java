package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontage;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateVideoMontage$TemplateVideoMontageContainer$$XmlAdapter extends IXmlAdapter<TemplateVideoMontage.TemplateVideoMontageContainer> {
    private HashMap<String, ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageContainer>> childElementBinders;

    public TemplateVideoMontage$TemplateVideoMontageContainer$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageContainer>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageContainer>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateVideoMontage$TemplateVideoMontageContainer$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateVideoMontage.TemplateVideoMontageContainer templateVideoMontageContainer, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateVideoMontageContainer.format = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateVideoMontage.TemplateVideoMontageContainer fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateVideoMontage.TemplateVideoMontageContainer templateVideoMontageContainer = new TemplateVideoMontage.TemplateVideoMontageContainer();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateVideoMontage.TemplateVideoMontageContainer> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateVideoMontageContainer, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? BlobConstants.CONTAINER_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateVideoMontageContainer;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateVideoMontageContainer;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateVideoMontage.TemplateVideoMontageContainer templateVideoMontageContainer, String str) throws XmlPullParserException, IOException {
        if (templateVideoMontageContainer == null) {
            return;
        }
        if (str == null) {
            str = BlobConstants.CONTAINER_ELEMENT;
        }
        xmlSerializer.startTag("", str);
        if (templateVideoMontageContainer.format != null) {
            xmlSerializer.startTag("", "Format");
            xmlSerializer.text(String.valueOf(templateVideoMontageContainer.format));
            xmlSerializer.endTag("", "Format");
        }
        xmlSerializer.endTag("", str);
    }
}
