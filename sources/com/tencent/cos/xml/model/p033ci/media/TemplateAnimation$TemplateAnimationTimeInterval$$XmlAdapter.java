package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateAnimation;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateAnimation$TemplateAnimationTimeInterval$$XmlAdapter extends IXmlAdapter<TemplateAnimation.TemplateAnimationTimeInterval> {
    private HashMap<String, ChildElementBinder<TemplateAnimation.TemplateAnimationTimeInterval>> childElementBinders;

    public TemplateAnimation$TemplateAnimationTimeInterval$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateAnimation.TemplateAnimationTimeInterval>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Start", new ChildElementBinder<TemplateAnimation.TemplateAnimationTimeInterval>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationTimeInterval$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationTimeInterval templateAnimationTimeInterval, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationTimeInterval.start = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<TemplateAnimation.TemplateAnimationTimeInterval>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimation$TemplateAnimationTimeInterval$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimation.TemplateAnimationTimeInterval templateAnimationTimeInterval, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateAnimationTimeInterval.duration = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateAnimation.TemplateAnimationTimeInterval fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateAnimation.TemplateAnimationTimeInterval templateAnimationTimeInterval = new TemplateAnimation.TemplateAnimationTimeInterval();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateAnimation.TemplateAnimationTimeInterval> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateAnimationTimeInterval, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TimeInterval" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateAnimationTimeInterval;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateAnimationTimeInterval;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateAnimation.TemplateAnimationTimeInterval templateAnimationTimeInterval, String str) throws XmlPullParserException, IOException {
        if (templateAnimationTimeInterval == null) {
            return;
        }
        if (str == null) {
            str = "TimeInterval";
        }
        xmlSerializer.startTag("", str);
        if (templateAnimationTimeInterval.start != null) {
            xmlSerializer.startTag("", "Start");
            xmlSerializer.text(String.valueOf(templateAnimationTimeInterval.start));
            xmlSerializer.endTag("", "Start");
        }
        if (templateAnimationTimeInterval.duration != null) {
            xmlSerializer.startTag("", "Duration");
            xmlSerializer.text(String.valueOf(templateAnimationTimeInterval.duration));
            xmlSerializer.endTag("", "Duration");
        }
        xmlSerializer.endTag("", str);
    }
}
