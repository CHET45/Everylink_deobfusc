package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.NoiseReductionTempleteResponseTemplate;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateNoiseReductionTempleteResponse$$XmlAdapter extends IXmlAdapter<UpdateNoiseReductionTempleteResponse> {
    private HashMap<String, ChildElementBinder<UpdateNoiseReductionTempleteResponse>> childElementBinders;

    public UpdateNoiseReductionTempleteResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateNoiseReductionTempleteResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Template", new ChildElementBinder<UpdateNoiseReductionTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateNoiseReductionTempleteResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateNoiseReductionTempleteResponse updateNoiseReductionTempleteResponse, String str) throws XmlPullParserException, IOException {
                updateNoiseReductionTempleteResponse.template = (NoiseReductionTempleteResponseTemplate) QCloudXml.fromXml(xmlPullParser, NoiseReductionTempleteResponseTemplate.class, "Template");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<UpdateNoiseReductionTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateNoiseReductionTempleteResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateNoiseReductionTempleteResponse updateNoiseReductionTempleteResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateNoiseReductionTempleteResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateNoiseReductionTempleteResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateNoiseReductionTempleteResponse updateNoiseReductionTempleteResponse = new UpdateNoiseReductionTempleteResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateNoiseReductionTempleteResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateNoiseReductionTempleteResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateNoiseReductionTempleteResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateNoiseReductionTempleteResponse;
    }
}
