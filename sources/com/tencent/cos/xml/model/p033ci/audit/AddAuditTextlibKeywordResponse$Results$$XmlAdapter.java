package com.tencent.cos.xml.model.p033ci.audit;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.p033ci.audit.AddAuditTextlibKeywordResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class AddAuditTextlibKeywordResponse$Results$$XmlAdapter extends IXmlAdapter<AddAuditTextlibKeywordResponse.Results> {
    private HashMap<String, ChildElementBinder<AddAuditTextlibKeywordResponse.Results>> childElementBinders;

    public AddAuditTextlibKeywordResponse$Results$$XmlAdapter() {
        HashMap<String, ChildElementBinder<AddAuditTextlibKeywordResponse.Results>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<AddAuditTextlibKeywordResponse.Results>() { // from class: com.tencent.cos.xml.model.ci.audit.AddAuditTextlibKeywordResponse$Results$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AddAuditTextlibKeywordResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ErrMsg", new ChildElementBinder<AddAuditTextlibKeywordResponse.Results>() { // from class: com.tencent.cos.xml.model.ci.audit.AddAuditTextlibKeywordResponse$Results$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AddAuditTextlibKeywordResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.errMsg = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("KeywordID", new ChildElementBinder<AddAuditTextlibKeywordResponse.Results>() { // from class: com.tencent.cos.xml.model.ci.audit.AddAuditTextlibKeywordResponse$Results$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AddAuditTextlibKeywordResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.keywordID = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Content", new ChildElementBinder<AddAuditTextlibKeywordResponse.Results>() { // from class: com.tencent.cos.xml.model.ci.audit.AddAuditTextlibKeywordResponse$Results$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AddAuditTextlibKeywordResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.content = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<AddAuditTextlibKeywordResponse.Results>() { // from class: com.tencent.cos.xml.model.ci.audit.AddAuditTextlibKeywordResponse$Results$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AddAuditTextlibKeywordResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Remark", new ChildElementBinder<AddAuditTextlibKeywordResponse.Results>() { // from class: com.tencent.cos.xml.model.ci.audit.AddAuditTextlibKeywordResponse$Results$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, AddAuditTextlibKeywordResponse.Results results, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                results.remark = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public AddAuditTextlibKeywordResponse.Results fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        AddAuditTextlibKeywordResponse.Results results = new AddAuditTextlibKeywordResponse.Results();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<AddAuditTextlibKeywordResponse.Results> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, results, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Results" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return results;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return results;
    }
}
