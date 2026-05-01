package com.tencent.cos.xml.model.p033ci;

import android.net.Uri;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.model.tag.pic.PicOperationRule;
import com.tencent.cos.xml.model.tag.pic.PicOperations;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

/* JADX INFO: loaded from: classes4.dex */
public class QRCodeUploadRequest extends ImageUploadRequest {
    private int cover;
    public String fileId;
    public String saveBucket;

    public QRCodeUploadRequest(String str, String str2, String str3) {
        super(str, str2, str3);
        this.cover = 0;
    }

    public QRCodeUploadRequest(String str, String str2, Uri uri) {
        super(str, str2, uri);
        this.cover = 0;
    }

    public QRCodeUploadRequest(String str, String str2, byte[] bArr) {
        super(str, str2, bArr);
        this.cover = 0;
    }

    public QRCodeUploadRequest(String str, String str2, InputStream inputStream) {
        super(str, str2, inputStream);
        this.cover = 0;
    }

    public QRCodeUploadRequest(String str, String str2, URL url) {
        super(str, str2, url);
        this.cover = 0;
    }

    @Override // com.tencent.cos.xml.model.p033ci.PicOperationProvider
    public PicOperations getPicOperations() {
        PicOperationRule picOperationRule = new PicOperationRule("QRcode/cover/" + this.cover);
        picOperationRule.setBucket(this.saveBucket);
        picOperationRule.setFileId(this.fileId);
        return new PicOperations(this.isPicInfo, Collections.singletonList(picOperationRule));
    }

    public void setCover(int i) {
        this.cover = i;
    }

    @Override // com.tencent.cos.xml.model.p033ci.ImageUploadRequest, com.tencent.cos.xml.model.object.PutObjectRequest, com.tencent.cos.xml.model.object.BasePutObjectRequest, com.tencent.cos.xml.model.object.ObjectRequest, com.tencent.cos.xml.model.CosXmlRequest
    public void checkParameters() throws CosXmlClientException {
        super.checkParameters();
    }
}
