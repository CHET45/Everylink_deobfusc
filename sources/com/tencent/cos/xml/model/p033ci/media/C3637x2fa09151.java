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

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseActionTags$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3637x2fa09151 extends IXmlAdapter<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags> {
    private HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags>> childElementBinders;

    public C3637x2fa09151() {
        HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Tags", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseActionTags$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags submitVideoTagJobResponseActionTags, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponseActionTags.tags == null) {
                    submitVideoTagJobResponseActionTags.tags = new ArrayList();
                }
                submitVideoTagJobResponseActionTags.tags.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponseTags) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseTags.class, "Tags"));
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseActionTags$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags submitVideoTagJobResponseActionTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponseActionTags.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseActionTags$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags submitVideoTagJobResponseActionTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponseActionTags.endTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags submitVideoTagJobResponseActionTags = new SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoTagJobResponseActionTags, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ActionTags" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoTagJobResponseActionTags;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoTagJobResponseActionTags;
    }
}
