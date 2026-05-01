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
public class COSOCRResponse$COSOCRResponseWordCoordPoint$$XmlAdapter extends IXmlAdapter<COSOCRResponse.COSOCRResponseWordCoordPoint> {
    private HashMap<String, ChildElementBinder<COSOCRResponse.COSOCRResponseWordCoordPoint>> childElementBinders;

    public COSOCRResponse$COSOCRResponseWordCoordPoint$$XmlAdapter() {
        HashMap<String, ChildElementBinder<COSOCRResponse.COSOCRResponseWordCoordPoint>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("WordCoordinate", new ChildElementBinder<COSOCRResponse.COSOCRResponseWordCoordPoint>() { // from class: com.tencent.cos.xml.model.ci.ai.COSOCRResponse$COSOCRResponseWordCoordPoint$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, COSOCRResponse.COSOCRResponseWordCoordPoint cOSOCRResponseWordCoordPoint, String str) throws XmlPullParserException, IOException {
                cOSOCRResponseWordCoordPoint.wordCoordinate = (COSOCRResponse.COSOCRResponseWordCoordinate) QCloudXml.fromXml(xmlPullParser, COSOCRResponse.COSOCRResponseWordCoordinate.class, "WordCoordinate");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public COSOCRResponse.COSOCRResponseWordCoordPoint fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        COSOCRResponse.COSOCRResponseWordCoordPoint cOSOCRResponseWordCoordPoint = new COSOCRResponse.COSOCRResponseWordCoordPoint();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<COSOCRResponse.COSOCRResponseWordCoordPoint> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, cOSOCRResponseWordCoordPoint, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WordCoordPoint" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return cOSOCRResponseWordCoordPoint;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return cOSOCRResponseWordCoordPoint;
    }
}
