package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAudioAuditJobResponse$AudioAuditScenarioInfo$$XmlAdapter extends IXmlAdapter<GetAudioAuditJobResponse.AudioAuditScenarioInfo> {
    private HashMap<String, ChildElementBinder<GetAudioAuditJobResponse.AudioAuditScenarioInfo>> childElementBinders;

    public GetAudioAuditJobResponse$AudioAuditScenarioInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAudioAuditJobResponse.AudioAuditScenarioInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditScenarioInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo audioAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditScenarioInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditScenarioInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo audioAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditScenarioInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditScenarioInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo audioAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditScenarioInfo.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditScenarioInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo audioAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditScenarioInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<GetAudioAuditJobResponse.AudioAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetAudioAuditJobResponse$AudioAuditScenarioInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAudioAuditJobResponse.AudioAuditScenarioInfo audioAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                audioAuditScenarioInfo.subLabel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAudioAuditJobResponse.AudioAuditScenarioInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAudioAuditJobResponse.AudioAuditScenarioInfo audioAuditScenarioInfo = new GetAudioAuditJobResponse.AudioAuditScenarioInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAudioAuditJobResponse.AudioAuditScenarioInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, audioAuditScenarioInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AudioAuditScenarioInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return audioAuditScenarioInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return audioAuditScenarioInfo;
    }
}
