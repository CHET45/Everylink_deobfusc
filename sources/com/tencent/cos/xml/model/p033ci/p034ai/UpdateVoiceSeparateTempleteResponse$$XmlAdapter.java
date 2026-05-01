package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.VoiceSeparateTempleteResponseTemplate;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateVoiceSeparateTempleteResponse$$XmlAdapter extends IXmlAdapter<UpdateVoiceSeparateTempleteResponse> {
    private HashMap<String, ChildElementBinder<UpdateVoiceSeparateTempleteResponse>> childElementBinders;

    public UpdateVoiceSeparateTempleteResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateVoiceSeparateTempleteResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Template", new ChildElementBinder<UpdateVoiceSeparateTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateVoiceSeparateTempleteResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateVoiceSeparateTempleteResponse updateVoiceSeparateTempleteResponse, String str) throws XmlPullParserException, IOException {
                updateVoiceSeparateTempleteResponse.template = (VoiceSeparateTempleteResponseTemplate) QCloudXml.fromXml(xmlPullParser, VoiceSeparateTempleteResponseTemplate.class, "Template");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<UpdateVoiceSeparateTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateVoiceSeparateTempleteResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateVoiceSeparateTempleteResponse updateVoiceSeparateTempleteResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateVoiceSeparateTempleteResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateVoiceSeparateTempleteResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateVoiceSeparateTempleteResponse updateVoiceSeparateTempleteResponse = new UpdateVoiceSeparateTempleteResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateVoiceSeparateTempleteResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateVoiceSeparateTempleteResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateVoiceSeparateTempleteResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateVoiceSeparateTempleteResponse;
    }
}
