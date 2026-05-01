package com.tencent.cos.xml.model.p033ci.media;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3507x6e213d9c extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>> childElementBinders;

    public C3507x6e213d9c() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Type", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.type = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("JobId", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.jobId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("StartTime", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.startTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("EndTime", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.endTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_CODE, new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.message = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.NAME_ELEMENT, new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseTasks.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ResultInfo", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                getWorkflowDetailResponseTasks.resultInfo = (GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseResultInfo.class, "ResultInfo");
            }
        });
        this.childElementBinders.put("JudgementInfo", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                getWorkflowDetailResponseTasks.judgementInfo = (GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseJudgementInfo.class, "JudgementInfo");
            }
        });
        this.childElementBinders.put("FileInfo", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseTasks$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks, String str) throws XmlPullParserException, IOException {
                if (getWorkflowDetailResponseTasks.fileInfo == null) {
                    getWorkflowDetailResponseTasks.fileInfo = new ArrayList();
                }
                getWorkflowDetailResponseTasks.fileInfo.add((GetWorkflowDetailResponse.GetWorkflowDetailResponseFileInfo) QCloudXml.fromXml(xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseFileInfo.class, "FileInfo"));
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks getWorkflowDetailResponseTasks = new GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseTasks> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseTasks, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Tasks" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseTasks;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseTasks;
    }
}
