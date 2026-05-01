package com.tencent.cos.xml.model.p033ci.media;

import com.tencent.cos.xml.model.p033ci.media.TemplateTranscode;
import com.tencent.qcloud.qcloudxml.core.ChildElementBinder;
import com.tencent.qcloud.qcloudxml.core.IXmlAdapter;
import com.tencent.qcloud.qcloudxml.core.QCloudXml;
import java.io.IOException;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes4.dex */
public class TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter extends IXmlAdapter<TemplateTranscode.TemplateTranscodeTransConfig> {
    private HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>> childElementBinders;

    public TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter() {
        HashMap<String, ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>> map = new HashMap<>();
        this.childElementBinders = map;
        map.put("AdjDarMethod", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.1
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.adjDarMethod = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IsCheckReso", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.2
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.isCheckReso = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("ResoAdjMethod", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.3
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.resoAdjMethod = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IsCheckVideoBitrate", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.4
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.isCheckVideoBitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("VideoBitrateAdjMethod", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.5
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.videoBitrateAdjMethod = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IsCheckAudioBitrate", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.6
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.isCheckAudioBitrate = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("AudioBitrateAdjMethod", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.7
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.audioBitrateAdjMethod = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IsCheckVideoFps", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.8
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.isCheckVideoFps = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("VideoFpsAdjMethod", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.9
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.videoFpsAdjMethod = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("DeleteMetadata", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.10
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.deleteMetadata = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("IsHdr2Sdr", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.11
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.isHdr2Sdr = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("TranscodeIndex", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.12
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                xmlPullParser.next();
                templateTranscodeTransConfig.transcodeIndex = xmlPullParser.getText();
            }
        });
        this.childElementBinders.put("HlsEncrypt", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.13
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                templateTranscodeTransConfig.hlsEncrypt = (TemplateTranscode.TemplateTranscodeHlsEncrypt) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeHlsEncrypt.class, "HlsEncrypt");
            }
        });
        this.childElementBinders.put("DashEncrypt", new ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig>() { // from class: com.tencent.cos.xml.model.ci.media.TemplateTranscode$TemplateTranscodeTransConfig$$XmlAdapter.14
            @Override // com.tencent.qcloud.qcloudxml.core.ChildElementBinder
            public void fromXml(XmlPullParser xmlPullParser, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
                templateTranscodeTransConfig.dashEncrypt = (TemplateTranscode.TemplateTranscodeDashEncrypt) QCloudXml.fromXml(xmlPullParser, TemplateTranscode.TemplateTranscodeDashEncrypt.class, "DashEncrypt");
            }
        });
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public TemplateTranscode.TemplateTranscodeTransConfig fromXml(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig = new TemplateTranscode.TemplateTranscodeTransConfig();
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2) {
                ChildElementBinder<TemplateTranscode.TemplateTranscodeTransConfig> childElementBinder = this.childElementBinders.get(xmlPullParser.getName());
                if (childElementBinder != null) {
                    childElementBinder.fromXml(xmlPullParser, templateTranscodeTransConfig, null);
                }
            } else if (eventType == 3) {
                if ((str == null ? "TransConfig" : str).equalsIgnoreCase(xmlPullParser.getName())) {
                    return templateTranscodeTransConfig;
                }
            } else {
                continue;
            }
            eventType = xmlPullParser.next();
        }
        return templateTranscodeTransConfig;
    }

    @Override // com.tencent.qcloud.qcloudxml.core.IXmlAdapter
    public void toXml(XmlSerializer xmlSerializer, TemplateTranscode.TemplateTranscodeTransConfig templateTranscodeTransConfig, String str) throws XmlPullParserException, IOException {
        if (templateTranscodeTransConfig == null) {
            return;
        }
        if (str == null) {
            str = "TransConfig";
        }
        xmlSerializer.startTag("", str);
        if (templateTranscodeTransConfig.adjDarMethod != null) {
            xmlSerializer.startTag("", "AdjDarMethod");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.adjDarMethod));
            xmlSerializer.endTag("", "AdjDarMethod");
        }
        if (templateTranscodeTransConfig.isCheckReso != null) {
            xmlSerializer.startTag("", "IsCheckReso");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.isCheckReso));
            xmlSerializer.endTag("", "IsCheckReso");
        }
        if (templateTranscodeTransConfig.resoAdjMethod != null) {
            xmlSerializer.startTag("", "ResoAdjMethod");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.resoAdjMethod));
            xmlSerializer.endTag("", "ResoAdjMethod");
        }
        if (templateTranscodeTransConfig.isCheckVideoBitrate != null) {
            xmlSerializer.startTag("", "IsCheckVideoBitrate");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.isCheckVideoBitrate));
            xmlSerializer.endTag("", "IsCheckVideoBitrate");
        }
        if (templateTranscodeTransConfig.videoBitrateAdjMethod != null) {
            xmlSerializer.startTag("", "VideoBitrateAdjMethod");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.videoBitrateAdjMethod));
            xmlSerializer.endTag("", "VideoBitrateAdjMethod");
        }
        if (templateTranscodeTransConfig.isCheckAudioBitrate != null) {
            xmlSerializer.startTag("", "IsCheckAudioBitrate");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.isCheckAudioBitrate));
            xmlSerializer.endTag("", "IsCheckAudioBitrate");
        }
        if (templateTranscodeTransConfig.audioBitrateAdjMethod != null) {
            xmlSerializer.startTag("", "AudioBitrateAdjMethod");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.audioBitrateAdjMethod));
            xmlSerializer.endTag("", "AudioBitrateAdjMethod");
        }
        if (templateTranscodeTransConfig.isCheckVideoFps != null) {
            xmlSerializer.startTag("", "IsCheckVideoFps");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.isCheckVideoFps));
            xmlSerializer.endTag("", "IsCheckVideoFps");
        }
        if (templateTranscodeTransConfig.videoFpsAdjMethod != null) {
            xmlSerializer.startTag("", "VideoFpsAdjMethod");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.videoFpsAdjMethod));
            xmlSerializer.endTag("", "VideoFpsAdjMethod");
        }
        if (templateTranscodeTransConfig.deleteMetadata != null) {
            xmlSerializer.startTag("", "DeleteMetadata");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.deleteMetadata));
            xmlSerializer.endTag("", "DeleteMetadata");
        }
        if (templateTranscodeTransConfig.isHdr2Sdr != null) {
            xmlSerializer.startTag("", "IsHdr2Sdr");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.isHdr2Sdr));
            xmlSerializer.endTag("", "IsHdr2Sdr");
        }
        if (templateTranscodeTransConfig.transcodeIndex != null) {
            xmlSerializer.startTag("", "TranscodeIndex");
            xmlSerializer.text(String.valueOf(templateTranscodeTransConfig.transcodeIndex));
            xmlSerializer.endTag("", "TranscodeIndex");
        }
        if (templateTranscodeTransConfig.hlsEncrypt != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscodeTransConfig.hlsEncrypt, "HlsEncrypt");
        }
        if (templateTranscodeTransConfig.dashEncrypt != null) {
            QCloudXml.toXml(xmlSerializer, templateTranscodeTransConfig.dashEncrypt, "DashEncrypt");
        }
        xmlSerializer.endTag("", str);
    }
}
