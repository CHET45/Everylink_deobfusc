package com.tencent.cos.xml.transfer;

import android.text.TextUtils;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.object.GetObjectBytesResult;
import com.tencent.cos.xml.model.tag.CosError;
import com.tencent.cos.xml.utils.BaseXmlSlimParser;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpResponse;
import com.tencent.qcloud.core.http.ResponseBodyConverter;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class ResponseBytesConverter<T> extends ResponseBodyConverter<T> {
    private GetObjectBytesResult getObjectBytesResult;

    public ResponseBytesConverter(GetObjectBytesResult getObjectBytesResult) {
        this.getObjectBytesResult = getObjectBytesResult;
    }

    @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
    public T convert(HttpResponse<T> httpResponse) throws QCloudServiceException, QCloudClientException {
        parseCOSXMLError(httpResponse);
        this.getObjectBytesResult.parseResponseBody(httpResponse);
        return (T) this.getObjectBytesResult;
    }

    private void parseCOSXMLError(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        int iCode = httpResponse.code();
        if (iCode < 200 || iCode >= 300) {
            CosXmlServiceException cosXmlServiceException = new CosXmlServiceException(httpResponse.message(), httpResponse.host());
            cosXmlServiceException.setStatusCode(iCode);
            cosXmlServiceException.setRequestId(httpResponse.header(Headers.REQUEST_ID));
            InputStream inputStreamByteStream = httpResponse.byteStream();
            if (inputStreamByteStream != null) {
                CosError cosError = new CosError();
                try {
                    BaseXmlSlimParser.parseError(inputStreamByteStream, cosError);
                    cosXmlServiceException.setErrorCode(cosError.code);
                    cosXmlServiceException.setErrorMessage(cosError.message);
                    if (!TextUtils.isEmpty(cosError.requestId)) {
                        cosXmlServiceException.setRequestId(cosError.requestId);
                    }
                    cosXmlServiceException.setServiceName(cosError.resource);
                    throw cosXmlServiceException;
                } catch (IOException e) {
                    throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
                } catch (XmlPullParserException e2) {
                    throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e2);
                }
            }
            throw cosXmlServiceException;
        }
    }
}
