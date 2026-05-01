package com.tencent.cos.xml.model.tag;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.CallbackResult;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CallbackResult$Error$$XmlAdapter extends IXmlAdapter<CallbackResult.Error> {
    private HashMap<String, ChildElementBinder<CallbackResult.Error>> childElementBinders;

    public CallbackResult$Error$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CallbackResult.Error>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put(Constants.ERROR_CODE, new ChildElementBinder<CallbackResult.Error>() { // from class: com.tencent.cos.xml.model.tag.CallbackResult$Error$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallbackResult.Error error, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                error.code = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(Constants.ERROR_MESSAGE, new ChildElementBinder<CallbackResult.Error>() { // from class: com.tencent.cos.xml.model.tag.CallbackResult$Error$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CallbackResult.Error error, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                error.message = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CallbackResult.Error fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CallbackResult.Error error = new CallbackResult.Error();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CallbackResult.Error> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, error, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? Constants.ERROR_ROOT_ELEMENT : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return error;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return error;
    }
}
