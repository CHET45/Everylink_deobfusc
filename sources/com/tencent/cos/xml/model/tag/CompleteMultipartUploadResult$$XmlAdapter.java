package com.tencent.cos.xml.model.tag;

import com.tencent.cos.xml.model.tag.pic.ImageInfo;
import com.tencent.cos.xml.model.tag.pic.PicObject;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class CompleteMultipartUploadResult$$XmlAdapter extends IXmlAdapter<CompleteMultipartUploadResult> {
    private HashMap<String, ChildElementBinder<CompleteMultipartUploadResult>> childElementBinders;

    public CompleteMultipartUploadResult$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CompleteMultipartUploadResult>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Key", new ChildElementBinder<CompleteMultipartUploadResult>() { // from class: com.tencent.cos.xml.model.tag.CompleteMultipartUploadResult$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CompleteMultipartUploadResult completeMultipartUploadResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                completeMultipartUploadResult.key = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<CompleteMultipartUploadResult>() { // from class: com.tencent.cos.xml.model.tag.CompleteMultipartUploadResult$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CompleteMultipartUploadResult completeMultipartUploadResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                completeMultipartUploadResult.location = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ETag", new ChildElementBinder<CompleteMultipartUploadResult>() { // from class: com.tencent.cos.xml.model.tag.CompleteMultipartUploadResult$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CompleteMultipartUploadResult completeMultipartUploadResult, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                completeMultipartUploadResult.eTag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ImageInfo", new ChildElementBinder<CompleteMultipartUploadResult>() { // from class: com.tencent.cos.xml.model.tag.CompleteMultipartUploadResult$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CompleteMultipartUploadResult completeMultipartUploadResult, String str) throws XmlPullParserException, IOException {
                completeMultipartUploadResult.imageInfo = (ImageInfo) QCloudXml.fromXml(xmlPullParser, ImageInfo.class, "ImageInfo");
            }
        });
        this.childElementBinders.put("ProcessResults", new ChildElementBinder<CompleteMultipartUploadResult>() { // from class: com.tencent.cos.xml.model.tag.CompleteMultipartUploadResult$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CompleteMultipartUploadResult completeMultipartUploadResult, String str) throws XmlPullParserException, IOException {
                if (completeMultipartUploadResult.processResults == null) {
                    completeMultipartUploadResult.processResults = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        completeMultipartUploadResult.processResults.add((PicObject) QCloudXml.fromXml(xmlPullParser, PicObject.class, "Object"));
                    } else if (eventType == 3 && "ProcessResults".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("CallbackResult", new ChildElementBinder<CompleteMultipartUploadResult>() { // from class: com.tencent.cos.xml.model.tag.CompleteMultipartUploadResult$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CompleteMultipartUploadResult completeMultipartUploadResult, String str) throws XmlPullParserException, IOException {
                completeMultipartUploadResult.callbackResult = (CallbackResult) QCloudXml.fromXml(xmlPullParser, CallbackResult.class, "CallbackResult");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CompleteMultipartUploadResult fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CompleteMultipartUploadResult completeMultipartUploadResult = new CompleteMultipartUploadResult();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CompleteMultipartUploadResult> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, completeMultipartUploadResult, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "CompleteMultipartUploadResult" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return completeMultipartUploadResult;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return completeMultipartUploadResult;
    }
}
