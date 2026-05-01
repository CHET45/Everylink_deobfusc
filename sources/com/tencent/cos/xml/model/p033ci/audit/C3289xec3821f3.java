package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3289xec3821f3 extends IXmlAdapter<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>> childElementBinders;

    public C3289xec3821f3() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TokenId", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.tokenId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Nickname", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.nickname = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DeviceId", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.deviceId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AppId", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.appId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Room", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.room = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IP", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.f1819iP = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Type", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ReceiveTokenId", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.receiveTokenId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Gender", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.gender = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Level", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.level = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Role", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseUserInfo$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseUserInfo.role = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo getLiveVideoAuditResponseUserInfo = new GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseUserInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getLiveVideoAuditResponseUserInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "UserInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getLiveVideoAuditResponseUserInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getLiveVideoAuditResponseUserInfo;
    }
}
