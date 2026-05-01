package com.aivox.common.http;

import com.aivox.base.http.BaseResponse;
import com.aivox.common.model.AllTransTimeBean;
import com.aivox.common.model.DeviceRightsBean;
import com.aivox.common.model.FunctionRightsBean;
import com.aivox.common.model.IssuesTagBean;
import com.aivox.common.model.LeftTimeBean;
import com.aivox.common.model.PresetLanguageBean;
import com.aivox.common.model.UserInfo;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/* JADX INFO: loaded from: classes.dex */
public interface IUserService {
    public static final String PREFIX = "service-user/user";

    @POST("service-user/user/change-email")
    Observable<BaseResponse<Object>> changeEmail(@Body RequestBody requestBody);

    @POST("service-user/user/change-password")
    Observable<BaseResponse<Object>> changePwd(@Body RequestBody requestBody);

    @GET("service-user/user/check/cloud-size")
    Observable<BaseResponse<Boolean>> checkCloudSpace(@Query("fileSize") long j);

    @POST("service-user/user/password")
    Observable<BaseResponse<Object>> createPwd(@Body RequestBody requestBody);

    @POST("service-user/user/forget-password")
    Observable<BaseResponse<Object>> forgetPwd(@Body RequestBody requestBody);

    @GET("service-user/user/all-record-time-list")
    Observable<BaseResponse<AllTransTimeBean>> getAllTransTimeList();

    @GET("service-user/user/user-equity")
    Observable<BaseResponse<List<DeviceRightsBean>>> getDeviceRights();

    @GET("service-user/user/function-equity")
    Observable<BaseResponse<FunctionRightsBean>> getFunctionRights();

    @GET("service-user/user/issue-type-list")
    Observable<BaseResponse<List<IssuesTagBean>>> getIssuesTagList();

    @GET("service-user/user/left-time")
    Observable<BaseResponse<LeftTimeBean>> getLeftTime();

    @GET("service-user/user/default-language")
    Observable<BaseResponse<PresetLanguageBean>> getPresetLanguage();

    @GET("service-user/user/info")
    Observable<BaseResponse<UserInfo>> getUserInfo();

    @POST("service-user/user/save-notify-email")
    Observable<BaseResponse<Object>> saveNotifyEmail(@Body RequestBody requestBody);

    @POST("service-user/user/asr-language")
    Observable<BaseResponse<PresetLanguageBean>> setAsrLanguage(@Body RequestBody requestBody);

    @POST("service-user/user/translate-language")
    Observable<BaseResponse<PresetLanguageBean>> setTranslateLanguage(@Body RequestBody requestBody);

    @POST("service-user/user/info")
    Observable<BaseResponse<UserInfo>> setUserInfo(@Body RequestBody requestBody);

    @POST("service-user/user/feedback")
    Observable<BaseResponse<Object>> userFeedback(@Body RequestBody requestBody);
}
