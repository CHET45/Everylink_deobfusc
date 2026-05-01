package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.model.tag.VersioningConfiguration;
import com.tencent.cos.xml.transfer.XmlBuilder;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketVersioningRequest extends BucketRequest {
    private VersioningConfiguration versioningConfiguration;

    public PutBucketVersioningRequest(String str) {
        super(str);
        this.versioningConfiguration = new VersioningConfiguration();
    }

    public void setEnableVersion(boolean z) {
        if (z) {
            this.versioningConfiguration.status = "Enabled";
        } else {
            this.versioningConfiguration.status = PutBucketIntelligentTieringRequest.STATUS_SUSPEND;
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("versioning", null);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.string("application/xml", XmlBuilder.buildVersioningConfiguration(this.versioningConfiguration));
    }
}
