package com.tencent.cos.xml.model.p033ci.asr.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeSpeechJobsResponse$$XmlAdapter extends IXmlAdapter<DescribeSpeechJobsResponse> {
    private HashMap<String, ChildElementBinder<DescribeSpeechJobsResponse>> childElementBinders;

    public DescribeSpeechJobsResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DescribeSpeechJobsResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("NextToken", new ChildElementBinder<DescribeSpeechJobsResponse>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.DescribeSpeechJobsResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeSpeechJobsResponse describeSpeechJobsResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeSpeechJobsResponse.nextToken = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobsDetail", new ChildElementBinder<DescribeSpeechJobsResponse>() { // from class: com.tencent.cos.xml.model.ci.asr.bean.DescribeSpeechJobsResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeSpeechJobsResponse describeSpeechJobsResponse, String str) throws XmlPullParserException, IOException {
                if (describeSpeechJobsResponse.jobsDetail == null) {
                    describeSpeechJobsResponse.jobsDetail = new ArrayList();
                }
                describeSpeechJobsResponse.jobsDetail.add((SpeechJobsDetail) QCloudXml.fromXml(xmlPullParser, SpeechJobsDetail.class, "JobsDetail"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DescribeSpeechJobsResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DescribeSpeechJobsResponse describeSpeechJobsResponse = new DescribeSpeechJobsResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DescribeSpeechJobsResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, describeSpeechJobsResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return describeSpeechJobsResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return describeSpeechJobsResponse;
    }
}
