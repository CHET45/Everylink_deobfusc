package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.RefererConfiguration;
import com.tencent.cos.xml.utils.QCloudXmlUtils;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketRefererRequest extends BucketRequest {
    private final RefererConfiguration refererConfiguration;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public boolean isNeedMD5() {
        return true;
    }

    public PutBucketRefererRequest(String str, boolean z, RefererConfiguration.RefererType refererType) {
        super(str);
        RefererConfiguration refererConfiguration = new RefererConfiguration();
        this.refererConfiguration = refererConfiguration;
        refererConfiguration.setEnabled(z);
        refererConfiguration.setRefererType(refererType);
        refererConfiguration.setAllowEmptyRefer(false);
        refererConfiguration.domainList = new ArrayList();
    }

    public void setEnabled(boolean z) {
        this.refererConfiguration.setEnabled(z);
    }

    public boolean getEnabled() {
        return this.refererConfiguration.getEnabled();
    }

    public void setRefererType(RefererConfiguration.RefererType refererType) {
        this.refererConfiguration.setRefererType(refererType);
    }

    public RefererConfiguration.RefererType getRefererType() {
        return this.refererConfiguration.getRefererType();
    }

    public void setAllowEmptyRefer(boolean z) {
        this.refererConfiguration.setAllowEmptyRefer(z);
    }

    public boolean getAllowEmptyRefer() {
        return this.refererConfiguration.getAllowEmptyRefer();
    }

    public void setDomainList(List<RefererConfiguration.Domain> list) {
        this.refererConfiguration.domainList = list;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("referer", null);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public RequestBodySerializer getRequestBody() throws CosXmlClientException {
        return RequestBodySerializer.string("application/xml", QCloudXmlUtils.toXml(this.refererConfiguration));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (this.refererConfiguration.domainList != null) {
            this.refererConfiguration.domainList.size();
        }
    }
}
