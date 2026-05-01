package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.CallbackResult;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CallbackResult$$XmlAdapter extends IXmlAdapter<CallbackResult> {
    private HashMap<String, ChildElementBinder<CallbackResult>> childElementBinders;

    public CallbackResult$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CallbackResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Status", new ChildElementBinder<CallbackResult>() { // from class: com.tencent.cos.xml.model.tag.CallbackResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallbackResult callbackResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                callbackResult.status = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CallbackBody", new ChildElementBinder<CallbackResult>() { // from class: com.tencent.cos.xml.model.tag.CallbackResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallbackResult callbackResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                callbackResult.callbackBody = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("CallbackBodyNotBase64", new ChildElementBinder<CallbackResult>() { // from class: com.tencent.cos.xml.model.tag.CallbackResult$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallbackResult callbackResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                callbackResult.callbackBodyNotBase64 = Boolean.parseBoolean(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(Constants.ERROR_ROOT_ELEMENT, new ChildElementBinder<CallbackResult>() { // from class: com.tencent.cos.xml.model.tag.CallbackResult$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallbackResult callbackResult, String str) throws XmlPullParserException, IOException {
                callbackResult.error = (CallbackResult.Error) QCloudXml.fromXml(xmlPullParser, CallbackResult.Error.class, Constants.ERROR_ROOT_ELEMENT);
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CallbackResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CallbackResult callbackResult = new CallbackResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CallbackResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, callbackResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CallbackResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return callbackResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return callbackResult;
    }
}
