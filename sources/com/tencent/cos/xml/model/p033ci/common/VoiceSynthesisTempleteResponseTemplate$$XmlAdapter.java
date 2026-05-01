package com.tencent.cos.xml.model.p033ci.common;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class VoiceSynthesisTempleteResponseTemplate$$XmlAdapter extends IXmlAdapter<VoiceSynthesisTempleteResponseTemplate> {
    private HashMap<String, ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>> childElementBinders;

    public VoiceSynthesisTempleteResponseTemplate$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSynthesisTempleteResponseTemplate$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSynthesisTempleteResponseTemplate.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSynthesisTempleteResponseTemplate$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSynthesisTempleteResponseTemplate.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BucketId", new ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSynthesisTempleteResponseTemplate$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSynthesisTempleteResponseTemplate.bucketId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSynthesisTempleteResponseTemplate$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSynthesisTempleteResponseTemplate.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tag", new ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSynthesisTempleteResponseTemplate$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSynthesisTempleteResponseTemplate.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSynthesisTempleteResponseTemplate$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSynthesisTempleteResponseTemplate.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSynthesisTempleteResponseTemplate$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                voiceSynthesisTempleteResponseTemplate.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TtsTpl", new ChildElementBinder<VoiceSynthesisTempleteResponseTemplate>() { // from class: com.tencent.cos.xml.model.ci.common.VoiceSynthesisTempleteResponseTemplate$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
                voiceSynthesisTempleteResponseTemplate.ttsTpl = (VoiceSynthesisTempleteResponseTtsTpl) QCloudXml.fromXml(xmlPullParser, VoiceSynthesisTempleteResponseTtsTpl.class, "TtsTpl");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VoiceSynthesisTempleteResponseTemplate fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate = new VoiceSynthesisTempleteResponseTemplate();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VoiceSynthesisTempleteResponseTemplate> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, voiceSynthesisTempleteResponseTemplate, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "VoiceSynthesisTempleteResponseTemplate" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return voiceSynthesisTempleteResponseTemplate;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return voiceSynthesisTempleteResponseTemplate;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, VoiceSynthesisTempleteResponseTemplate voiceSynthesisTempleteResponseTemplate, String str) throws XmlPullParserException, IOException {
        if (voiceSynthesisTempleteResponseTemplate == null) {
            return;
        }
        if (str == null) {
            str = "VoiceSynthesisTempleteResponseTemplate";
        }
        xmlSerializer.startTag("", str);
        if (voiceSynthesisTempleteResponseTemplate.templateId != null) {
            xmlSerializer.startTag("", "TemplateId");
            xmlSerializer.text(String.valueOf(voiceSynthesisTempleteResponseTemplate.templateId));
            xmlSerializer.endTag("", "TemplateId");
        }
        if (voiceSynthesisTempleteResponseTemplate.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(voiceSynthesisTempleteResponseTemplate.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (voiceSynthesisTempleteResponseTemplate.bucketId != null) {
            xmlSerializer.startTag("", "BucketId");
            xmlSerializer.text(String.valueOf(voiceSynthesisTempleteResponseTemplate.bucketId));
            xmlSerializer.endTag("", "BucketId");
        }
        if (voiceSynthesisTempleteResponseTemplate.category != null) {
            xmlSerializer.startTag("", "Category");
            xmlSerializer.text(String.valueOf(voiceSynthesisTempleteResponseTemplate.category));
            xmlSerializer.endTag("", "Category");
        }
        if (voiceSynthesisTempleteResponseTemplate.tag != null) {
            xmlSerializer.startTag("", "Tag");
            xmlSerializer.text(String.valueOf(voiceSynthesisTempleteResponseTemplate.tag));
            xmlSerializer.endTag("", "Tag");
        }
        if (voiceSynthesisTempleteResponseTemplate.updateTime != null) {
            xmlSerializer.startTag("", "UpdateTime");
            xmlSerializer.text(String.valueOf(voiceSynthesisTempleteResponseTemplate.updateTime));
            xmlSerializer.endTag("", "UpdateTime");
        }
        if (voiceSynthesisTempleteResponseTemplate.createTime != null) {
            xmlSerializer.startTag("", "CreateTime");
            xmlSerializer.text(String.valueOf(voiceSynthesisTempleteResponseTemplate.createTime));
            xmlSerializer.endTag("", "CreateTime");
        }
        if (voiceSynthesisTempleteResponseTemplate.ttsTpl != null) {
            QCloudXml.toXml(xmlSerializer, voiceSynthesisTempleteResponseTemplate.ttsTpl, "TtsTpl");
        }
        xmlSerializer.endTag("", str);
    }
}
