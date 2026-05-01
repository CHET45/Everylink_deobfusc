package com.aivox.common.http;

import com.aivox.base.http.BaseResponse;
import com.aivox.common.model.ActivityBean;
import com.aivox.common.model.AiChatBean;
import com.aivox.common.model.AiTitleBean;
import com.aivox.common.model.AudioInfoBean;
import com.aivox.common.model.AudioMarkBean;
import com.aivox.common.model.AudioNewAllListBean;
import com.aivox.common.model.DouBaoConfigBean;
import com.aivox.common.model.ExtraAudioBean;
import com.aivox.common.model.FolderBean;
import com.aivox.common.model.GlassOtaUpdateBean;
import com.aivox.common.model.NoticeBean;
import com.aivox.common.model.OpenAiConfigBean;
import com.aivox.common.model.OtaUpdateBean;
import com.aivox.common.model.OtherUserInfo;
import com.aivox.common.model.PayOrderResultBean;
import com.aivox.common.model.PricePackageList;
import com.aivox.common.model.PurchaseHistoryBean;
import com.aivox.common.model.ShareBean;
import com.aivox.common.model.TransKeyBean;
import com.aivox.common.model.Transcribe;
import com.aivox.common.model.TranscribeListBean;
import com.aivox.common_ui.update.UpdateBean;
import com.microsoft.azure.storage.table.TableConstants;
import io.reactivex.Observable;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/* JADX INFO: loaded from: classes.dex */
public interface IAudioService {
    public static final String PREFIX = "service-audio";

    @POST("service-audio/privilege/mac/convert")
    Observable<BaseResponse<Boolean>> activateDevice(@Body RequestBody requestBody);

    @POST("service-audio/audio/image/create")
    Observable<BaseResponse<Integer>> addImgTag(@Body RequestBody requestBody);

    @POST("service-audio/audio/mark")
    Observable<BaseResponse<Integer>> addNoteMark(@Body RequestBody requestBody);

    @POST("service-audio/audio/file/upload")
    Observable<BaseResponse<Integer>> addRecordFromLocal(@Body RequestBody requestBody);

    @POST("service-audio/tag/audio/v2/add")
    Observable<BaseResponse<Object>> addToFolder(@Body RequestBody requestBody);

    @DELETE("service-audio/asr/content/batch-delete/{audioTransId}")
    Observable<BaseResponse<Object>> batchDeleteContent(@Path("audioTransId") String str);

    @POST("service-audio/asr/content/batch-update")
    Observable<BaseResponse<List<Transcribe>>> batchEditContent(@Body RequestBody requestBody);

    @POST("service-audio/charge/cancel-subscription")
    Observable<BaseResponse<Object>> cancelSubscription(@Body RequestBody requestBody);

    @PATCH("service-audio/audio/file/speaker-avatar-style")
    Observable<BaseResponse<Object>> changeSpeakerAvatar(@Body RequestBody requestBody);

    @GET("service-audio/privilege/mac/available")
    Observable<BaseResponse<Boolean>> checkDeviceActivated(@Query("macAddress") String str);

    @GET("service-audio/system/glasses-ota")
    Observable<BaseResponse<GlassOtaUpdateBean>> checkGlassOtaUpdate(@Query("hardwareVersion") String str);

    @GET("service-audio/system/ota")
    Observable<BaseResponse<OtaUpdateBean>> checkOtaUpdate(@Query("hardwareVersion") String str, @Query("softwareVersion") double d);

    @GET("service-audio/system/apk")
    Observable<BaseResponse<UpdateBean>> checkUpdate(@Query(TableConstants.ErrorConstants.ERROR_EXCEPTION_TYPE) int i);

    @DELETE("service-audio/audio/file/v2/delete-trash/list")
    Observable<BaseResponse<Object>> clearRecycleBin();

    @PUT("service-audio/charge/close-payment")
    Observable<BaseResponse<Object>> closePayment(@Query("orderNo") String str, @Query("paymentType") int i);

    @POST("service-audio/tag/add")
    Observable<BaseResponse<Object>> createFolder(@Body RequestBody requestBody);

    @POST("service-audio/local/asr/init-audio")
    Observable<BaseResponse<Integer>> createLocalAudioInfo(@Body RequestBody requestBody);

    @POST("service-audio/charge/create-order")
    Observable<BaseResponse<String>> createOrder(@Body RequestBody requestBody);

    @DELETE("service-audio/audio/file/delete/{audioIds}")
    Observable<BaseResponse<Object>> delCloudFile(@Path("audioIds") String str);

    @DELETE("service-audio/audio/file/v2/delete-trash")
    Observable<BaseResponse<Object>> delRecycleFile(@Query("audioIds") int[] iArr);

    @DELETE("service-audio/ai/chat/delete-list")
    Observable<BaseResponse<Object>> deleteAiChatList(@Query("aiChatIds") List<Integer> list);

    @DELETE("service-audio/tag/delete/{tagId}")
    Observable<BaseResponse<Object>> deleteFolder(@Path("tagId") int i);

    @DELETE("service-audio/audio/image/delete/{id}")
    Observable<BaseResponse<Object>> deleteImgTag(@Path("id") int i);

    @DELETE("service-audio/audio/marks")
    Observable<BaseResponse<Object>> deleteNoteMark(@Query("markIds") List<Integer> list);

    @POST("service-audio/notify/delete")
    Observable<BaseResponse<Object>> deleteNotify(@Body RequestBody requestBody);

    @PATCH("service-audio/ai/chat")
    Observable<BaseResponse<Object>> editAiChat(@Body RequestBody requestBody);

    @PATCH("service-audio/audio/mark")
    Observable<BaseResponse<Object>> editNoteMark(@Body RequestBody requestBody);

    @PATCH("service-audio/audio/file/rename-record")
    Observable<BaseResponse<AudioInfoBean>> editRecordInfo(@Body RequestBody requestBody);

    @POST("service-audio/ai/title/generation-title")
    Observable<BaseResponse<Object>> generateAiTitleById(@Body RequestBody requestBody);

    @POST("service-audio/ai/summary/generation")
    Observable<BaseResponse<Integer>> generateByAi(@Body RequestBody requestBody);

    @GET("service-audio/system/get-activity-picture")
    Observable<BaseResponse<ActivityBean>> getActivityInfo();

    @GET("service-audio/ai/summary")
    Observable<BaseResponse<AiChatBean.Records>> getAiChatDetail(@Query("audioAiSummaryId") int i);

    @GET("service-audio/ai/summary/list")
    Observable<BaseResponse<AiChatBean>> getAiChatList(@Query("audioId") int i, @Query("page") int i2, @Query("pageSize") int i3);

    @GET("service-audio/ai/title/info")
    Observable<BaseResponse<AiTitleBean>> getAiTitleById(@Query("audioId") int i);

    @GET("service-audio/audio/all")
    Observable<BaseResponse<AudioNewAllListBean.DataBean>> getAllAudioList(@Query("page") int i, @Query("pageSize") int i2, @Query("searchContent") String str, @Query("sort") int i3);

    @GET("service-audio/notify/list")
    Observable<BaseResponse<NoticeBean>> getAllNewNotice(@Query("page") int i, @Query("pageSize") int i2, @Query("isRead") Integer num);

    @GET("service-audio/tag/audio/v2/asterisk")
    Observable<BaseResponse<AudioNewAllListBean.DataBean>> getAsteriskAudioList(@Query("page") int i, @Query("pageSize") int i2, @Query("sort") int i3);

    @GET("service-audio/audio/file/audio-list")
    Observable<BaseResponse<ExtraAudioBean>> getAudioListById(@Query("page") int i, @Query("pageSize") int i2, @Query("audioIdList") String[] strArr);

    @GET("service-audio/system/microsoft")
    Observable<BaseResponse<String>> getAzureInfo();

    @GET("service-audio/system/doubao")
    Observable<BaseResponse<DouBaoConfigBean>> getDouBaoConfig();

    @GET("service-audio/tag/list")
    Observable<BaseResponse<List<FolderBean>>> getFolderList();

    @GET("service-audio/audio/file/local-list")
    Observable<BaseResponse<List<AudioInfoBean>>> getLocalRecordList(@Query("id") List<Integer> list);

    @GET("service-audio/audio/marks")
    Observable<BaseResponse<List<AudioMarkBean>>> getNoteMarkList(@Query("audioId") int i);

    @GET("service-audio/system/open-api")
    Observable<BaseResponse<OpenAiConfigBean>> getOpenAiConfig();

    @GET("service-audio/other-user-info")
    Observable<BaseResponse<OtherUserInfo>> getOtherUserInfo(@Query("email") String str);

    @GET("service-audio/tag/audio/v2/personal")
    Observable<BaseResponse<AudioNewAllListBean.DataBean>> getPersonalAudioList(@Query("tagId") int i, @Query("page") int i2, @Query("pageSize") int i3, @Query("sort") int i4);

    @GET("service-audio/charge/products")
    Observable<BaseResponse<List<PricePackageList>>> getPricePackageList(@Query("category") int i, @Query("platform") int i2);

    @GET("service-audio/charge/order-list")
    Observable<BaseResponse<PurchaseHistoryBean>> getPurchaseHistoryList(@Query("page") int i, @Query("pageSize") int i2);

    @GET("service-audio/tag/audio/v2/recycle-bin")
    Observable<BaseResponse<AudioNewAllListBean.DataBean>> getRecycleAudioList(@Query("page") int i, @Query("pageSize") int i2, @Query("sort") int i3);

    @POST("service-audio/share/sign")
    Observable<BaseResponse<ShareBean>> getSignForAllShare(@Body RequestBody requestBody);

    @POST("service-audio/share/sign-app")
    Observable<BaseResponse<Object>> getSignForAppShare(@Body RequestBody requestBody);

    @GET("service-audio/system/tencent-and-ai-asr-key")
    Observable<BaseResponse<TransKeyBean>> getTencentAndAiKey(@Query("from") int i, @Query("to") int i2);

    @GET("service-audio/system/tencent-cos-temp-key")
    Observable<BaseResponse<String>> getTencentCosKey();

    @GET("service-audio/system/tencent-key")
    Observable<BaseResponse<TransKeyBean>> getTencentKey(@Query("from") int i, @Query("to") int i2);

    @GET("service-audio/asr/content")
    Observable<BaseResponse<TranscribeListBean>> getTransResultByPage(@Query("audioId") int i, @Query("page") int i2, @Query("pageSize") int i3);

    @GET("service-audio/audio/translation/status/{id}")
    Observable<BaseResponse<Integer>> getTransStatus(@Path("id") int i);

    @GET("service-audio/system/translation-config-info")
    Observable<BaseResponse<String>> getTranslateInfo();

    @GET
    Observable<BaseResponse<String>> glassApi(@Url String str);

    @Headers({"Content-Type: application/json"})
    @HTTP(hasBody = true, method = "DELETE")
    Observable<BaseResponse<String>> glassDeleteApi(@Url String str, @Body Map<String, Object> map);

    @POST
    @Multipart
    Observable<BaseResponse<String>> glassOtaApi(@Url String str, @Part MultipartBody.Part part);

    @POST("service-audio/system/glasses-sync")
    Observable<BaseResponse<Object>> glassSync(@Body RequestBody requestBody);

    @DELETE("service-audio/tag/audio/asterisk-list/delete/{audioIds}")
    Observable<BaseResponse<Object>> moveOutFromFavorite(@Path("audioIds") String str);

    @DELETE("service-audio/tag/audio/delete/{audioIds}")
    Observable<BaseResponse<Object>> moveOutFromFolder(@Path("audioIds") String str);

    @POST("service-audio/charge/pay-order")
    Observable<BaseResponse<PayOrderResultBean>> payOrder(@Body RequestBody requestBody);

    @GET("service-audio/audio/file/detail")
    Observable<BaseResponse<AudioInfoBean>> recordDetails(@Query("audioId") int i);

    @POST("service-audio/tag/update")
    Observable<BaseResponse<Object>> renameFolder(@Body RequestBody requestBody);

    @POST("service-audio/share/save-app")
    Observable<BaseResponse<Object>> saveAppShare(@Body RequestBody requestBody);

    @POST("service-audio/local/asr/save-content")
    Observable<BaseResponse<Object>> saveLocalAsrContent(@Body RequestBody requestBody);

    @PATCH("service-audio/local/asr/update-record/{audioId}")
    Observable<BaseResponse<AudioInfoBean>> saveLocalRecordInfo(@Path("audioId") int i, @Body RequestBody requestBody);

    @PATCH("service-audio/audio/file/update-record")
    Observable<BaseResponse<AudioInfoBean>> saveRecordInfo(@Body RequestBody requestBody);

    @GET("service-audio/asr/separate-progress/{audioId}")
    Observable<BaseResponse<Integer>> separateProgress(@Path("audioId") int i);

    @POST("service-audio/asr/auto-speech-recognition")
    Observable<BaseResponse<Object>> speechToText(@Body RequestBody requestBody);

    @PUT("service-audio/sync/local-to-cloud")
    Observable<BaseResponse<Object>> syncFile(@Body RequestBody requestBody);

    @PUT("service-audio/sync/recycle-to-cloud-batch")
    Observable<BaseResponse<Object>> syncFilesFromRecycle(@Body RequestBody requestBody);

    @GET("service-audio/asr/progress/{audioId}")
    Observable<BaseResponse<Integer>> transcribeProgress(@Path("audioId") int i);

    @POST("service-audio/audio/translation/full-text")
    Observable<BaseResponse<Object>> translateAll(@Body RequestBody requestBody);

    @PUT("service-audio/asr/content/update-speaker-name")
    Observable<BaseResponse<Object>> updateSpeakerName(@Body RequestBody requestBody);

    @POST("service-audio/charge/google-pay-verify")
    Observable<BaseResponse<Object>> verifyGooglePay(@Body RequestBody requestBody);
}
