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

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseCarInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3028x37d068d6 extends IXmlAdapter<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo> {
    private HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo>> childElementBinders;

    public C3028x37d068d6() {
        HashMap<String, ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseCarInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo postVideoTargetRecResponseCarInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseCarInfo.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseCarInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo postVideoTargetRecResponseCarInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postVideoTargetRecResponseCarInfo.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo>() { // from class: com.tencent.cos.xml.model.ci.ai.PostVideoTargetRecResponse$PostVideoTargetRecResponseCarInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo postVideoTargetRecResponseCarInfo, String str) throws XmlPullParserException, IOException {
                postVideoTargetRecResponseCarInfo.location = (PostVideoTargetRecResponse.PostVideoTargetRecResponseLocation) QCloudXml.fromXml(xmlPullParser, PostVideoTargetRecResponse.PostVideoTargetRecResponseLocation.class, "Location");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo postVideoTargetRecResponseCarInfo = new PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostVideoTargetRecResponse.PostVideoTargetRecResponseCarInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postVideoTargetRecResponseCarInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CarInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postVideoTargetRecResponseCarInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postVideoTargetRecResponseCarInfo;
    }
}
