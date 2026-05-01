package com.tencent.cos.xml.transfer;

import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.crypto.Headers;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.tag.CosError;
import com.tencent.cos.xml.utils.BaseXmlSlimParser;
import com.tencent.qcloud.core.common.QCloudClientException;
import com.tencent.qcloud.core.common.QCloudServiceException;
import com.tencent.qcloud.core.http.HttpResponse;
import com.tencent.qcloud.core.http.ResponseBodyConverter;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class ResponseXmlS3BodySerializer<T> extends ResponseBodyConverter<T> {
    private CosXmlResult cosXmlResult;

    public ResponseXmlS3BodySerializer(CosXmlResult cosXmlResult) {
        this.cosXmlResult = cosXmlResult;
    }

    @Override // com.tencent.qcloud.core.http.ResponseBodyConverter
    public T convert(HttpResponse httpResponse) throws QCloudServiceException, QCloudClientException {
        parseCOSXMLError(httpResponse);
        this.cosXmlResult.parseResponseBody(httpResponse);
        return (T) this.cosXmlResult;
    }

    private void parseCOSXMLError(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        int iCode = httpResponse.code();
        if (iCode < 200 || iCode >= 300) {
            CosXmlServiceException cosXmlServiceException = new CosXmlServiceException(httpResponse.message(), httpResponse.host());
            cosXmlServiceException.setStatusCode(iCode);
            cosXmlServiceException.setRequestId(httpResponse.header(Headers.REQUEST_ID));
            String strHeader = httpResponse.header("Content-Type");
            CosError cosError = new CosError();
            if ("application/json".equalsIgnoreCase(strHeader)) {
                try {
                    cosError = CosError.fromJson(httpResponse.string());
                } catch (IOException e) {
                    throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
                } catch (JSONException e2) {
                    throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e2);
                }
            } else {
                InputStream inputStreamByteStream = httpResponse.byteStream();
                if (inputStreamByteStream != null) {
                    try {
                        BaseXmlSlimParser.parseError(inputStreamByteStream, cosError);
                    } catch (IOException e3) {
                        throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e3);
                    } catch (XmlPullParserException e4) {
                        throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e4);
                    }
                }
            }
            if (cosError.code != null) {
                cosXmlServiceException.setErrorCode(cosError.code);
            }
            if (cosError.message != null) {
                cosXmlServiceException.setErrorMessage(cosError.message);
            }
            if (cosError.requestId != null) {
                cosXmlServiceException.setRequestId(cosError.requestId);
            }
            if (cosError.resource != null) {
                cosXmlServiceException.setServiceName(cosError.resource);
                throw cosXmlServiceException;
            }
            throw cosXmlServiceException;
        }
    }
}
