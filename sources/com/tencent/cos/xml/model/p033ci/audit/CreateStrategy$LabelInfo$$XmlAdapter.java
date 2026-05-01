package com.tencent.cos.xml.model.p033ci.audit;

import com.tencent.cos.xml.model.p033ci.audit.CreateStrategy;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class CreateStrategy$LabelInfo$$XmlAdapter extends IXmlAdapter<CreateStrategy.LabelInfo> {
    private HashMap<String, ChildElementBinder<CreateStrategy.LabelInfo>> childElementBinders;

    public CreateStrategy$LabelInfo$$XmlAdapter() {
        HashMap<String, ChildElementBinder<CreateStrategy.LabelInfo>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("Porn", new ChildElementBinder<CreateStrategy.LabelInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$LabelInfo$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.LabelInfo labelInfo, String str) throws XmlPullParserException, IOException {
                if (labelInfo.porn == null) {
                    labelInfo.porn = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        labelInfo.porn.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "Porn".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("Politics", new ChildElementBinder<CreateStrategy.LabelInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$LabelInfo$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.LabelInfo labelInfo, String str) throws XmlPullParserException, IOException {
                if (labelInfo.politics == null) {
                    labelInfo.politics = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        labelInfo.politics.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "Politics".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("Terrorism", new ChildElementBinder<CreateStrategy.LabelInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$LabelInfo$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.LabelInfo labelInfo, String str) throws XmlPullParserException, IOException {
                if (labelInfo.terrorism == null) {
                    labelInfo.terrorism = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        labelInfo.terrorism.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "Terrorism".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("Ads", new ChildElementBinder<CreateStrategy.LabelInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$LabelInfo$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.LabelInfo labelInfo, String str) throws XmlPullParserException, IOException {
                if (labelInfo.ads == null) {
                    labelInfo.ads = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        labelInfo.ads.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "Ads".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("Abuse", new ChildElementBinder<CreateStrategy.LabelInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$LabelInfo$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.LabelInfo labelInfo, String str) throws XmlPullParserException, IOException {
                if (labelInfo.abuse == null) {
                    labelInfo.abuse = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        labelInfo.abuse.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "Abuse".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
        this.childElementBinders.put("Illegal", new ChildElementBinder<CreateStrategy.LabelInfo>() { // from class: com.tencent.cos.xml.model.ci.audit.CreateStrategy$LabelInfo$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, CreateStrategy.LabelInfo labelInfo, String str) throws XmlPullParserException, IOException {
                if (labelInfo.illegal == null) {
                    labelInfo.illegal = new ArrayList();
                }
                int eventType = xmlPullParser.getEventType();
                while (eventType != 1) {
                    if (eventType == 2) {
                        xmlPullParser.next();
                        labelInfo.illegal.add(xmlPullParser.getText());
                    } else if (eventType == 3 && "Illegal".equalsIgnoreCase(xmlPullParser.getName())) {
                        return;
                    }
                    eventType = xmlPullParser.next();
                }
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public CreateStrategy.LabelInfo fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        CreateStrategy.LabelInfo labelInfo = new CreateStrategy.LabelInfo();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<CreateStrategy.LabelInfo> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, labelInfo, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "LabelInfo" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return labelInfo;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return labelInfo;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, CreateStrategy.LabelInfo labelInfo, String str) throws XmlPullParserException, IOException {
        if (labelInfo == null) {
            return;
        }
        if (str == null) {
            str = "LabelInfo";
        }
        xmlSerializer.startTag("", str);
        if (labelInfo.porn != null) {
            for (int i = 0; i < labelInfo.porn.size(); i++) {
                xmlSerializer.startTag("", "Porn");
                xmlSerializer.text(String.valueOf(labelInfo.porn.get(i)));
                xmlSerializer.endTag("", "Porn");
            }
        }
        if (labelInfo.politics != null) {
            for (int i2 = 0; i2 < labelInfo.politics.size(); i2++) {
                xmlSerializer.startTag("", "Politics");
                xmlSerializer.text(String.valueOf(labelInfo.politics.get(i2)));
                xmlSerializer.endTag("", "Politics");
            }
        }
        if (labelInfo.terrorism != null) {
            for (int i3 = 0; i3 < labelInfo.terrorism.size(); i3++) {
                xmlSerializer.startTag("", "Terrorism");
                xmlSerializer.text(String.valueOf(labelInfo.terrorism.get(i3)));
                xmlSerializer.endTag("", "Terrorism");
            }
        }
        if (labelInfo.ads != null) {
            for (int i4 = 0; i4 < labelInfo.ads.size(); i4++) {
                xmlSerializer.startTag("", "Ads");
                xmlSerializer.text(String.valueOf(labelInfo.ads.get(i4)));
                xmlSerializer.endTag("", "Ads");
            }
        }
        if (labelInfo.abuse != null) {
            for (int i5 = 0; i5 < labelInfo.abuse.size(); i5++) {
                xmlSerializer.startTag("", "Abuse");
                xmlSerializer.text(String.valueOf(labelInfo.abuse.get(i5)));
                xmlSerializer.endTag("", "Abuse");
            }
        }
        if (labelInfo.illegal != null) {
            for (int i6 = 0; i6 < labelInfo.illegal.size(); i6++) {
                xmlSerializer.startTag("", "Illegal");
                xmlSerializer.text(String.valueOf(labelInfo.illegal.get(i6)));
                xmlSerializer.endTag("", "Illegal");
            }
        }
        xmlSerializer.endTag("", str);
    }
}
