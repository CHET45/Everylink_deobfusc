package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetLiveVideoAuditResponse$AudioSectionInfoLibResults$$XmlAdapter extends IXmlAdapter<GetLiveVideoAuditResponse.AudioSectionInfoLibResults> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.AudioSectionInfoLibResults>> childElementBinders;

    public GetLiveVideoAuditResponse$AudioSectionInfoLibResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.AudioSectionInfoLibResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("LibType", new ChildElementBinder<GetLiveVideoAuditResponse.AudioSectionInfoLibResults>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$AudioSectionInfoLibResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.AudioSectionInfoLibResults audioSectionInfoLibResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSectionInfoLibResults.libType = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("LibName", new ChildElementBinder<GetLiveVideoAuditResponse.AudioSectionInfoLibResults>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$AudioSectionInfoLibResults$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.AudioSectionInfoLibResults audioSectionInfoLibResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioSectionInfoLibResults.libName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Keywords", new ChildElementBinder<GetLiveVideoAuditResponse.AudioSectionInfoLibResults>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$AudioSectionInfoLibResults$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.AudioSectionInfoLibResults audioSectionInfoLibResults, String str) throws XmlPullParserException, IOException {
                if (audioSectionInfoLibResults.keywords == null) {
                    audioSectionInfoLibResults.keywords = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        audioSectionInfoLibResults.keywords.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "Keywords".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.AudioSectionInfoLibResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.AudioSectionInfoLibResults audioSectionInfoLibResults = new GetLiveVideoAuditResponse.AudioSectionInfoLibResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.AudioSectionInfoLibResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, audioSectionInfoLibResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "LibResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return audioSectionInfoLibResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return audioSectionInfoLibResults;
    }
}
