package com.tencent.cos.xml;

import android.content.Context;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.EmptyResponseResult;
import com.tencent.cos.xml.model.p033ci.DeleteBucketDPStateRequest;
import com.tencent.cos.xml.model.p033ci.DeleteBucketDPStateResult;
import com.tencent.cos.xml.model.p033ci.DescribeDocProcessBucketsRequest;
import com.tencent.cos.xml.model.p033ci.DescribeDocProcessBucketsResult;
import com.tencent.cos.xml.model.p033ci.PutBucketDPStateRequest;
import com.tencent.cos.xml.model.p033ci.PutBucketDPStateResult;
import com.tencent.cos.xml.model.p033ci.QRCodeUploadRequest;
import com.tencent.cos.xml.model.p033ci.QRCodeUploadResult;
import com.tencent.cos.xml.model.p033ci.SensitiveContentRecognitionRequest;
import com.tencent.cos.xml.model.p033ci.SensitiveContentRecognitionResult;
import com.tencent.cos.xml.model.p033ci.asr.CreateSpeechJobsRequest;
import com.tencent.cos.xml.model.p033ci.asr.CreateSpeechJobsResult;
import com.tencent.cos.xml.model.p033ci.asr.DescribeSpeechBucketsRequest;
import com.tencent.cos.xml.model.p033ci.asr.DescribeSpeechBucketsResult;
import com.tencent.cos.xml.model.p033ci.asr.DescribeSpeechJobRequest;
import com.tencent.cos.xml.model.p033ci.asr.DescribeSpeechJobResult;
import com.tencent.cos.xml.model.p033ci.asr.DescribeSpeechJobsRequest;
import com.tencent.cos.xml.model.p033ci.asr.DescribeSpeechJobsResult;
import com.tencent.cos.xml.model.p033ci.asr.DescribeSpeechQueuesRequest;
import com.tencent.cos.xml.model.p033ci.asr.DescribeSpeechQueuesResult;
import com.tencent.cos.xml.model.p033ci.audit.AddAuditTextlibKeywordRequest;
import com.tencent.cos.xml.model.p033ci.audit.AddAuditTextlibKeywordResult;
import com.tencent.cos.xml.model.p033ci.audit.CancelLiveVideoAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.CancelLiveVideoAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.CreateAuditTextlibRequest;
import com.tencent.cos.xml.model.p033ci.audit.CreateAuditTextlibResult;
import com.tencent.cos.xml.model.p033ci.audit.CreateStrategyRequest;
import com.tencent.cos.xml.model.p033ci.audit.CreateStrategyResult;
import com.tencent.cos.xml.model.p033ci.audit.DeleteAuditTextlibKeywordRequest;
import com.tencent.cos.xml.model.p033ci.audit.DeleteAuditTextlibKeywordResult;
import com.tencent.cos.xml.model.p033ci.audit.DeleteAuditTextlibRequest;
import com.tencent.cos.xml.model.p033ci.audit.DeleteAuditTextlibResult;
import com.tencent.cos.xml.model.p033ci.audit.GetAudioAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetAudioAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.GetAuditTextlibKeywordListRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetAuditTextlibKeywordListResult;
import com.tencent.cos.xml.model.p033ci.audit.GetAuditTextlibListRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetAuditTextlibListResult;
import com.tencent.cos.xml.model.p033ci.audit.GetDocumentAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetDocumentAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.GetImageAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetImageAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetLiveVideoAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.GetStrategyDetailRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetStrategyDetailResult;
import com.tencent.cos.xml.model.p033ci.audit.GetStrategyListRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetStrategyListResult;
import com.tencent.cos.xml.model.p033ci.audit.GetTextAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetVideoAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetVideoAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.GetWebPageAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.GetWebPageAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.PostAudioAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.PostAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.PostDocumentAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.PostImageAuditReportRequest;
import com.tencent.cos.xml.model.p033ci.audit.PostImageAuditReportResult;
import com.tencent.cos.xml.model.p033ci.audit.PostImagesAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.PostImagesAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.PostLiveVideoAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.PostLiveVideoAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.PostTextAuditReportRequest;
import com.tencent.cos.xml.model.p033ci.audit.PostTextAuditReportResult;
import com.tencent.cos.xml.model.p033ci.audit.PostTextAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.PostVideoAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.PostWebPageAuditRequest;
import com.tencent.cos.xml.model.p033ci.audit.TextAuditResult;
import com.tencent.cos.xml.model.p033ci.audit.UpdateAuditTextlibRequest;
import com.tencent.cos.xml.model.p033ci.audit.UpdateAuditTextlibResult;
import com.tencent.cos.xml.model.p033ci.audit.UpdateStrategyRequest;
import com.tencent.cos.xml.model.p033ci.audit.UpdateStrategyResult;
import com.tencent.cos.xml.model.p033ci.media.GetWorkflowListRequest;
import com.tencent.cos.xml.model.p033ci.media.GetWorkflowListResult;
import com.tencent.cos.xml.model.p033ci.media.SearchMediaQueueRequest;
import com.tencent.cos.xml.model.p033ci.media.SearchMediaQueueResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitAnimationJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitConcatJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitDigitalWatermarkJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitExtractDigitalWatermarkJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitExtractDigitalWatermarkJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaInfoJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaInfoJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitMediaSegmentJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitPicProcessJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitSmartCoverJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitSnapshotJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitTranscodeJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoMontageJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoMontageJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoTagJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitVideoTagJobResult;
import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJobRequest;
import com.tencent.cos.xml.model.p033ci.media.SubmitVoiceSeparateJobResult;
import com.tencent.cos.xml.model.p033ci.media.TemplateAnimationRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplateAnimationResult;
import com.tencent.cos.xml.model.p033ci.media.TemplateConcatRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplateConcatResult;
import com.tencent.cos.xml.model.p033ci.media.TemplatePicProcessRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplatePicProcessResult;
import com.tencent.cos.xml.model.p033ci.media.TemplateSmartCoverRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplateSmartCoverResult;
import com.tencent.cos.xml.model.p033ci.media.TemplateSnapshotRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplateSnapshotResult;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscodeRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplateTranscodeResult;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontageRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplateVideoMontageResult;
import com.tencent.cos.xml.model.p033ci.media.TemplateVoiceSeparateRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplateVoiceSeparateResult;
import com.tencent.cos.xml.model.p033ci.media.TemplateWatermarkRequest;
import com.tencent.cos.xml.model.p033ci.media.TemplateWatermarkResult;
import com.tencent.cos.xml.model.p033ci.media.TriggerWorkflowRequest;
import com.tencent.cos.xml.model.p033ci.media.TriggerWorkflowResult;
import com.tencent.cos.xml.model.p033ci.media.UpdateMediaQueueRequest;
import com.tencent.cos.xml.model.p033ci.media.UpdateMediaQueueResult;
import com.tencent.cos.xml.model.p033ci.metainsight.CreateDatasetBindingRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.CreateDatasetBindingResult;
import com.tencent.cos.xml.model.p033ci.metainsight.CreateDatasetRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.CreateDatasetResult;
import com.tencent.cos.xml.model.p033ci.metainsight.CreateFileMetaIndexRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.CreateFileMetaIndexResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DatasetFaceSearchRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DatasetFaceSearchResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DatasetSimpleQueryRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DatasetSimpleQueryResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DeleteDatasetBindingRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DeleteDatasetBindingResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DeleteDatasetRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DeleteDatasetResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DeleteFileMetaIndexRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DeleteFileMetaIndexResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeDatasetBindingRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeDatasetBindingResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeDatasetBindingsRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeDatasetBindingsResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeDatasetRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeDatasetResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeDatasetsRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeDatasetsResult;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeFileMetaIndexRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.DescribeFileMetaIndexResult;
import com.tencent.cos.xml.model.p033ci.metainsight.SearchImageRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.SearchImageResult;
import com.tencent.cos.xml.model.p033ci.metainsight.UpdateDatasetRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.UpdateDatasetResult;
import com.tencent.cos.xml.model.p033ci.metainsight.UpdateFileMetaIndexRequest;
import com.tencent.cos.xml.model.p033ci.metainsight.UpdateFileMetaIndexResult;
import com.tencent.cos.xml.model.p033ci.p034ai.CloseAIBucketRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.CloseAIBucketResult;
import com.tencent.cos.xml.model.p033ci.p034ai.CloseAsrBucketRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.CloseAsrBucketResult;
import com.tencent.cos.xml.model.p033ci.p034ai.CreateWordsGeneralizeJobRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.CreateWordsGeneralizeJobResult;
import com.tencent.cos.xml.model.p033ci.p034ai.DescribeAiQueuesRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.DescribeAiQueuesResult;
import com.tencent.cos.xml.model.p033ci.p034ai.DescribeWordsGeneralizeJobRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.DescribeWordsGeneralizeJobResult;
import com.tencent.cos.xml.model.p033ci.p034ai.GetAIBucketRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.GetAIBucketResult;
import com.tencent.cos.xml.model.p033ci.p034ai.GetAIQueueRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.GetAIQueueResult;
import com.tencent.cos.xml.model.p033ci.p034ai.ImageSearchBucketRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.OpenAsrBucketRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.OpenAsrBucketResult;
import com.tencent.cos.xml.model.p033ci.p034ai.OpenBucketAiRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.OpenBucketAiResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReductionRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReductionResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReductionTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostNoiseReductionTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBodyRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSegmentVideoBodyResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHoundRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSoundHoundResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSpeechRecognitionTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostSpeechRecognitionTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostTranslationRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostTranslationResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetRecRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetRecResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVideoTargetTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesisRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesisResult;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesisTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.PostVoiceSynthesisTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateAIQueueRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateAIQueueResult;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateAsrQueueRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateAsrQueueResult;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateNoiseReductionTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateNoiseReductionTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateSpeechRecognitionTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateSpeechRecognitionTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateVideoTargetTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateVideoTargetTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateVoiceSeparateTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateVoiceSeparateTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateVoiceSynthesisTempleteRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.UpdateVoiceSynthesisTempleteResult;
import com.tencent.cos.xml.model.p033ci.p034ai.VocalScoreRequest;
import com.tencent.cos.xml.model.p033ci.p034ai.VocalScoreResult;
import com.tencent.qcloud.core.auth.COSXmlSigner;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.SignerFactory;

/* JADX INFO: loaded from: classes4.dex */
public class CIService extends CosXmlService {
    public static final String SIGNERTYPE_CISIGNER = "CISigner";

    private static class CISigner extends COSXmlSigner {
        private CISigner() {
        }

        @Override // com.tencent.qcloud.core.auth.COSXmlSigner
        protected String getSessionTokenKey() {
            return "x-ci-security-token";
        }
    }

    public CIService(Context context, CosXmlServiceConfig cosXmlServiceConfig, QCloudCredentialProvider qCloudCredentialProvider) {
        super(context, cosXmlServiceConfig, qCloudCredentialProvider);
        this.signerType = SIGNERTYPE_CISIGNER;
        SignerFactory.registerSigner(this.signerType, new CISigner());
    }

    @Override // com.tencent.cos.xml.CosXmlService, com.tencent.cos.xml.CosXmlBaseService
    protected String signerTypeCompat(String str, CosXmlRequest cosXmlRequest) {
        return (((cosXmlRequest instanceof SensitiveContentRecognitionRequest) || (cosXmlRequest instanceof QRCodeUploadRequest)) && SIGNERTYPE_CISIGNER.equals(str)) ? "CosXmlSigner" : str;
    }

    public DescribeDocProcessBucketsResult describeDocProcessBuckets(DescribeDocProcessBucketsRequest describeDocProcessBucketsRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeDocProcessBucketsResult) execute(describeDocProcessBucketsRequest, new DescribeDocProcessBucketsResult());
    }

    public void describeDocProcessBucketsAsync(DescribeDocProcessBucketsRequest describeDocProcessBucketsRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeDocProcessBucketsRequest, new DescribeDocProcessBucketsResult(), cosXmlResultListener);
    }

    public PutBucketDPStateResult putBucketDocumentPreviewState(PutBucketDPStateRequest putBucketDPStateRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PutBucketDPStateResult) execute(putBucketDPStateRequest, new PutBucketDPStateResult());
    }

    public void putBucketDocumentPreviewStateAsync(PutBucketDPStateRequest putBucketDPStateRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(putBucketDPStateRequest, new PutBucketDPStateResult(), cosXmlResultListener);
    }

    public DeleteBucketDPStateResult deleteBucketDocumentPreviewState(DeleteBucketDPStateRequest deleteBucketDPStateRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DeleteBucketDPStateResult) execute(deleteBucketDPStateRequest, new DeleteBucketDPStateResult());
    }

    public void deleteBucketDocumentPreviewStateAsync(DeleteBucketDPStateRequest deleteBucketDPStateRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(deleteBucketDPStateRequest, new DeleteBucketDPStateResult(), cosXmlResultListener);
    }

    public QRCodeUploadResult qrCodeUpload(QRCodeUploadRequest qRCodeUploadRequest) throws CosXmlServiceException, CosXmlClientException {
        return (QRCodeUploadResult) execute(qRCodeUploadRequest, new QRCodeUploadResult());
    }

    public void qrCodeUploadAsync(QRCodeUploadRequest qRCodeUploadRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(qRCodeUploadRequest, new QRCodeUploadResult(), cosXmlResultListener);
    }

    public SensitiveContentRecognitionResult sensitiveContentRecognition(SensitiveContentRecognitionRequest sensitiveContentRecognitionRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SensitiveContentRecognitionResult) execute(sensitiveContentRecognitionRequest, new SensitiveContentRecognitionResult());
    }

    public void sensitiveContentRecognitionAsync(SensitiveContentRecognitionRequest sensitiveContentRecognitionRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(sensitiveContentRecognitionRequest, new SensitiveContentRecognitionResult(), cosXmlResultListener);
    }

    public PostImagesAuditResult postImagesAudit(PostImagesAuditRequest postImagesAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostImagesAuditResult) execute(postImagesAuditRequest, new PostImagesAuditResult());
    }

    public void postImagesAuditAsync(PostImagesAuditRequest postImagesAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postImagesAuditRequest, new PostImagesAuditResult(), cosXmlResultListener);
    }

    public GetImageAuditResult getImageAudit(GetImageAuditRequest getImageAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetImageAuditResult) execute(getImageAuditRequest, new GetImageAuditResult());
    }

    public void getImageAuditAsync(GetImageAuditRequest getImageAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getImageAuditRequest, new GetImageAuditResult(), cosXmlResultListener);
    }

    public PostAuditResult postVideoAudit(PostVideoAuditRequest postVideoAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostAuditResult) execute(postVideoAuditRequest, new PostAuditResult());
    }

    public void postVideoAuditAsync(PostVideoAuditRequest postVideoAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postVideoAuditRequest, new PostAuditResult(), cosXmlResultListener);
    }

    public GetVideoAuditResult getVideoAudit(GetVideoAuditRequest getVideoAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetVideoAuditResult) execute(getVideoAuditRequest, new GetVideoAuditResult());
    }

    public void getVideoAuditAsync(GetVideoAuditRequest getVideoAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getVideoAuditRequest, new GetVideoAuditResult(), cosXmlResultListener);
    }

    public PostAuditResult postAudioAudit(PostAudioAuditRequest postAudioAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostAuditResult) execute(postAudioAuditRequest, new PostAuditResult());
    }

    public void postAudioAuditAsync(PostAudioAuditRequest postAudioAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postAudioAuditRequest, new PostAuditResult(), cosXmlResultListener);
    }

    public GetAudioAuditResult getAudioAudit(GetAudioAuditRequest getAudioAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetAudioAuditResult) execute(getAudioAuditRequest, new GetAudioAuditResult());
    }

    public void getAudioAuditAsync(GetAudioAuditRequest getAudioAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getAudioAuditRequest, new GetAudioAuditResult(), cosXmlResultListener);
    }

    public TextAuditResult postTextAudit(PostTextAuditRequest postTextAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TextAuditResult) execute(postTextAuditRequest, new TextAuditResult());
    }

    public void postTextAuditAsync(PostTextAuditRequest postTextAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postTextAuditRequest, new TextAuditResult(), cosXmlResultListener);
    }

    public TextAuditResult getTextAudit(GetTextAuditRequest getTextAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TextAuditResult) execute(getTextAuditRequest, new TextAuditResult());
    }

    public void getTextAuditAsync(GetTextAuditRequest getTextAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getTextAuditRequest, new TextAuditResult(), cosXmlResultListener);
    }

    public PostAuditResult postDocumentAudit(PostDocumentAuditRequest postDocumentAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostAuditResult) execute(postDocumentAuditRequest, new PostAuditResult());
    }

    public void postDocumentAuditAsync(PostDocumentAuditRequest postDocumentAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postDocumentAuditRequest, new PostAuditResult(), cosXmlResultListener);
    }

    public GetDocumentAuditResult getDocumentAudit(GetDocumentAuditRequest getDocumentAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetDocumentAuditResult) execute(getDocumentAuditRequest, new GetDocumentAuditResult());
    }

    public void getDocumentAuditAsync(GetDocumentAuditRequest getDocumentAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getDocumentAuditRequest, new GetDocumentAuditResult(), cosXmlResultListener);
    }

    public PostAuditResult postWebPageAudit(PostWebPageAuditRequest postWebPageAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostAuditResult) execute(postWebPageAuditRequest, new PostAuditResult());
    }

    public void postWebPageAuditAsync(PostWebPageAuditRequest postWebPageAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postWebPageAuditRequest, new PostAuditResult(), cosXmlResultListener);
    }

    public GetWebPageAuditResult getWebPageAudit(GetWebPageAuditRequest getWebPageAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetWebPageAuditResult) execute(getWebPageAuditRequest, new GetWebPageAuditResult());
    }

    public void getWebPageAuditAsync(GetWebPageAuditRequest getWebPageAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getWebPageAuditRequest, new GetWebPageAuditResult(), cosXmlResultListener);
    }

    public DescribeSpeechBucketsResult describeSpeechBuckets(DescribeSpeechBucketsRequest describeSpeechBucketsRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeSpeechBucketsResult) execute(describeSpeechBucketsRequest, new DescribeSpeechBucketsResult());
    }

    public void describeSpeechBucketsAsync(DescribeSpeechBucketsRequest describeSpeechBucketsRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeSpeechBucketsRequest, new DescribeSpeechBucketsResult(), cosXmlResultListener);
    }

    public DescribeSpeechQueuesResult describeSpeechQueues(DescribeSpeechQueuesRequest describeSpeechQueuesRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeSpeechQueuesResult) execute(describeSpeechQueuesRequest, new DescribeSpeechQueuesResult());
    }

    public void describeSpeechQueuesAsync(DescribeSpeechQueuesRequest describeSpeechQueuesRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeSpeechQueuesRequest, new DescribeSpeechQueuesResult(), cosXmlResultListener);
    }

    public CreateSpeechJobsResult createSpeechJobs(CreateSpeechJobsRequest createSpeechJobsRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CreateSpeechJobsResult) execute(createSpeechJobsRequest, new CreateSpeechJobsResult());
    }

    public void createSpeechJobsAsync(CreateSpeechJobsRequest createSpeechJobsRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(createSpeechJobsRequest, new CreateSpeechJobsResult(), cosXmlResultListener);
    }

    public DescribeSpeechJobResult describeSpeechJob(DescribeSpeechJobRequest describeSpeechJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeSpeechJobResult) execute(describeSpeechJobRequest, new DescribeSpeechJobResult());
    }

    public void describeSpeechJobAsync(DescribeSpeechJobRequest describeSpeechJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeSpeechJobRequest, new DescribeSpeechJobResult(), cosXmlResultListener);
    }

    public DescribeSpeechJobsResult describeSpeechJobs(DescribeSpeechJobsRequest describeSpeechJobsRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeSpeechJobsResult) execute(describeSpeechJobsRequest, new DescribeSpeechJobsResult());
    }

    public void describeSpeechJobsAsync(DescribeSpeechJobsRequest describeSpeechJobsRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeSpeechJobsRequest, new DescribeSpeechJobsResult(), cosXmlResultListener);
    }

    public OpenBucketAiResult openBucketAi(OpenBucketAiRequest openBucketAiRequest) throws CosXmlServiceException, CosXmlClientException {
        return (OpenBucketAiResult) execute(openBucketAiRequest, new OpenBucketAiResult());
    }

    public void openBucketAiAsync(OpenBucketAiRequest openBucketAiRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(openBucketAiRequest, new OpenBucketAiResult(), cosXmlResultListener);
    }

    public DescribeAiQueuesResult describeAiQueues(DescribeAiQueuesRequest describeAiQueuesRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeAiQueuesResult) execute(describeAiQueuesRequest, new DescribeAiQueuesResult());
    }

    public void describeAiQueuesAsync(DescribeAiQueuesRequest describeAiQueuesRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeAiQueuesRequest, new DescribeAiQueuesResult(), cosXmlResultListener);
    }

    public CreateWordsGeneralizeJobResult createWordsGeneralizeJob(CreateWordsGeneralizeJobRequest createWordsGeneralizeJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CreateWordsGeneralizeJobResult) execute(createWordsGeneralizeJobRequest, new CreateWordsGeneralizeJobResult());
    }

    public void createWordsGeneralizeJobAsync(CreateWordsGeneralizeJobRequest createWordsGeneralizeJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(createWordsGeneralizeJobRequest, new CreateWordsGeneralizeJobResult(), cosXmlResultListener);
    }

    public DescribeWordsGeneralizeJobResult describeWordsGeneralizeJob(DescribeWordsGeneralizeJobRequest describeWordsGeneralizeJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeWordsGeneralizeJobResult) execute(describeWordsGeneralizeJobRequest, new DescribeWordsGeneralizeJobResult());
    }

    public void describeWordsGeneralizeJobAsync(DescribeWordsGeneralizeJobRequest describeWordsGeneralizeJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeWordsGeneralizeJobRequest, new DescribeWordsGeneralizeJobResult(), cosXmlResultListener);
    }

    public TriggerWorkflowResult triggerWorkflow(TriggerWorkflowRequest triggerWorkflowRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TriggerWorkflowResult) execute(triggerWorkflowRequest, new TriggerWorkflowResult());
    }

    public void triggerWorkflowAsync(TriggerWorkflowRequest triggerWorkflowRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(triggerWorkflowRequest, new TriggerWorkflowResult(), cosXmlResultListener);
    }

    public SubmitExtractDigitalWatermarkJobResult submitExtractDigitalWatermarkJob(SubmitExtractDigitalWatermarkJobRequest submitExtractDigitalWatermarkJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitExtractDigitalWatermarkJobResult) execute(submitExtractDigitalWatermarkJobRequest, new SubmitExtractDigitalWatermarkJobResult());
    }

    public void submitExtractDigitalWatermarkJobAsync(SubmitExtractDigitalWatermarkJobRequest submitExtractDigitalWatermarkJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitExtractDigitalWatermarkJobRequest, new SubmitExtractDigitalWatermarkJobResult(), cosXmlResultListener);
    }

    public UpdateMediaQueueResult updateMediaQueue(UpdateMediaQueueRequest updateMediaQueueRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateMediaQueueResult) execute(updateMediaQueueRequest, new UpdateMediaQueueResult());
    }

    public void updateMediaQueueAsync(UpdateMediaQueueRequest updateMediaQueueRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateMediaQueueRequest, new UpdateMediaQueueResult(), cosXmlResultListener);
    }

    public SubmitAnimationJobResult submitAnimationJob(SubmitAnimationJobRequest submitAnimationJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitAnimationJobResult) execute(submitAnimationJobRequest, new SubmitAnimationJobResult());
    }

    public void submitAnimationJobAsync(SubmitAnimationJobRequest submitAnimationJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitAnimationJobRequest, new SubmitAnimationJobResult(), cosXmlResultListener);
    }

    public SearchMediaQueueResult searchMediaQueue(SearchMediaQueueRequest searchMediaQueueRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SearchMediaQueueResult) execute(searchMediaQueueRequest, new SearchMediaQueueResult());
    }

    public void searchMediaQueueAsync(SearchMediaQueueRequest searchMediaQueueRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(searchMediaQueueRequest, new SearchMediaQueueResult(), cosXmlResultListener);
    }

    public SubmitVideoTagJobResult submitVideoTagJob(SubmitVideoTagJobRequest submitVideoTagJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitVideoTagJobResult) execute(submitVideoTagJobRequest, new SubmitVideoTagJobResult());
    }

    public void submitVideoTagJobAsync(SubmitVideoTagJobRequest submitVideoTagJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitVideoTagJobRequest, new SubmitVideoTagJobResult(), cosXmlResultListener);
    }

    public SubmitMediaSegmentJobResult submitMediaSegmentJob(SubmitMediaSegmentJobRequest submitMediaSegmentJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitMediaSegmentJobResult) execute(submitMediaSegmentJobRequest, new SubmitMediaSegmentJobResult());
    }

    public void submitMediaSegmentJobAsync(SubmitMediaSegmentJobRequest submitMediaSegmentJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitMediaSegmentJobRequest, new SubmitMediaSegmentJobResult(), cosXmlResultListener);
    }

    public SubmitSmartCoverJobResult submitSmartCoverJob(SubmitSmartCoverJobRequest submitSmartCoverJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitSmartCoverJobResult) execute(submitSmartCoverJobRequest, new SubmitSmartCoverJobResult());
    }

    public void submitSmartCoverJobAsync(SubmitSmartCoverJobRequest submitSmartCoverJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitSmartCoverJobRequest, new SubmitSmartCoverJobResult(), cosXmlResultListener);
    }

    public SubmitSnapshotJobResult submitSnapshotJob(SubmitSnapshotJobRequest submitSnapshotJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitSnapshotJobResult) execute(submitSnapshotJobRequest, new SubmitSnapshotJobResult());
    }

    public void submitSnapshotJobAsync(SubmitSnapshotJobRequest submitSnapshotJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitSnapshotJobRequest, new SubmitSnapshotJobResult(), cosXmlResultListener);
    }

    public SubmitMediaInfoJobResult submitMediaInfoJob(SubmitMediaInfoJobRequest submitMediaInfoJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitMediaInfoJobResult) execute(submitMediaInfoJobRequest, new SubmitMediaInfoJobResult());
    }

    public void submitMediaInfoJobAsync(SubmitMediaInfoJobRequest submitMediaInfoJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitMediaInfoJobRequest, new SubmitMediaInfoJobResult(), cosXmlResultListener);
    }

    public SubmitConcatJobResult submitConcatJob(SubmitConcatJobRequest submitConcatJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitConcatJobResult) execute(submitConcatJobRequest, new SubmitConcatJobResult());
    }

    public void submitConcatJobAsync(SubmitConcatJobRequest submitConcatJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitConcatJobRequest, new SubmitConcatJobResult(), cosXmlResultListener);
    }

    public SubmitDigitalWatermarkJobResult submitDigitalWatermarkJob(SubmitDigitalWatermarkJobRequest submitDigitalWatermarkJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitDigitalWatermarkJobResult) execute(submitDigitalWatermarkJobRequest, new SubmitDigitalWatermarkJobResult());
    }

    public void submitDigitalWatermarkJobAsync(SubmitDigitalWatermarkJobRequest submitDigitalWatermarkJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitDigitalWatermarkJobRequest, new SubmitDigitalWatermarkJobResult(), cosXmlResultListener);
    }

    public SubmitVideoMontageJobResult submitVideoMontageJob(SubmitVideoMontageJobRequest submitVideoMontageJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitVideoMontageJobResult) execute(submitVideoMontageJobRequest, new SubmitVideoMontageJobResult());
    }

    public void submitVideoMontageJobAsync(SubmitVideoMontageJobRequest submitVideoMontageJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitVideoMontageJobRequest, new SubmitVideoMontageJobResult(), cosXmlResultListener);
    }

    public SubmitVoiceSeparateJobResult submitVoiceSeparateJob(SubmitVoiceSeparateJobRequest submitVoiceSeparateJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitVoiceSeparateJobResult) execute(submitVoiceSeparateJobRequest, new SubmitVoiceSeparateJobResult());
    }

    public void submitVoiceSeparateJobAsync(SubmitVoiceSeparateJobRequest submitVoiceSeparateJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitVoiceSeparateJobRequest, new SubmitVoiceSeparateJobResult(), cosXmlResultListener);
    }

    public SubmitPicProcessJobResult submitPicProcessJob(SubmitPicProcessJobRequest submitPicProcessJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitPicProcessJobResult) execute(submitPicProcessJobRequest, new SubmitPicProcessJobResult());
    }

    public void submitPicProcessJobAsync(SubmitPicProcessJobRequest submitPicProcessJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitPicProcessJobRequest, new SubmitPicProcessJobResult(), cosXmlResultListener);
    }

    public GetWorkflowListResult getWorkflowList(GetWorkflowListRequest getWorkflowListRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetWorkflowListResult) execute(getWorkflowListRequest, new GetWorkflowListResult());
    }

    public void getWorkflowListAsync(GetWorkflowListRequest getWorkflowListRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getWorkflowListRequest, new GetWorkflowListResult(), cosXmlResultListener);
    }

    public SubmitTranscodeJobResult submitTranscodeJob(SubmitTranscodeJobRequest submitTranscodeJobRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SubmitTranscodeJobResult) execute(submitTranscodeJobRequest, new SubmitTranscodeJobResult());
    }

    public void submitTranscodeJobAsync(SubmitTranscodeJobRequest submitTranscodeJobRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(submitTranscodeJobRequest, new SubmitTranscodeJobResult(), cosXmlResultListener);
    }

    public CancelLiveVideoAuditResult cancelLiveVideoAudit(CancelLiveVideoAuditRequest cancelLiveVideoAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CancelLiveVideoAuditResult) execute(cancelLiveVideoAuditRequest, new CancelLiveVideoAuditResult());
    }

    public void cancelLiveVideoAuditAsync(CancelLiveVideoAuditRequest cancelLiveVideoAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(cancelLiveVideoAuditRequest, new CancelLiveVideoAuditResult(), cosXmlResultListener);
    }

    public PostLiveVideoAuditResult postLiveVideoAudit(PostLiveVideoAuditRequest postLiveVideoAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostLiveVideoAuditResult) execute(postLiveVideoAuditRequest, new PostLiveVideoAuditResult());
    }

    public void postLiveVideoAuditAsync(PostLiveVideoAuditRequest postLiveVideoAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postLiveVideoAuditRequest, new PostLiveVideoAuditResult(), cosXmlResultListener);
    }

    public GetLiveVideoAuditResult getLiveVideoAudit(GetLiveVideoAuditRequest getLiveVideoAuditRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetLiveVideoAuditResult) execute(getLiveVideoAuditRequest, new GetLiveVideoAuditResult());
    }

    public void getLiveVideoAuditAsync(GetLiveVideoAuditRequest getLiveVideoAuditRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getLiveVideoAuditRequest, new GetLiveVideoAuditResult(), cosXmlResultListener);
    }

    public PostImageAuditReportResult postImageAuditReport(PostImageAuditReportRequest postImageAuditReportRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostImageAuditReportResult) execute(postImageAuditReportRequest, new PostImageAuditReportResult());
    }

    public void postImageAuditReportAsync(PostImageAuditReportRequest postImageAuditReportRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postImageAuditReportRequest, new PostImageAuditReportResult(), cosXmlResultListener);
    }

    public PostTextAuditReportResult postTextAuditReport(PostTextAuditReportRequest postTextAuditReportRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostTextAuditReportResult) execute(postTextAuditReportRequest, new PostTextAuditReportResult());
    }

    public void postTextAuditReportAsync(PostTextAuditReportRequest postTextAuditReportRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postTextAuditReportRequest, new PostTextAuditReportResult(), cosXmlResultListener);
    }

    public GetStrategyListResult getAuditStrategyList(GetStrategyListRequest getStrategyListRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetStrategyListResult) execute(getStrategyListRequest, new GetStrategyListResult());
    }

    public void getAuditStrategyListAsync(GetStrategyListRequest getStrategyListRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getStrategyListRequest, new GetStrategyListResult(), cosXmlResultListener);
    }

    public GetStrategyDetailResult getAuditStrategyDetail(GetStrategyDetailRequest getStrategyDetailRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetStrategyDetailResult) execute(getStrategyDetailRequest, new GetStrategyDetailResult());
    }

    public void getAuditStrategyDetailAsync(GetStrategyDetailRequest getStrategyDetailRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getStrategyDetailRequest, new GetStrategyDetailResult(), cosXmlResultListener);
    }

    public CreateStrategyResult createAuditStrategy(CreateStrategyRequest createStrategyRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CreateStrategyResult) execute(createStrategyRequest, new CreateStrategyResult());
    }

    public void createAuditStrategyAsync(CreateStrategyRequest createStrategyRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(createStrategyRequest, new CreateStrategyResult(), cosXmlResultListener);
    }

    public UpdateStrategyResult updateAuditStrategy(UpdateStrategyRequest updateStrategyRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateStrategyResult) execute(updateStrategyRequest, new UpdateStrategyResult());
    }

    public void updateAuditStrategyAsync(UpdateStrategyRequest updateStrategyRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateStrategyRequest, new UpdateStrategyResult(), cosXmlResultListener);
    }

    public CreateAuditTextlibResult createAuditTextlib(CreateAuditTextlibRequest createAuditTextlibRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CreateAuditTextlibResult) execute(createAuditTextlibRequest, new CreateAuditTextlibResult());
    }

    public void createAuditTextlibAsync(CreateAuditTextlibRequest createAuditTextlibRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(createAuditTextlibRequest, new CreateAuditTextlibResult(), cosXmlResultListener);
    }

    public UpdateAuditTextlibResult updateAuditTextlib(UpdateAuditTextlibRequest updateAuditTextlibRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateAuditTextlibResult) execute(updateAuditTextlibRequest, new UpdateAuditTextlibResult());
    }

    public void updateAuditTextlibAsync(UpdateAuditTextlibRequest updateAuditTextlibRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateAuditTextlibRequest, new UpdateAuditTextlibResult(), cosXmlResultListener);
    }

    public DeleteAuditTextlibResult deleteAuditTextlib(DeleteAuditTextlibRequest deleteAuditTextlibRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DeleteAuditTextlibResult) execute(deleteAuditTextlibRequest, new DeleteAuditTextlibResult());
    }

    public void deleteAuditTextlibAsync(DeleteAuditTextlibRequest deleteAuditTextlibRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(deleteAuditTextlibRequest, new DeleteAuditTextlibResult(), cosXmlResultListener);
    }

    public GetAuditTextlibListResult getAuditTextlibList(GetAuditTextlibListRequest getAuditTextlibListRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetAuditTextlibListResult) execute(getAuditTextlibListRequest, new GetAuditTextlibListResult());
    }

    public void getAuditTextlibListAsync(GetAuditTextlibListRequest getAuditTextlibListRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getAuditTextlibListRequest, new GetAuditTextlibListResult(), cosXmlResultListener);
    }

    public AddAuditTextlibKeywordResult addAuditTextlibKeyword(AddAuditTextlibKeywordRequest addAuditTextlibKeywordRequest) throws CosXmlServiceException, CosXmlClientException {
        return (AddAuditTextlibKeywordResult) execute(addAuditTextlibKeywordRequest, new AddAuditTextlibKeywordResult());
    }

    public void addAuditTextlibKeywordAsync(AddAuditTextlibKeywordRequest addAuditTextlibKeywordRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(addAuditTextlibKeywordRequest, new AddAuditTextlibKeywordResult(), cosXmlResultListener);
    }

    public GetAuditTextlibKeywordListResult getAuditTextlibKeywordList(GetAuditTextlibKeywordListRequest getAuditTextlibKeywordListRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetAuditTextlibKeywordListResult) execute(getAuditTextlibKeywordListRequest, new GetAuditTextlibKeywordListResult());
    }

    public void getAuditTextlibKeywordListAsync(GetAuditTextlibKeywordListRequest getAuditTextlibKeywordListRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getAuditTextlibKeywordListRequest, new GetAuditTextlibKeywordListResult(), cosXmlResultListener);
    }

    public DeleteAuditTextlibKeywordResult deleteAuditTextlibKeyword(DeleteAuditTextlibKeywordRequest deleteAuditTextlibKeywordRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DeleteAuditTextlibKeywordResult) execute(deleteAuditTextlibKeywordRequest, new DeleteAuditTextlibKeywordResult());
    }

    public void deleteAuditTextlibKeywordAsync(DeleteAuditTextlibKeywordRequest deleteAuditTextlibKeywordRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(deleteAuditTextlibKeywordRequest, new DeleteAuditTextlibKeywordResult(), cosXmlResultListener);
    }

    public GetAIBucketResult getAIBucket(GetAIBucketRequest getAIBucketRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetAIBucketResult) execute(getAIBucketRequest, new GetAIBucketResult());
    }

    public void getAIBucketAsync(GetAIBucketRequest getAIBucketRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getAIBucketRequest, new GetAIBucketResult(), cosXmlResultListener);
    }

    public UpdateAIQueueResult updateAIQueue(UpdateAIQueueRequest updateAIQueueRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateAIQueueResult) execute(updateAIQueueRequest, new UpdateAIQueueResult());
    }

    public void updateAIQueueAsync(UpdateAIQueueRequest updateAIQueueRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateAIQueueRequest, new UpdateAIQueueResult(), cosXmlResultListener);
    }

    public CloseAIBucketResult closeAIBucket(CloseAIBucketRequest closeAIBucketRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CloseAIBucketResult) execute(closeAIBucketRequest, new CloseAIBucketResult());
    }

    public void closeAIBucketAsync(CloseAIBucketRequest closeAIBucketRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(closeAIBucketRequest, new CloseAIBucketResult(), cosXmlResultListener);
    }

    public EmptyResponseResult imageSearchBucket(ImageSearchBucketRequest imageSearchBucketRequest) throws CosXmlServiceException, CosXmlClientException {
        return (EmptyResponseResult) execute(imageSearchBucketRequest, new EmptyResponseResult());
    }

    public void imageSearchBucketAsync(ImageSearchBucketRequest imageSearchBucketRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(imageSearchBucketRequest, new EmptyResponseResult(), cosXmlResultListener);
    }

    public PostVideoTargetTempleteResult postVideoTargetTemplete(PostVideoTargetTempleteRequest postVideoTargetTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostVideoTargetTempleteResult) execute(postVideoTargetTempleteRequest, new PostVideoTargetTempleteResult());
    }

    public void postVideoTargetTempleteAsync(PostVideoTargetTempleteRequest postVideoTargetTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postVideoTargetTempleteRequest, new PostVideoTargetTempleteResult(), cosXmlResultListener);
    }

    public UpdateVideoTargetTempleteResult updateVideoTargetTemplete(UpdateVideoTargetTempleteRequest updateVideoTargetTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateVideoTargetTempleteResult) execute(updateVideoTargetTempleteRequest, new UpdateVideoTargetTempleteResult());
    }

    public void updateVideoTargetTempleteAsync(UpdateVideoTargetTempleteRequest updateVideoTargetTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateVideoTargetTempleteRequest, new UpdateVideoTargetTempleteResult(), cosXmlResultListener);
    }

    public PostSegmentVideoBodyResult postSegmentVideoBody(PostSegmentVideoBodyRequest postSegmentVideoBodyRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostSegmentVideoBodyResult) execute(postSegmentVideoBodyRequest, new PostSegmentVideoBodyResult());
    }

    public void postSegmentVideoBodyAsync(PostSegmentVideoBodyRequest postSegmentVideoBodyRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postSegmentVideoBodyRequest, new PostSegmentVideoBodyResult(), cosXmlResultListener);
    }

    public OpenAsrBucketResult openAsrBucket(OpenAsrBucketRequest openAsrBucketRequest) throws CosXmlServiceException, CosXmlClientException {
        return (OpenAsrBucketResult) execute(openAsrBucketRequest, new OpenAsrBucketResult());
    }

    public void openAsrBucketAsync(OpenAsrBucketRequest openAsrBucketRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(openAsrBucketRequest, new OpenAsrBucketResult(), cosXmlResultListener);
    }

    public CloseAsrBucketResult closeAsrBucket(CloseAsrBucketRequest closeAsrBucketRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CloseAsrBucketResult) execute(closeAsrBucketRequest, new CloseAsrBucketResult());
    }

    public void closeAsrBucketAsync(CloseAsrBucketRequest closeAsrBucketRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(closeAsrBucketRequest, new CloseAsrBucketResult(), cosXmlResultListener);
    }

    public GetAIQueueResult getAIQueue(GetAIQueueRequest getAIQueueRequest) throws CosXmlServiceException, CosXmlClientException {
        return (GetAIQueueResult) execute(getAIQueueRequest, new GetAIQueueResult());
    }

    public void getAIQueueAsync(GetAIQueueRequest getAIQueueRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(getAIQueueRequest, new GetAIQueueResult(), cosXmlResultListener);
    }

    public UpdateAsrQueueResult updateAsrQueue(UpdateAsrQueueRequest updateAsrQueueRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateAsrQueueResult) execute(updateAsrQueueRequest, new UpdateAsrQueueResult());
    }

    public void updateAsrQueueAsync(UpdateAsrQueueRequest updateAsrQueueRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateAsrQueueRequest, new UpdateAsrQueueResult(), cosXmlResultListener);
    }

    public UpdateVoiceSeparateTempleteResult updateVoiceSeparateTemplete(UpdateVoiceSeparateTempleteRequest updateVoiceSeparateTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateVoiceSeparateTempleteResult) execute(updateVoiceSeparateTempleteRequest, new UpdateVoiceSeparateTempleteResult());
    }

    public void updateVoiceSeparateTempleteAsync(UpdateVoiceSeparateTempleteRequest updateVoiceSeparateTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateVoiceSeparateTempleteRequest, new UpdateVoiceSeparateTempleteResult(), cosXmlResultListener);
    }

    public PostNoiseReductionResult postNoiseReduction(PostNoiseReductionRequest postNoiseReductionRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostNoiseReductionResult) execute(postNoiseReductionRequest, new PostNoiseReductionResult());
    }

    public void postNoiseReductionAsync(PostNoiseReductionRequest postNoiseReductionRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postNoiseReductionRequest, new PostNoiseReductionResult(), cosXmlResultListener);
    }

    public PostNoiseReductionTempleteResult postNoiseReductionTemplete(PostNoiseReductionTempleteRequest postNoiseReductionTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostNoiseReductionTempleteResult) execute(postNoiseReductionTempleteRequest, new PostNoiseReductionTempleteResult());
    }

    public void postNoiseReductionTempleteAsync(PostNoiseReductionTempleteRequest postNoiseReductionTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postNoiseReductionTempleteRequest, new PostNoiseReductionTempleteResult(), cosXmlResultListener);
    }

    public PostVoiceSynthesisResult postVoiceSynthesis(PostVoiceSynthesisRequest postVoiceSynthesisRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostVoiceSynthesisResult) execute(postVoiceSynthesisRequest, new PostVoiceSynthesisResult());
    }

    public void postVoiceSynthesisAsync(PostVoiceSynthesisRequest postVoiceSynthesisRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postVoiceSynthesisRequest, new PostVoiceSynthesisResult(), cosXmlResultListener);
    }

    public PostVoiceSynthesisTempleteResult postVoiceSynthesisTemplete(PostVoiceSynthesisTempleteRequest postVoiceSynthesisTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostVoiceSynthesisTempleteResult) execute(postVoiceSynthesisTempleteRequest, new PostVoiceSynthesisTempleteResult());
    }

    public void postVoiceSynthesisTempleteAsync(PostVoiceSynthesisTempleteRequest postVoiceSynthesisTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postVoiceSynthesisTempleteRequest, new PostVoiceSynthesisTempleteResult(), cosXmlResultListener);
    }

    public UpdateVoiceSynthesisTempleteResult updateVoiceSynthesisTemplete(UpdateVoiceSynthesisTempleteRequest updateVoiceSynthesisTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateVoiceSynthesisTempleteResult) execute(updateVoiceSynthesisTempleteRequest, new UpdateVoiceSynthesisTempleteResult());
    }

    public void updateVoiceSynthesisTempleteAsync(UpdateVoiceSynthesisTempleteRequest updateVoiceSynthesisTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateVoiceSynthesisTempleteRequest, new UpdateVoiceSynthesisTempleteResult(), cosXmlResultListener);
    }

    public PostSpeechRecognitionTempleteResult postSpeechRecognitionTemplete(PostSpeechRecognitionTempleteRequest postSpeechRecognitionTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostSpeechRecognitionTempleteResult) execute(postSpeechRecognitionTempleteRequest, new PostSpeechRecognitionTempleteResult());
    }

    public void postSpeechRecognitionTempleteAsync(PostSpeechRecognitionTempleteRequest postSpeechRecognitionTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postSpeechRecognitionTempleteRequest, new PostSpeechRecognitionTempleteResult(), cosXmlResultListener);
    }

    public UpdateSpeechRecognitionTempleteResult updateSpeechRecognitionTemplete(UpdateSpeechRecognitionTempleteRequest updateSpeechRecognitionTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateSpeechRecognitionTempleteResult) execute(updateSpeechRecognitionTempleteRequest, new UpdateSpeechRecognitionTempleteResult());
    }

    public void updateSpeechRecognitionTempleteAsync(UpdateSpeechRecognitionTempleteRequest updateSpeechRecognitionTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateSpeechRecognitionTempleteRequest, new UpdateSpeechRecognitionTempleteResult(), cosXmlResultListener);
    }

    public PostSoundHoundResult postSoundHound(PostSoundHoundRequest postSoundHoundRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostSoundHoundResult) execute(postSoundHoundRequest, new PostSoundHoundResult());
    }

    public void postSoundHoundAsync(PostSoundHoundRequest postSoundHoundRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postSoundHoundRequest, new PostSoundHoundResult(), cosXmlResultListener);
    }

    public UpdateNoiseReductionTempleteResult updateNoiseReductionTemplete(UpdateNoiseReductionTempleteRequest updateNoiseReductionTempleteRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateNoiseReductionTempleteResult) execute(updateNoiseReductionTempleteRequest, new UpdateNoiseReductionTempleteResult());
    }

    public void updateNoiseReductionTempleteAsync(UpdateNoiseReductionTempleteRequest updateNoiseReductionTempleteRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateNoiseReductionTempleteRequest, new UpdateNoiseReductionTempleteResult(), cosXmlResultListener);
    }

    public PostVideoTargetRecResult postVideoTargetRec(PostVideoTargetRecRequest postVideoTargetRecRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostVideoTargetRecResult) execute(postVideoTargetRecRequest, new PostVideoTargetRecResult());
    }

    public void postVideoTargetRecAsync(PostVideoTargetRecRequest postVideoTargetRecRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postVideoTargetRecRequest, new PostVideoTargetRecResult(), cosXmlResultListener);
    }

    public TemplatePicProcessResult templatePicProcess(TemplatePicProcessRequest templatePicProcessRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplatePicProcessResult) execute(templatePicProcessRequest, new TemplatePicProcessResult());
    }

    public void templatePicProcessAsync(TemplatePicProcessRequest templatePicProcessRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templatePicProcessRequest, new TemplatePicProcessResult(), cosXmlResultListener);
    }

    public TemplateAnimationResult templateAnimation(TemplateAnimationRequest templateAnimationRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplateAnimationResult) execute(templateAnimationRequest, new TemplateAnimationResult());
    }

    public void templateAnimationAsync(TemplateAnimationRequest templateAnimationRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templateAnimationRequest, new TemplateAnimationResult(), cosXmlResultListener);
    }

    public TemplateSnapshotResult templateSnapshot(TemplateSnapshotRequest templateSnapshotRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplateSnapshotResult) execute(templateSnapshotRequest, new TemplateSnapshotResult());
    }

    public void templateSnapshotAsync(TemplateSnapshotRequest templateSnapshotRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templateSnapshotRequest, new TemplateSnapshotResult(), cosXmlResultListener);
    }

    public TemplateWatermarkResult templateWatermark(TemplateWatermarkRequest templateWatermarkRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplateWatermarkResult) execute(templateWatermarkRequest, new TemplateWatermarkResult());
    }

    public void templateWatermarkAsync(TemplateWatermarkRequest templateWatermarkRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templateWatermarkRequest, new TemplateWatermarkResult(), cosXmlResultListener);
    }

    public TemplateConcatResult templateConcat(TemplateConcatRequest templateConcatRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplateConcatResult) execute(templateConcatRequest, new TemplateConcatResult());
    }

    public void templateConcatAsync(TemplateConcatRequest templateConcatRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templateConcatRequest, new TemplateConcatResult(), cosXmlResultListener);
    }

    public TemplateVoiceSeparateResult templateVoiceSeparate(TemplateVoiceSeparateRequest templateVoiceSeparateRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplateVoiceSeparateResult) execute(templateVoiceSeparateRequest, new TemplateVoiceSeparateResult());
    }

    public void templateVoiceSeparateAsync(TemplateVoiceSeparateRequest templateVoiceSeparateRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templateVoiceSeparateRequest, new TemplateVoiceSeparateResult(), cosXmlResultListener);
    }

    public TemplateTranscodeResult templateTranscode(TemplateTranscodeRequest templateTranscodeRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplateTranscodeResult) execute(templateTranscodeRequest, new TemplateTranscodeResult());
    }

    public void templateTranscodeAsync(TemplateTranscodeRequest templateTranscodeRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templateTranscodeRequest, new TemplateTranscodeResult(), cosXmlResultListener);
    }

    public TemplateSmartCoverResult templateSmartCover(TemplateSmartCoverRequest templateSmartCoverRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplateSmartCoverResult) execute(templateSmartCoverRequest, new TemplateSmartCoverResult());
    }

    public void templateSmartCoverAsync(TemplateSmartCoverRequest templateSmartCoverRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templateSmartCoverRequest, new TemplateSmartCoverResult(), cosXmlResultListener);
    }

    public TemplateVideoMontageResult templateVideoMontage(TemplateVideoMontageRequest templateVideoMontageRequest) throws CosXmlServiceException, CosXmlClientException {
        return (TemplateVideoMontageResult) execute(templateVideoMontageRequest, new TemplateVideoMontageResult());
    }

    public void templateVideoMontageAsync(TemplateVideoMontageRequest templateVideoMontageRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(templateVideoMontageRequest, new TemplateVideoMontageResult(), cosXmlResultListener);
    }

    public CreateDatasetResult createDataset(CreateDatasetRequest createDatasetRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CreateDatasetResult) execute(createDatasetRequest, new CreateDatasetResult());
    }

    public void createDatasetAsync(CreateDatasetRequest createDatasetRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(createDatasetRequest, new CreateDatasetResult(), cosXmlResultListener);
    }

    public CreateDatasetBindingResult createDatasetBinding(CreateDatasetBindingRequest createDatasetBindingRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CreateDatasetBindingResult) execute(createDatasetBindingRequest, new CreateDatasetBindingResult());
    }

    public void createDatasetBindingAsync(CreateDatasetBindingRequest createDatasetBindingRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(createDatasetBindingRequest, new CreateDatasetBindingResult(), cosXmlResultListener);
    }

    public CreateFileMetaIndexResult createFileMetaIndex(CreateFileMetaIndexRequest createFileMetaIndexRequest) throws CosXmlServiceException, CosXmlClientException {
        return (CreateFileMetaIndexResult) execute(createFileMetaIndexRequest, new CreateFileMetaIndexResult());
    }

    public void createFileMetaIndexAsync(CreateFileMetaIndexRequest createFileMetaIndexRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(createFileMetaIndexRequest, new CreateFileMetaIndexResult(), cosXmlResultListener);
    }

    public DatasetFaceSearchResult datasetFaceSearch(DatasetFaceSearchRequest datasetFaceSearchRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DatasetFaceSearchResult) execute(datasetFaceSearchRequest, new DatasetFaceSearchResult());
    }

    public void datasetFaceSearchAsync(DatasetFaceSearchRequest datasetFaceSearchRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(datasetFaceSearchRequest, new DatasetFaceSearchResult(), cosXmlResultListener);
    }

    public DatasetSimpleQueryResult datasetSimpleQuery(DatasetSimpleQueryRequest datasetSimpleQueryRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DatasetSimpleQueryResult) execute(datasetSimpleQueryRequest, new DatasetSimpleQueryResult());
    }

    public void datasetSimpleQueryAsync(DatasetSimpleQueryRequest datasetSimpleQueryRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(datasetSimpleQueryRequest, new DatasetSimpleQueryResult(), cosXmlResultListener);
    }

    public DeleteDatasetResult deleteDataset(DeleteDatasetRequest deleteDatasetRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DeleteDatasetResult) execute(deleteDatasetRequest, new DeleteDatasetResult());
    }

    public void deleteDatasetAsync(DeleteDatasetRequest deleteDatasetRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(deleteDatasetRequest, new DeleteDatasetResult(), cosXmlResultListener);
    }

    public DeleteDatasetBindingResult deleteDatasetBinding(DeleteDatasetBindingRequest deleteDatasetBindingRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DeleteDatasetBindingResult) execute(deleteDatasetBindingRequest, new DeleteDatasetBindingResult());
    }

    public void deleteDatasetBindingAsync(DeleteDatasetBindingRequest deleteDatasetBindingRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(deleteDatasetBindingRequest, new DeleteDatasetBindingResult(), cosXmlResultListener);
    }

    public DeleteFileMetaIndexResult deleteFileMetaIndex(DeleteFileMetaIndexRequest deleteFileMetaIndexRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DeleteFileMetaIndexResult) execute(deleteFileMetaIndexRequest, new DeleteFileMetaIndexResult());
    }

    public void deleteFileMetaIndexAsync(DeleteFileMetaIndexRequest deleteFileMetaIndexRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(deleteFileMetaIndexRequest, new DeleteFileMetaIndexResult(), cosXmlResultListener);
    }

    public DescribeDatasetResult describeDataset(DescribeDatasetRequest describeDatasetRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeDatasetResult) execute(describeDatasetRequest, new DescribeDatasetResult());
    }

    public void describeDatasetAsync(DescribeDatasetRequest describeDatasetRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeDatasetRequest, new DescribeDatasetResult(), cosXmlResultListener);
    }

    public DescribeDatasetBindingResult describeDatasetBinding(DescribeDatasetBindingRequest describeDatasetBindingRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeDatasetBindingResult) execute(describeDatasetBindingRequest, new DescribeDatasetBindingResult());
    }

    public void describeDatasetBindingAsync(DescribeDatasetBindingRequest describeDatasetBindingRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeDatasetBindingRequest, new DescribeDatasetBindingResult(), cosXmlResultListener);
    }

    public DescribeDatasetBindingsResult describeDatasetBindings(DescribeDatasetBindingsRequest describeDatasetBindingsRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeDatasetBindingsResult) execute(describeDatasetBindingsRequest, new DescribeDatasetBindingsResult());
    }

    public void describeDatasetBindingsAsync(DescribeDatasetBindingsRequest describeDatasetBindingsRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeDatasetBindingsRequest, new DescribeDatasetBindingsResult(), cosXmlResultListener);
    }

    public DescribeDatasetsResult describeDatasets(DescribeDatasetsRequest describeDatasetsRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeDatasetsResult) execute(describeDatasetsRequest, new DescribeDatasetsResult());
    }

    public void describeDatasetsAsync(DescribeDatasetsRequest describeDatasetsRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeDatasetsRequest, new DescribeDatasetsResult(), cosXmlResultListener);
    }

    public DescribeFileMetaIndexResult describeFileMetaIndex(DescribeFileMetaIndexRequest describeFileMetaIndexRequest) throws CosXmlServiceException, CosXmlClientException {
        return (DescribeFileMetaIndexResult) execute(describeFileMetaIndexRequest, new DescribeFileMetaIndexResult());
    }

    public void describeFileMetaIndexAsync(DescribeFileMetaIndexRequest describeFileMetaIndexRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(describeFileMetaIndexRequest, new DescribeFileMetaIndexResult(), cosXmlResultListener);
    }

    public SearchImageResult searchImage(SearchImageRequest searchImageRequest) throws CosXmlServiceException, CosXmlClientException {
        return (SearchImageResult) execute(searchImageRequest, new SearchImageResult());
    }

    public void searchImageAsync(SearchImageRequest searchImageRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(searchImageRequest, new SearchImageResult(), cosXmlResultListener);
    }

    public UpdateDatasetResult updateDataset(UpdateDatasetRequest updateDatasetRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateDatasetResult) execute(updateDatasetRequest, new UpdateDatasetResult());
    }

    public void updateDatasetAsync(UpdateDatasetRequest updateDatasetRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateDatasetRequest, new UpdateDatasetResult(), cosXmlResultListener);
    }

    public UpdateFileMetaIndexResult updateFileMetaIndex(UpdateFileMetaIndexRequest updateFileMetaIndexRequest) throws CosXmlServiceException, CosXmlClientException {
        return (UpdateFileMetaIndexResult) execute(updateFileMetaIndexRequest, new UpdateFileMetaIndexResult());
    }

    public void updateFileMetaIndexAsync(UpdateFileMetaIndexRequest updateFileMetaIndexRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(updateFileMetaIndexRequest, new UpdateFileMetaIndexResult(), cosXmlResultListener);
    }

    public VocalScoreResult vocalScore(VocalScoreRequest vocalScoreRequest) throws CosXmlServiceException, CosXmlClientException {
        return (VocalScoreResult) execute(vocalScoreRequest, new VocalScoreResult());
    }

    public void vocalScoreAsync(VocalScoreRequest vocalScoreRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(vocalScoreRequest, new VocalScoreResult(), cosXmlResultListener);
    }

    public PostTranslationResult postTranslation(PostTranslationRequest postTranslationRequest) throws CosXmlServiceException, CosXmlClientException {
        return (PostTranslationResult) execute(postTranslationRequest, new PostTranslationResult());
    }

    public void postTranslationAsync(PostTranslationRequest postTranslationRequest, CosXmlResultListener cosXmlResultListener) {
        schedule(postTranslationRequest, new PostTranslationResult(), cosXmlResultListener);
    }
}
