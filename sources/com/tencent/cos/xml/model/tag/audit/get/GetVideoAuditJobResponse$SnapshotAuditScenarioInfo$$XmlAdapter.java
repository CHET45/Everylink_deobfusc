package com.tencent.cos.xml.model.tag.audit.get;

import com.tencent.cos.xml.model.tag.audit.bean.AuditOcrResults;
import com.tencent.cos.xml.model.tag.audit.bean.ImageAuditScenarioInfo;
import com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter extends IXmlAdapter<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo> {
    private HashMap<String, ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>> childElementBinders;

    public GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Label", new ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotAuditScenarioInfo.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HitFlag", new ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotAuditScenarioInfo.hitFlag = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotAuditScenarioInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("SubLabel", new ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotAuditScenarioInfo.subLabel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Category", new ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                snapshotAuditScenarioInfo.category = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("OcrResults", new ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (snapshotAuditScenarioInfo.ocrResults == null) {
                    snapshotAuditScenarioInfo.ocrResults = new ArrayList();
                }
                snapshotAuditScenarioInfo.ocrResults.add((AuditOcrResults) QCloudXml.fromXml(xmlPullParser, AuditOcrResults.class, "OcrResults"));
            }
        });
        this.childElementBinders.put("ObjectResults", new ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (snapshotAuditScenarioInfo.objectResults == null) {
                    snapshotAuditScenarioInfo.objectResults = new ArrayList();
                }
                snapshotAuditScenarioInfo.objectResults.add((ImageAuditScenarioInfo.ObjectResults) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.ObjectResults.class, "ObjectResults"));
            }
        });
        this.childElementBinders.put("LibResults", new ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo>() { // from class: com.tencent.cos.xml.model.tag.audit.get.GetVideoAuditJobResponse$SnapshotAuditScenarioInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo, String str) throws XmlPullParserException, IOException {
                if (snapshotAuditScenarioInfo.libResults == null) {
                    snapshotAuditScenarioInfo.libResults = new ArrayList();
                }
                snapshotAuditScenarioInfo.libResults.add((ImageAuditScenarioInfo.LibResults) QCloudXml.fromXml(xmlPullParser, ImageAuditScenarioInfo.LibResults.class, "LibResults"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetVideoAuditJobResponse.SnapshotAuditScenarioInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetVideoAuditJobResponse.SnapshotAuditScenarioInfo snapshotAuditScenarioInfo = new GetVideoAuditJobResponse.SnapshotAuditScenarioInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetVideoAuditJobResponse.SnapshotAuditScenarioInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, snapshotAuditScenarioInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "SnapshotAuditScenarioInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return snapshotAuditScenarioInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return snapshotAuditScenarioInfo;
    }
}
