package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.SubmitVideoTagJobResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseVideoTagResult$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3650xfc53437e extends IXmlAdapter<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult> {
    private HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult>> childElementBinders;

    public C3650xfc53437e() {
        HashMap<String, ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("StreamData", new ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult>() { // from class: com.tencent.cos.xml.model.ci.media.SubmitVideoTagJobResponse$SubmitVideoTagJobResponseVideoTagResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult submitVideoTagJobResponseVideoTagResult, String str) throws XmlPullParserException, IOException {
                submitVideoTagJobResponseVideoTagResult.streamData = (SubmitVideoTagJobResponse.SubmitVideoTagJobResponseStreamData) QCloudXml.fromXml(xmlPullParser, SubmitVideoTagJobResponse.SubmitVideoTagJobResponseStreamData.class, "StreamData");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult submitVideoTagJobResponseVideoTagResult = new SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<SubmitVideoTagJobResponse.SubmitVideoTagJobResponseVideoTagResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, submitVideoTagJobResponseVideoTagResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "VideoTagResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return submitVideoTagJobResponseVideoTagResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return submitVideoTagJobResponseVideoTagResult;
    }
}
