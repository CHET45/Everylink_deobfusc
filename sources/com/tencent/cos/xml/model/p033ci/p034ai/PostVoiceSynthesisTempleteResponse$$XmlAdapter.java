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
public class PostVoiceSynthesisTempleteResponse$$XmlAdapter extends IXmlAdapter<PostVoiceSynthesisTempleteResponse> {
    private HashMap<String, ChildElementBinder<PostVoiceSynthesisTempleteResponse>> childElementBinders;

    public PostVoiceSynthesisTempleteResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PostVoiceSynthesisTempleteResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Template", new ChildElementBinder<PostVoiceSynthesisTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisTempleteResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisTempleteResponse postVoiceSynthesisTempleteResponse, String str) throws XmlPullParserException, IOException {
                postVoiceSynthesisTempleteResponse.template = (VoiceSynthesisTempleteResponseTemplate) QCloudXml.fromXml(xmlPullParser, VoiceSynthesisTempleteResponseTemplate.class, "Template");
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<PostVoiceSynthesisTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVoiceSynthesisTempleteResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVoiceSynthesisTempleteResponse postVoiceSynthesisTempleteResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVoiceSynthesisTempleteResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVoiceSynthesisTempleteResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVoiceSynthesisTempleteResponse postVoiceSynthesisTempleteResponse = new PostVoiceSynthesisTempleteResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVoiceSynthesisTempleteResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVoiceSynthesisTempleteResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVoiceSynthesisTempleteResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVoiceSynthesisTempleteResponse;
    }
}
