package com.tencent.cos.xml.model.object;

import com.github.houbb.heaven.constant.PunctuationConst;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.qcloud.core.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public final class OptionObjectResult extends CosXmlResult {
    public List<String> accessControlAllowHeaders;
    public List<String> accessControlAllowMethods;
    public String accessControlAllowOrigin;
    public List<String> accessControlExposeHeaders;
    public long accessControlMaxAge;

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public void parseResponseBody(HttpResponse httpResponse) throws CosXmlServiceException, CosXmlClientException {
        super.parseResponseBody(httpResponse);
        this.accessControlAllowOrigin = httpResponse.header("Access-Control-Allow-Origin");
        if (httpResponse.header("Access-Control-Max-Age") != null) {
            this.accessControlMaxAge = Long.parseLong(httpResponse.header("Access-Control-Max-Age"));
        }
        if (httpResponse.header("Access-Control-Allow-Methods") != null) {
            this.accessControlAllowMethods = Arrays.asList(httpResponse.header("Access-Control-Allow-Methods").split(PunctuationConst.COMMA));
        }
        if (httpResponse.header("Access-Control-Allow-Headers") != null) {
            this.accessControlAllowHeaders = Arrays.asList(httpResponse.header("Access-Control-Allow-Headers").split(PunctuationConst.COMMA));
        }
        if (httpResponse.header("Access-Control-Expose-Headers") != null) {
            this.accessControlExposeHeaders = Arrays.asList(httpResponse.header("Access-Control-Expose-Headers").split(PunctuationConst.COMMA));
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlResult
    public String printResult() {
        return super.printResult() + "\n" + this.accessControlAllowOrigin + "\n" + this.accessControlMaxAge + "\n";
    }
}
