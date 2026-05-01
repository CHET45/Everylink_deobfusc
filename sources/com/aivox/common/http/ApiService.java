package com.aivox.common.http;

import com.aivox.base.http.BaseResponse;
import com.aivox.common.model.BannerBean;
import com.aivox.common.model.Model;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/* JADX INFO: loaded from: classes.dex */
public interface ApiService {
    @POST("serviceVideoApp/videofile/deleteFile")
    Observable<BaseResponse<Model>> deleteVideoTxt(@Body RequestBody requestBody);

    @GET("serviceVideoApp/carousel/list")
    Observable<BaseResponse<List<BannerBean>>> getMineBanner();
}
