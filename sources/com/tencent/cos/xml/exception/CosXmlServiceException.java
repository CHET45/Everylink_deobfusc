package com.tencent.cos.xml.exception;

import com.tencent.qcloud.core.common.QCloudServiceException;

/* JADX INFO: loaded from: classes4.dex */
public class CosXmlServiceException extends QCloudServiceException {
    private static final long serialVersionUID = 1;
    private String host;
    private String httpMsg;

    public CosXmlServiceException(String str, String str2) {
        super(null);
        this.httpMsg = str;
        this.host = str2;
    }

    public CosXmlServiceException(String str, Exception exc) {
        super(str, exc);
    }

    public String getHttpMessage() {
        return this.httpMsg;
    }

    public String getHost() {
        return this.host;
    }

    public CosXmlServiceException(QCloudServiceException qCloudServiceException) {
        super(null);
        setErrorCode(qCloudServiceException.getErrorCode());
        setErrorMessage(qCloudServiceException.getErrorMessage());
        setRequestId(qCloudServiceException.getRequestId());
        setServiceName(qCloudServiceException.getServiceName());
        setStatusCode(qCloudServiceException.getStatusCode());
    }

    @Override // com.tencent.qcloud.core.common.QCloudServiceException, java.lang.Throwable
    public String getMessage() {
        return getErrorMessage() + " (Service: " + getServiceName() + "; Status Code: " + getStatusCode() + "; Status Message: " + this.httpMsg + "; Error Code: " + getErrorCode() + "; Request ID: " + getRequestId() + ")";
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "CosXmlServiceException{" + getMessage() + '}';
    }
}
