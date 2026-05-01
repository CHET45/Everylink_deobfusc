package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetRecResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseCarRecognition$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3029xff93d9bf extends IXmlAdapter<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition> {
    private HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition>> childElementBinders;

    public C3029xff93d9bf() {
        HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Time", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseCarRecognition$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition postVideoTargetRecResponseCarRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseCarRecognition.time = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseCarRecognition$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition postVideoTargetRecResponseCarRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseCarRecognition.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CarInfo", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseCarRecognition$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition postVideoTargetRecResponseCarRecognition, String str) throws XmlPullParserException, IOException {
                if (postVideoTargetRecResponseCarRecognition.carInfo == null) {
                    postVideoTargetRecResponseCarRecognition.carInfo = new ArrayList();
                }
                postVideoTargetRecResponseCarRecognition.carInfo.add((PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo.class, "CarInfo"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition postVideoTargetRecResponseCarRecognition = new PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVideoTargetRecResponseCarRecognition, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CarRecognition" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVideoTargetRecResponseCarRecognition;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVideoTargetRecResponseCarRecognition;
    }
}
