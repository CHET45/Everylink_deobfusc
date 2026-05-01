package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.COSOCRResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class COSOCRResponse$$XmlAdapter extends IXmlAdapter<COSOCRResponse> {
    private HashMap<String, ChildElementBinder<COSOCRResponse>> childElementBinders;

    public COSOCRResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<COSOCRResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("TextDetections", new ChildElementBinder<COSOCRResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse cOSOCRResponse, String str) throws XmlPullParserException, IOException {
                cOSOCRResponse.textDetections = (COSOCRResponse.COSOCRResponseTextDetections) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponseTextDetections.class, "TextDetections");
            }
        });
        this.childElementBinders.put("Language", new ChildElementBinder<COSOCRResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse cOSOCRResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                cOSOCRResponse.language = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Angel", new ChildElementBinder<COSOCRResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse cOSOCRResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                cOSOCRResponse.angel = Float.parseFloat(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PdfPageSize", new ChildElementBinder<COSOCRResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse cOSOCRResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                cOSOCRResponse.pdfPageSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<COSOCRResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse cOSOCRResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                cOSOCRResponse.requestId = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public COSOCRResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        COSOCRResponse cOSOCRResponse = new COSOCRResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<COSOCRResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, cOSOCRResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return cOSOCRResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return cOSOCRResponse;
    }
}
