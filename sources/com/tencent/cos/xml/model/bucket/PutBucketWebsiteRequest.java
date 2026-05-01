package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.tag.WebsiteConfiguration;
import com.tencent.cos.xml.transfer.XmlBuilder;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketWebsiteRequest extends BucketRequest {
    private WebsiteConfiguration websiteConfiguration;

    public PutBucketWebsiteRequest(String str) {
        super(str);
        this.websiteConfiguration = new WebsiteConfiguration();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.string("application/xml", XmlBuilder.buildWebsiteConfiguration(this.websiteConfiguration));
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("website", null);
        return super.getQueryString();
    }

    public void setIndexDocument(String str) {
        if (str != null) {
            if (this.websiteConfiguration.indexDocument == null) {
                this.websiteConfiguration.indexDocument = new WebsiteConfiguration.IndexDocument();
            }
            this.websiteConfiguration.indexDocument.suffix = str;
        }
    }

    public void setErrorDocument(String str) {
        if (str != null) {
            if (this.websiteConfiguration.errorDocument == null) {
                this.websiteConfiguration.errorDocument = new WebsiteConfiguration.ErrorDocument();
            }
            this.websiteConfiguration.errorDocument.key = str;
        }
    }

    public void setRedirectAllRequestTo(String str) {
        if (str != null) {
            if (this.websiteConfiguration.redirectAllRequestTo == null) {
                this.websiteConfiguration.redirectAllRequestTo = new WebsiteConfiguration.RedirectAllRequestTo();
            }
            this.websiteConfiguration.redirectAllRequestTo.protocol = str;
        }
    }

    public void setRoutingRules(List<WebsiteConfiguration.RoutingRule> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        if (this.websiteConfiguration.routingRules == null) {
            this.websiteConfiguration.routingRules = new ArrayList();
        }
        Iterator<WebsiteConfiguration.RoutingRule> it = list.iterator();
        while (it.hasNext()) {
            this.websiteConfiguration.routingRules.add(it.next());
        }
    }
}
