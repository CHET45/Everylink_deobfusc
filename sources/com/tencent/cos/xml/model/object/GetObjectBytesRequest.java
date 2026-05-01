package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.common.ClientErrorCode;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.File;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public final class GetObjectBytesRequest extends ObjectRequest {
    private boolean objectKeySimplifyCheck;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return null;
    }

    public void setObjectKeySimplifyCheck(boolean z) {
        this.objectKeySimplifyCheck = z;
    }

    public GetObjectBytesRequest(String str, String str2) {
        super(str, str2);
        this.objectKeySimplifyCheck = true;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "GET";
    }

    @Override // com.tencent.cos.xml.model.object.ObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        if (this.objectKeySimplifyCheck) {
            String canonicalPath = this.cosPath;
            try {
                canonicalPath = new File("/" + this.cosPath).getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ("/".equals(canonicalPath)) {
                throw new CosXmlClientException(ClientErrorCode.INVALID_ARGUMENT.getCode(), "The key in the getobject is illegal");
            }
        }
    }
}
