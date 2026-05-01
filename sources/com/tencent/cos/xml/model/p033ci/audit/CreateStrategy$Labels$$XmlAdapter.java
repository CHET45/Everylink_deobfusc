package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.CreateStrategy;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CreateStrategy$Labels$$XmlAdapter extends IXmlAdapter<CreateStrategy.Labels> {
    private HashMap<String, ChildElementBinder<CreateStrategy.Labels>> childElementBinders;

    public CreateStrategy$Labels$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CreateStrategy.Labels>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Image", new ChildElementBinder<CreateStrategy.Labels>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$Labels$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.image = (CreateStrategy.LabelInfo) QCloudXml.fromXml(xmlPullParser, CreateStrategy.LabelInfo.class, "Image");
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<CreateStrategy.Labels>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$Labels$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.text = (CreateStrategy.LabelInfo) QCloudXml.fromXml(xmlPullParser, CreateStrategy.LabelInfo.class, "Text");
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<CreateStrategy.Labels>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$Labels$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.Labels labels, String str) throws XmlPullParserException, IOException {
                labels.audio = (CreateStrategy.LabelInfo) QCloudXml.fromXml(xmlPullParser, CreateStrategy.LabelInfo.class, "Audio");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CreateStrategy.Labels fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CreateStrategy.Labels labels = new CreateStrategy.Labels();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CreateStrategy.Labels> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, labels, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Labels" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return labels;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return labels;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateStrategy.Labels labels, String str) throws XmlPullParserException, IOException {
        if (labels == null) {
            return;
        }
        if (str == null) {
            str = "Labels";
        }
        xmlSerializer.startTag("", str);
        if (labels.image != null) {
            QCloudXml.toXml(xmlSerializer, labels.image, "Image");
        }
        if (labels.text != null) {
            QCloudXml.toXml(xmlSerializer, labels.text, "Text");
        }
        if (labels.audio != null) {
            QCloudXml.toXml(xmlSerializer, labels.audio, "Audio");
        }
        xmlSerializer.endTag("", str);
    }
}
