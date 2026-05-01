package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.TemplateSnapshot;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateSnapshotResponse$$XmlAdapter extends IXmlAdapter<TemplateSnapshotResponse> {
    private HashMap<String, ChildElementBinder<TemplateSnapshotResponse>> childElementBinders;

    public TemplateSnapshotResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateSnapshotResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Tag", new ChildElementBinder<TemplateSnapshotResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshotResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshotResponse templateSnapshotResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotResponse.tag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<TemplateSnapshotResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshotResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshotResponse templateSnapshotResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotResponse.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateId", new ChildElementBinder<TemplateSnapshotResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshotResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshotResponse templateSnapshotResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotResponse.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<TemplateSnapshotResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshotResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshotResponse templateSnapshotResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotResponse.updateTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<TemplateSnapshotResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshotResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshotResponse templateSnapshotResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateSnapshotResponse.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(BlobConstants.SNAPSHOT_ELEMENT, new ChildElementBinder<TemplateSnapshotResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateSnapshotResponse$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateSnapshotResponse templateSnapshotResponse, String str) throws XmlPullParserException, IOException {
                templateSnapshotResponse.snapshot = (TemplateSnapshot.TemplateSnapshotSnapshot) QCloudXml.fromXml(xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot.class, BlobConstants.SNAPSHOT_ELEMENT);
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateSnapshotResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateSnapshotResponse templateSnapshotResponse = new TemplateSnapshotResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateSnapshotResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateSnapshotResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateSnapshotResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateSnapshotResponse;
    }
}
