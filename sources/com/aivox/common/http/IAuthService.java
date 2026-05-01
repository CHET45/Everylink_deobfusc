package com.aivox.common.http;

import com.aivox.base.http.BaseResponse;
import com.aivox.common.model.LoginBean;
import com.aivox.common.model.RefreshBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/* JADX INFO: loaded from: classes.dex */
public interface IAuthService {
    public static final String PREFIX = "auth-service/passport";

    @GET("auth-service/passport/email/check-exist")
    Observable<BaseResponse<Integer>> checkEmailExist(@Query("email") String str);

    @POST("auth-service/passport/app/email/login-by-sms")
    Observable<BaseResponse<LoginBean>> loginByCode(@Body RequestBody requestBody);

    @POST("auth-service/passport/app/google/login")
    Observable<BaseResponse<LoginBean>> loginByGoogle(@Body RequestBody requestBody);

    @POST("auth-service/passport/app/email/login-by-password")
    Observable<BaseResponse<LoginBean>> loginByPwd(@Body RequestBody requestBody);

    @POST("auth-service/passport/app/login-by-password")
    Observable<BaseResponse<LoginBean>> loginByPwdPhone(@Body RequestBody requestBody);

    @POST("auth-service/passport/app/login-by-sms")
    Observable<BaseResponse<LoginBean>> loginBySmS(@Body RequestBody requestBody);

    @POST("auth-service/passport/logoff")
    Observable<BaseResponse<Object>> logoff(@Body RequestBody requestBody);

    @POST("auth-service/passport/app/logout")
    Observable<BaseResponse<Object>> logout();

    @POST("auth-service/passport/refresh-token")
    Observable<BaseResponse<RefreshBean>> refreshToken();

    @POST("auth-service/passport/app/phone/sms")
    Observable<BaseResponse<Object>> sendPhoneSmsCode(@Body RequestBody requestBody);

    @POST("auth-service/passport/email/send-sms")
    Observable<BaseResponse<Object>> sendSmsCode(@Body RequestBody requestBody);
}
