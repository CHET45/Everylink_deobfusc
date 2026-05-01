package com.tencent.cos.xml.model.p033ci.audit;

import com.github.houbb.heaven.util.lang.BoolUtil;
import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoOcrResultsLocation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3297x2728d458 extends IXmlAdapter<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation> {
    private HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation>> childElementBinders;

    public C3297x2728d458() {
        HashMap<String, ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("X", new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoOcrResultsLocation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation snapshotInfoOcrResultsLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotInfoOcrResultsLocation.f1820x = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(BoolUtil.f541Y, new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoOcrResultsLocation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation snapshotInfoOcrResultsLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotInfoOcrResultsLocation.f1821y = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoOcrResultsLocation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation snapshotInfoOcrResultsLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotInfoOcrResultsLocation.height = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoOcrResultsLocation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation snapshotInfoOcrResultsLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotInfoOcrResultsLocation.width = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Rotate", new ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation>() { // from class: com.tencent.cos.xml.model.ci.audit.GetLiveVideoAuditResponse$SnapshotInfoOcrResultsLocation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation snapshotInfoOcrResultsLocation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotInfoOcrResultsLocation.rotate = Float.parseFloat(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation snapshotInfoOcrResultsLocation = new GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetLiveVideoAuditResponse.SnapshotInfoOcrResultsLocation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, snapshotInfoOcrResultsLocation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ObjectResults" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return snapshotInfoOcrResultsLocation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return snapshotInfoOcrResultsLocation;
    }
}
