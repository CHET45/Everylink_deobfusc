package com.aivox.common.http;

import android.os.Build;
import androidx.exifinterface.media.ExifInterface;
import com.aivox.base.http.HttpCommonInterceptor;
import com.aivox.base.http.MyGsonConverterFactory;
import com.aivox.base.language.MultiLanguageUtil;
import com.aivox.base.util.BaseAppUtils;
import com.aivox.base.util.LogUtil;
import com.aivox.base.util.SPUtil;
import com.aivox.common.ble.service.CommonServiceUtils;
import com.aivox.common.model.DataHandle;
import com.aivox.common.util.StringUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lzy.okgo.model.HttpHeaders;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;

/* JADX INFO: loaded from: classes.dex */
public class RetrofitServiceManager {
    private static final int DEFAULT_CONNECT_TIME_OUT = 20;
    private static final int DEFAULT_READ_TIME_OUT = 40;
    private final Retrofit mRetrofit;

    private static class SingletonHolder {
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();

        private SingletonHolder() {
        }
    }

    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public RetrofitServiceManager() {
        this(false);
    }

    public RetrofitServiceManager(boolean z) {
        this.mRetrofit = new Retrofit.Builder().client(getOkHttpClient(z)).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(MyGsonConverterFactory.create()).baseUrl(StringUtil.getHttpUrl() + "/").build();
    }

    public OkHttpClient getOkHttpClient(boolean z) {
        OkHttpClient.Builder timeout = new OkHttpClient.Builder().connectionPool(new ConnectionPool(5, 10L, TimeUnit.SECONDS)).connectTimeout(z ? 60L : 20L, TimeUnit.SECONDS).writeTimeout(z ? 120L : 40L, TimeUnit.SECONDS).readTimeout(z ? 120L : 40L, TimeUnit.SECONDS);
        timeout.addInterceptor(buildCommonInterceptor());
        timeout.addInterceptor(new Interceptor() { // from class: com.aivox.common.http.RetrofitServiceManager.1
            @Override // okhttp3.Interceptor
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();
                String connectedDeviceName = DataHandle.getIns().hasConnectedBluetooth(false) ? CommonServiceUtils.getInstance().getConnectedDeviceName() : "";
                return chain.proceed(request.newBuilder().header("Device", connectedDeviceName != null ? connectedDeviceName : "").build());
            }
        });
        timeout.addInterceptor(new LogInterceptor());
        return timeout.build();
    }

    public HttpCommonInterceptor buildCommonInterceptor() {
        String str = (String) SPUtil.get(SPUtil.TOKEN, "");
        return new HttpCommonInterceptor.Builder().addHeaderParams(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, MultiLanguageUtil.getInstance().getLanguage()).addHeaderParams("AppVersion", BaseAppUtils.getVersionName()).addHeaderParams("SysVersion", Build.VERSION.RELEASE).addHeaderParams(ExifInterface.TAG_MODEL, Build.BRAND).addHeaderParams("MobileType", 1).addHeaderParams("Token", str).addHeaderParams("Refresh-Token", (String) SPUtil.get(SPUtil.REFRESH_TOKEN, "")).build();
    }

    public <T> T create(Class<T> cls) {
        return (T) this.mRetrofit.create(cls);
    }

    private class LogInterceptor implements Interceptor {
        private LogInterceptor() {
        }

        /* JADX WARN: Removed duplicated region for block: B:34:0x01be  */
        @Override // okhttp3.Interceptor
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public okhttp3.Response intercept(okhttp3.Interceptor.Chain r10) throws java.io.IOException {
            /*
                Method dump skipped, instruction units count: 482
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.aivox.common.http.RetrofitServiceManager.LogInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
        }
    }

    public static String unicodeToUTF_8(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            int i2 = i + 6;
            if (i2 < str.length() && cCharAt == '\\' && str.charAt(i + 1) == 'u') {
                try {
                    sb.append((char) Integer.parseInt(str.substring(i + 2, i2), 16));
                } catch (NumberFormatException e) {
                    e.fillInStackTrace();
                }
                i = i2;
            } else {
                sb.append(str.charAt(i));
                i++;
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String bodyToString(RequestBody requestBody) {
        try {
            Buffer buffer = new Buffer();
            if (requestBody != null) {
                requestBody.writeTo(buffer);
                return buffer.readUtf8();
            }
            return "";
        } catch (IOException unused) {
            return "did not work";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void printLog(String str) {
        LogUtil.m336e(str);
    }
}
