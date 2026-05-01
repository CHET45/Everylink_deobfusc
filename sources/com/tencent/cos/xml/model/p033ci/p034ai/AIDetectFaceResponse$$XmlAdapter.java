package com.tencent.cos.xml.model.p033ci.p034ai;

import androidx.exifinterface.media.ExifInterface;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectFaceResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AIDetectFaceResponse$$XmlAdapter extends IXmlAdapter<AIDetectFaceResponse> {
    private HashMap<String, ChildElementBinder<AIDetectFaceResponse>> childElementBinders;

    public AIDetectFaceResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AIDetectFaceResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(ExifInterface.TAG_IMAGE_WIDTH, new ChildElementBinder<AIDetectFaceResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse aIDetectFaceResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectFaceResponse.imageWidth = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("ImageHeight", new ChildElementBinder<AIDetectFaceResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse aIDetectFaceResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectFaceResponse.imageHeight = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("FaceModelVersion", new ChildElementBinder<AIDetectFaceResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse aIDetectFaceResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectFaceResponse.faceModelVersion = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("RequestId", new ChildElementBinder<AIDetectFaceResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse aIDetectFaceResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                aIDetectFaceResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FaceInfos", new ChildElementBinder<AIDetectFaceResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.AIDetectFaceResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AIDetectFaceResponse aIDetectFaceResponse, String str) throws XmlPullParserException, IOException {
                aIDetectFaceResponse.faceInfos = (AIDetectFaceResponse.AIDetectFaceResponseFaceInfos) QCloudXml.fromXml(xmlPullParser, AIDetectFaceResponse.AIDetectFaceResponseFaceInfos.class, "FaceInfos");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AIDetectFaceResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AIDetectFaceResponse aIDetectFaceResponse = new AIDetectFaceResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AIDetectFaceResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, aIDetectFaceResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return aIDetectFaceResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return aIDetectFaceResponse;
    }
}
