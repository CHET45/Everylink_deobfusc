package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.TemplateAnimation;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateAnimation$TemplateAnimationContainer$$XmlAdapter extends IXmlAdapter<TemplateAnimation.TemplateAnimationContainer> {
    private HashMap<String, ChildElementBinder<TemplateAnimation.TemplateAnimationContainer>> childElementBinders;

    public TemplateAnimation$TemplateAnimationContainer$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateAnimation.TemplateAnimationContainer>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<TemplateAnimation.TemplateAnimationContainer>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationContainer$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationContainer templateAnimationContainer, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationContainer.format = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateAnimation.TemplateAnimationContainer fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateAnimation.TemplateAnimationContainer templateAnimationContainer = new TemplateAnimation.TemplateAnimationContainer();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateAnimation.TemplateAnimationContainer> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateAnimationContainer, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? BlobConstants.CONTAINER_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateAnimationContainer;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateAnimationContainer;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateAnimation.TemplateAnimationContainer templateAnimationContainer, String str) throws XmlPullParserException, IOException {
        if (templateAnimationContainer == null) {
            return;
        }
        if (str == null) {
            str = BlobConstants.CONTAINER_ELEMENT;
        }
        xmlSerializer.startTag("", str);
        if (templateAnimationContainer.format != null) {
            xmlSerializer.startTag("", "Format");
            xmlSerializer.text(String.valueOf(templateAnimationContainer.format));
            xmlSerializer.endTag("", "Format");
        }
        xmlSerializer.endTag("", str);
    }
}
