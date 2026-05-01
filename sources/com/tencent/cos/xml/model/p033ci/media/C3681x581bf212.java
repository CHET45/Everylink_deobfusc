package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.TemplateAnimationResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TemplateAnimationResponseTransTpl$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3681x581bf212 extends IXmlAdapter<TemplateAnimationResponse.TemplateAnimationResponseTransTpl> {
    private HashMap<String, ChildElementBinder<TemplateAnimationResponse.TemplateAnimationResponseTransTpl>> childElementBinders;

    public C3681x581bf212() {
        HashMap<String, ChildElementBinder<TemplateAnimationResponse.TemplateAnimationResponseTransTpl>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Video", new ChildElementBinder<TemplateAnimationResponse.TemplateAnimationResponseTransTpl>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TemplateAnimationResponseTransTpl$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TemplateAnimationResponseTransTpl templateAnimationResponseTransTpl, String str) throws XmlPullParserException, IOException {
                templateAnimationResponseTransTpl.video = (TemplateAnimationResponse.TransTplVideo) QCloudXml.fromXml(xmlPullParser, TemplateAnimationResponse.TransTplVideo.class, "Video");
            }
        });
        this.childElementBinders.put(BlobConstants.CONTAINER_ELEMENT, new ChildElementBinder<TemplateAnimationResponse.TemplateAnimationResponseTransTpl>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TemplateAnimationResponseTransTpl$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TemplateAnimationResponseTransTpl templateAnimationResponseTransTpl, String str) throws XmlPullParserException, IOException {
                templateAnimationResponseTransTpl.container = (TemplateAnimationResponse.TransTplContainer) QCloudXml.fromXml(xmlPullParser, TemplateAnimationResponse.TransTplContainer.class, BlobConstants.CONTAINER_ELEMENT);
            }
        });
        this.childElementBinders.put("TimeInterval", new ChildElementBinder<TemplateAnimationResponse.TemplateAnimationResponseTransTpl>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateAnimationResponse$TemplateAnimationResponseTransTpl$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateAnimationResponse.TemplateAnimationResponseTransTpl templateAnimationResponseTransTpl, String str) throws XmlPullParserException, IOException {
                templateAnimationResponseTransTpl.timeInterval = (TemplateAnimationResponse.TransTplTimeInterval) QCloudXml.fromXml(xmlPullParser, TemplateAnimationResponse.TransTplTimeInterval.class, "TimeInterval");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateAnimationResponse.TemplateAnimationResponseTransTpl fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateAnimationResponse.TemplateAnimationResponseTransTpl templateAnimationResponseTransTpl = new TemplateAnimationResponse.TemplateAnimationResponseTransTpl();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateAnimationResponse.TemplateAnimationResponseTransTpl> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateAnimationResponseTransTpl, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TransTpl" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateAnimationResponseTransTpl;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateAnimationResponseTransTpl;
    }
}
