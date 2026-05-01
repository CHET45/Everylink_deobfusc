package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateTranscode$TemplateTranscodeContainer$$XmlAdapter extends IXmlAdapter<TemplateTranscode.TemplateTranscodeContainer> {
    private HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeContainer>> childElementBinders;

    public TemplateTranscode$TemplateTranscodeContainer$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeContainer>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<TemplateTranscode.TemplateTranscodeContainer>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeContainer$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeContainer templateTranscodeContainer, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeContainer.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ClipConfig", new ChildElementBinder<TemplateTranscode.TemplateTranscodeContainer>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeContainer$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeContainer templateTranscodeContainer, String str) throws XmlPullParserException, IOException {
                templateTranscodeContainer.clipConfig = (TemplateTranscode.TemplateTranscodeClipConfig) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeClipConfig.class, "ClipConfig");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscode.TemplateTranscodeContainer fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscode.TemplateTranscodeContainer templateTranscodeContainer = new TemplateTranscode.TemplateTranscodeContainer();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscode.TemplateTranscodeContainer> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeContainer, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? BlobConstants.CONTAINER_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeContainer;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeContainer;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateTranscode.TemplateTranscodeContainer templateTranscodeContainer, String str) throws XmlPullParserException, IOException {
        if (templateTranscodeContainer == null) {
            return;
        }
        if (str == null) {
            str = BlobConstants.CONTAINER_ELEMENT;
        }
        xmlSerializer.startTag("", str);
        if (templateTranscodeContainer.format != null) {
            xmlSerializer.startTag("", "Format");
            xmlSerializer.text(String.valueOf(templateTranscodeContainer.format));
            xmlSerializer.endTag("", "Format");
        }
        if (templateTranscodeContainer.clipConfig != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscodeContainer.clipConfig, "ClipConfig");
        }
        xmlSerializer.endTag("", str);
    }
}
