package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJob;
import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJobResponse;
import com.tencent.cos.xml.model.p033ci.media.TemplateSnapshot;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3609xd15291ff extends IXmlAdapter<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>> childElementBinders;

    public C3609xd15291ff() {
        HashMap<String, ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TemplateId", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation submitSnapshotJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseOperation.templateId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TemplateName", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation submitSnapshotJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseOperation.templateName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(BlobConstants.SNAPSHOT_ELEMENT, new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation submitSnapshotJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitSnapshotJobResponseOperation.snapshot = (TemplateSnapshot.TemplateSnapshotSnapshot) QCloudXml.fromXml(xmlPullParser, TemplateSnapshot.TemplateSnapshotSnapshot.class, BlobConstants.SNAPSHOT_ELEMENT);
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation submitSnapshotJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitSnapshotJobResponseOperation.output = (SubmitSnapshotJob.SubmitSnapshotJobOutput) QCloudXml.fromXml(xmlPullParser, SubmitSnapshotJob.SubmitSnapshotJobOutput.class, "Output");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation submitSnapshotJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitSnapshotJobResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation submitSnapshotJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSnapshotJobResponse$SubmitSnapshotJobResponseOperation$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation submitSnapshotJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSnapshotJobResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation submitSnapshotJobResponseOperation = new SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSnapshotJobResponse.SubmitSnapshotJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSnapshotJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSnapshotJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSnapshotJobResponseOperation;
    }
}
