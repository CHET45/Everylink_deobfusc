package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSectionInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3284x98b5ea57 extends IXmlAdapter<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo>> childElementBinders;

    public C3284x98b5ea57() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSectionInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo getLiveVideoAuditResponseAudioSectionInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSectionInfo.hitFlag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSectionInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo getLiveVideoAuditResponseAudioSectionInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSectionInfo.score = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSectionInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo getLiveVideoAuditResponseAudioSectionInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSectionInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Keywords", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSectionInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo getLiveVideoAuditResponseAudioSectionInfo, String str) throws XmlPullParserException, IOException {
                if (getLiveVideoAuditResponseAudioSectionInfo.keywords == null) {
                    getLiveVideoAuditResponseAudioSectionInfo.keywords = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        getLiveVideoAuditResponseAudioSectionInfo.keywords.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "Keywords".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSectionInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo getLiveVideoAuditResponseAudioSectionInfo, String str) throws XmlPullParserException, IOException {
                if (getLiveVideoAuditResponseAudioSectionInfo.libResults == null) {
                    getLiveVideoAuditResponseAudioSectionInfo.libResults = new ArrayList();
                }
                getLiveVideoAuditResponseAudioSectionInfo.libResults.add((GetLiveVideoAuditResponse.AudioSectionInfoLibResults) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.AudioSectionInfoLibResults.class, "LibResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo getLiveVideoAuditResponseAudioSectionInfo = new GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getLiveVideoAuditResponseAudioSectionInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "GetLiveVideoAuditResponseAudioSectionInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getLiveVideoAuditResponseAudioSectionInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getLiveVideoAuditResponseAudioSectionInfo;
    }
}
