package com.tencent.beacon.base.net.call;

import android.text.TextUtils;
import com.tencent.beacon.base.net.BodyType;
import com.tencent.beacon.base.net.HttpMethod;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes4.dex */
public class HttpRequestEntity {
    private BodyType bodyType;
    private byte[] content;
    private final Map<String, String> formParams;
    private final Map<String, String> headers;
    private String json;
    private final HttpMethod method;
    private final String tag;
    private final String url;

    /* synthetic */ HttpRequestEntity(HttpMethod httpMethod, String str, Map map, BodyType bodyType, String str2, Map map2, byte[] bArr, String str3, C2677d c2677d) {
        this(httpMethod, str, map, bodyType, str2, map2, bArr, str3);
    }

    public static C2672a builder() {
        return new C2672a();
    }

    public BodyType bodyType() {
        return this.bodyType;
    }

    public byte[] content() {
        return this.content;
    }

    public Map<String, String> formParams() {
        return this.formParams;
    }

    public Map<String, String> headers() {
        return this.headers;
    }

    public String json() {
        return this.json;
    }

    public HttpMethod method() {
        return this.method;
    }

    public String tag() {
        return this.tag;
    }

    public String toString() {
        return "HttpRequestEntity{url='" + this.url + "', method=" + this.method + ", headers=" + this.headers + ", formParams=" + this.formParams + ", bodyType=" + this.bodyType + ", json='" + this.json + "', tag='" + this.tag + "'}";
    }

    public String url() {
        return this.url;
    }

    private HttpRequestEntity(HttpMethod httpMethod, String str, Map<String, String> map, BodyType bodyType, String str2, Map<String, String> map2, byte[] bArr, String str3) {
        this.method = httpMethod;
        this.url = str;
        this.headers = map;
        this.bodyType = bodyType;
        this.json = str2;
        this.formParams = map2;
        this.content = bArr;
        this.tag = str3;
    }

    /* JADX INFO: renamed from: com.tencent.beacon.base.net.call.HttpRequestEntity$a */
    public static class C2672a {

        /* JADX INFO: renamed from: a */
        private HttpMethod f1332a;

        /* JADX INFO: renamed from: b */
        private String f1333b;

        /* JADX INFO: renamed from: c */
        private String f1334c;

        /* JADX INFO: renamed from: d */
        private Map<String, String> f1335d = new HashMap(3);

        /* JADX INFO: renamed from: e */
        private Map<String, String> f1336e = new HashMap(3);

        /* JADX INFO: renamed from: f */
        private String f1337f;

        /* JADX INFO: renamed from: g */
        private BodyType f1338g;

        /* JADX INFO: renamed from: h */
        private byte[] f1339h;

        /* JADX INFO: renamed from: a */
        private void m1366a(BodyType bodyType) {
            if (this.f1338g == null) {
                this.f1338g = bodyType;
            }
            if (this.f1338g != bodyType) {
                throw new IllegalStateException("bodyType already set!");
            }
        }

        /* JADX INFO: renamed from: b */
        public C2672a m1371b(String str) {
            this.f1333b = str;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2672a m1367a(HttpMethod httpMethod) {
            this.f1332a = httpMethod;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2672a m1369a(Map<String, String> map) {
            m1366a(BodyType.FORM);
            this.f1335d.putAll(map);
            return this;
        }

        /* JADX INFO: renamed from: a */
        public C2672a m1368a(String str) {
            this.f1334c = str;
            return this;
        }

        /* JADX INFO: renamed from: a */
        public HttpRequestEntity m1370a() {
            if (this.f1332a != null) {
                if (!TextUtils.isEmpty(this.f1333b)) {
                    BodyType bodyType = this.f1338g;
                    if (bodyType != null) {
                        int i = C2677d.f1354a[bodyType.ordinal()];
                        if (i != 1) {
                            if (i != 2) {
                                if (i == 3 && this.f1339h == null) {
                                    throw new NullPointerException("data request body == null");
                                }
                            } else if (this.f1335d.isEmpty()) {
                                throw new NullPointerException("form request body == null");
                            }
                        } else if (TextUtils.isEmpty(this.f1337f)) {
                            throw new NullPointerException("json request body == null");
                        }
                        return new HttpRequestEntity(this.f1332a, this.f1333b, this.f1336e, this.f1338g, this.f1337f, this.f1335d, this.f1339h, this.f1334c, null);
                    }
                    throw new NullPointerException("bodyType == null");
                }
                throw new NullPointerException("request url == null!");
            }
            throw new NullPointerException("request method == null");
        }
    }
}
