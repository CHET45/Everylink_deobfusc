package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateTranscodeResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateTranscodeResponse$$XmlAdapter extends IXmlAdapter<TemplateTranscodeResponse> {
    private HashMap<String, ChildElementBinder<TemplateTranscodeResponse>> childElementBinders;

    public TemplateTranscodeResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateTranscodeResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Template", new ChildElementBinder<TemplateTranscodeResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse templateTranscodeResponse, String str) throws XmlPullParserException, IOException {
                templateTranscodeResponse.template = (TemplateTranscodeResponse.TemplateTranscodeResponseTemplate) QCloudXml.fromXml(xmlPullParser, TemplateTranscodeResponse.TemplateTranscodeResponseTemplate.class, "Template");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<TemplateTranscodeResponse>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscodeResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscodeResponse templateTranscodeResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscodeResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscodeResponse templateTranscodeResponse = new TemplateTranscodeResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscodeResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeResponse;
    }
}
