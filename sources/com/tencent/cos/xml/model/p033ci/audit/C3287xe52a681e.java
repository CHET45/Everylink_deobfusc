package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3287xe52a681e extends IXmlAdapter<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>> childElementBinders;

    public C3287xe52a681e() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshot.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SnapshotTime", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshot.snapshotTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshot.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshot.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Result", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getLiveVideoAuditResponseSnapshot.result = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseSnapshot.pornInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseSnapshot.adsInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo.class, "AdsInfo");
            }
        });
        this.childElementBinders.put("MeaninglessInfo", new ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$GetLiveVideoAuditResponseSnapshot$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot, String str) throws XmlPullParserException, IOException {
                getLiveVideoAuditResponseSnapshot.meaninglessInfo = (GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshotInfo.class, "MeaninglessInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot getLiveVideoAuditResponseSnapshot = new GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.GetLiveVideoAuditResponseSnapshot> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getLiveVideoAuditResponseSnapshot, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? BlobConstants.SNAPSHOT_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getLiveVideoAuditResponseSnapshot;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getLiveVideoAuditResponseSnapshot;
    }
}
