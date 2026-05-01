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
public class CreateWorkflowMediaWorkflow$$XmlAdapter extends IXmlAdapter<CreateWorkflowMediaWorkflow> {
    private HashMap<String, ChildElementBinder<CreateWorkflowMediaWorkflow>> childElementBinders;

    public CreateWorkflowMediaWorkflow$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CreateWorkflowMediaWorkflow>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.NAME_ELEMENT, new ChildElementBinder<CreateWorkflowMediaWorkflow>() { // from class: com.tencent.cos.xml.model.ci.common.CreateWorkflowMediaWorkflow$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateWorkflowMediaWorkflow createWorkflowMediaWorkflow, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createWorkflowMediaWorkflow.name = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("State", new ChildElementBinder<CreateWorkflowMediaWorkflow>() { // from class: com.tencent.cos.xml.model.ci.common.CreateWorkflowMediaWorkflow$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateWorkflowMediaWorkflow createWorkflowMediaWorkflow, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                createWorkflowMediaWorkflow.state = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CreateWorkflowMediaWorkflow fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CreateWorkflowMediaWorkflow createWorkflowMediaWorkflow = new CreateWorkflowMediaWorkflow();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CreateWorkflowMediaWorkflow> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, createWorkflowMediaWorkflow, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CreateWorkflowMediaWorkflow" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return createWorkflowMediaWorkflow;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return createWorkflowMediaWorkflow;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateWorkflowMediaWorkflow createWorkflowMediaWorkflow, String str) throws XmlPullParserException, IOException {
        if (createWorkflowMediaWorkflow == null) {
            return;
        }
        if (str == null) {
            str = "CreateWorkflowMediaWorkflow";
        }
        xmlSerializer.startTag("", str);
        if (createWorkflowMediaWorkflow.name != null) {
            xmlSerializer.startTag("", Constants.NAME_ELEMENT);
            xmlSerializer.text(String.valueOf(createWorkflowMediaWorkflow.name));
            xmlSerializer.endTag("", Constants.NAME_ELEMENT);
        }
        if (createWorkflowMediaWorkflow.state != null) {
            xmlSerializer.startTag("", "State");
            xmlSerializer.text(String.valueOf(createWorkflowMediaWorkflow.state));
            xmlSerializer.endTag("", "State");
        }
        xmlSerializer.endTag("", str);
    }
}
