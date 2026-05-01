package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.common.MediaInfo;
import com.tencent.cos.xml.model.p033ci.common.MediaResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBody;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBodyResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBodyResponse$PostSegmentVideoBodyResponseOperation$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3003x1427df1b extends IXmlAdapter<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation> {
    private HashMap<String, ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation>> childElementBinders;

    public C3003x1427df1b() {
        HashMap<String, ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("SegmentVideoBody", new ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBodyResponse$PostSegmentVideoBodyResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation postSegmentVideoBodyResponseOperation, String str) throws XmlPullParserException, IOException {
                postSegmentVideoBodyResponseOperation.segmentVideoBody = (PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody) QCloudXml.fromXml(xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodySegmentVideoBody.class, "SegmentVideoBody");
            }
        });
        this.childElementBinders.put("Output", new ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBodyResponse$PostSegmentVideoBodyResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation postSegmentVideoBodyResponseOperation, String str) throws XmlPullParserException, IOException {
                postSegmentVideoBodyResponseOperation.output = (PostSegmentVideoBody.PostSegmentVideoBodyOutput) QCloudXml.fromXml(xmlPullParser, PostSegmentVideoBody.PostSegmentVideoBodyOutput.class, "Output");
            }
        });
        this.childElementBinders.put("UserData", new ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBodyResponse$PostSegmentVideoBodyResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation postSegmentVideoBodyResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodyResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBodyResponse$PostSegmentVideoBodyResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation postSegmentVideoBodyResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                postSegmentVideoBodyResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("MediaInfo", new ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBodyResponse$PostSegmentVideoBodyResponseOperation$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation postSegmentVideoBodyResponseOperation, String str) throws XmlPullParserException, IOException {
                postSegmentVideoBodyResponseOperation.mediaInfo = (MediaInfo) QCloudXml.fromXml(xmlPullParser, MediaInfo.class, "MediaInfo");
            }
        });
        this.childElementBinders.put("MediaResult", new ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.PostSegmentVideoBodyResponse$PostSegmentVideoBodyResponseOperation$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation postSegmentVideoBodyResponseOperation, String str) throws XmlPullParserException, IOException {
                postSegmentVideoBodyResponseOperation.mediaResult = (MediaResult) QCloudXml.fromXml(xmlPullParser, MediaResult.class, "MediaResult");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation postSegmentVideoBodyResponseOperation = new PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PostSegmentVideoBodyResponse.PostSegmentVideoBodyResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, postSegmentVideoBodyResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return postSegmentVideoBodyResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return postSegmentVideoBodyResponseOperation;
    }
}
