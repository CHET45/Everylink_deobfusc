package com.tencent.cos.xml.model.p033ci.common;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class EffectConfig$$XmlAdapter extends IXmlAdapter<EffectConfig> {
    private HashMap<String, ChildElementBinder<EffectConfig>> childElementBinders;

    public EffectConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<EffectConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("EnableStartFadein", new ChildElementBinder<EffectConfig>() { // from class: com.tencent.cos.xml.model.ci.common.EffectConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, EffectConfig effectConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                effectConfig.enableStartFadein = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartFadeinTime", new ChildElementBinder<EffectConfig>() { // from class: com.tencent.cos.xml.model.ci.common.EffectConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, EffectConfig effectConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                effectConfig.startFadeinTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EnableEndFadeout", new ChildElementBinder<EffectConfig>() { // from class: com.tencent.cos.xml.model.ci.common.EffectConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, EffectConfig effectConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                effectConfig.enableEndFadeout = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndFadeoutTime", new ChildElementBinder<EffectConfig>() { // from class: com.tencent.cos.xml.model.ci.common.EffectConfig$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, EffectConfig effectConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                effectConfig.endFadeoutTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EnableBgmFade", new ChildElementBinder<EffectConfig>() { // from class: com.tencent.cos.xml.model.ci.common.EffectConfig$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, EffectConfig effectConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                effectConfig.enableBgmFade = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BgmFadeTime", new ChildElementBinder<EffectConfig>() { // from class: com.tencent.cos.xml.model.ci.common.EffectConfig$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, EffectConfig effectConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                effectConfig.bgmFadeTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public EffectConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        EffectConfig effectConfig = new EffectConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<EffectConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, effectConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "EffectConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return effectConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return effectConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, EffectConfig effectConfig, String str) throws XmlPullParserException, IOException {
        if (effectConfig == null) {
            return;
        }
        if (str == null) {
            str = "EffectConfig";
        }
        xmlSerializer.startTag("", str);
        if (effectConfig.enableStartFadein != null) {
            xmlSerializer.startTag("", "EnableStartFadein");
            xmlSerializer.text(String.valueOf(effectConfig.enableStartFadein));
            xmlSerializer.endTag("", "EnableStartFadein");
        }
        if (effectConfig.startFadeinTime != null) {
            xmlSerializer.startTag("", "StartFadeinTime");
            xmlSerializer.text(String.valueOf(effectConfig.startFadeinTime));
            xmlSerializer.endTag("", "StartFadeinTime");
        }
        if (effectConfig.enableEndFadeout != null) {
            xmlSerializer.startTag("", "EnableEndFadeout");
            xmlSerializer.text(String.valueOf(effectConfig.enableEndFadeout));
            xmlSerializer.endTag("", "EnableEndFadeout");
        }
        if (effectConfig.endFadeoutTime != null) {
            xmlSerializer.startTag("", "EndFadeoutTime");
            xmlSerializer.text(String.valueOf(effectConfig.endFadeoutTime));
            xmlSerializer.endTag("", "EndFadeoutTime");
        }
        if (effectConfig.enableBgmFade != null) {
            xmlSerializer.startTag("", "EnableBgmFade");
            xmlSerializer.text(String.valueOf(effectConfig.enableBgmFade));
            xmlSerializer.endTag("", "EnableBgmFade");
        }
        if (effectConfig.bgmFadeTime != null) {
            xmlSerializer.startTag("", "BgmFadeTime");
            xmlSerializer.text(String.valueOf(effectConfig.bgmFadeTime));
            xmlSerializer.endTag("", "BgmFadeTime");
        }
        xmlSerializer.endTag("", str);
    }
}
