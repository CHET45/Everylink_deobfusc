package com.tencent.cos.xml.utils;

import android.util.Xml;
import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.tag.CosError;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class BaseXmlSlimParser {
    public static void parseError(InputStream inputStream, CosError cosError) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase(Constants.ERROR_CODE)) {
                    xmlPullParserNewPullParser.next();
                    cosError.code = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase(Constants.ERROR_MESSAGE)) {
                    xmlPullParserNewPullParser.next();
                    cosError.message = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Resource")) {
                    xmlPullParserNewPullParser.next();
                    cosError.resource = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("RequestId")) {
                    xmlPullParserNewPullParser.next();
                    cosError.requestId = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("TraceId")) {
                    xmlPullParserNewPullParser.next();
                    cosError.traceId = xmlPullParserNewPullParser.getText();
                }
            }
        }
    }
}
