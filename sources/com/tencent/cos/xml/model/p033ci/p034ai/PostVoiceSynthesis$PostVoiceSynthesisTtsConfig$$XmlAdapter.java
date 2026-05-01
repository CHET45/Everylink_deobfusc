package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesis;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class PostVoiceSynthesis$PostVoiceSynthesisTtsConfig$$XmlAdapter extends IXmlAdapter<PostVoiceSynthesis.PostVoiceSynthesisTtsConfig> {
    private HashMap<String, ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisTtsConfig>> childElementBinders;

    public PostVoiceSynthesis$PostVoiceSynthesisTtsConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisTtsConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("InputType", new ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisTtsConfig>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesis$PostVoiceSynthesisTtsConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesis.PostVoiceSynthesisTtsConfig postVoiceSynthesisTtsConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisTtsConfig.inputType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Input", new ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisTtsConfig>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesis$PostVoiceSynthesisTtsConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesis.PostVoiceSynthesisTtsConfig postVoiceSynthesisTtsConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisTtsConfig.input = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVoiceSynthesis.PostVoiceSynthesisTtsConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVoiceSynthesis.PostVoiceSynthesisTtsConfig postVoiceSynthesisTtsConfig = new PostVoiceSynthesis.PostVoiceSynthesisTtsConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVoiceSynthesis.PostVoiceSynthesisTtsConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVoiceSynthesisTtsConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TtsConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVoiceSynthesisTtsConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVoiceSynthesisTtsConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, PostVoiceSynthesis.PostVoiceSynthesisTtsConfig postVoiceSynthesisTtsConfig, String str) throws XmlPullParserException, IOException {
        if (postVoiceSynthesisTtsConfig == null) {
            return;
        }
        if (str == null) {
            str = "TtsConfig";
        }
        xmlSerializer.startTag("", str);
        if (postVoiceSynthesisTtsConfig.inputType != null) {
            xmlSerializer.startTag("", "InputType");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTtsConfig.inputType));
            xmlSerializer.endTag("", "InputType");
        }
        if (postVoiceSynthesisTtsConfig.input != null) {
            xmlSerializer.startTag("", "Input");
            xmlSerializer.text(String.valueOf(postVoiceSynthesisTtsConfig.input));
            xmlSerializer.endTag("", "Input");
        }
        xmlSerializer.endTag("", str);
    }
}
