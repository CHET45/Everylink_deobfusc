package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseBasicInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3496x7a09e80a extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo>> childElementBinders;

    public C3496x7a09e80a() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ContentType", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseBasicInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo getWorkflowDetailResponseBasicInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseBasicInfo.contentType = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(BlobConstants.SIZE_ELEMENT, new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseBasicInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo getWorkflowDetailResponseBasicInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseBasicInfo.size = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ETag", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseBasicInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo getWorkflowDetailResponseBasicInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseBasicInfo.eTag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("LastModified", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseBasicInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo getWorkflowDetailResponseBasicInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseBasicInfo.lastModified = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseBasicInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo getWorkflowDetailResponseBasicInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseBasicInfo.object = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo getWorkflowDetailResponseBasicInfo = new GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseBasicInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseBasicInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "BasicInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseBasicInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseBasicInfo;
    }
}
