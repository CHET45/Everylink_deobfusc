package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateConcat;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateConcat$TemplateConcatSceneChangeInfo$$XmlAdapter extends IXmlAdapter<TemplateConcat.TemplateConcatSceneChangeInfo> {
    private HashMap<String, ChildElementBinder<TemplateConcat.TemplateConcatSceneChangeInfo>> childElementBinders;

    public TemplateConcat$TemplateConcatSceneChangeInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateConcat.TemplateConcatSceneChangeInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Mode", new ChildElementBinder<TemplateConcat.TemplateConcatSceneChangeInfo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatSceneChangeInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatSceneChangeInfo templateConcatSceneChangeInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatSceneChangeInfo.mode = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Time", new ChildElementBinder<TemplateConcat.TemplateConcatSceneChangeInfo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateConcat$TemplateConcatSceneChangeInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateConcat.TemplateConcatSceneChangeInfo templateConcatSceneChangeInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateConcatSceneChangeInfo.time = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateConcat.TemplateConcatSceneChangeInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateConcat.TemplateConcatSceneChangeInfo templateConcatSceneChangeInfo = new TemplateConcat.TemplateConcatSceneChangeInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateConcat.TemplateConcatSceneChangeInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateConcatSceneChangeInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SceneChangeInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateConcatSceneChangeInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateConcatSceneChangeInfo;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateConcat.TemplateConcatSceneChangeInfo templateConcatSceneChangeInfo, String str) throws XmlPullParserException, IOException {
        if (templateConcatSceneChangeInfo == null) {
            return;
        }
        if (str == null) {
            str = "SceneChangeInfo";
        }
        xmlSerializer.startTag("", str);
        if (templateConcatSceneChangeInfo.mode != null) {
            xmlSerializer.startTag("", "Mode");
            xmlSerializer.text(String.valueOf(templateConcatSceneChangeInfo.mode));
            xmlSerializer.endTag("", "Mode");
        }
        if (templateConcatSceneChangeInfo.time != null) {
            xmlSerializer.startTag("", "Time");
            xmlSerializer.text(String.valueOf(templateConcatSceneChangeInfo.time));
            xmlSerializer.endTag("", "Time");
        }
        xmlSerializer.endTag("", str);
    }
}
