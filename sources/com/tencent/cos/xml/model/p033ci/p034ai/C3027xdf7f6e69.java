package com.tencent.cos.xml.model.p033ci.p034ai;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetRecResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseBodyRecognition$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3027xdf7f6e69 extends IXmlAdapter<PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition> {
    private HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition>> childElementBinders;

    public C3027xdf7f6e69() {
        HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Time", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseBodyRecognition$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition postVideoTargetRecResponseBodyRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseBodyRecognition.time = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.URL_ELEMENT, new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseBodyRecognition$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition postVideoTargetRecResponseBodyRecognition, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseBodyRecognition.url = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("BodyInfo", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseBodyRecognition$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition postVideoTargetRecResponseBodyRecognition, String str) throws XmlPullParserException, IOException {
                postVideoTargetRecResponseBodyRecognition.bodyInfo = (PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyInfo) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyInfo.class, "BodyInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition postVideoTargetRecResponseBodyRecognition = new PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVideoTargetRecResponseBodyRecognition, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "BodyRecognition" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVideoTargetRecResponseBodyRecognition;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVideoTargetRecResponseBodyRecognition;
    }
}
