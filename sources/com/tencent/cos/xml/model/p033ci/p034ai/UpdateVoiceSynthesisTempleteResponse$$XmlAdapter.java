package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.VoiceSynthesisTempleteResponseTemplate;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateVoiceSynthesisTempleteResponse$$XmlAdapter extends IXmlAdapter<UpdateVoiceSynthesisTempleteResponse> {
    private HashMap<String, ChildElementBinder<UpdateVoiceSynthesisTempleteResponse>> childElementBinders;

    public UpdateVoiceSynthesisTempleteResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateVoiceSynthesisTempleteResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Template", new ChildElementBinder<UpdateVoiceSynthesisTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateVoiceSynthesisTempleteResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateVoiceSynthesisTempleteResponse updateVoiceSynthesisTempleteResponse, String str) throws XmlPullParserException, IOException {
                updateVoiceSynthesisTempleteResponse.template = (VoiceSynthesisTempleteResponseTemplate) QCloudXml.fromXml(xmlPullParser, VoiceSynthesisTempleteResponseTemplate.class, "Template");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<UpdateVoiceSynthesisTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateVoiceSynthesisTempleteResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateVoiceSynthesisTempleteResponse updateVoiceSynthesisTempleteResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateVoiceSynthesisTempleteResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateVoiceSynthesisTempleteResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateVoiceSynthesisTempleteResponse updateVoiceSynthesisTempleteResponse = new UpdateVoiceSynthesisTempleteResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateVoiceSynthesisTempleteResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateVoiceSynthesisTempleteResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateVoiceSynthesisTempleteResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateVoiceSynthesisTempleteResponse;
    }
}
