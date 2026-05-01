package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CreateCRcodeResponse$$XmlAdapter extends IXmlAdapter<CreateCRcodeResponse> {
    private HashMap<String, ChildElementBinder<CreateCRcodeResponse>> childElementBinders;

    public CreateCRcodeResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CreateCRcodeResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ResultImage", new ChildElementBinder<CreateCRcodeResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.CreateCRcodeResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateCRcodeResponse createCRcodeResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createCRcodeResponse.resultImage = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CreateCRcodeResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CreateCRcodeResponse createCRcodeResponse = new CreateCRcodeResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CreateCRcodeResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, createCRcodeResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return createCRcodeResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return createCRcodeResponse;
    }
}
