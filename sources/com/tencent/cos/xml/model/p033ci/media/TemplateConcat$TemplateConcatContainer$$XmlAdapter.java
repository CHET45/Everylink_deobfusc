package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.TemplateConcat;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateConcat$TemplateConcatContainer$$XmlAdapter extends IXmlAdapter<TemplateConcat.TemplateConcatContainer> {
    private HashMap<String, ChildElementBinder<TemplateConcat.TemplateConcatContainer>> childElementBinders;

    public TemplateConcat$TemplateConcatContainer$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateConcat.TemplateConcatContainer>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<TemplateConcat.TemplateConcatContainer>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatContainer$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatContainer templateConcatContainer, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatContainer.format = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateConcat.TemplateConcatContainer fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateConcat.TemplateConcatContainer templateConcatContainer = new TemplateConcat.TemplateConcatContainer();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateConcat.TemplateConcatContainer> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateConcatContainer, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? BlobConstants.CONTAINER_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateConcatContainer;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateConcatContainer;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateConcat.TemplateConcatContainer templateConcatContainer, String str) throws XmlPullParserException, IOException {
        if (templateConcatContainer == null) {
            return;
        }
        if (str == null) {
            str = BlobConstants.CONTAINER_ELEMENT;
        }
        xmlSerializer.startTag("", str);
        if (templateConcatContainer.format != null) {
            xmlSerializer.startTag("", "Format");
            xmlSerializer.text(String.valueOf(templateConcatContainer.format));
            xmlSerializer.endTag("", "Format");
        }
        xmlSerializer.endTag("", str);
    }
}
