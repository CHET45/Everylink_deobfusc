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

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3288x1218e8ec extends IXmlAdapter<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>> childElementBinders;

    public C3288x1218e8ec() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("HitFlag", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshotInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshotInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshotInfo.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshotInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshotInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OcrResults", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo, String str) throws XmlPullParserException, IOException {
                if (getLiveVideoAuditResponseSnapshotInfo.ocrResults == null) {
                    getLiveVideoAuditResponseSnapshotInfo.ocrResults = new ArrayList();
                }
                getLiveVideoAuditResponseSnapshotInfo.ocrResults.add((GetLiveVideoAuditResponse.SnapshotInfoOcrResults) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoOcrResults.class, "OcrResults"));
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo, String str) throws XmlPullParserException, IOException {
                if (getLiveVideoAuditResponseSnapshotInfo.libResults == null) {
                    getLiveVideoAuditResponseSnapshotInfo.libResults = new ArrayList();
                }
                getLiveVideoAuditResponseSnapshotInfo.libResults.add((GetLiveVideoAuditResponse.SnapshotInfoLibResults) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoLibResults.class, "LibResults"));
            }
        });
        this.childElementBinders.put("ObjectResults", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshotInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo, String str) throws XmlPullParserException, IOException {
                if (getLiveVideoAuditResponseSnapshotInfo.objectResults == null) {
                    getLiveVideoAuditResponseSnapshotInfo.objectResults = new ArrayList();
                }
                getLiveVideoAuditResponseSnapshotInfo.objectResults.add((GetLiveVideoAuditResponse.SnapshotInfoObjectResults) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoObjectResults.class, "ObjectResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo getLiveVideoAuditResponseSnapshotInfo = new GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getLiveVideoAuditResponseSnapshotInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "GetLiveVideoAuditResponseSnapshotInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getLiveVideoAuditResponseSnapshotInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getLiveVideoAuditResponseSnapshotInfo;
    }
}
