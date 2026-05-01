package com.tencent.cos.xml.model;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.qcloud.core.http.HttpResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public abstract class CosXmlResult {
    public String accessUrl;
    public Map<String, List<String>> headers;
    public String host;
    public int httpCode;
    public String httpMessage;

    protected void xmlParser(HttpResponse httpResponse) throws XmlPullParserException, IOException {
    }

    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        this.httpCode = httpResponse.code();
        this.httpMessage = httpResponse.message();
        this.headers = httpResponse.headers();
        this.host = httpResponse.host();
        try {
            xmlParser(httpResponse);
        } catch (IOException e) {
            throw new CosXmlClientException(ClientErrorCode.POOR_NETWORK.getCode(), e);
        } catch (XmlPullParserException e2) {
            throw new CosXmlClientException(ClientErrorCode.SERVERERROR.getCode(), e2);
        }
    }

    public String printResult() {
        return this.httpCode + PunctuationConst.f489OR + this.httpMessage;
    }

    public String getHeader(String str) {
        List<String> list;
        if (!this.headers.containsKey(str) || (list = this.headers.get(str)) == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
