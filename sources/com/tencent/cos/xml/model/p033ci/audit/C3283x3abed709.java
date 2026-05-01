package com.tencent.cos.xml.model.p033ci.audit;

import androidx.exifinterface.media.ExifInterface;
import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3283x3abed709 extends IXmlAdapter<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>> childElementBinders;

    public C3283x3abed709() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSection.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSection.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(ExifInterface.TAG_OFFSET_TIME, new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSection.offsetTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSection.duration = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSection.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseAudioSection.result = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseAudioSection.pornInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseAudioSection$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseAudioSection.adsInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSectionInfo.class, "AdsInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection getLiveVideoAuditResponseAudioSection = new GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseAudioSection> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getLiveVideoAuditResponseAudioSection, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "AudioSection" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getLiveVideoAuditResponseAudioSection;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getLiveVideoAuditResponseAudioSection;
    }
}
