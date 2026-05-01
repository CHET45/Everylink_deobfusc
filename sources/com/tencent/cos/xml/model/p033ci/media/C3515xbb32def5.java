package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.GetWorkflowListResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$GetWorkflowListResponseMediaWorkflowList$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3515xbb32def5 extends IXmlAdapter<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList> {
    private HashMap<String, ChildElementBinder<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList>> childElementBinders;

    public C3515xbb32def5() {
        HashMap<String, ChildElementBinder<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$GetWorkflowListResponseMediaWorkflowList$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList getWorkflowListResponseMediaWorkflowList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponseMediaWorkflowList.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("WorkflowId", new ChildElementBinder<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$GetWorkflowListResponseMediaWorkflowList$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList getWorkflowListResponseMediaWorkflowList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponseMediaWorkflowList.workflowId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$GetWorkflowListResponseMediaWorkflowList$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList getWorkflowListResponseMediaWorkflowList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponseMediaWorkflowList.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$GetWorkflowListResponseMediaWorkflowList$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList getWorkflowListResponseMediaWorkflowList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponseMediaWorkflowList.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowListResponse$GetWorkflowListResponseMediaWorkflowList$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList getWorkflowListResponseMediaWorkflowList, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowListResponseMediaWorkflowList.updateTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList getWorkflowListResponseMediaWorkflowList = new GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowListResponse.GetWorkflowListResponseMediaWorkflowList> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowListResponseMediaWorkflowList, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "MediaWorkflowList" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowListResponseMediaWorkflowList;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowListResponseMediaWorkflowList;
    }
}
