package com.tencent.cos.xml.model.bucket;

import android.util.Xml;
import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.ListMultipartUploads;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class ListMultiUploadsResult extends CosXmlResult {
    public ListMultipartUploads listMultipartUploads = new ListMultipartUploads();

    @Override // com.tencent.cos.xml.model.CosXmlResult
    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
        parseListMultipartUploadsResult(httpResponse.byteStream(), this.listMultipartUploads);
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        ListMultipartUploads listMultipartUploads = this.listMultipartUploads;
        if (listMultipartUploads != null) {
            return listMultipartUploads.toString();
        }
        return super.printResult();
    }

    public static void parseListMultipartUploadsResult(InputStream inputStream, ListMultipartUploads listMultipartUploads) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, "UTF-8");
        listMultipartUploads.uploads = new ArrayList();
        listMultipartUploads.commonPrefixes = new ArrayList();
        ListMultipartUploads.Upload upload = null;
        ListMultipartUploads.CommonPrefixes commonPrefixes = null;
        ListMultipartUploads.Owner owner = null;
        ListMultipartUploads.Initiator initiator = null;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("Bucket")) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.bucket = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Encoding-Type")) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.encodingType = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("KeyMarker")) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.keyMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("UploadIdMarker")) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.uploadIdMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("NextKeyMarker")) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.nextKeyMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("NextUploadIdMarker")) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.nextUploadIdMarker = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("MaxUploads")) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.maxUploads = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("IsTruncated")) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.isTruncated = Boolean.parseBoolean(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase(Constants.PREFIX_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    if (commonPrefixes == null) {
                        listMultipartUploads.prefix = xmlPullParserNewPullParser.getText();
                    } else {
                        commonPrefixes.prefix = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase(Constants.DELIMITER_ELEMENT)) {
                    xmlPullParserNewPullParser.next();
                    listMultipartUploads.delimiter = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Upload")) {
                    upload = new ListMultipartUploads.Upload();
                } else if (name.equalsIgnoreCase("Key")) {
                    xmlPullParserNewPullParser.next();
                    upload.key = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("UploadId")) {
                    xmlPullParserNewPullParser.next();
                    upload.uploadID = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("StorageClass")) {
                    xmlPullParserNewPullParser.next();
                    upload.storageClass = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("Initiator")) {
                    initiator = new ListMultipartUploads.Initiator();
                } else if (name.equalsIgnoreCase("UIN")) {
                    xmlPullParserNewPullParser.next();
                    if (initiator != null) {
                        initiator.uin = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Owner")) {
                    owner = new ListMultipartUploads.Owner();
                } else if (name.equalsIgnoreCase("UID")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.uid = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("ID")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.f1839id = xmlPullParserNewPullParser.getText();
                    } else if (initiator != null) {
                        initiator.f1838id = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("DisplayName")) {
                    xmlPullParserNewPullParser.next();
                    if (owner != null) {
                        owner.displayName = xmlPullParserNewPullParser.getText();
                    } else if (initiator != null) {
                        initiator.displayName = xmlPullParserNewPullParser.getText();
                    }
                } else if (name.equalsIgnoreCase("Initiated")) {
                    xmlPullParserNewPullParser.next();
                    upload.initiated = xmlPullParserNewPullParser.getText();
                } else if (name.equalsIgnoreCase("CommonPrefixs")) {
                    commonPrefixes = new ListMultipartUploads.CommonPrefixes();
                }
            } else if (eventType == 3) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (name2.equalsIgnoreCase("Upload")) {
                    listMultipartUploads.uploads.add(upload);
                    upload = null;
                } else if (name2.equalsIgnoreCase("CommonPrefixs")) {
                    listMultipartUploads.commonPrefixes.add(commonPrefixes);
                    commonPrefixes = null;
                } else if (name2.equalsIgnoreCase("Owner")) {
                    upload.owner = owner;
                    owner = null;
                } else if (name2.equalsIgnoreCase("Initiator")) {
                    upload.initiator = initiator;
                    initiator = null;
                }
            }
        }
    }
}
