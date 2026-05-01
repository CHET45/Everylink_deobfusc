package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseVideo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3508xd2e9db09 extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo>> childElementBinders;

    public C3508xd2e9db09() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Height", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseVideo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo getWorkflowDetailResponseVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseVideo.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseVideo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo getWorkflowDetailResponseVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseVideo.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Dar", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseVideo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo getWorkflowDetailResponseVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseVideo.dar = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseVideo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo getWorkflowDetailResponseVideo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseVideo.duration = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo getWorkflowDetailResponseVideo = new GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseVideo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseVideo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Video" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseVideo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseVideo;
    }
}
