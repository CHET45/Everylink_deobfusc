package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.GetWorkflowListResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetWorkflowListResponse$$XmlAdapter extends IXmlAdapter<GetWorkflowListResponse> {
    private HashMap<String, ChildElementBinder<GetWorkflowListResponse>> childElementBinders;

    public GetWorkflowListResponse$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetWorkflowListResponse>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("RequestId", new ChildElementBinder<GetWorkflowListResponse>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse getWorkflowListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponse.requestId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TotalCount", new ChildElementBinder<GetWorkflowListResponse>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse getWorkflowListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponse.totalCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageNumber", new ChildElementBinder<GetWorkflowListResponse>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse getWorkflowListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponse.pageNumber = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("PageSize", new ChildElementBinder<GetWorkflowListResponse>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse getWorkflowListResponse, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponse.pageSize = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("MediaWorkflowList", new ChildElementBinder<GetWorkflowListResponse>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse getWorkflowListResponse, String str) throws XmlPullParserException, IOException {
                if (getWorkflowListResponse.mediaWorkflowList == null) {
                    getWorkflowListResponse.mediaWorkflowList = new ArrayList();
                }
                getWorkflowListResponse.mediaWorkflowList.add((GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList) QCloudXml.fromXml(xmlPullParser, GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList.class, "MediaWorkflowList"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowListResponse fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowListResponse getWorkflowListResponse = new GetWorkflowListResponse();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowListResponse> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowListResponse, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Response" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowListResponse;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowListResponse;
    }
}
