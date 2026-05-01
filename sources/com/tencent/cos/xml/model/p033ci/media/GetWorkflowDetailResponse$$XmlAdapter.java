package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWorkflowDetailResponse$$XmlAdapter extends IXmlAdapter<GetWorkflowDetailResponse> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse>> childElementBinders;

    public GetWorkflowDetailResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetWorkflowDetailResponse>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse getWorkflowDetailResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("WorkflowExecution", new ChildElementBinder<GetWorkflowDetailResponse>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse getWorkflowDetailResponse, String str) throws XmlPullParserException, IOException {
                getWorkflowDetailResponse.workflowExecution = (GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution.class, "WorkflowExecution");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse getWorkflowDetailResponse = new GetWorkflowDetailResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponse;
    }
}
