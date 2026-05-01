package com.tencent.cos.xml.model.bucket;

import com.tencent.cos.xml.common.COSACL;
import com.tencent.cos.xml.common.COSRequestHeaderKey;
import com.tencent.cos.xml.model.tag.ACLAccount;
import com.tencent.cos.xml.model.tag.CreateBucketConfiguration;
import com.tencent.cos.xml.transfer.XmlBuilder;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class PutBucketRequest extends BucketRequest {
    private CreateBucketConfiguration createBucketConfiguration;

    public PutBucketRequest(String str) {
        super(str);
        this.createBucketConfiguration = null;
    }

    public void enableMAZ(boolean z) {
        if (z) {
            this.createBucketConfiguration = new CreateBucketConfiguration();
        } else {
            this.createBucketConfiguration = null;
        }
    }

    public void setXCOSACL(String str) {
        if (str != null) {
            addHeader("x-cos-acl", str);
        }
    }

    public void setXCOSACL(COSACL cosacl) {
        if (cosacl != null) {
            addHeader("x-cos-acl", cosacl.getAcl());
        }
    }

    public void setXCOSGrantRead(ACLAccount aCLAccount) {
        if (aCLAccount != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_READ, aCLAccount.getAccount());
        }
    }

    public void setXCOSGrantRead(String str) {
        if (str != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_READ, str);
        }
    }

    public void setXCOSGrantWrite(ACLAccount aCLAccount) {
        if (aCLAccount != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_WRITE, aCLAccount.getAccount());
        }
    }

    public void setXCOSGrantWrite(String str) {
        if (str != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_WRITE, str);
        }
    }

    public void setXCOSReadWrite(ACLAccount aCLAccount) {
        if (aCLAccount != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_FULL_CONTROL, aCLAccount.getAccount());
        }
    }

    public void setXCOSReadWrite(String str) {
        if (str != null) {
            addHeader(COSRequestHeaderKey.X_COS_GRANT_FULL_CONTROL, str);
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        CreateBucketConfiguration createBucketConfiguration = this.createBucketConfiguration;
        if (createBucketConfiguration != null) {
            return RequestBodySerializer.string("application/xml", XmlBuilder.buildCreateBucketConfiguration(createBucketConfiguration));
        }
        return RequestBodySerializer.wrap(RequestBody.create((MediaType) null, new byte[0]));
    }
}
