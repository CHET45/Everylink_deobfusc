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

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseWorkflowExecution$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3509x9fc3c2a7 extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>> childElementBinders;

    public C3509x9fc3c2a7() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("WorkflowId", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseWorkflowExecution$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution getWorkflowDetailResponseWorkflowExecution, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseWorkflowExecution.workflowId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("WorkflowName", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseWorkflowExecution$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution getWorkflowDetailResponseWorkflowExecution, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseWorkflowExecution.workflowName = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("RunId", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseWorkflowExecution$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution getWorkflowDetailResponseWorkflowExecution, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseWorkflowExecution.runId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseWorkflowExecution$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution getWorkflowDetailResponseWorkflowExecution, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseWorkflowExecution.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Object", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseWorkflowExecution$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution getWorkflowDetailResponseWorkflowExecution, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseWorkflowExecution.object = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseWorkflowExecution$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution getWorkflowDetailResponseWorkflowExecution, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseWorkflowExecution.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Tasks", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseWorkflowExecution$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution getWorkflowDetailResponseWorkflowExecution, String str) throws XmlPullParserException, IOException {
                if (getWorkflowDetailResponseWorkflowExecution.tasks == null) {
                    getWorkflowDetailResponseWorkflowExecution.tasks = new ArrayList();
                }
                getWorkflowDetailResponseWorkflowExecution.tasks.add((GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks.class, "Tasks"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution getWorkflowDetailResponseWorkflowExecution = new GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseWorkflowExecution> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseWorkflowExecution, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "WorkflowExecution" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseWorkflowExecution;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseWorkflowExecution;
    }
}
