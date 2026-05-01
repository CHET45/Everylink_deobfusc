package com.tencent.cos.xml.model.bucket;

import android.text.TextUtils;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.InventoryConfiguration;
import com.tencent.cos.xml.transfer.XmlBuilder;
import com.tencent.qcloud.core.http.RequestBodySerializer;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes4.dex */
public class PutBucketInventoryRequest extends BucketRequest {
    private static Pattern pattern = Pattern.compile("[a-zA-Z0-9-_.]+");
    private InventoryConfiguration inventoryConfiguration;

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public boolean isNeedMD5() {
        return true;
    }

    public PutBucketInventoryRequest(String str) {
        super(str);
        InventoryConfiguration inventoryConfiguration = new InventoryConfiguration();
        this.inventoryConfiguration = inventoryConfiguration;
        inventoryConfiguration.isEnabled = true;
        this.inventoryConfiguration.f1834id = "None";
        this.inventoryConfiguration.schedule = new InventoryConfiguration.Schedule();
        this.inventoryConfiguration.destination = new InventoryConfiguration.Destination();
        this.inventoryConfiguration.destination.cosBucketDestination = new InventoryConfiguration.COSBucketDestination();
    }

    public void setInventoryId(String str) {
        this.inventoryConfiguration.f1834id = str;
    }

    public void isEnable(boolean z) {
        this.inventoryConfiguration.isEnabled = z;
    }

    public void setFilter(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.inventoryConfiguration.filter = new InventoryConfiguration.Filter();
        this.inventoryConfiguration.filter.prefix = str;
    }

    public void setDestination(String str, String str2, String str3, String str4, String str5) {
        if (str != null) {
            this.inventoryConfiguration.destination.cosBucketDestination.format = str;
        }
        if (str2 != null) {
            this.inventoryConfiguration.destination.cosBucketDestination.accountId = str2;
        }
        if (str3 != null && str4 != null) {
            this.inventoryConfiguration.destination.cosBucketDestination.bucket = "qcs::cos:" + str4 + "::" + str3;
        }
        if (str5 != null) {
            this.inventoryConfiguration.destination.cosBucketDestination.prefix = str5;
        }
        this.inventoryConfiguration.destination.cosBucketDestination.encryption = new InventoryConfiguration.Encryption();
        this.inventoryConfiguration.destination.cosBucketDestination.encryption.sSECOS = "";
    }

    public void setScheduleFrequency(String str) {
        if (str != null) {
            this.inventoryConfiguration.schedule.frequency = str;
        }
    }

    public void setOptionalFields(InventoryConfiguration.Field field) {
        if (field != null) {
            if (this.inventoryConfiguration.optionalFields == null) {
                this.inventoryConfiguration.optionalFields = new InventoryConfiguration.OptionalFields();
                this.inventoryConfiguration.optionalFields.fields = new HashSet(6);
            }
            this.inventoryConfiguration.optionalFields.fields.add(field.getValue());
        }
    }

    public void setIncludedObjectVersions(InventoryConfiguration.IncludedObjectVersions includedObjectVersions) {
        if (includedObjectVersions != null) {
            this.inventoryConfiguration.includedObjectVersions = includedObjectVersions.getDesc();
        }
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public String getMethod() {
        return "PUT";
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    public Map<String, String> getQueryString() {
        this.queryParameters.put("inventory", null);
        this.queryParameters.put("id", this.inventoryConfiguration.f1834id);
        return super.getQueryString();
    }

    @Override // com.tencent.cos.xml.model.CosXmlRequest
    protected RequestBodySerializer xmlBuilder() throws XmlPullParserException, IOException {
        return RequestBodySerializer.string("application/xml", XmlBuilder.buildInventoryConfiguration(this.inventoryConfiguration));
    }

    @Override // com.tencent.cos.xml.model.bucket.BucketRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
        pattern.matcher(this.inventoryConfiguration.f1834id).find();
        String str = this.inventoryConfiguration.includedObjectVersions;
        String str2 = this.inventoryConfiguration.schedule.frequency;
        String str3 = this.inventoryConfiguration.destination.cosBucketDestination.bucket;
        String str4 = this.inventoryConfiguration.destination.cosBucketDestination.format;
    }
}
