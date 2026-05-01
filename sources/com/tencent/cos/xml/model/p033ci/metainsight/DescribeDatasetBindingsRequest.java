package com.tencent.cos.xml.model.p033ci.metainsight;

import com.microsoft.azure.storage.Constants;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.appid.AppIdRequest;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class DescribeDatasetBindingsRequest extends AppIdRequest {
    public String datasetname;
    public int maxresults;
    public String nexttoken;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return null;
    }

    public DescribeDatasetBindingsRequest(String str) {
        super(str);
        this.nexttoken = "";
        addHeader("Accept", "application/json");
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("datasetname", this.datasetname);
        this.queryParameters.put(Constants.QueryConstants.MAX_RESULTS, String.valueOf(this.maxresults));
        this.queryParameters.put("nexttoken", this.nexttoken);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getPath(CosXmlServiceConfig cosXmlServiceConfig) {
        return "/datasetbindings";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }
}
