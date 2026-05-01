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

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseData$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3639x65c948c extends IXmlAdapter<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData> {
    private HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData>> childElementBinders;

    public C3639x65c948c() {
        HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Tags", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseData$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData submitVideoTagJobResponseData, String str) throws XmlPullParserException, IOException {
                submitVideoTagJobResponseData.tags = (SubmitVideoTagJobResponse.SubmitVideoTagJobResponseTags) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseTags.class, "Tags");
            }
        });
        this.childElementBinders.put("PersonTags", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseData$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData submitVideoTagJobResponseData, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponseData.personTags == null) {
                    submitVideoTagJobResponseData.personTags = new ArrayList();
                }
                submitVideoTagJobResponseData.personTags.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePersonTags.class, "PersonTags"));
            }
        });
        this.childElementBinders.put("PlaceTags", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseData$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData submitVideoTagJobResponseData, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponseData.placeTags == null) {
                    submitVideoTagJobResponseData.placeTags = new ArrayList();
                }
                submitVideoTagJobResponseData.placeTags.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponsePlaceTags.class, "PlaceTags"));
            }
        });
        this.childElementBinders.put("ActionTags", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseData$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData submitVideoTagJobResponseData, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponseData.actionTags == null) {
                    submitVideoTagJobResponseData.actionTags = new ArrayList();
                }
                submitVideoTagJobResponseData.actionTags.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseActionTags.class, "ActionTags"));
            }
        });
        this.childElementBinders.put("ObjectTags", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseData$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData submitVideoTagJobResponseData, String str) throws XmlPullParserException, IOException {
                if (submitVideoTagJobResponseData.objectTags == null) {
                    submitVideoTagJobResponseData.objectTags = new ArrayList();
                }
                submitVideoTagJobResponseData.objectTags.add((SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseObjectTags.class, "ObjectTags"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData submitVideoTagJobResponseData = new SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseData> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoTagJobResponseData, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Data" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoTagJobResponseData;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoTagJobResponseData;
    }
}
