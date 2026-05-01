package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.GetAuditTextlibKeywordListResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class GetAuditTextlibKeywordListResponse$Keywords$$XmlAdapter extends IXmlAdapter<GetAuditTextlibKeywordListResponse.Keywords> {
    private HashMap<String, ChildElementBinder<GetAuditTextlibKeywordListResponse.Keywords>> childElementBinders;

    public GetAuditTextlibKeywordListResponse$Keywords$$XmlAdapter() {
        HashMap<String, ChildElementBinder<GetAuditTextlibKeywordListResponse.Keywords>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("KeywordID", new ChildElementBinder<GetAuditTextlibKeywordListResponse.Keywords>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibKeywordListResponse$Keywords$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibKeywordListResponse.Keywords keywords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                keywords.keywordID = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Content", new ChildElementBinder<GetAuditTextlibKeywordListResponse.Keywords>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibKeywordListResponse$Keywords$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibKeywordListResponse.Keywords keywords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                keywords.content = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Label", new ChildElementBinder<GetAuditTextlibKeywordListResponse.Keywords>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibKeywordListResponse$Keywords$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibKeywordListResponse.Keywords keywords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                keywords.label = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Remark", new ChildElementBinder<GetAuditTextlibKeywordListResponse.Keywords>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibKeywordListResponse$Keywords$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibKeywordListResponse.Keywords keywords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                keywords.remark = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CreateTime", new ChildElementBinder<GetAuditTextlibKeywordListResponse.Keywords>() { // from class: com.tencent.cos.xml.model.ci.audit.GetAuditTextlibKeywordListResponse$Keywords$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetAuditTextlibKeywordListResponse.Keywords keywords, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                keywords.createTime = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetAuditTextlibKeywordListResponse.Keywords fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetAuditTextlibKeywordListResponse.Keywords keywords = new GetAuditTextlibKeywordListResponse.Keywords();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetAuditTextlibKeywordListResponse.Keywords> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, keywords, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Keywords" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return keywords;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return keywords;
    }
}
