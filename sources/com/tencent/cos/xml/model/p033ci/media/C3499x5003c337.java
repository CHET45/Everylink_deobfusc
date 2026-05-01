package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseImageInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3499x5003c337 extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo>> childElementBinders;

    public C3499x5003c337() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Format", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseImageInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo getWorkflowDetailResponseImageInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseImageInfo.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseImageInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo getWorkflowDetailResponseImageInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseImageInfo.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseImageInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo getWorkflowDetailResponseImageInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseImageInfo.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Md5", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseImageInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo getWorkflowDetailResponseImageInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseImageInfo.md5 = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FrameCount", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseImageInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo getWorkflowDetailResponseImageInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseImageInfo.frameCount = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo getWorkflowDetailResponseImageInfo = new GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseImageInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseImageInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ImageInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseImageInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseImageInfo;
    }
}
