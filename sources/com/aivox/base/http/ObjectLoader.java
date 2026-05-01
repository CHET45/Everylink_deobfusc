package com.aivox.base.http;

import android.content.Context;
import com.aivox.base.httprx.exception.NoNetException;
import com.aivox.base.util.NetUtil;
import com.alibaba.fastjson.JSONObject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/* JADX INFO: loaded from: classes.dex */
public class ObjectLoader {
    public Context context;
    public HashMap<String, Object> map;

    public ObjectLoader(Context context) {
        this.context = context;
    }

    protected <T> Observable<T> observe(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.m1898io()).unsubscribeOn(Schedulers.m1898io()).observeOn(AndroidSchedulers.mainThread());
    }

    protected <T> Observable<T> observeCheckNet(Observable<T> observable) {
        return observable.takeUntil(checkNetConnect(this.context)).subscribeOn(Schedulers.m1898io()).unsubscribeOn(Schedulers.m1898io()).observeOn(AndroidSchedulers.mainThread());
    }

    public RequestBody getReqBody() {
        return stringToReqBody(new JSONObject(this.map).toString());
    }

    public RequestBody mapToReqBody(HashMap<String, Object> map) {
        return stringToReqBody(new JSONObject(map).toString());
    }

    public RequestBody stringToReqBody(String str) {
        NetUtil.isConnected(this.context, true);
        return RequestBody.create(MediaType.parse("application/json"), str);
    }

    private static Observable<Object> checkNetConnect(final Context context) {
        return new Observable<Object>() { // from class: com.aivox.base.http.ObjectLoader.1
            @Override // io.reactivex.Observable
            protected void subscribeActual(Observer<? super Object> observer) {
                if (NetUtil.isConnected(context)) {
                    return;
                }
                observer.onError(new NoNetException());
            }
        };
    }
}
