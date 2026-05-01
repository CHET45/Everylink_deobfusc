package com.tencent.cos.xml.model.tag.audit.get;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetVideoAuditJobResponse$Snapshot$$XmlAdapter extends IXmlAdapter<GetVideoAuditJobResponse.Snapshot> {
    private HashMap<String, ChildElementBinder<GetVideoAuditJobResponse.Snapshot>> childElementBinders;

    public GetVideoAuditJobResponse$Snapshot$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetVideoAuditJobResponse.Snapshot>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.URL_ELEMENT, new ChildElementBinder<GetVideoAuditJobResponse.Snapshot>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$Snapshot$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.Snapshot snapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshot.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("SnapshotTime", new ChildElementBinder<GetVideoAuditJobResponse.Snapshot>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$Snapshot$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.Snapshot snapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshot.snapshotTime = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Text", new ChildElementBinder<GetVideoAuditJobResponse.Snapshot>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$Snapshot$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.Snapshot snapshot, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshot.text = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PornInfo", new ChildElementBinder<GetVideoAuditJobResponse.Snapshot>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$Snapshot$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.Snapshot snapshot, String str) throws XmlPullParserException, IOException {
                snapshot.pornInfo = (GetVideoAuditJobResponse.SnapshotAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo.class, "PornInfo");
            }
        });
        this.childElementBinders.put("TerrorismInfo", new ChildElementBinder<GetVideoAuditJobResponse.Snapshot>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$Snapshot$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.Snapshot snapshot, String str) throws XmlPullParserException, IOException {
                snapshot.terrorismInfo = (GetVideoAuditJobResponse.SnapshotAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo.class, "TerrorismInfo");
            }
        });
        this.childElementBinders.put("PoliticsInfo", new ChildElementBinder<GetVideoAuditJobResponse.Snapshot>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$Snapshot$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.Snapshot snapshot, String str) throws XmlPullParserException, IOException {
                snapshot.politicsInfo = (GetVideoAuditJobResponse.SnapshotAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo.class, "PoliticsInfo");
            }
        });
        this.childElementBinders.put("AdsInfo", new ChildElementBinder<GetVideoAuditJobResponse.Snapshot>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$Snapshot$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.Snapshot snapshot, String str) throws XmlPullParserException, IOException {
                snapshot.adsInfo = (GetVideoAuditJobResponse.SnapshotAuditScenarioInfo) QCloudXml.fromXml(xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo.class, "AdsInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetVideoAuditJobResponse.Snapshot fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetVideoAuditJobResponse.Snapshot snapshot = new GetVideoAuditJobResponse.Snapshot();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetVideoAuditJobResponse.Snapshot> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, snapshot, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? BlobConstants.SNAPSHOT_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return snapshot;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return snapshot;
    }
}
