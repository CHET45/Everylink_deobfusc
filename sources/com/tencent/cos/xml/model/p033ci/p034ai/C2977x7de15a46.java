package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.GetSearchImageResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$GetSearchImageResponseImageInfos$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C2977x7de15a46 extends IXmlAdapter<GetSearchImageResponse.GetSearchImageResponseImageInfos> {
    private HashMap<String, ChildElementBinder<GetSearchImageResponse.GetSearchImageResponseImageInfos>> childElementBinders;

    public C2977x7de15a46() {
        HashMap<String, ChildElementBinder<GetSearchImageResponse.GetSearchImageResponseImageInfos>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("EntityId", new ChildElementBinder<GetSearchImageResponse.GetSearchImageResponseImageInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$GetSearchImageResponseImageInfos$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetSearchImageResponse.GetSearchImageResponseImageInfos getSearchImageResponseImageInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getSearchImageResponseImageInfos.entityId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CustomContent", new ChildElementBinder<GetSearchImageResponse.GetSearchImageResponseImageInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$GetSearchImageResponseImageInfos$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetSearchImageResponse.GetSearchImageResponseImageInfos getSearchImageResponseImageInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getSearchImageResponseImageInfos.customContent = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tags", new ChildElementBinder<GetSearchImageResponse.GetSearchImageResponseImageInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$GetSearchImageResponseImageInfos$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetSearchImageResponse.GetSearchImageResponseImageInfos getSearchImageResponseImageInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getSearchImageResponseImageInfos.tags = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("PicName", new ChildElementBinder<GetSearchImageResponse.GetSearchImageResponseImageInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$GetSearchImageResponseImageInfos$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetSearchImageResponse.GetSearchImageResponseImageInfos getSearchImageResponseImageInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getSearchImageResponseImageInfos.picName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Score", new ChildElementBinder<GetSearchImageResponse.GetSearchImageResponseImageInfos>() { // from class: com.tencent.cos.xml.model.ci.ai.GetSearchImageResponse$GetSearchImageResponseImageInfos$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetSearchImageResponse.GetSearchImageResponseImageInfos getSearchImageResponseImageInfos, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getSearchImageResponseImageInfos.score = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetSearchImageResponse.GetSearchImageResponseImageInfos fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetSearchImageResponse.GetSearchImageResponseImageInfos getSearchImageResponseImageInfos = new GetSearchImageResponse.GetSearchImageResponseImageInfos();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetSearchImageResponse.GetSearchImageResponseImageInfos> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getSearchImageResponseImageInfos, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "ImageInfos" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getSearchImageResponseImageInfos;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getSearchImageResponseImageInfos;
    }
}
