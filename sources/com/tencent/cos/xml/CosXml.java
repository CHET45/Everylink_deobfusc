package com.tencent.cos.xml;

import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlBooleanListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.EmptyResponseResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketCORSRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketCORSResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketDomainRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketDomainResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketInventoryRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketInventoryResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketLifecycleRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketLifecycleResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketPolicyRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketPolicyResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketReplicationRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketReplicationResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketTaggingRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketTaggingResult;
import com.tencent.cos.xml.model.bucket.DeleteBucketWebsiteRequest;
import com.tencent.cos.xml.model.bucket.DeleteBucketWebsiteResult;
import com.tencent.cos.xml.model.bucket.GetBucketACLRequest;
import com.tencent.cos.xml.model.bucket.GetBucketACLResult;
import com.tencent.cos.xml.model.bucket.GetBucketAccelerateRequest;
import com.tencent.cos.xml.model.bucket.GetBucketAccelerateResult;
import com.tencent.cos.xml.model.bucket.GetBucketCORSRequest;
import com.tencent.cos.xml.model.bucket.GetBucketCORSResult;
import com.tencent.cos.xml.model.bucket.GetBucketDomainRequest;
import com.tencent.cos.xml.model.bucket.GetBucketDomainResult;
import com.tencent.cos.xml.model.bucket.GetBucketIntelligentTieringRequest;
import com.tencent.cos.xml.model.bucket.GetBucketIntelligentTieringResult;
import com.tencent.cos.xml.model.bucket.GetBucketInventoryRequest;
import com.tencent.cos.xml.model.bucket.GetBucketInventoryResult;
import com.tencent.cos.xml.model.bucket.GetBucketLifecycleRequest;
import com.tencent.cos.xml.model.bucket.GetBucketLifecycleResult;
import com.tencent.cos.xml.model.bucket.GetBucketLocationRequest;
import com.tencent.cos.xml.model.bucket.GetBucketLocationResult;
import com.tencent.cos.xml.model.bucket.GetBucketLoggingRequest;
import com.tencent.cos.xml.model.bucket.GetBucketLoggingResult;
import com.tencent.cos.xml.model.bucket.GetBucketObjectVersionsRequest;
import com.tencent.cos.xml.model.bucket.GetBucketObjectVersionsResult;
import com.tencent.cos.xml.model.bucket.GetBucketPolicyRequest;
import com.tencent.cos.xml.model.bucket.GetBucketPolicyResult;
import com.tencent.cos.xml.model.bucket.GetBucketRefererRequest;
import com.tencent.cos.xml.model.bucket.GetBucketRefererResult;
import com.tencent.cos.xml.model.bucket.GetBucketReplicationRequest;
import com.tencent.cos.xml.model.bucket.GetBucketReplicationResult;
import com.tencent.cos.xml.model.bucket.GetBucketRequest;
import com.tencent.cos.xml.model.bucket.GetBucketResult;
import com.tencent.cos.xml.model.bucket.GetBucketTaggingRequest;
import com.tencent.cos.xml.model.bucket.GetBucketTaggingResult;
import com.tencent.cos.xml.model.bucket.GetBucketVersioningRequest;
import com.tencent.cos.xml.model.bucket.GetBucketVersioningResult;
import com.tencent.cos.xml.model.bucket.GetBucketWebsiteRequest;
import com.tencent.cos.xml.model.bucket.GetBucketWebsiteResult;
import com.tencent.cos.xml.model.bucket.HeadBucketRequest;
import com.tencent.cos.xml.model.bucket.HeadBucketResult;
import com.tencent.cos.xml.model.bucket.ListBucketInventoryRequest;
import com.tencent.cos.xml.model.bucket.ListBucketInventoryResult;
import com.tencent.cos.xml.model.bucket.ListBucketVersionsRequest;
import com.tencent.cos.xml.model.bucket.ListBucketVersionsResult;
import com.tencent.cos.xml.model.bucket.PutBucketACLRequest;
import com.tencent.cos.xml.model.bucket.PutBucketACLResult;
import com.tencent.cos.xml.model.bucket.PutBucketAccelerateRequest;
import com.tencent.cos.xml.model.bucket.PutBucketAccelerateResult;
import com.tencent.cos.xml.model.bucket.PutBucketCORSRequest;
import com.tencent.cos.xml.model.bucket.PutBucketCORSResult;
import com.tencent.cos.xml.model.bucket.PutBucketDomainRequest;
import com.tencent.cos.xml.model.bucket.PutBucketDomainResult;
import com.tencent.cos.xml.model.bucket.PutBucketIntelligentTieringRequest;
import com.tencent.cos.xml.model.bucket.PutBucketIntelligentTieringResult;
import com.tencent.cos.xml.model.bucket.PutBucketInventoryRequest;
import com.tencent.cos.xml.model.bucket.PutBucketInventoryResult;
import com.tencent.cos.xml.model.bucket.PutBucketLifecycleRequest;
import com.tencent.cos.xml.model.bucket.PutBucketLifecycleResult;
import com.tencent.cos.xml.model.bucket.PutBucketLoggingRequest;
import com.tencent.cos.xml.model.bucket.PutBucketLoggingResult;
import com.tencent.cos.xml.model.bucket.PutBucketPolicyRequest;
import com.tencent.cos.xml.model.bucket.PutBucketPolicyResult;
import com.tencent.cos.xml.model.bucket.PutBucketRefererRequest;
import com.tencent.cos.xml.model.bucket.PutBucketRefererResult;
import com.tencent.cos.xml.model.bucket.PutBucketReplicationRequest;
import com.tencent.cos.xml.model.bucket.PutBucketReplicationResult;
import com.tencent.cos.xml.model.bucket.PutBucketRequest;
import com.tencent.cos.xml.model.bucket.PutBucketResult;
import com.tencent.cos.xml.model.bucket.PutBucketTaggingRequest;
import com.tencent.cos.xml.model.bucket.PutBucketTaggingResult;
import com.tencent.cos.xml.model.bucket.PutBucketVersioningRequest;
import com.tencent.cos.xml.model.bucket.PutBucketVersioningResult;
import com.tencent.cos.xml.model.bucket.PutBucketWebsiteRequest;
import com.tencent.cos.xml.model.bucket.PutBucketWebsiteResult;
import com.tencent.cos.xml.model.object.AppendObjectRequest;
import com.tencent.cos.xml.model.object.AppendObjectResult;
import com.tencent.cos.xml.model.object.DeleteMultiObjectRequest;
import com.tencent.cos.xml.model.object.DeleteMultiObjectResult;
import com.tencent.cos.xml.model.object.DeleteObjectTaggingRequest;
import com.tencent.cos.xml.model.object.DeleteObjectTaggingResult;
import com.tencent.cos.xml.model.object.GetObjectACLRequest;
import com.tencent.cos.xml.model.object.GetObjectACLResult;
import com.tencent.cos.xml.model.object.GetObjectResult;
import com.tencent.cos.xml.model.object.GetObjectTaggingRequest;
import com.tencent.cos.xml.model.object.GetObjectTaggingResult;
import com.tencent.cos.xml.model.object.OptionObjectRequest;
import com.tencent.cos.xml.model.object.OptionObjectResult;
import com.tencent.cos.xml.model.object.PutObjectACLRequest;
import com.tencent.cos.xml.model.object.PutObjectACLResult;
import com.tencent.cos.xml.model.object.PutObjectTaggingRequest;
import com.tencent.cos.xml.model.object.PutObjectTaggingResult;
import com.tencent.cos.xml.model.object.RestoreRequest;
import com.tencent.cos.xml.model.object.RestoreResult;
import com.tencent.cos.xml.model.object.SelectObjectContentRequest;
import com.tencent.cos.xml.model.object.SelectObjectContentResult;
import com.tencent.cos.xml.model.p033ci.GetDescribeMediaBucketsRequest;
import com.tencent.cos.xml.model.p033ci.GetDescribeMediaBucketsResult;
import com.tencent.cos.xml.model.p033ci.GetMediaInfoRequest;
import com.tencent.cos.xml.model.p033ci.GetMediaInfoResult;
import com.tencent.cos.xml.model.p033ci.GetSnapshotRequest;
import com.tencent.cos.xml.model.p033ci.GetSnapshotResult;
import com.tencent.cos.xml.model.p033ci.PreviewDocumentInHtmlBytesRequest;
import com.tencent.cos.xml.model.p033ci.PreviewDocumentInHtmlLinkRequest;
import com.tencent.cos.xml.model.p033ci.PreviewDocumentInHtmlLinkResult;
import com.tencent.cos.xml.model.p033ci.PreviewDocumentInHtmlRequest;
import com.tencent.cos.xml.model.p033ci.PreviewDocumentInHtmlResult;
import com.tencent.cos.xml.model.p033ci.PreviewDocumentRequest;
import com.tencent.cos.xml.model.p033ci.PreviewDocumentResult;
import com.tencent.cos.xml.model.p033ci.media.GetPrivateM3U8Request;
import com.tencent.cos.xml.model.p033ci.media.GetPrivateM3U8Result;
import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailRequest;
import com.tencent.cos.xml.model.p033ci.media.GetWorkflowDetailResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIBodyRecognitionRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIBodyRecognitionResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectCarRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectCarResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectFaceRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectFaceResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectPetRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIDetectPetResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIEnhanceImageRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIFaceEffectRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIFaceEffectResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIGameRecRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIGameRecResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIIDCardOCRRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIIDCardOCRResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIImageColoringRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AIImageCropRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AILicenseRecRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AILicenseRecResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AIPortraitMattingRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AISuperResolutionRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AddImageSearchRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AssessQualityRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AssessQualityResult;
import com.tencent.cos.xml.model.p033ci.p034ai.AutoTranslationBlockRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.AutoTranslationBlockResult;
import com.tencent.cos.xml.model.p033ci.p034ai.COSOCRRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.COSOCRResult;
import com.tencent.cos.xml.model.p033ci.p034ai.CreateCRcodeRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.CreateCRcodeResult;
import com.tencent.cos.xml.model.p033ci.p034ai.DeleteImageSearchRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.DetectLabelRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.DetectLabelResult;
import com.tencent.cos.xml.model.p033ci.p034ai.GetActionSequenceRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.GetActionSequenceResult;
import com.tencent.cos.xml.model.p033ci.p034ai.GetLiveCodeRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.GetLiveCodeResult;
import com.tencent.cos.xml.model.p033ci.p034ai.GetSearchImageRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.GetSearchImageResult;
import com.tencent.cos.xml.model.p033ci.p034ai.GoodsMattingRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.ImageRepairRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.LivenessRecognitionRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.LivenessRecognitionResult;
import com.tencent.cos.xml.model.p033ci.p034ai.RecognitionQRcodeRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.RecognitionQRcodeResult;
import com.tencent.cos.xml.model.p033ci.p034ai.RecognizeLogoRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.RecognizeLogoResult;
import com.tencent.cos.xml.model.service.GetServiceRequest;
import com.tencent.cos.xml.model.service.GetServiceResult;
import com.tencent.cos.xml.model.tag.COSMetaData;

/* JADX INFO: loaded from: classes4.dex */
public interface CosXml extends SimpleCosXml {
    EmptyResponseResult addImageSearch(AddImageSearchRequest addImageSearchRequest) throws CosXmlServiceException, CosXmlClientException;

    void addImageSearchAsync(AddImageSearchRequest addImageSearchRequest, CosXmlResultListener cosXmlResultListener);

    AIBodyRecognitionResult aiBodyRecognition(AIBodyRecognitionRequest aIBodyRecognitionRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiBodyRecognitionAsync(AIBodyRecognitionRequest aIBodyRecognitionRequest, CosXmlResultListener cosXmlResultListener);

    AIDetectCarResult aiDetectCar(AIDetectCarRequest aIDetectCarRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiDetectCarAsync(AIDetectCarRequest aIDetectCarRequest, CosXmlResultListener cosXmlResultListener);

    AIDetectFaceResult aiDetectFace(AIDetectFaceRequest aIDetectFaceRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiDetectFaceAsync(AIDetectFaceRequest aIDetectFaceRequest, CosXmlResultListener cosXmlResultListener);

    AIDetectPetResult aiDetectPet(AIDetectPetRequest aIDetectPetRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiDetectPetAsync(AIDetectPetRequest aIDetectPetRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectResult aiEnhanceImage(AIEnhanceImageRequest aIEnhanceImageRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiEnhanceImageAsync(AIEnhanceImageRequest aIEnhanceImageRequest, CosXmlResultListener cosXmlResultListener);

    AIFaceEffectResult aiFaceEffect(AIFaceEffectRequest aIFaceEffectRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiFaceEffectAsync(AIFaceEffectRequest aIFaceEffectRequest, CosXmlResultListener cosXmlResultListener);

    AIGameRecResult aiGameRec(AIGameRecRequest aIGameRecRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiGameRecAsync(AIGameRecRequest aIGameRecRequest, CosXmlResultListener cosXmlResultListener);

    AIIDCardOCRResult aiIDCardOCR(AIIDCardOCRRequest aIIDCardOCRRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiIDCardOCRAsync(AIIDCardOCRRequest aIIDCardOCRRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectResult aiImageColoring(AIImageColoringRequest aIImageColoringRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiImageColoringAsync(AIImageColoringRequest aIImageColoringRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectResult aiImageCrop(AIImageCropRequest aIImageCropRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiImageCropAsync(AIImageCropRequest aIImageCropRequest, CosXmlResultListener cosXmlResultListener);

    AILicenseRecResult aiLicenseRec(AILicenseRecRequest aILicenseRecRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiLicenseRecAsync(AILicenseRecRequest aILicenseRecRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectResult aiPortraitMatting(AIPortraitMattingRequest aIPortraitMattingRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiPortraitMattingAsync(AIPortraitMattingRequest aIPortraitMattingRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectResult aiSuperResolution(AISuperResolutionRequest aISuperResolutionRequest) throws CosXmlServiceException, CosXmlClientException;

    void aiSuperResolutionAsync(AISuperResolutionRequest aISuperResolutionRequest, CosXmlResultListener cosXmlResultListener);

    AppendObjectResult appendObject(AppendObjectRequest appendObjectRequest) throws CosXmlServiceException, CosXmlClientException;

    void appendObjectAsync(AppendObjectRequest appendObjectRequest, CosXmlResultListener cosXmlResultListener);

    AssessQualityResult assessQuality(AssessQualityRequest assessQualityRequest) throws CosXmlServiceException, CosXmlClientException;

    void assessQualityAsync(AssessQualityRequest assessQualityRequest, CosXmlResultListener cosXmlResultListener);

    AutoTranslationBlockResult autoTranslationBlock(AutoTranslationBlockRequest autoTranslationBlockRequest) throws CosXmlServiceException, CosXmlClientException;

    void autoTranslationBlockAsync(AutoTranslationBlockRequest autoTranslationBlockRequest, CosXmlResultListener cosXmlResultListener);

    COSOCRResult cOSOCR(COSOCRRequest cOSOCRRequest) throws CosXmlServiceException, CosXmlClientException;

    void cOSOCRAsync(COSOCRRequest cOSOCRRequest, CosXmlResultListener cosXmlResultListener);

    CreateCRcodeResult createCRcode(CreateCRcodeRequest createCRcodeRequest) throws CosXmlServiceException, CosXmlClientException;

    void createCRcodeAsync(CreateCRcodeRequest createCRcodeRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketResult deleteBucket(DeleteBucketRequest deleteBucketRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketAsync(DeleteBucketRequest deleteBucketRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketCORSResult deleteBucketCORS(DeleteBucketCORSRequest deleteBucketCORSRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketCORSAsync(DeleteBucketCORSRequest deleteBucketCORSRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketDomainResult deleteBucketDomain(DeleteBucketDomainRequest deleteBucketDomainRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketDomainAsync(DeleteBucketDomainRequest deleteBucketDomainRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketInventoryResult deleteBucketInventory(DeleteBucketInventoryRequest deleteBucketInventoryRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketInventoryAsync(DeleteBucketInventoryRequest deleteBucketInventoryRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketLifecycleResult deleteBucketLifecycle(DeleteBucketLifecycleRequest deleteBucketLifecycleRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketLifecycleAsync(DeleteBucketLifecycleRequest deleteBucketLifecycleRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketPolicyResult deleteBucketPolicy(DeleteBucketPolicyRequest deleteBucketPolicyRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketPolicyAsync(DeleteBucketPolicyRequest deleteBucketPolicyRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketReplicationResult deleteBucketReplication(DeleteBucketReplicationRequest deleteBucketReplicationRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketReplicationAsync(DeleteBucketReplicationRequest deleteBucketReplicationRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketTaggingResult deleteBucketTagging(DeleteBucketTaggingRequest deleteBucketTaggingRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketTaggingAsync(DeleteBucketTaggingRequest deleteBucketTaggingRequest, CosXmlResultListener cosXmlResultListener);

    DeleteBucketWebsiteResult deleteBucketWebsite(DeleteBucketWebsiteRequest deleteBucketWebsiteRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteBucketWebsiteAsync(DeleteBucketWebsiteRequest deleteBucketWebsiteRequest, CosXmlResultListener cosXmlResultListener);

    EmptyResponseResult deleteImageSearch(DeleteImageSearchRequest deleteImageSearchRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteImageSearchAsync(DeleteImageSearchRequest deleteImageSearchRequest, CosXmlResultListener cosXmlResultListener);

    DeleteMultiObjectResult deleteMultiObject(DeleteMultiObjectRequest deleteMultiObjectRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteMultiObjectAsync(DeleteMultiObjectRequest deleteMultiObjectRequest, CosXmlResultListener cosXmlResultListener);

    boolean deleteObject(String str, String str2) throws CosXmlServiceException, CosXmlClientException;

    void deleteObjectAsync(String str, String str2, CosXmlBooleanListener cosXmlBooleanListener);

    DeleteObjectTaggingResult deleteObjectTagging(DeleteObjectTaggingRequest deleteObjectTaggingRequest) throws CosXmlServiceException, CosXmlClientException;

    void deleteObjectTaggingAsync(DeleteObjectTaggingRequest deleteObjectTaggingRequest, CosXmlResultListener cosXmlResultListener);

    DetectLabelResult detectLabel(DetectLabelRequest detectLabelRequest) throws CosXmlServiceException, CosXmlClientException;

    void detectLabelAsync(DetectLabelRequest detectLabelRequest, CosXmlResultListener cosXmlResultListener);

    boolean doesBucketExist(String str) throws CosXmlServiceException, CosXmlClientException;

    void doesBucketExistAsync(String str, CosXmlBooleanListener cosXmlBooleanListener);

    boolean doesObjectExist(String str, String str2) throws CosXmlServiceException, CosXmlClientException;

    void doesObjectExistAsync(String str, String str2, CosXmlBooleanListener cosXmlBooleanListener);

    GetActionSequenceResult getActionSequence(GetActionSequenceRequest getActionSequenceRequest) throws CosXmlServiceException, CosXmlClientException;

    void getActionSequenceAsync(GetActionSequenceRequest getActionSequenceRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketResult getBucket(GetBucketRequest getBucketRequest) throws CosXmlServiceException, CosXmlClientException;

    GetBucketACLResult getBucketACL(GetBucketACLRequest getBucketACLRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketACLAsync(GetBucketACLRequest getBucketACLRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketAccelerateResult getBucketAccelerate(GetBucketAccelerateRequest getBucketAccelerateRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketAccelerateAsync(GetBucketAccelerateRequest getBucketAccelerateRequest, CosXmlResultListener cosXmlResultListener);

    void getBucketAsync(GetBucketRequest getBucketRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketCORSResult getBucketCORS(GetBucketCORSRequest getBucketCORSRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketCORSAsync(GetBucketCORSRequest getBucketCORSRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketDomainResult getBucketDomain(GetBucketDomainRequest getBucketDomainRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketDomainAsync(GetBucketDomainRequest getBucketDomainRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketIntelligentTieringResult getBucketIntelligentTiering(GetBucketIntelligentTieringRequest getBucketIntelligentTieringRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketIntelligentTieringAsync(GetBucketIntelligentTieringRequest getBucketIntelligentTieringRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketInventoryResult getBucketInventory(GetBucketInventoryRequest getBucketInventoryRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketInventoryAsync(GetBucketInventoryRequest getBucketInventoryRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketLifecycleResult getBucketLifecycle(GetBucketLifecycleRequest getBucketLifecycleRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketLifecycleAsync(GetBucketLifecycleRequest getBucketLifecycleRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketLocationResult getBucketLocation(GetBucketLocationRequest getBucketLocationRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketLocationAsync(GetBucketLocationRequest getBucketLocationRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketLoggingResult getBucketLogging(GetBucketLoggingRequest getBucketLoggingRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketLoggingAsync(GetBucketLoggingRequest getBucketLoggingRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketObjectVersionsResult getBucketObjectVersions(GetBucketObjectVersionsRequest getBucketObjectVersionsRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketObjectVersionsAsync(GetBucketObjectVersionsRequest getBucketObjectVersionsRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketPolicyResult getBucketPolicy(GetBucketPolicyRequest getBucketPolicyRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketPolicyAsync(GetBucketPolicyRequest getBucketPolicyRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketRefererResult getBucketReferer(GetBucketRefererRequest getBucketRefererRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketRefererAsync(GetBucketRefererRequest getBucketRefererRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketReplicationResult getBucketReplication(GetBucketReplicationRequest getBucketReplicationRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketReplicationAsync(GetBucketReplicationRequest getBucketReplicationRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketTaggingResult getBucketTagging(GetBucketTaggingRequest getBucketTaggingRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketTaggingAsync(GetBucketTaggingRequest getBucketTaggingRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketVersioningResult getBucketVersioning(GetBucketVersioningRequest getBucketVersioningRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketVersioningAsync(GetBucketVersioningRequest getBucketVersioningRequest, CosXmlResultListener cosXmlResultListener);

    GetBucketWebsiteResult getBucketWebsite(GetBucketWebsiteRequest getBucketWebsiteRequest) throws CosXmlServiceException, CosXmlClientException;

    void getBucketWebsiteAsync(GetBucketWebsiteRequest getBucketWebsiteRequest, CosXmlResultListener cosXmlResultListener);

    GetDescribeMediaBucketsResult getDescribeMediaBuckets(GetDescribeMediaBucketsRequest getDescribeMediaBucketsRequest) throws CosXmlServiceException, CosXmlClientException;

    void getDescribeMediaBucketsAsync(GetDescribeMediaBucketsRequest getDescribeMediaBucketsRequest, CosXmlResultListener cosXmlResultListener);

    GetLiveCodeResult getLiveCode(GetLiveCodeRequest getLiveCodeRequest) throws CosXmlServiceException, CosXmlClientException;

    void getLiveCodeAsync(GetLiveCodeRequest getLiveCodeRequest, CosXmlResultListener cosXmlResultListener);

    GetMediaInfoResult getMediaInfo(GetMediaInfoRequest getMediaInfoRequest) throws CosXmlServiceException, CosXmlClientException;

    void getMediaInfoAsync(GetMediaInfoRequest getMediaInfoRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectACLResult getObjectACL(GetObjectACLRequest getObjectACLRequest) throws CosXmlServiceException, CosXmlClientException;

    void getObjectACLAsync(GetObjectACLRequest getObjectACLRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectTaggingResult getObjectTagging(GetObjectTaggingRequest getObjectTaggingRequest) throws CosXmlServiceException, CosXmlClientException;

    void getObjectTaggingAsync(GetObjectTaggingRequest getObjectTaggingRequest, CosXmlResultListener cosXmlResultListener);

    GetPrivateM3U8Result getPrivateM3U8(GetPrivateM3U8Request getPrivateM3U8Request) throws CosXmlServiceException, CosXmlClientException;

    void getPrivateM3U8Async(GetPrivateM3U8Request getPrivateM3U8Request, CosXmlResultListener cosXmlResultListener);

    GetSearchImageResult getSearchImage(GetSearchImageRequest getSearchImageRequest) throws CosXmlServiceException, CosXmlClientException;

    void getSearchImageAsync(GetSearchImageRequest getSearchImageRequest, CosXmlResultListener cosXmlResultListener);

    GetServiceResult getService(GetServiceRequest getServiceRequest) throws CosXmlServiceException, CosXmlClientException;

    void getServiceAsync(GetServiceRequest getServiceRequest, CosXmlResultListener cosXmlResultListener);

    GetSnapshotResult getSnapshot(GetSnapshotRequest getSnapshotRequest) throws CosXmlServiceException, CosXmlClientException;

    void getSnapshotAsync(GetSnapshotRequest getSnapshotRequest, CosXmlResultListener cosXmlResultListener);

    GetWorkflowDetailResult getWorkflowDetail(GetWorkflowDetailRequest getWorkflowDetailRequest) throws CosXmlServiceException, CosXmlClientException;

    void getWorkflowDetailAsync(GetWorkflowDetailRequest getWorkflowDetailRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectResult goodsMatting(GoodsMattingRequest goodsMattingRequest) throws CosXmlServiceException, CosXmlClientException;

    void goodsMattingAsync(GoodsMattingRequest goodsMattingRequest, CosXmlResultListener cosXmlResultListener);

    HeadBucketResult headBucket(HeadBucketRequest headBucketRequest) throws CosXmlServiceException, CosXmlClientException;

    void headBucketAsync(HeadBucketRequest headBucketRequest, CosXmlResultListener cosXmlResultListener);

    GetObjectResult imageRepair(ImageRepairRequest imageRepairRequest) throws CosXmlServiceException, CosXmlClientException;

    void imageRepairAsync(ImageRepairRequest imageRepairRequest, CosXmlResultListener cosXmlResultListener);

    ListBucketInventoryResult listBucketInventory(ListBucketInventoryRequest listBucketInventoryRequest) throws CosXmlServiceException, CosXmlClientException;

    void listBucketInventoryAsync(ListBucketInventoryRequest listBucketInventoryRequest, CosXmlResultListener cosXmlResultListener);

    ListBucketVersionsResult listBucketVersions(ListBucketVersionsRequest listBucketVersionsRequest) throws CosXmlServiceException, CosXmlClientException;

    void listBucketVersionsAsync(ListBucketVersionsRequest listBucketVersionsRequest, CosXmlResultListener cosXmlResultListener);

    LivenessRecognitionResult livenessRecognition(LivenessRecognitionRequest livenessRecognitionRequest) throws CosXmlServiceException, CosXmlClientException;

    void livenessRecognitionAsync(LivenessRecognitionRequest livenessRecognitionRequest, CosXmlResultListener cosXmlResultListener);

    OptionObjectResult optionObject(OptionObjectRequest optionObjectRequest) throws CosXmlServiceException, CosXmlClientException;

    void optionObjectAsync(OptionObjectRequest optionObjectRequest, CosXmlResultListener cosXmlResultListener);

    PreviewDocumentResult previewDocument(PreviewDocumentRequest previewDocumentRequest) throws CosXmlServiceException, CosXmlClientException;

    void previewDocumentAsync(PreviewDocumentRequest previewDocumentRequest, CosXmlResultListener cosXmlResultListener);

    PreviewDocumentInHtmlResult previewDocumentInHtml(PreviewDocumentInHtmlRequest previewDocumentInHtmlRequest) throws CosXmlServiceException, CosXmlClientException;

    void previewDocumentInHtmlAsync(PreviewDocumentInHtmlRequest previewDocumentInHtmlRequest, CosXmlResultListener cosXmlResultListener);

    byte[] previewDocumentInHtmlBytes(PreviewDocumentInHtmlBytesRequest previewDocumentInHtmlBytesRequest) throws CosXmlServiceException, CosXmlClientException;

    byte[] previewDocumentInHtmlBytes(String str, String str2) throws CosXmlServiceException, CosXmlClientException;

    PreviewDocumentInHtmlLinkResult previewDocumentInHtmlLink(PreviewDocumentInHtmlLinkRequest previewDocumentInHtmlLinkRequest) throws CosXmlServiceException, CosXmlClientException;

    void previewDocumentInHtmlLinkAsync(PreviewDocumentInHtmlLinkRequest previewDocumentInHtmlLinkRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketResult putBucket(PutBucketRequest putBucketRequest) throws CosXmlServiceException, CosXmlClientException;

    PutBucketACLResult putBucketACL(PutBucketACLRequest putBucketACLRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketACLAsync(PutBucketACLRequest putBucketACLRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketAccelerateResult putBucketAccelerate(PutBucketAccelerateRequest putBucketAccelerateRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketAccelerateAsync(PutBucketAccelerateRequest putBucketAccelerateRequest, CosXmlResultListener cosXmlResultListener);

    void putBucketAsync(PutBucketRequest putBucketRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketCORSResult putBucketCORS(PutBucketCORSRequest putBucketCORSRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketCORSAsync(PutBucketCORSRequest putBucketCORSRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketDomainResult putBucketDomain(PutBucketDomainRequest putBucketDomainRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketDomainAsync(PutBucketDomainRequest putBucketDomainRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketIntelligentTieringResult putBucketIntelligentTiering(PutBucketIntelligentTieringRequest putBucketIntelligentTieringRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketIntelligentTieringAsync(PutBucketIntelligentTieringRequest putBucketIntelligentTieringRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketInventoryResult putBucketInventory(PutBucketInventoryRequest putBucketInventoryRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketInventoryAsync(PutBucketInventoryRequest putBucketInventoryRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketLifecycleResult putBucketLifecycle(PutBucketLifecycleRequest putBucketLifecycleRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketLifecycleAsync(PutBucketLifecycleRequest putBucketLifecycleRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketLoggingResult putBucketLogging(PutBucketLoggingRequest putBucketLoggingRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketLoggingAsync(PutBucketLoggingRequest putBucketLoggingRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketPolicyResult putBucketPolicy(PutBucketPolicyRequest putBucketPolicyRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketPolicyAsync(PutBucketPolicyRequest putBucketPolicyRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketRefererResult putBucketReferer(PutBucketRefererRequest putBucketRefererRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketRefererAsync(PutBucketRefererRequest putBucketRefererRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketReplicationResult putBucketReplication(PutBucketReplicationRequest putBucketReplicationRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketReplicationAsync(PutBucketReplicationRequest putBucketReplicationRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketTaggingResult putBucketTagging(PutBucketTaggingRequest putBucketTaggingRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketTaggingAsync(PutBucketTaggingRequest putBucketTaggingRequest, CosXmlResultListener cosXmlResultListener);

    void putBucketVersionAsync(PutBucketVersioningRequest putBucketVersioningRequest, CosXmlResultListener cosXmlResultListener);

    PutBucketVersioningResult putBucketVersioning(PutBucketVersioningRequest putBucketVersioningRequest) throws CosXmlServiceException, CosXmlClientException;

    PutBucketWebsiteResult putBucketWebsite(PutBucketWebsiteRequest putBucketWebsiteRequest) throws CosXmlServiceException, CosXmlClientException;

    void putBucketWebsiteAsync(PutBucketWebsiteRequest putBucketWebsiteRequest, CosXmlResultListener cosXmlResultListener);

    PutObjectACLResult putObjectACL(PutObjectACLRequest putObjectACLRequest) throws CosXmlServiceException, CosXmlClientException;

    void putObjectACLAsync(PutObjectACLRequest putObjectACLRequest, CosXmlResultListener cosXmlResultListener);

    PutObjectTaggingResult putObjectTagging(PutObjectTaggingRequest putObjectTaggingRequest) throws CosXmlServiceException, CosXmlClientException;

    void putObjectTaggingAsync(PutObjectTaggingRequest putObjectTaggingRequest, CosXmlResultListener cosXmlResultListener);

    RecognitionQRcodeResult recognitionQRcode(RecognitionQRcodeRequest recognitionQRcodeRequest) throws CosXmlServiceException, CosXmlClientException;

    void recognitionQRcodeAsync(RecognitionQRcodeRequest recognitionQRcodeRequest, CosXmlResultListener cosXmlResultListener);

    RecognizeLogoResult recognizeLogo(RecognizeLogoRequest recognizeLogoRequest) throws CosXmlServiceException, CosXmlClientException;

    void recognizeLogoAsync(RecognizeLogoRequest recognizeLogoRequest, CosXmlResultListener cosXmlResultListener);

    RestoreResult restoreObject(RestoreRequest restoreRequest) throws CosXmlServiceException, CosXmlClientException;

    void restoreObjectAsync(RestoreRequest restoreRequest, CosXmlResultListener cosXmlResultListener);

    SelectObjectContentResult selectObjectContent(SelectObjectContentRequest selectObjectContentRequest) throws CosXmlServiceException, CosXmlClientException;

    void selectObjectContentAsync(SelectObjectContentRequest selectObjectContentRequest, CosXmlResultListener cosXmlResultListener);

    @Deprecated
    boolean updateObjectMeta(String str, String str2, COSMetaData cOSMetaData) throws CosXmlServiceException, CosXmlClientException;

    @Deprecated
    void updateObjectMetaAsync(String str, String str2, COSMetaData cOSMetaData, CosXmlBooleanListener cosXmlBooleanListener);

    boolean updateObjectMetaData(String str, String str2, COSMetaData cOSMetaData) throws CosXmlServiceException, CosXmlClientException;

    void updateObjectMetaDataAsync(String str, String str2, COSMetaData cOSMetaData, CosXmlBooleanListener cosXmlBooleanListener);
}
