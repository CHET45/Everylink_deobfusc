package com.tencent.beacon.base.net.adapter;

import com.tencent.beacon.base.net.BResponse;
import com.tencent.beacon.base.net.C2684d;
import com.tencent.beacon.base.net.call.Callback;
import com.tencent.beacon.base.net.call.HttpRequestEntity;
import com.tencent.beacon.base.net.call.JceRequestEntity;
import com.tencent.beacon.base.net.p021b.C2669d;
import com.tencent.beacon.base.util.C2694b;
import com.tencent.beacon.base.util.C2695c;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/* JADX INFO: renamed from: com.tencent.beacon.base.net.adapter.b */
/* JADX INFO: compiled from: HttpAdapter.java */
/* JADX INFO: loaded from: classes4.dex */
public class C2646b extends AbstractNetAdapter {
    /* JADX INFO: renamed from: a */
    private BResponse m1170a(String str, String str2, Map<String, String> map, byte[] bArr) throws Throwable {
        HttpURLConnection httpURLConnectionM1171a = m1171a(str, str2, map);
        httpURLConnectionM1171a.connect();
        OutputStream outputStream = httpURLConnectionM1171a.getOutputStream();
        if (outputStream != null && bArr != null) {
            outputStream.write(bArr);
            outputStream.close();
        }
        return new BResponse(httpURLConnectionM1171a.getHeaderFields(), httpURLConnectionM1171a.getResponseCode(), httpURLConnectionM1171a.getResponseMessage(), m1172a(httpURLConnectionM1171a.getInputStream()));
    }

    private byte[] buildBody(HttpRequestEntity httpRequestEntity) throws UnsupportedEncodingException {
        int i = C2645a.f1126a[httpRequestEntity.bodyType().ordinal()];
        if (i == 1) {
            return httpRequestEntity.content();
        }
        if (i == 2) {
            return C2669d.m1339b(httpRequestEntity.formParams()).getBytes("UTF-8");
        }
        if (i != 3) {
            return null;
        }
        return httpRequestEntity.json().getBytes("UTF-8");
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(JceRequestEntity jceRequestEntity, Callback<byte[]> callback) {
        String strName = jceRequestEntity.getType().name();
        try {
            byte[] content = jceRequestEntity.getContent();
            if (content != null && content.length >= 65535) {
                callback.onFailure(new C2684d(jceRequestEntity.getType().toString(), "406", -1, "content length is too long:" + content.length));
                return;
            }
            BResponse bResponseM1170a = m1170a(jceRequestEntity.getUrl(), "POST", jceRequestEntity.getHeader(), content);
            if (bResponseM1170a.code != 200) {
                callback.onFailure(new C2684d(strName, "452", bResponseM1170a.code, "response status code != 2XX. msg: " + bResponseM1170a.msg));
            } else {
                C2669d.m1336a(bResponseM1170a.headers);
                callback.onResponse(bResponseM1170a.body);
            }
        } catch (ConnectException e) {
            C2695c.m1465a(e);
            callback.onFailure(new C2684d(strName, "451", -1, "https connect timeout: " + e.getMessage(), e));
        } catch (Throwable th) {
            C2695c.m1465a(th);
            callback.onFailure(new C2684d(strName, "499", -1, "https connect error: " + th.getMessage(), th));
        }
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0045: MOVE (r7 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:22:0x0045 */
    /* JADX INFO: renamed from: a */
    private byte[] m1172a(InputStream inputStream) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        IOException e;
        Closeable closeable;
        Closeable closeable2 = null;
        try {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
            } catch (IOException e2) {
                byteArrayOutputStream = null;
                e = e2;
            } catch (Throwable th) {
                th = th;
                C2694b.m1438a(inputStream, closeable2);
                throw th;
            }
            try {
                byte[] bArr = new byte[2048];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i != -1) {
                        byteArrayOutputStream.write(bArr, 0, i);
                    } else {
                        byteArrayOutputStream.flush();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        inputStream.close();
                        C2694b.m1438a(inputStream, byteArrayOutputStream);
                        return byteArray;
                    }
                }
            } catch (IOException e3) {
                e = e3;
                C2695c.m1465a(e);
                C2694b.m1438a(inputStream, byteArrayOutputStream);
                throw e;
            }
        } catch (Throwable th2) {
            th = th2;
            closeable2 = closeable;
            C2694b.m1438a(inputStream, closeable2);
            throw th;
        }
    }

    @Override // com.tencent.beacon.base.net.adapter.AbstractNetAdapter
    public void request(HttpRequestEntity httpRequestEntity, Callback<BResponse> callback) {
        String strTag = httpRequestEntity.tag();
        try {
            BResponse bResponseM1170a = m1170a(httpRequestEntity.url(), httpRequestEntity.method().name(), httpRequestEntity.headers(), buildBody(httpRequestEntity));
            if (bResponseM1170a.code != 200) {
                callback.onFailure(new C2684d(strTag, "452", bResponseM1170a.code, "response status code != 2XX. msg: " + bResponseM1170a.msg));
            } else {
                callback.onResponse(bResponseM1170a);
            }
        } catch (ConnectException e) {
            C2695c.m1465a(e);
            callback.onFailure(new C2684d(strTag, "451", -1, "https connect timeout: " + e.getMessage(), e));
        } catch (Throwable th) {
            C2695c.m1465a(th);
            callback.onFailure(new C2684d(strTag, "499", -1, "https connect error: " + th.getMessage(), th));
        }
    }

    /* JADX INFO: renamed from: a */
    private HttpURLConnection m1171a(String str, String str2, Map<String, String> map) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setConnectTimeout(30000);
        httpURLConnection.setReadTimeout(10000);
        httpURLConnection.setRequestMethod(str2);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setInstanceFollowRedirects(false);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
        }
        return httpURLConnection;
    }
}
