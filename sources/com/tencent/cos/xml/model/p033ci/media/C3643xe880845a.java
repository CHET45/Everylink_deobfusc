package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVideoTagJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseObjectTags$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3643xe880845a extends IXmlAdapter<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags> {
    private HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags>> childElementBinders;

    public C3643xe880845a() {
        HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Objects", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseObjectTags$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags submitVideoTagJobResponseObjectTags, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponseObjectTags.objects == null) {
                    submitVideoTagJobResponseObjectTags.objects = new ArrayList();
                }
                submitVideoTagJobResponseObjectTags.objects.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjects) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjects.class, "Objects"));
            }
        });
        this.childElementBinders.put("TimeStamp", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseObjectTags$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags submitVideoTagJobResponseObjectTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponseObjectTags.timeStamp = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags submitVideoTagJobResponseObjectTags = new SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoTagJobResponseObjectTags, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ObjectTags" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoTagJobResponseObjectTags;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoTagJobResponseObjectTags;
    }
}
