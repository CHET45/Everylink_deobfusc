package com.aivox.common.http;

import android.content.Context;
import com.aivox.base.http.BaseResponse;
import com.aivox.base.http.ObjectLoader;
import com.aivox.base.http.PayLoad;
import com.aivox.common.model.Model;
import com.aivox.common.util.BaseUtil;
import io.reactivex.Observable;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class Api extends ObjectLoader {
    public ApiService apiService;

    public Api(Context context) {
        super(context);
    }

    private ApiService getApiService() {
        if (this.apiService == null) {
            this.apiService = BaseUtil.getApiService();
        }
        return this.apiService;
    }

    public Observable<Model> deleteVideoTxt(int i) {
        this.map = new HashMap<>();
        this.map.put("id", Integer.valueOf(i));
        return observe(getApiService().deleteVideoTxt(mapToReqBody(this.map))).map(new PayLoad<Model>() { // from class: com.aivox.common.http.Api.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.aivox.base.http.PayLoad, io.reactivex.functions.Function
            public Model apply(BaseResponse<Model> baseResponse) {
                return (Model) super.apply((BaseResponse) baseResponse);
            }
        });
    }
}
