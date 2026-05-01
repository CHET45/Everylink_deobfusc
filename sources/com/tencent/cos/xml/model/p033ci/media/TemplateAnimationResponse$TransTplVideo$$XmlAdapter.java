package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateAnimationResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateAnimationResponse$TransTplVideo$$XmlAdapter extends IXmlAdapter<TemplateAnimationResponse.TransTplVideo> {
    private HashMap<String, ChildElementBinder<TemplateAnimationResponse.TransTplVideo>> childElementBinders;

    public TemplateAnimationResponse$TransTplVideo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateAnimationResponse.TransTplVideo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Codec", new ChildElementBinder<TemplateAnimationResponse.TransTplVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TransTplVideo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TransTplVideo transTplVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transTplVideo.codec = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<TemplateAnimationResponse.TransTplVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TransTplVideo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TransTplVideo transTplVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transTplVideo.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<TemplateAnimationResponse.TransTplVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TransTplVideo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TransTplVideo transTplVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transTplVideo.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Fps", new ChildElementBinder<TemplateAnimationResponse.TransTplVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TransTplVideo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TransTplVideo transTplVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transTplVideo.fps = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AnimateOnlyKeepKeyFrame", new ChildElementBinder<TemplateAnimationResponse.TransTplVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TransTplVideo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TransTplVideo transTplVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transTplVideo.animateOnlyKeepKeyFrame = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AnimateTimentervalOfFrame", new ChildElementBinder<TemplateAnimationResponse.TransTplVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TransTplVideo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TransTplVideo transTplVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transTplVideo.animateTimentervalOfFrame = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AnimateFramesPerSecond", new ChildElementBinder<TemplateAnimationResponse.TransTplVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TransTplVideo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TransTplVideo transTplVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transTplVideo.animateFramesPerSecond = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Quality", new ChildElementBinder<TemplateAnimationResponse.TransTplVideo>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TransTplVideo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TransTplVideo transTplVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                transTplVideo.quality = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateAnimationResponse.TransTplVideo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateAnimationResponse.TransTplVideo transTplVideo = new TemplateAnimationResponse.TransTplVideo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateAnimationResponse.TransTplVideo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, transTplVideo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Video" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return transTplVideo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return transTplVideo;
    }
}
