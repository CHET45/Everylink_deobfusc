package com.tencent.cos.xml.transfer;

import android.content.ContentResolver;
import android.net.Uri;
import com.tencent.cos.xml.model.object.GetObjectResult;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpResponse;
import com.tencent.qcloud.core.http.ResponseFileConverter;

/* JADX INFO: loaded from: classes4.dex */
public class ResponseFileBodySerializer<T2> extends ResponseFileConverter<T2> {
    private GetObjectResult getObjectResult;

    public ResponseFileBodySerializer(GetObjectResult getObjectResult, String str, long j) {
        super(str, j);
        this.getObjectResult = getObjectResult;
    }

    public ResponseFileBodySerializer(GetObjectResult getObjectResult, Uri uri, ContentResolver contentResolver, long j) {
        super(uri, contentResolver, j);
        this.getObjectResult = getObjectResult;
    }

    @Override // com.tencent.qcloud.core.http.ResponseFileConverter, com.tencent.qcloud.core.http.ResponseBodyConverter
    public T2 convert(HttpResponse httpResponse) throws QCloudServiceException, QCloudClientException {
        parseCOSXMLError(httpResponse);
        this.getObjectResult.parseResponseBody(httpResponse);
        super.convert(httpResponse);
        return (T2) this.getObjectResult;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:?, code lost:
    
        throw r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void parseCOSXMLError(com.tencent.qcloud.core.http.HttpResponse r5) throws com.tencent.cos.xml.exception.CosXmlServiceException, com.tencent.cos.xml.exception.CosXmlClientException {
        /*
            r4 = this;
            int r0 = r5.code()
            r1 = 200(0xc8, float:2.8E-43)
            if (r0 < r1) goto Ld
            r1 = 300(0x12c, float:4.2E-43)
            if (r0 >= r1) goto Ld
            return
        Ld:
            java.lang.String r1 = r5.message()
            com.tencent.cos.xml.exception.CosXmlServiceException r2 = new com.tencent.cos.xml.exception.CosXmlServiceException
            java.lang.String r3 = r5.host()
            r2.<init>(r1, r3)
            r2.setStatusCode(r0)
            java.lang.String r0 = "x-cos-request-id"
            java.lang.String r0 = r5.header(r0)
            r2.setRequestId(r0)
            java.io.InputStream r5 = r5.byteStream()
            if (r5 == 0) goto L5f
            com.tencent.cos.xml.model.tag.CosError r0 = new com.tencent.cos.xml.model.tag.CosError
            r0.<init>()
            com.tencent.cos.xml.utils.BaseXmlSlimParser.parseError(r5, r0)     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            java.lang.String r5 = r0.code     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            r2.setErrorCode(r5)     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            java.lang.String r5 = r0.message     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            r2.setErrorMessage(r5)     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            java.lang.String r5 = r0.requestId     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            if (r5 != 0) goto L4b
            java.lang.String r5 = r0.requestId     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            r2.setRequestId(r5)     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
        L4b:
            java.lang.String r5 = r0.resource     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            r2.setServiceName(r5)     // Catch: java.io.IOException -> L51 org.xmlpull.v1.XmlPullParserException -> L5e
            goto L5f
        L51:
            r5 = move-exception
            com.tencent.cos.xml.exception.CosXmlClientException r0 = new com.tencent.cos.xml.exception.CosXmlClientException
            com.tencent.cos.xml.common.ClientErrorCode r1 = com.tencent.cos.xml.common.ClientErrorCode.POOR_NETWORK
            int r1 = r1.getCode()
            r0.<init>(r1, r5)
            throw r0
        L5e:
            throw r2
        L5f:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.cos.xml.transfer.ResponseFileBodySerializer.parseCOSXMLError(com.tencent.qcloud.core.http.HttpResponse):void");
    }
}
