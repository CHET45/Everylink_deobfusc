package com.tencent.cos.xml.model.p033ci.common;

import com.microsoft.azure.storage.Constants;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CreateWorkflowResponseMediaWorkflow$$XmlAdapter extends IXmlAdapter<CreateWorkflowResponseMediaWorkflow> {
    private HashMap<String, ChildElementBinder<CreateWorkflowResponseMediaWorkflow>> childElementBinders;

    public CreateWorkflowResponseMediaWorkflow$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CreateWorkflowResponseMediaWorkflow>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<CreateWorkflowResponseMediaWorkflow>() { // from class: com.tencent.cos.xml.model.ci.common.CreateWorkflowResponseMediaWorkflow$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateWorkflowResponseMediaWorkflow createWorkflowResponseMediaWorkflow, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createWorkflowResponseMediaWorkflow.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("WorkflowId", new ChildElementBinder<CreateWorkflowResponseMediaWorkflow>() { // from class: com.tencent.cos.xml.model.ci.common.CreateWorkflowResponseMediaWorkflow$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateWorkflowResponseMediaWorkflow createWorkflowResponseMediaWorkflow, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createWorkflowResponseMediaWorkflow.workflowId = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<CreateWorkflowResponseMediaWorkflow>() { // from class: com.tencent.cos.xml.model.ci.common.CreateWorkflowResponseMediaWorkflow$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateWorkflowResponseMediaWorkflow createWorkflowResponseMediaWorkflow, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createWorkflowResponseMediaWorkflow.state = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<CreateWorkflowResponseMediaWorkflow>() { // from class: com.tencent.cos.xml.model.ci.common.CreateWorkflowResponseMediaWorkflow$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateWorkflowResponseMediaWorkflow createWorkflowResponseMediaWorkflow, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createWorkflowResponseMediaWorkflow.createTime = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("UpdateTime", new ChildElementBinder<CreateWorkflowResponseMediaWorkflow>() { // from class: com.tencent.cos.xml.model.ci.common.CreateWorkflowResponseMediaWorkflow$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateWorkflowResponseMediaWorkflow createWorkflowResponseMediaWorkflow, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createWorkflowResponseMediaWorkflow.updateTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CreateWorkflowResponseMediaWorkflow fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CreateWorkflowResponseMediaWorkflow createWorkflowResponseMediaWorkflow = new CreateWorkflowResponseMediaWorkflow();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CreateWorkflowResponseMediaWorkflow> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, createWorkflowResponseMediaWorkflow, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CreateWorkflowResponseMediaWorkflow" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return createWorkflowResponseMediaWorkflow;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return createWorkflowResponseMediaWorkflow;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateWorkflowResponseMediaWorkflow createWorkflowResponseMediaWorkflow, String str) throws XmlPullParserException, IOException {
        if (createWorkflowResponseMediaWorkflow == null) {
            return;
        }
        if (str == null) {
            str = "CreateWorkflowResponseMediaWorkflow";
        }
        xmlSerializer.startTag("", str);
        if (createWorkflowResponseMediaWorkflow.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(createWorkflowResponseMediaWorkflow.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (createWorkflowResponseMediaWorkflow.workflowId != null) {
            xmlSerializer.startTag("", "WorkflowId");
            xmlSerializer.text(String.valueOf(createWorkflowResponseMediaWorkflow.workflowId));
            xmlSerializer.endTag("", "WorkflowId");
        }
        if (createWorkflowResponseMediaWorkflow.state != null) {
            xmlSerializer.startTag("", "State");
            xmlSerializer.text(String.valueOf(createWorkflowResponseMediaWorkflow.state));
            xmlSerializer.endTag("", "State");
        }
        if (createWorkflowResponseMediaWorkflow.createTime != null) {
            xmlSerializer.startTag("", "CreateTime");
            xmlSerializer.text(String.valueOf(createWorkflowResponseMediaWorkflow.createTime));
            xmlSerializer.endTag("", "CreateTime");
        }
        if (createWorkflowResponseMediaWorkflow.updateTime != null) {
            xmlSerializer.startTag("", "UpdateTime");
            xmlSerializer.text(String.valueOf(createWorkflowResponseMediaWorkflow.updateTime));
            xmlSerializer.endTag("", "UpdateTime");
        }
        xmlSerializer.endTag("", str);
    }
}
