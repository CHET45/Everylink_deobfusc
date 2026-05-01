package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetLiveVideoAuditResponse$SnapshotInfoLibResults$$XmlAdapter extends IXmlAdapter<GetLiveVideoAuditResponse.SnapshotInfoLibResults> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoLibResults>> childElementBinders;

    public GetLiveVideoAuditResponse$SnapshotInfoLibResults$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoLibResults>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ImageId", new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoLibResults>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoLibResults$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoLibResults snapshotInfoLibResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotInfoLibResults.imageId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoLibResults>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoLibResults$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoLibResults snapshotInfoLibResults, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotInfoLibResults.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.SnapshotInfoLibResults fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.SnapshotInfoLibResults snapshotInfoLibResults = new GetLiveVideoAuditResponse.SnapshotInfoLibResults();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoLibResults> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, snapshotInfoLibResults, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "LibResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return snapshotInfoLibResults;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return snapshotInfoLibResults;
    }
}
