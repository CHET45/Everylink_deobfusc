package com.tencent.cos.xml.model.tag.pic;

import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PicObject$$XmlAdapter extends IXmlAdapter<PicObject> {
    private HashMap<String, ChildElementBinder<PicObject>> childElementBinders;

    public PicObject$$XmlAdapter() {
        HashMap<String, ChildElementBinder<PicObject>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Key", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.key = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Location", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.location = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Format", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.format = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Width", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.width = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.height = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put(BlobConstants.SIZE_ELEMENT, new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.size = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("Quality", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.quality = Integer.parseInt(xmlPullParser.getText());
            }
        });
        this.childElementBinders.put("ETag", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.etag = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("FrameCount", new ChildElementBinder<PicObject>() { // from class: com.tencent.cos.xml.model.tag.pic.PicObject$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, PicObject picObject, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                picObject.frameCount = Integer.parseInt(xmlPullParser.getText());
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public PicObject fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        PicObject picObject = new PicObject();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<PicObject> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, picObject, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "Object" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return picObject;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return picObject;
    }
}
