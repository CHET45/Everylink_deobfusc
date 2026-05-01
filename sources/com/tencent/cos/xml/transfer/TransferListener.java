package com.tencent.cos.xml.transfer;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;

/* JADX INFO: loaded from: classes4.dex */
public interface TransferListener {
    void onError(String str, CosXmlClientException cosXmlClientException, CosXmlServiceException cosXmlServiceException);

    void onProgressChanged(String str, long j, long j2);

    void onStateChanged(String str, TransferState transferState);
}
