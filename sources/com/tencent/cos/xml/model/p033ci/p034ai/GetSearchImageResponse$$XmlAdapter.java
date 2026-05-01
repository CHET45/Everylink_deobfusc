package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.GetSearchImageResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetSearchImageResponse$$XmlAdapter extends IXmlAdapter<GetSearchImageResponse> {
    private HashMap<String, ChildElementBinder<GetSearchImageResponse>> childElementBinders;

    public GetSearchImageResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetSearchImageResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetSearchImageResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetSearchImageResponse getSearchImageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getSearchImageResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Count", new ChildElementBinder<GetSearchImageResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetSearchImageResponse getSearchImageResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getSearchImageResponse.count = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("ImageInfos", new ChildElementBinder<GetSearchImageResponse>() { // from class: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetSearchImageResponse getSearchImageResponse, String str) throws XmlPullParserException, IOException {
                if (getSearchImageResponse.imageInfos == null) {
                    getSearchImageResponse.imageInfos = new ArrayList();
                }
                getSearchImageResponse.imageInfos.add((GetSearchImageResponse.GetSearchImageResponseImageInfos) QCloudXml.fromXml(xmlPullParser, GetSearchImageResponse.GetSearchImageResponseImageInfos.class, "ImageInfos"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetSearchImageResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetSearchImageResponse getSearchImageResponse = new GetSearchImageResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetSearchImageResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getSearchImageResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getSearchImageResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getSearchImageResponse;
    }
}
