package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJob;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJobResponse;
import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3587xa8513595 extends IXmlAdapter<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation>> childElementBinders;

    public C3587xa8513595() {
        HashMap<String, ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Segment", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation submitMediaSegmentJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitMediaSegmentJobResponseOperation.segment = (SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment) QCloudXml.fromXml(xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobSegment.class, "Segment");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation submitMediaSegmentJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitMediaSegmentJobResponseOperation.output = (SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput) QCloudXml.fromXml(xmlPullParser, SubmitMediaSegmentJob.SubmitMediaSegmentJobOutput.class, "Output");
            }
        });
        this.childElementBinders.put("MediaInfo", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation submitMediaSegmentJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitMediaSegmentJobResponseOperation.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation submitMediaSegmentJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitMediaSegmentJobResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation submitMediaSegmentJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaSegmentJobResponse$SubmitMediaSegmentJobResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation submitMediaSegmentJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaSegmentJobResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation submitMediaSegmentJobResponseOperation = new SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaSegmentJobResponse.SubmitMediaSegmentJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaSegmentJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaSegmentJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaSegmentJobResponseOperation;
    }
}
