package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetLiveVideoAuditResponse$SnapshotInfoObjectResults$$XmlAdapter extends IXmlAdapter<GetLiveVideoAuditResponse.SnapshotInfoObjectResults> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoObjectResults>> childElementBinders;

    public GetLiveVideoAuditResponse$SnapshotInfoObjectResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoObjectResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoObjectResults>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoObjectResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoObjectResults snapshotInfoObjectResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotInfoObjectResults.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoObjectResults>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoObjectResults$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoObjectResults snapshotInfoObjectResults, String str) throws XmlPullParserException, IOException {
                snapshotInfoObjectResults.location = (GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation) QCloudXml.fromXml(xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation.class, "Location");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.SnapshotInfoObjectResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.SnapshotInfoObjectResults snapshotInfoObjectResults = new GetLiveVideoAuditResponse.SnapshotInfoObjectResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoObjectResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, snapshotInfoObjectResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ObjectResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return snapshotInfoObjectResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return snapshotInfoObjectResults;
    }
}
