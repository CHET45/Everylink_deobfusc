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

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponsePetInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3033x5d12a421 extends IXmlAdapter<PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo> {
    private HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo>> childElementBinders;

    public C3033x5d12a421() {
        HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponsePetInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo postVideoTargetRecResponsePetInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponsePetInfo.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponsePetInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo postVideoTargetRecResponsePetInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponsePetInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponsePetInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo postVideoTargetRecResponsePetInfo, String str) throws XmlPullParserException, IOException {
                postVideoTargetRecResponsePetInfo.location = (PostVideoTargetRecResponse.PostVideoTargetRecResponseLocation) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseLocation.class, "Location");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo postVideoTargetRecResponsePetInfo = new PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponsePetInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVideoTargetRecResponsePetInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "PetInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVideoTargetRecResponsePetInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVideoTargetRecResponsePetInfo;
    }
}
