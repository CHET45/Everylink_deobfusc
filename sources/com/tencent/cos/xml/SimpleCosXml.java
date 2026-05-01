package com.tencent.cos.xml;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.listener.CosXmlResultSimpleListener;
import com.tencent.cos.xml.model.bucket.ListMultiUploadsRequest;
import com.tencent.cos.xml.model.bucket.ListMultiUploadsResult;
import com.tencent.cos.xml.model.object.AbortMultiUploadRequest;
import com.tencent.cos.xml.model.object.AbortMultiUploadResult;
import com.tencent.cos.xml.model.object.CompleteMultiUploadRequest;
import com.tencent.cos.xml.model.object.CompleteMultiUploadResult;
import com.tencent.cos.xml.model.object.CopyObjectRequest;
import com.tencent.cos.xml.model.object.CopyObjectResult;
import com.tencent.cos.xml.model.object.DeleteObjectRequest;
import com.tencent.cos.xml.model.object.DeleteObjectResult;
import com.tencent.cos.xml.model.object.HeadObjectRequest;
import com.tencent.cos.xml.model.object.HeadObjectResult;
import com.tencent.cos.xml.model.object.ImageProcessRequest;
import com.tencent.cos.xml.model.object.ImageProcessResult;
import com.tencent.cos.xml.model.object.InitMultipartUploadRequest;
import com.tencent.cos.xml.model.object.InitMultipartUploadResult;
import com.tencent.cos.xml.model.object.ListPartsRequest;
import com.tencent.cos.xml.model.object.ListPartsResult;
import com.tencent.cos.xml.model.object.PreBuildConnectionRequest;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.cos.xml.model.object.UploadPartCopyRequest;
import com.tencent.cos.xml.model.object.UploadPartCopyResult;

/* JADX INFO: loaded from: classes4.dex */
public interface SimpleCosXml extends BaseCosXml {
    AbortMultiUploadResult abortMultiUpload(AbortMultiUploadRequest abortMultiUploadRequest) throws CosXmlServiceException, CosXmlClientException;

    void abortMultiUploadAsync(AbortMultiUploadRequest abortMultiUploadRequest, CosXmlResultListener cosXmlResultListener);

    CompleteMultiUploadResult completeMultiUpload(CompleteMultiUploadRequest completeMultiUploadRequest) throws CosXmlServiceException, CosXmlClientException;

    void completeMultiUploadAsync(CompleteMultiUploadRequest completeMultiUploadRequest, CosXmlResultListener cosXmlResultListener);

    CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) throws CosXmlServiceException, CosXmlClientException;

    UploadPartCopyResult copyObject(UploadPartCopyRequest uploadPartCopyRequest) throws CosXmlServiceException, CosXmlClientException;

    void copyObjectAsync(CopyObjectRequest copyObjectRequest, CosXmlResultListener cosXmlResultListener);

    void copyObjectAsync(UploadPartCopyRequest uploadPartCopyRequest, CosXmlResultListener cosXmlResultListener);

    DeleteObjectResult deleteObject(DeleteObjectRequest deleteObjectRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteObjectAsync(DeleteObjectRequest deleteObjectRequest, CosXmlResultListener cosXmlResultListener);

    HeadObjectResult headObject(HeadObjectRequest headObjectRequest) throws CosXmlServiceException, CosXmlClientException;

    void headObjectAsync(HeadObjectRequest headObjectRequest, CosXmlResultListener cosXmlResultListener);

    ImageProcessResult imageProcess(ImageProcessRequest imageProcessRequest) throws CosXmlServiceException, CosXmlClientException;

    void imageProcessAsync(ImageProcessRequest imageProcessRequest, CosXmlResultListener cosXmlResultListener);

    InitMultipartUploadResult initMultipartUpload(InitMultipartUploadRequest initMultipartUploadRequest) throws CosXmlServiceException, CosXmlClientException;

    void initMultipartUploadAsync(InitMultipartUploadRequest initMultipartUploadRequest, CosXmlResultListener cosXmlResultListener);

    ListMultiUploadsResult listMultiUploads(ListMultiUploadsRequest listMultiUploadsRequest) throws CosXmlServiceException, CosXmlClientException;

    void listMultiUploadsAsync(ListMultiUploadsRequest listMultiUploadsRequest, CosXmlResultListener cosXmlResultListener);

    ListPartsResult listParts(ListPartsRequest listPartsRequest) throws CosXmlServiceException, CosXmlClientException;

    void listPartsAsync(ListPartsRequest listPartsRequest, CosXmlResultListener cosXmlResultListener);

    boolean preBuildConnection(PreBuildConnectionRequest preBuildConnectionRequest) throws CosXmlServiceException, CosXmlClientException;

    boolean preBuildConnection(String str) throws CosXmlServiceException, CosXmlClientException;

    void preBuildConnectionAsync(PreBuildConnectionRequest preBuildConnectionRequest, CosXmlResultSimpleListener cosXmlResultSimpleListener);

    void preBuildConnectionAsync(String str, CosXmlResultSimpleListener cosXmlResultSimpleListener);

    PutObjectResult putObject(PutObjectRequest putObjectRequest) throws CosXmlServiceException, CosXmlClientException;

    void putObjectAsync(PutObjectRequest putObjectRequest, CosXmlResultListener cosXmlResultListener);
}
