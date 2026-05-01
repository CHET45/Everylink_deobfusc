package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.SpeechRecognitionTempleteResponseTemplate;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateSpeechRecognitionTempleteResponse$$XmlAdapter extends IXmlAdapter<UpdateSpeechRecognitionTempleteResponse> {
    private HashMap<String, ChildElementBinder<UpdateSpeechRecognitionTempleteResponse>> childElementBinders;

    public UpdateSpeechRecognitionTempleteResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateSpeechRecognitionTempleteResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Template", new ChildElementBinder<UpdateSpeechRecognitionTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateSpeechRecognitionTempleteResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateSpeechRecognitionTempleteResponse updateSpeechRecognitionTempleteResponse, String str) throws XmlPullParserException, IOException {
                updateSpeechRecognitionTempleteResponse.template = (SpeechRecognitionTempleteResponseTemplate) QCloudXml.fromXml(xmlPullParser, SpeechRecognitionTempleteResponseTemplate.class, "Template");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<UpdateSpeechRecognitionTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateSpeechRecognitionTempleteResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateSpeechRecognitionTempleteResponse updateSpeechRecognitionTempleteResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateSpeechRecognitionTempleteResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateSpeechRecognitionTempleteResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateSpeechRecognitionTempleteResponse updateSpeechRecognitionTempleteResponse = new UpdateSpeechRecognitionTempleteResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateSpeechRecognitionTempleteResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateSpeechRecognitionTempleteResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateSpeechRecognitionTempleteResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateSpeechRecognitionTempleteResponse;
    }
}
