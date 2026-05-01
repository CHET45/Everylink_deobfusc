package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJob;
import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3601x43b015d3 extends IXmlAdapter<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation>> childElementBinders;

    public C3601x43b015d3() {
        HashMap<String, ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("SmartCover", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation submitSmartCoverJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseOperation.smartCover = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation submitSmartCoverJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitSmartCoverJobResponseOperation.output = (SubmitSmartCoverJob.SubmitSmartCoverJobOutput) QCloudXml.fromXml(xmlPullParser, SubmitSmartCoverJob.SubmitSmartCoverJobOutput.class, "Output");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation submitSmartCoverJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitSmartCoverJobResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation submitSmartCoverJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitSmartCoverJobResponse$SubmitSmartCoverJobResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation submitSmartCoverJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitSmartCoverJobResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation submitSmartCoverJobResponseOperation = new SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitSmartCoverJobResponse.SubmitSmartCoverJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitSmartCoverJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitSmartCoverJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitSmartCoverJobResponseOperation;
    }
}
