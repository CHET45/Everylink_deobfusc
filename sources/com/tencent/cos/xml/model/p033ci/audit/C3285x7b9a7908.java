package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3285x7b9a7908 extends IXmlAdapter<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo>> childElementBinders;

    public C3285x7b9a7908() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo getLiveVideoAuditResponseInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Count", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo getLiveVideoAuditResponseInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseInfo.count = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo getLiveVideoAuditResponseInfo = new GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getLiveVideoAuditResponseInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "GetLiveVideoAuditResponseInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getLiveVideoAuditResponseInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getLiveVideoAuditResponseInfo;
    }
}
