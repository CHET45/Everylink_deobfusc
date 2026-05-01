package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.VideoTargetTempleteResponseTemplate;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class UpdateVideoTargetTempleteResponse$$XmlAdapter extends IXmlAdapter<UpdateVideoTargetTempleteResponse> {
    private HashMap<String, ChildElementBinder<UpdateVideoTargetTempleteResponse>> childElementBinders;

    public UpdateVideoTargetTempleteResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<UpdateVideoTargetTempleteResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<UpdateVideoTargetTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateVideoTargetTempleteResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateVideoTargetTempleteResponse updateVideoTargetTempleteResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                updateVideoTargetTempleteResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Template", new ChildElementBinder<UpdateVideoTargetTempleteResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.UpdateVideoTargetTempleteResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, UpdateVideoTargetTempleteResponse updateVideoTargetTempleteResponse, String str) throws XmlPullParserException, IOException {
                updateVideoTargetTempleteResponse.template = (VideoTargetTempleteResponseTemplate) QCloudXml.fromXml(xmlPullParser, VideoTargetTempleteResponseTemplate.class, "Template");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public UpdateVideoTargetTempleteResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        UpdateVideoTargetTempleteResponse updateVideoTargetTempleteResponse = new UpdateVideoTargetTempleteResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<UpdateVideoTargetTempleteResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, updateVideoTargetTempleteResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return updateVideoTargetTempleteResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return updateVideoTargetTempleteResponse;
    }
}
