package com.tencent.cos.xml.model.tag.eventstreaming;

import android.util.Xml;
import androidx.core.app.NotificationCompat;
import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public abstract class SelectObjectContentEventUnmarshaller {
    public abstract SelectObjectContentEvent unmarshal(Message message) throws Exception;

    public static SelectObjectContentEvent unmarshalMessage(Message message) throws CosXmlServiceException {
        String stringHeader = getStringHeader(message, ":message-type");
        if ("error".equals(stringHeader)) {
            throw unmarshalErrorMessage(message);
        }
        if (NotificationCompat.CATEGORY_EVENT.equals(stringHeader)) {
            return unmarshalEventMessage(message);
        }
        throw new CosXmlServiceException("Service returned unknown message type: " + stringHeader, (String) null);
    }

    private static CosXmlServiceException unmarshalErrorMessage(Message message) throws CosXmlServiceException {
        String stringHeader = getStringHeader(message, ":error-code");
        String stringHeader2 = getStringHeader(message, ":error-message");
        SelectObjectContentEventException selectObjectContentEventException = new SelectObjectContentEventException("S3 returned an error: " + stringHeader2 + " (" + stringHeader + ")");
        selectObjectContentEventException.setErrorCode(stringHeader);
        selectObjectContentEventException.setErrorMessage(stringHeader2);
        return new CosXmlServiceException("Select object content error event", selectObjectContentEventException);
    }

    private static SelectObjectContentEvent unmarshalEventMessage(Message message) throws CosXmlServiceException {
        String stringHeader = getStringHeader(message, ":event-type");
        try {
            return forEventType(stringHeader).unmarshal(message);
        } catch (Exception e) {
            throw new CosXmlServiceException("Failed to read response event of type " + stringHeader, e);
        }
    }

    public static SelectObjectContentEventUnmarshaller forEventType(String str) {
        if ("Records".equals(str)) {
            return new RecordsEventUnmarshaller();
        }
        if ("Stats".equals(str)) {
            return new StatsEventUnmarshaller();
        }
        if ("Progress".equals(str)) {
            return new ProgressEventUnmarshaller();
        }
        if ("Cont".equals(str)) {
            return new ContinuationEventUnmarshaller();
        }
        if (Constants.END_ELEMENT.equals(str)) {
            return new EndEventUnmarshaller();
        }
        return new UnknownEventUnmarshaller();
    }

    private static String getStringHeader(Message message, String str) throws CosXmlServiceException {
        HeaderValue headerValue = message.getHeaders().get(str);
        if (headerValue == null) {
            throw new CosXmlServiceException("Unexpected lack of '" + str + "' header from service.", (String) null);
        }
        if (headerValue.getType() != HeaderType.STRING) {
            String str2 = "Unexpected non-string '" + str + "' header: " + headerValue.getType();
            throw new CosXmlServiceException(str2, (String) null);
        }
        return headerValue.getString();
    }

    public static class RecordsEventUnmarshaller extends SelectObjectContentEventUnmarshaller {
        @Override // com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEventUnmarshaller
        public SelectObjectContentEvent.RecordsEvent unmarshal(Message message) {
            return new SelectObjectContentEvent.RecordsEvent().withPayload(ByteBuffer.wrap(message.getPayload()));
        }
    }

    public static class StatsEventUnmarshaller extends SelectObjectContentEventUnmarshaller {
        @Override // com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEventUnmarshaller
        public SelectObjectContentEvent.StatsEvent unmarshal(Message message) throws Exception {
            return new SelectObjectContentEvent.StatsEvent().withDetails(SelectObjectContentEventUnmarshaller.parsePayloadStats(message));
        }
    }

    public static class ProgressEventUnmarshaller extends SelectObjectContentEventUnmarshaller {
        @Override // com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEventUnmarshaller
        public SelectObjectContentEvent.ProgressEvent unmarshal(Message message) throws Exception {
            return new SelectObjectContentEvent.ProgressEvent().withDetails(SelectObjectContentEventUnmarshaller.parsePayloadProgress(message));
        }
    }

    public static class ContinuationEventUnmarshaller extends SelectObjectContentEventUnmarshaller {
        @Override // com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEventUnmarshaller
        public SelectObjectContentEvent.ContinuationEvent unmarshal(Message message) {
            return new SelectObjectContentEvent.ContinuationEvent();
        }
    }

    public static class EndEventUnmarshaller extends SelectObjectContentEventUnmarshaller {
        @Override // com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEventUnmarshaller
        public SelectObjectContentEvent.EndEvent unmarshal(Message message) {
            return new SelectObjectContentEvent.EndEvent();
        }
    }

    public static class UnknownEventUnmarshaller extends SelectObjectContentEventUnmarshaller {
        @Override // com.tencent.cos.xml.model.tag.eventstreaming.SelectObjectContentEventUnmarshaller
        public SelectObjectContentEvent unmarshal(Message message) {
            return new SelectObjectContentEvent();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Stats parsePayloadStats(Message message) throws XmlPullParserException, IOException {
        long[] payloadBytesProgress = parsePayloadBytesProgress(message);
        return new Stats(Long.valueOf(payloadBytesProgress[0]), Long.valueOf(payloadBytesProgress[1]), Long.valueOf(payloadBytesProgress[2]));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Progress parsePayloadProgress(Message message) throws XmlPullParserException, IOException {
        long[] payloadBytesProgress = parsePayloadBytesProgress(message);
        return new Progress(Long.valueOf(payloadBytesProgress[0]), Long.valueOf(payloadBytesProgress[1]), Long.valueOf(payloadBytesProgress[2]));
    }

    private static long[] parsePayloadBytesProgress(Message message) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(new ByteArrayInputStream(message.getPayload()), "UTF-8");
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
            if (eventType == 2) {
                String name = xmlPullParserNewPullParser.getName();
                if (name.equalsIgnoreCase("BytesScanned")) {
                    xmlPullParserNewPullParser.next();
                    j = Long.parseLong(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase("BytesProcessed")) {
                    xmlPullParserNewPullParser.next();
                    j2 = Long.parseLong(xmlPullParserNewPullParser.getText());
                } else if (name.equalsIgnoreCase("BytesReturned")) {
                    xmlPullParserNewPullParser.next();
                    j3 = Long.parseLong(xmlPullParserNewPullParser.getText());
                }
            }
        }
        return new long[]{j, j2, j3};
    }
}
