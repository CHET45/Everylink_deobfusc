package com.tencent.cos.xml.model.p033ci.p034ai.bean;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CreateWordsGeneralizeJobResponse$$XmlAdapter extends IXmlAdapter<CreateWordsGeneralizeJobResponse> {
    private HashMap<String, ChildElementBinder<CreateWordsGeneralizeJobResponse>> childElementBinders;

    public CreateWordsGeneralizeJobResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CreateWordsGeneralizeJobResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("JobsDetail", new ChildElementBinder<CreateWordsGeneralizeJobResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.bean.CreateWordsGeneralizeJobResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateWordsGeneralizeJobResponse createWordsGeneralizeJobResponse, String str) throws XmlPullParserException, IOException {
                createWordsGeneralizeJobResponse.jobsDetail = (WordsGeneralizeJobDetail) QCloudXml.fromXml(xmlPullParser, WordsGeneralizeJobDetail.class, "JobsDetail");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CreateWordsGeneralizeJobResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CreateWordsGeneralizeJobResponse createWordsGeneralizeJobResponse = new CreateWordsGeneralizeJobResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CreateWordsGeneralizeJobResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, createWordsGeneralizeJobResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return createWordsGeneralizeJobResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return createWordsGeneralizeJobResponse;
    }
}
