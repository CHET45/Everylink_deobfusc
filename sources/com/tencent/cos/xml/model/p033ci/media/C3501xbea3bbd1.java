package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseJudgementInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3501xbea3bbd1 extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo>> childElementBinders;

    public C3501xbea3bbd1() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("ObjectCount", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseJudgementInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo getWorkflowDetailResponseJudgementInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseJudgementInfo.objectCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("JudgementResult", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseJudgementInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo getWorkflowDetailResponseJudgementInfo, String str) throws XmlPullParserException, IOException {
                if (getWorkflowDetailResponseJudgementInfo.judgementResult == null) {
                    getWorkflowDetailResponseJudgementInfo.judgementResult = new ArrayList();
                }
                getWorkflowDetailResponseJudgementInfo.judgementResult.add((GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementResult.class, "JudgementResult"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo getWorkflowDetailResponseJudgementInfo = new GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseJudgementInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "JudgementInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseJudgementInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseJudgementInfo;
    }
}
