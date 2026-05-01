package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseResultInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3505x2761544d extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo>> childElementBinders;

    public C3505x2761544d() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ObjectCount", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseResultInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo getWorkflowDetailResponseResultInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseResultInfo.objectCount = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SpriteObjectCount", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseResultInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo getWorkflowDetailResponseResultInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseResultInfo.spriteObjectCount = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ObjectInfo", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseResultInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo getWorkflowDetailResponseResultInfo, String str) throws XmlPullParserException, IOException {
                if (getWorkflowDetailResponseResultInfo.objectInfo == null) {
                    getWorkflowDetailResponseResultInfo.objectInfo = new ArrayList();
                }
                getWorkflowDetailResponseResultInfo.objectInfo.add((GetWorkflowDetailResponse.GetWorkflowDetailResponseObjectInfo) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseObjectInfo.class, "ObjectInfo"));
            }
        });
        this.childElementBinders.put("SpriteObjectInfo", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseResultInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo getWorkflowDetailResponseResultInfo, String str) throws XmlPullParserException, IOException {
                if (getWorkflowDetailResponseResultInfo.spriteObjectInfo == null) {
                    getWorkflowDetailResponseResultInfo.spriteObjectInfo = new ArrayList();
                }
                getWorkflowDetailResponseResultInfo.spriteObjectInfo.add((GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo.class, "SpriteObjectInfo"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo getWorkflowDetailResponseResultInfo = new GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseResultInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ResultInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseResultInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseResultInfo;
    }
}
