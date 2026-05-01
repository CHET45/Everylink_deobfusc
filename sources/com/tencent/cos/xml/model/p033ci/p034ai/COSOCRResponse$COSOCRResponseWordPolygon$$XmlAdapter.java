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
public class COSOCRResponse$COSOCRResponseWordPolygon$$XmlAdapter extends IXmlAdapter<COSOCRResponse.COSOCRResponseWordPolygon> {
    private HashMap<String, ChildElementBinder<COSOCRResponse.COSOCRResponseWordPolygon>> childElementBinders;

    public COSOCRResponse$COSOCRResponseWordPolygon$$XmlAdapter() {
        HashMap<String, ChildElementBinder<COSOCRResponse.COSOCRResponseWordPolygon>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("LeftTop", new ChildElementBinder<COSOCRResponse.COSOCRResponseWordPolygon>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseWordPolygon$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseWordPolygon cOSOCRResponseWordPolygon, String str) throws XmlPullParserException, IOException {
                cOSOCRResponseWordPolygon.leftTop = (COSOCRResponse.COSOCRResponsePolygon) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponsePolygon.class, "LeftTop");
            }
        });
        this.childElementBinders.put("RightTop", new ChildElementBinder<COSOCRResponse.COSOCRResponseWordPolygon>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseWordPolygon$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseWordPolygon cOSOCRResponseWordPolygon, String str) throws XmlPullParserException, IOException {
                cOSOCRResponseWordPolygon.rightTop = (COSOCRResponse.COSOCRResponsePolygon) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponsePolygon.class, "RightTop");
            }
        });
        this.childElementBinders.put("RightBottom", new ChildElementBinder<COSOCRResponse.COSOCRResponseWordPolygon>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseWordPolygon$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseWordPolygon cOSOCRResponseWordPolygon, String str) throws XmlPullParserException, IOException {
                cOSOCRResponseWordPolygon.rightBottom = (COSOCRResponse.COSOCRResponsePolygon) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponsePolygon.class, "RightBottom");
            }
        });
        this.childElementBinders.put("LeftBottom", new ChildElementBinder<COSOCRResponse.COSOCRResponseWordPolygon>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseWordPolygon$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseWordPolygon cOSOCRResponseWordPolygon, String str) throws XmlPullParserException, IOException {
                cOSOCRResponseWordPolygon.leftBottom = (COSOCRResponse.COSOCRResponsePolygon) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponsePolygon.class, "LeftBottom");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public COSOCRResponse.COSOCRResponseWordPolygon fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        COSOCRResponse.COSOCRResponseWordPolygon cOSOCRResponseWordPolygon = new COSOCRResponse.COSOCRResponseWordPolygon();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<COSOCRResponse.COSOCRResponseWordPolygon> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, cOSOCRResponseWordPolygon, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WordPolygon" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return cOSOCRResponseWordPolygon;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return cOSOCRResponseWordPolygon;
    }
}
