package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJob;
import com.tencent.cos.xml.model.p033ci.media.TemplateAnimation;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class SubmitAnimationJob$SubmitAnimationJobAnimation$$XmlAdapter extends IXmlAdapter<SubmitAnimationJob.SubmitAnimationJobAnimation> {
    private HashMap<String, ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobAnimation>> childElementBinders;

    public SubmitAnimationJob$SubmitAnimationJobAnimation$$XmlAdapter() {
        HashMap<String, ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobAnimation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(BlobConstants.CONTAINER_ELEMENT, new ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobAnimation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJob$SubmitAnimationJobAnimation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJob.SubmitAnimationJobAnimation submitAnimationJobAnimation, String str) throws XmlPullParserException, IOException {
                submitAnimationJobAnimation.container = (TemplateAnimation.TemplateAnimationContainer) QCloudXml.fromXml(xmlPullParser, TemplateAnimation.TemplateAnimationContainer.class, BlobConstants.CONTAINER_ELEMENT);
            }
        });
        this.childElementBinders.put("Video", new ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobAnimation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJob$SubmitAnimationJobAnimation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJob.SubmitAnimationJobAnimation submitAnimationJobAnimation, String str) throws XmlPullParserException, IOException {
                submitAnimationJobAnimation.video = (TemplateAnimation.TemplateAnimationVideo) QCloudXml.fromXml(xmlPullParser, TemplateAnimation.TemplateAnimationVideo.class, "Video");
            }
        });
        this.childElementBinders.put("TimeInterval", new ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobAnimation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitAnimationJob$SubmitAnimationJobAnimation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitAnimationJob.SubmitAnimationJobAnimation submitAnimationJobAnimation, String str) throws XmlPullParserException, IOException {
                submitAnimationJobAnimation.timeInterval = (TemplateAnimation.TemplateAnimationTimeInterval) QCloudXml.fromXml(xmlPullParser, TemplateAnimation.TemplateAnimationTimeInterval.class, "TimeInterval");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitAnimationJob.SubmitAnimationJobAnimation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitAnimationJob.SubmitAnimationJobAnimation submitAnimationJobAnimation = new SubmitAnimationJob.SubmitAnimationJobAnimation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitAnimationJob.SubmitAnimationJobAnimation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitAnimationJobAnimation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Animation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitAnimationJobAnimation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitAnimationJobAnimation;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, SubmitAnimationJob.SubmitAnimationJobAnimation submitAnimationJobAnimation, String str) throws XmlPullParserException, IOException {
        if (submitAnimationJobAnimation == null) {
            return;
        }
        if (str == null) {
            str = "Animation";
        }
        xmlSerializer.startTag("", str);
        if (submitAnimationJobAnimation.container != null) {
            QCloudXml.toXml(xmlSerializer, submitAnimationJobAnimation.container, BlobConstants.CONTAINER_ELEMENT);
        }
        if (submitAnimationJobAnimation.video != null) {
            QCloudXml.toXml(xmlSerializer, submitAnimationJobAnimation.video, "Video");
        }
        if (submitAnimationJobAnimation.timeInterval != null) {
            QCloudXml.toXml(xmlSerializer, submitAnimationJobAnimation.timeInterval, "TimeInterval");
        }
        xmlSerializer.endTag("", str);
    }
}
