package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseJudgementResult$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3502x44268a00 extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult>> childElementBinders;

    public C3502x44268a00() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ObjectName", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseJudgementResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult getWorkflowDetailResponseJudgementResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseJudgementResult.objectName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ObjectUrl", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseJudgementResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult getWorkflowDetailResponseJudgementResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseJudgementResult.objectUrl = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseJudgementResult$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult getWorkflowDetailResponseJudgementResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseJudgementResult.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("InputObjectInfo", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseJudgementResult$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult getWorkflowDetailResponseJudgementResult, String str) throws XmlPullParserException, IOException {
                getWorkflowDetailResponseJudgementResult.inputObjectInfo = (GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo.class, "InputObjectInfo");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult getWorkflowDetailResponseJudgementResult = new GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseJudgementResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JudgementResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseJudgementResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseJudgementResult;
    }
}
