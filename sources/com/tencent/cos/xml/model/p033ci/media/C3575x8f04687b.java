package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitMediaInfoJobResponse;
import com.tencent.cos.xml.model.tag.MediaInfo;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3575x8f04687b extends IXmlAdapter<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation> {
    private HashMap<String, ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation>> childElementBinders;

    public C3575x8f04687b() {
        HashMap<String, ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("MediaInfo", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation submitMediaInfoJobResponseOperation, String str) throws XmlPullParserException, IOException {
                submitMediaInfoJobResponseOperation.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation submitMediaInfoJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitMediaInfoJobResponse$SubmitMediaInfoJobResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation submitMediaInfoJobResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                submitMediaInfoJobResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation submitMediaInfoJobResponseOperation = new SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitMediaInfoJobResponse.SubmitMediaInfoJobResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitMediaInfoJobResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitMediaInfoJobResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitMediaInfoJobResponseOperation;
    }
}
