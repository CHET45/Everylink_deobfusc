package com.tencent.cos.xml.model.object;

import com.tencent.cos.xml.model.tag.RestoreConfigure;
import com.tencent.cos.xml.transfer.XmlBuilder;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class RestoreRequest extends ObjectRequest {
    private RestoreConfigure restoreConfigure;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public boolean isNeedMD5() {
        return true;
    }

    public RestoreRequest(String str, String str2) {
        super(str, str2);
        RestoreConfigure restoreConfigure = new RestoreConfigure();
        this.restoreConfigure = restoreConfigure;
        restoreConfigure.casJobParameters = new RestoreConfigure.CASJobParameters();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("restore", null);
        return this.queryParameters;
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "POST";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.string("application/xml", XmlBuilder.buildRestore(this.restoreConfigure));
    }

    public void setExpireDays(int i) {
        if (i < 0) {
            i = 0;
        }
        this.restoreConfigure.days = i;
    }

    public void setTier(RestoreConfigure.Tier tier) {
        if (tier != null) {
            this.restoreConfigure.casJobParameters.tier = tier.getTier();
        }
    }

    public RestoreConfigure getRestoreConfigure() {
        return this.restoreConfigure;
    }
}
