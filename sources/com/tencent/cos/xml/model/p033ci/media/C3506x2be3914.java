package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseSpriteObjectInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3506x2be3914 extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo>> childElementBinders;

    public C3506x2be3914() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ObjectName", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseSpriteObjectInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo getWorkflowDetailResponseSpriteObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseSpriteObjectInfo.objectName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ObjectUrl", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseSpriteObjectInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo getWorkflowDetailResponseSpriteObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseSpriteObjectInfo.objectUrl = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo getWorkflowDetailResponseSpriteObjectInfo = new GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseSpriteObjectInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseSpriteObjectInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SpriteObjectInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseSpriteObjectInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseSpriteObjectInfo;
    }
}
