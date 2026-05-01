package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseMediaInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3503xf4d457c0 extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo>> childElementBinders;

    public C3503xf4d457c0() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Video", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseMediaInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo getWorkflowDetailResponseMediaInfo, String str) throws XmlPullParserException, IOException {
                getWorkflowDetailResponseMediaInfo.video = (GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo.class, "Video");
            }
        });
        this.childElementBinders.put("Audio", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseMediaInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo getWorkflowDetailResponseMediaInfo, String str) throws XmlPullParserException, IOException {
                getWorkflowDetailResponseMediaInfo.audio = (GetWorkflowDetailResponse.GetWorkflowDetailResponseAudio) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseAudio.class, "Audio");
            }
        });
        this.childElementBinders.put("Format", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseMediaInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo getWorkflowDetailResponseMediaInfo, String str) throws XmlPullParserException, IOException {
                getWorkflowDetailResponseMediaInfo.format = (GetWorkflowDetailResponse.GetWorkflowDetailResponseFormat) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseFormat.class, "Format");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo getWorkflowDetailResponseMediaInfo = new GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseMediaInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseMediaInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseMediaInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseMediaInfo;
    }
}
