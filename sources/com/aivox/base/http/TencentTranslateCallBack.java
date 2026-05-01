package com.aivox.base.http;

import android.app.Activity;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.aivox.base.util.LogUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.lzy.okgo.callback.StringCallback;
import okhttp3.Call;
import okhttp3.Response;

/* JADX INFO: loaded from: classes.dex */
public abstract class TencentTranslateCallBack<T> extends StringCallback {
    private Activity activity;
    Class<T> clazz;

    public void onError() {
    }

    public abstract void onSuccess(T t);

    public TencentTranslateCallBack(Class<T> cls, Activity activity) {
        this.clazz = cls;
        this.activity = activity;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.lzy.okgo.callback.AbsCallback
    public void onSuccess(String str, Call call, Response response) {
        if (str != null) {
            Activity activity = this.activity;
            if (activity == null || activity.isFinishing() || this.activity.isDestroyed()) {
                return;
            }
            JSONObject object = JSON.parseObject(str);
            LogUtil.m338i("json:" + object.toJSONString());
            if (object.getInteger("ret").intValue() == 0) {
                onSuccess(new Gson().fromJson(str, (Class) this.clazz));
                return;
            } else {
                Toast.makeText(this.activity, object.getString(NotificationCompat.CATEGORY_MESSAGE), 0).show();
                onError();
                return;
            }
        }
        Toast.makeText(this.activity, "网络请求错误", 0).show();
        onError();
    }

    @Override // com.lzy.okgo.callback.AbsCallback
    public void onError(Call call, Response response, Exception exc) {
        super.onError(call, response, exc);
        Toast.makeText(this.activity, "网络错误", 0).show();
    }
}
