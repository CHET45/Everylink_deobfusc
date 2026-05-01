package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetRecResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseVideoTargetRecResult$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3035xb55f86bd extends IXmlAdapter<PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult> {
    private HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult>> childElementBinders;

    public C3035xb55f86bd() {
        HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("BodyRecognition", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseVideoTargetRecResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult postVideoTargetRecResponseVideoTargetRecResult, String str) throws XmlPullParserException, IOException {
                if (postVideoTargetRecResponseVideoTargetRecResult.bodyRecognition == null) {
                    postVideoTargetRecResponseVideoTargetRecResult.bodyRecognition = new ArrayList();
                }
                postVideoTargetRecResponseVideoTargetRecResult.bodyRecognition.add((PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseBodyRecognition.class, "BodyRecognition"));
            }
        });
        this.childElementBinders.put("PetRecognition", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseVideoTargetRecResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult postVideoTargetRecResponseVideoTargetRecResult, String str) throws XmlPullParserException, IOException {
                if (postVideoTargetRecResponseVideoTargetRecResult.petRecognition == null) {
                    postVideoTargetRecResponseVideoTargetRecResult.petRecognition = new ArrayList();
                }
                postVideoTargetRecResponseVideoTargetRecResult.petRecognition.add((PostVideoTargetRecResponse.PostVideoTargetRecResponsePetRecognition) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponsePetRecognition.class, "PetRecognition"));
            }
        });
        this.childElementBinders.put("CarRecognition", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseVideoTargetRecResult$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult postVideoTargetRecResponseVideoTargetRecResult, String str) throws XmlPullParserException, IOException {
                if (postVideoTargetRecResponseVideoTargetRecResult.carRecognition == null) {
                    postVideoTargetRecResponseVideoTargetRecResult.carRecognition = new ArrayList();
                }
                postVideoTargetRecResponseVideoTargetRecResult.carRecognition.add((PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseCarRecognition.class, "CarRecognition"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult postVideoTargetRecResponseVideoTargetRecResult = new PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseVideoTargetRecResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVideoTargetRecResponseVideoTargetRecResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "VideoTargetRecResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVideoTargetRecResponseVideoTargetRecResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVideoTargetRecResponseVideoTargetRecResult;
    }
}
