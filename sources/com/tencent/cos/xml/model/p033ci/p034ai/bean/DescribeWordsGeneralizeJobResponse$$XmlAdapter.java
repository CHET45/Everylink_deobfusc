package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeWordsGeneralizeJobResponse$$XmlAdapter extends IXmlAdapter<DescribeWordsGeneralizeJobResponse> {
    private HashMap<String, ChildElementBinder<DescribeWordsGeneralizeJobResponse>> childElementBinders;

    public DescribeWordsGeneralizeJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<DescribeWordsGeneralizeJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<DescribeWordsGeneralizeJobResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.DescribeWordsGeneralizeJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeWordsGeneralizeJobResponse describeWordsGeneralizeJobResponse, String str) throws XmlPullParserException, IOException {
                describeWordsGeneralizeJobResponse.jobsDetail = (WordsGeneralizeJobDetail) QCloudXml.fromXml(xmlPullParser, WordsGeneralizeJobDetail.class, "JobsDetail");
            }
        });
        this.childElementBinders.put("NonExistJobIds", new ChildElementBinder<DescribeWordsGeneralizeJobResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.DescribeWordsGeneralizeJobResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, DescribeWordsGeneralizeJobResponse describeWordsGeneralizeJobResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                describeWordsGeneralizeJobResponse.nonExistJobIds = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public DescribeWordsGeneralizeJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        DescribeWordsGeneralizeJobResponse describeWordsGeneralizeJobResponse = new DescribeWordsGeneralizeJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<DescribeWordsGeneralizeJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, describeWordsGeneralizeJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return describeWordsGeneralizeJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return describeWordsGeneralizeJobResponse;
    }
}
