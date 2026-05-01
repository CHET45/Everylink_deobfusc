package com.tencent.cos.xml.model.p033ci.media;

import androidx.exifinterface.media.ExifInterface;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResponse;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: renamed from: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter */
/* JADX INFO: loaded from: classes4.dex */
public class C3500x191fa505 extends IXmlAdapter<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo> {
    private HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>> childElementBinders;

    public C3500x191fa505() {
        HashMap<String, ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Width", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseInputObjectInfo.width = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Height", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseInputObjectInfo.height = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Dar", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseInputObjectInfo.dar = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("Duration", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseInputObjectInfo.duration = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(BlobConstants.SIZE_ELEMENT, new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseInputObjectInfo.size = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put(ExifInterface.TAG_IMAGE_WIDTH, new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseInputObjectInfo.imageWidth = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ImageHeight", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseInputObjectInfo.imageHeight = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ImageDar", new ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo>() { // from class: com.tencent.cos.xml.model.ci.media.GetWorkflowDetailResponse$GetWorkflowDetailResponseInputObjectInfo$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                getWorkflowDetailResponseInputObjectInfo.imageDar = xmlPullParser.getText();
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo getWorkflowDetailResponseInputObjectInfo = new GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<GetWorkflowDetailResponse.GetWorkflowDetailResponseInputObjectInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, getWorkflowDetailResponseInputObjectInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "InputObjectInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return getWorkflowDetailResponseInputObjectInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return getWorkflowDetailResponseInputObjectInfo;
    }
}
