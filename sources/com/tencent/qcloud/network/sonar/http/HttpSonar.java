package com.tencent.qcloud.network.sonar.http;

import android.text.TextUtils;
import com.aivox.common.socket.WebSocketHandler;
import com.tencent.qcloud.network.sonar.Sonar;
import com.tencent.qcloud.network.sonar.SonarRequest;
import com.tencent.qcloud.network.sonar.SonarResult;
import com.tencent.qcloud.network.sonar.SonarType;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/* JADX INFO: loaded from: classes4.dex */
public class HttpSonar implements Sonar<HttpResult> {
    public boolean bypassProxy;

    @Override // com.tencent.qcloud.network.sonar.Sonar
    public void stop() {
    }

    public HttpSonar(boolean z) {
        this.bypassProxy = z;
    }

    @Override // com.tencent.qcloud.network.sonar.Sonar
    public SonarResult<HttpResult> start(SonarRequest sonarRequest) {
        HttpURLConnection httpURLConnection;
        if (!sonarRequest.isNetworkAvailable()) {
            return new SonarResult<>(SonarType.HTTP, new Exception(Sonar.ERROR_MSG_NO_NETWORK));
        }
        if (TextUtils.isEmpty(sonarRequest.getHost())) {
            return new SonarResult<>(SonarType.HTTP, new Exception(Sonar.ERROR_MSG_HOST_IS_EMPTY));
        }
        HttpResult httpResult = new HttpResult();
        httpResult.domain = sonarRequest.getHost();
        httpResult.bypassProxy = this.bypassProxy;
        HttpURLConnection httpURLConnection2 = null;
        try {
            try {
                URL url = new URL("https://" + sonarRequest.getHost());
                httpResult.url = url.toString();
                if (this.bypassProxy) {
                    httpURLConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);
                } else {
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                }
                httpURLConnection2 = httpURLConnection;
                httpURLConnection2.setConnectTimeout(WebSocketHandler.ACK_TIMEOUT_FOR_AUDIO_SAVE);
                httpURLConnection2.setReadTimeout(WebSocketHandler.ACK_TIMEOUT_FOR_AUDIO_SAVE);
                httpURLConnection2.setRequestMethod("HEAD");
                long jCurrentTimeMillis = System.currentTimeMillis();
                httpURLConnection2.connect();
                int responseCode = httpURLConnection2.getResponseCode();
                long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                httpResult.responseCode = responseCode;
                httpResult.timeConsuming = jCurrentTimeMillis2;
                httpResult.responseHeaders = httpURLConnection2.getHeaderFields();
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                return new SonarResult<>(SonarType.HTTP, httpResult);
            } catch (Exception e) {
                SonarResult<HttpResult> sonarResult = new SonarResult<>(SonarType.HTTP, e);
                if (httpURLConnection2 != null) {
                    httpURLConnection2.disconnect();
                }
                return sonarResult;
            }
        } catch (Throwable th) {
            if (httpURLConnection2 != null) {
                httpURLConnection2.disconnect();
            }
            throw th;
        }
    }
}
