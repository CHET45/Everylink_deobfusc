package com.tencent.cos.xml.model.p033ci.p034ai;

import com.tencent.cos.xml.model.p033ci.p034ai.VocalScore;
import com.tencent.cos.xml.model.p033ci.p034ai.VocalScoreResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class VocalScoreResponse$VocalScoreResponseOperation$$XmlAdapter extends IXmlAdapter<VocalScoreResponse.VocalScoreResponseOperation> {
    private HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponseOperation>> childElementBinders;

    public VocalScoreResponse$VocalScoreResponseOperation$$XmlAdapter() {
        HashMap<String, ChildElementBinder<VocalScoreResponse.VocalScoreResponseOperation>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("UserData", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseOperation$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseOperation vocalScoreResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseOperation.userData = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobLevel", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseOperation$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseOperation vocalScoreResponseOperation, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                vocalScoreResponseOperation.jobLevel = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("VocalScore", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseOperation$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseOperation vocalScoreResponseOperation, String str) throws XmlPullParserException, IOException {
                vocalScoreResponseOperation.vocalScore = (VocalScore.VocalScoreVocalScore) QCloudXml.fromXml(xmlPullParser, VocalScore.VocalScoreVocalScore.class, "VocalScore");
            }
        });
        this.childElementBinders.put("VocalScoreResult", new ChildElementBinder<VocalScoreResponse.VocalScoreResponseOperation>() { // from class: com.tencent.cos.xml.model.ci.ai.VocalScoreResponse$VocalScoreResponseOperation$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, VocalScoreResponse.VocalScoreResponseOperation vocalScoreResponseOperation, String str) throws XmlPullParserException, IOException {
                vocalScoreResponseOperation.vocalScoreResult = (VocalScoreResponse.VocalScoreResponseVocalScoreResult) QCloudXml.fromXml(xmlPullParser, VocalScoreResponse.VocalScoreResponseVocalScoreResult.class, "VocalScoreResult");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public VocalScoreResponse.VocalScoreResponseOperation fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        VocalScoreResponse.VocalScoreResponseOperation vocalScoreResponseOperation = new VocalScoreResponse.VocalScoreResponseOperation();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<VocalScoreResponse.VocalScoreResponseOperation> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, vocalScoreResponseOperation, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Operation" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return vocalScoreResponseOperation;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return vocalScoreResponseOperation;
    }
}
