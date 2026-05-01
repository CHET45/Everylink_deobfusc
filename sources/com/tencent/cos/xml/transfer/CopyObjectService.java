package com.tencent.cos.xml.transfer;

import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.CompleteMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadResult;
import com.tencent.cos.xml.model.object.CopyObjectRequest;
import com.tencent.cos.xml.model.object.CopyObjectResult;
import com.tencent.cos.xml.model.object.HeadObjectRequest;
import com.tencent.cos.xml.model.object.HeadObjectResult;
import com.tencent.cos.xml.model.object.InitMultipartUploadRequest;
import com.tencent.cos.xml.model.object.UploadPartCopyRequest;
import com.tencent.cos.xml.model.object.UploadPartCopyResult;
import com.tencent.cos.xml.transfer.UploadService;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
@Deprecated
public class CopyObjectService {
    private CosXmlService cosXmlService;
    private String sourceCustomerKey;
    private String sourceCustomerKeyId;
    private String sourceJsonContent;
    private long maxSliceSize = 5242880;
    private UploadService.EncryptionType encryptionType = UploadService.EncryptionType.NONE;

    public static class CopyServerResult extends CosXmlResult {
        public String eTag;
    }

    public CopyObjectService(CosXmlService cosXmlService) {
        this.cosXmlService = cosXmlService;
    }

    public void setCopySourceCustomerKey(String str) {
        this.encryptionType = UploadService.EncryptionType.SSEC;
        this.sourceCustomerKey = str;
    }

    public void setCopySourceCustomerKeyIdAndJsonContent(String str, String str2) {
        this.sourceCustomerKeyId = str;
        this.sourceJsonContent = str2;
        this.encryptionType = UploadService.EncryptionType.SSEKMS;
    }

    /* JADX INFO: renamed from: com.tencent.cos.xml.transfer.CopyObjectService$1 */
    static /* synthetic */ class C44461 {

        /* JADX INFO: renamed from: $SwitchMap$com$tencent$cos$xml$transfer$UploadService$EncryptionType */
        static final /* synthetic */ int[] f1849x7f2b125a;

        static {
            int[] iArr = new int[UploadService.EncryptionType.values().length];
            f1849x7f2b125a = iArr;
            try {
                iArr[UploadService.EncryptionType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1849x7f2b125a[UploadService.EncryptionType.SSEC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1849x7f2b125a[UploadService.EncryptionType.SSEKMS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void setCopySourceEncryptionRequest(CosXmlRequest cosXmlRequest) throws CosXmlClientException {
        if (cosXmlRequest == null) {
            return;
        }
        int i = C44461.f1849x7f2b125a[this.encryptionType.ordinal()];
        if (i == 2) {
            if (cosXmlRequest instanceof HeadObjectRequest) {
                ((HeadObjectRequest) cosXmlRequest).setCOSServerSideEncryptionWithCustomerKey(this.sourceCustomerKey);
                return;
            } else {
                if (cosXmlRequest instanceof CopyObjectRequest) {
                    ((CopyObjectRequest) cosXmlRequest).setCopySourceServerSideEncryptionCustomerKey(this.sourceCustomerKey);
                    return;
                }
                return;
            }
        }
        if (i != 3) {
            return;
        }
        if (cosXmlRequest instanceof HeadObjectRequest) {
            ((HeadObjectRequest) cosXmlRequest).setCOSServerSideEncryptionWithKMS(this.sourceCustomerKeyId, this.sourceJsonContent);
        } else if (cosXmlRequest instanceof CopyObjectRequest) {
            ((CopyObjectRequest) cosXmlRequest).setCopySourceServerSideEncryptionKMS(this.sourceCustomerKeyId, this.sourceJsonContent);
        }
    }

    public CosXmlResult copyObject(String str, String str2, CopyObjectRequest.CopySourceStruct copySourceStruct) throws CosXmlServiceException, CosXmlClientException {
        CopyServerResult copyServerResult = new CopyServerResult();
        long jHeadObject = headObject(copySourceStruct.bucket, copySourceStruct.cosPath);
        if (jHeadObject >= this.maxSliceSize) {
            CompleteMultiUploadResult completeMultiUploadResultCopyObjectForLargeFile = copyObjectForLargeFile(str, str2, copySourceStruct, jHeadObject);
            copyServerResult.headers = completeMultiUploadResultCopyObjectForLargeFile.headers;
            copyServerResult.httpCode = completeMultiUploadResultCopyObjectForLargeFile.httpCode;
            copyServerResult.httpMessage = completeMultiUploadResultCopyObjectForLargeFile.httpMessage;
            copyServerResult.accessUrl = completeMultiUploadResultCopyObjectForLargeFile.accessUrl;
            copyServerResult.eTag = completeMultiUploadResultCopyObjectForLargeFile.completeMultipartUpload.eTag;
        } else {
            CopyObjectResult copyObjectResultCopyObjectForSmallFile = copyObjectForSmallFile(str, str2, copySourceStruct);
            copyServerResult.headers = copyObjectResultCopyObjectForSmallFile.headers;
            copyServerResult.httpCode = copyObjectResultCopyObjectForSmallFile.httpCode;
            copyServerResult.httpMessage = copyObjectResultCopyObjectForSmallFile.httpMessage;
            copyServerResult.accessUrl = copyObjectResultCopyObjectForSmallFile.accessUrl;
            copyServerResult.eTag = copyObjectResultCopyObjectForSmallFile.copyObject.eTag;
        }
        return copyServerResult;
    }

    public CosXmlResult copyObject(String str, String str2, CopyObjectRequest.CopySourceStruct copySourceStruct, long j) throws CosXmlServiceException, CosXmlClientException {
        if (j >= this.maxSliceSize) {
            return copyObjectForLargeFile(str, str2, copySourceStruct, j);
        }
        return copyObjectForSmallFile(str, str2, copySourceStruct);
    }

    private long headObject(String str, String str2) throws CosXmlServiceException, CosXmlClientException {
        HeadObjectRequest headObjectRequest = new HeadObjectRequest(str, str2);
        setCopySourceEncryptionRequest(headObjectRequest);
        HeadObjectResult headObjectResultHeadObject = this.cosXmlService.headObject(headObjectRequest);
        if (headObjectResultHeadObject != null) {
            return Long.valueOf(headObjectResultHeadObject.headers.get("Content-Length").get(0)).longValue();
        }
        return -1L;
    }

    private CopyObjectResult copyObjectForSmallFile(String str, String str2, CopyObjectRequest.CopySourceStruct copySourceStruct) throws CosXmlServiceException, CosXmlClientException {
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(str, str2, copySourceStruct);
        setCopySourceEncryptionRequest(copyObjectRequest);
        return this.cosXmlService.copyObject(copyObjectRequest);
    }

    private CompleteMultiUploadResult copyObjectForLargeFile(String str, String str2, CopyObjectRequest.CopySourceStruct copySourceStruct, long j) throws CosXmlServiceException, CosXmlClientException {
        String strInitMultiUpload = initMultiUpload(str, str2);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        long j2 = this.maxSliceSize;
        long j3 = -1;
        int i = 1;
        while (true) {
            long j4 = j - 1;
            if (j3 < j4) {
                long j5 = j3 + 1;
                long j6 = j3 + j2;
                long j7 = j6 >= j4 ? j4 : j6;
                linkedHashMap.put(Integer.valueOf(i), copyObjectForLargeFile(str, str2, i, strInitMultiUpload, copySourceStruct, j5, j7).copyObject.eTag);
                i++;
                j3 = j7;
            } else {
                return completeMultipart(str, str2, strInitMultiUpload, linkedHashMap);
            }
        }
    }

    private String initMultiUpload(String str, String str2) throws CosXmlServiceException, CosXmlClientException {
        return this.cosXmlService.initMultipartUpload(new InitMultipartUploadRequest(str, str2)).initMultipartUpload.uploadId;
    }

    private UploadPartCopyResult copyObjectForLargeFile(String str, String str2, int i, String str3, CopyObjectRequest.CopySourceStruct copySourceStruct, long j, long j2) throws CosXmlServiceException, CosXmlClientException {
        UploadPartCopyRequest uploadPartCopyRequest = new UploadPartCopyRequest(str, str2, i, str3, copySourceStruct, j, j2);
        setCopySourceEncryptionRequest(uploadPartCopyRequest);
        return this.cosXmlService.copyObject(uploadPartCopyRequest);
    }

    private CompleteMultiUploadResult completeMultipart(String str, String str2, String str3, Map<Integer, String> map) throws CosXmlServiceException, CosXmlClientException {
        return this.cosXmlService.completeMultiUpload(new CompleteMultiUploadRequest(str, str2, str3, map));
    }
}
