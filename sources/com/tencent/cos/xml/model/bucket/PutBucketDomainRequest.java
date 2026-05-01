package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.DomainConfiguration;
import com.tencent.cos.xml.transfer.XmlBuilder;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketDomainRequest extends BucketRequest {
    private DomainConfiguration domainConfiguration;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public boolean isNeedMD5() {
        return true;
    }

    public PutBucketDomainRequest(String str) {
        super(str);
        DomainConfiguration domainConfiguration = new DomainConfiguration();
        this.domainConfiguration = domainConfiguration;
        domainConfiguration.domainRules = new ArrayList();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.string("application/xml", XmlBuilder.buildDomainConfiguration(this.domainConfiguration));
    }

    public void addDomainRule(DomainConfiguration.DomainRule domainRule) {
        if (domainRule != null) {
            this.domainConfiguration.domainRules.add(domainRule);
        }
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (this.domainConfiguration.domainRules.size() > 0) {
            for (DomainConfiguration.DomainRule domainRule : this.domainConfiguration.domainRules) {
            }
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("domain", null);
        return super.getQueryString();
    }
}
