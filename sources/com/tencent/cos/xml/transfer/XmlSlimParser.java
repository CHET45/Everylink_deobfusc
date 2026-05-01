package com.tencent.cos.xml.transfer;

import android.util.Xml;
import com.microsoft.azure.storage.blob.BlobConstants;
import com.tencent.cos.xml.model.tag.CopyObject;
import com.tencent.cos.xml.model.tag.InitiateMultipartUpload;
import com.tencent.cos.xml.model.tag.ListParts;
import com.tencent.cos.xml.utils.BaseXmlSlimParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class XmlSlimParser extends BaseXmlSlimParser {
    public static void parseInitiateMultipartUploadResult(InputStream inputStream, InitiateMultipartUpload initiateMultipartUpload) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("Bucket")) {
                    xmlPullParserNewPullParser.next();
                    initiateMultipartUpload.bucket = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Key")) {
                    xmlPullParserNewPullParser.next();
                    initiateMultipartUpload.key = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("UploadId")) {
                    xmlPullParserNewPullParser.next();
                    initiateMultipartUpload.uploadId = xmlPullParserNewPullParser.getText();
                }
            }
        }
    }

    public static void parseListPartsResult(InputStream inputStream, ListParts listParts) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        listParts.parts = new ArrayList();
        ListParts.Owner owner = null;
        ListParts.Initiator initiator = null;
        ListParts.Part part = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("Bucket")) {
                    xmlPullParserNewPullParser.next();
                    listParts.bucket = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("EncodingType")) {
                    xmlPullParserNewPullParser.next();
                    listParts.encodingType = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Encoding-type")) {
                    xmlPullParserNewPullParser.next();
                    listParts.encodingType = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Key")) {
                    xmlPullParserNewPullParser.next();
                    listParts.key = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("UploadId")) {
                    xmlPullParserNewPullParser.next();
                    listParts.uploadId = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Owner")) {
                    owner = new ListParts.Owner();
                } else if (name.equalsIgnoreCase("Initiator")) {
                    initiator = new ListParts.Initiator();
                } else if (name.equalsIgnoreCase("ID")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.f1841id = xmlPullParserNewPullParser.getText();
                    } else if (initiator != null) {
                        initiator.f1840id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("DisplayName")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.disPlayName = xmlPullParserNewPullParser.getText();
                    } else if (initiator != null) {
                        initiator.disPlayName = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("PartNumberMarker")) {
                    xmlPullParserNewPullParser.next();
                    listParts.partNumberMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("StorageClass")) {
                    xmlPullParserNewPullParser.next();
                    listParts.storageClass = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("NextPartNumberMarker")) {
                    xmlPullParserNewPullParser.next();
                    listParts.nextPartNumberMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("MaxParts")) {
                    xmlPullParserNewPullParser.next();
                    listParts.maxParts = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("IsTruncated")) {
                    xmlPullParserNewPullParser.next();
                    listParts.isTruncated = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase("Part")) {
                    part = new ListParts.Part();
                } else if (name.equalsIgnoreCase("PartNumber")) {
                    xmlPullParserNewPullParser.next();
                    part.partNumber = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("LastModified")) {
                    xmlPullParserNewPullParser.next();
                    part.lastModified = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("ETag")) {
                    xmlPullParserNewPullParser.next();
                    part.eTag = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase(BlobConstants.SIZE_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    part.size = xmlPullParserNewPullParser.getText();
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Owner")) {
                    listParts.owner = owner;
                    owner = null;
                } else if (name2.equalsIgnoreCase("Initiator")) {
                    listParts.initiator = initiator;
                    initiator = null;
                } else if (name2.equalsIgnoreCase("Part")) {
                    listParts.parts.add(part);
                    part = null;
                }
            }
        }
    }

    public static void parseCopyObjectResult(InputStream inputStream, CopyObject copyObject) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("ETag")) {
                    xmlPullParserNewPullParser.next();
                    copyObject.eTag = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("LastModified")) {
                    xmlPullParserNewPullParser.next();
                    copyObject.lastModified = xmlPullParserNewPullParser.getText();
                }
            }
        }
    }
}
