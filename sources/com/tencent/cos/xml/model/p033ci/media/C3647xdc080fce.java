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

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePlaceTags$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3647xdc080fce extends IXmlAdapter<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags> {
    private HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags>> childElementBinders;

    public C3647xdc080fce() {
        HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Tags", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePlaceTags$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags submitVideoTagJobResponsePlaceTags, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponsePlaceTags.tags == null) {
                    submitVideoTagJobResponsePlaceTags.tags = new ArrayList();
                }
                submitVideoTagJobResponsePlaceTags.tags.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponseTags) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseTags.class, "Tags"));
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePlaceTags$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags submitVideoTagJobResponsePlaceTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponsePlaceTags.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePlaceTags$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags submitVideoTagJobResponsePlaceTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponsePlaceTags.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartIndex", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePlaceTags$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags submitVideoTagJobResponsePlaceTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponsePlaceTags.startIndex = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndIndex", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePlaceTags$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags submitVideoTagJobResponsePlaceTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponsePlaceTags.endIndex = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ClipFrameResult", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponsePlaceTags$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags submitVideoTagJobResponsePlaceTags, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitVideoTagJobResponsePlaceTags.clipFrameResult = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags submitVideoTagJobResponsePlaceTags = new SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoTagJobResponsePlaceTags, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PlaceTags" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoTagJobResponsePlaceTags;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoTagJobResponsePlaceTags;
    }
}
